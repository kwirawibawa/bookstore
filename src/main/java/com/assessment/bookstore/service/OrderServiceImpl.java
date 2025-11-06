package com.assessment.bookstore.service;

import com.assessment.bookstore.exception.NotEnoughStockException;
import com.assessment.bookstore.exception.OrderPaidException;
import com.assessment.bookstore.exception.PaymentFailedException;
import com.assessment.bookstore.exception.UnauthorizedActionException;
import com.assessment.bookstore.model.entity.Book;
import com.assessment.bookstore.model.entity.Order;
import com.assessment.bookstore.model.entity.OrderItem;
import com.assessment.bookstore.model.entity.User;
import com.assessment.bookstore.model.request.CreateOrderRequest;
import com.assessment.bookstore.model.request.OrderItemRequest;
import com.assessment.bookstore.model.response.OrderItemResponse;
import com.assessment.bookstore.model.response.OrderResponse;
import com.assessment.bookstore.model.response.SearchResponse;
import com.assessment.bookstore.repository.BookRepository;
import com.assessment.bookstore.repository.OrderRepository;
import com.assessment.bookstore.repository.UserRepository;
import com.assessment.bookstore.util.CommonConstant;
import com.assessment.bookstore.util.CommonPaging;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    @Transactional
    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        User user = getCurrentUser();

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            Book book = bookRepository.findById(itemReq.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found: " + itemReq.getBookId()));

            if (itemReq.getQuantity() > book.getStock()) {
                throw new NotEnoughStockException("Not enough stock for " + book.getTitle());
            }

            BigDecimal itemPrice = book.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            OrderItem orderItem = OrderItem.builder()
                    .book(book)
                    .quantity(itemReq.getQuantity())
                    .price(itemPrice)
                    .build();

            orderItems.add(orderItem);
            totalPrice = totalPrice.add(itemPrice);
        }

        for (OrderItemRequest itemReq : request.getItems()) {
            Book book = bookRepository.findById(itemReq.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found: " + itemReq.getBookId()));

            if (itemReq.getQuantity() > book.getStock()) {
                throw new NotEnoughStockException("Not enough stock for " + book.getTitle());
            }

            book.setStock(book.getStock() - itemReq.getQuantity());
            bookRepository.save(book);
        }

        Order order = Order.builder()
                .user(user)
                .status(Order.Status.PENDING)
                .totalPrice(totalPrice)
                .build();
        order.setCreatedAt(LocalDateTime.now());

        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }
        order.setItems(orderItems);
        order = orderRepository.save(order);

        return mapToResponse(order);
    }

    @Transactional
    @Override
    public OrderResponse payOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        User currentUser = getCurrentUser();

        if (!order.getUser().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(User.Role.ADMIN)) {
            throw new UnauthorizedActionException("You are not allowed to pay this order");
        }

        if (order.getStatus() == Order.Status.PAID) {
            throw new OrderPaidException("Order already paid");
        }

        boolean paymentSuccess = paymentService.simulatePayment(order);

        if (!paymentSuccess) {
            throw new PaymentFailedException("Payment failed for order " + orderId);
        }

        order.setStatus(Order.Status.PAID);
        order.setPaidAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponse getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));

        User currentUser = getCurrentUser();

        if (!order.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().equals(User.Role.ADMIN)) {
            throw new UnauthorizedActionException("You are not allowed to access this order");
        }

        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    @Override
    public SearchResponse<OrderResponse> getAllOrders(int pageNumber, int pageSize) {
        User currentUser = getCurrentUser();
        boolean isAdmin = currentUser.getRole().equals(User.Role.ADMIN);

        long totalData = orderRepository.countOrders(currentUser.getId(), isAdmin);
        List<Order> orders = orderRepository.findOrders(currentUser.getId(), isAdmin, pageNumber, pageSize);

        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            orderResponses.add(mapToResponse(order));
        }

        CommonPaging paging = new CommonPaging(pageNumber, pageSize, totalData);

        return new SearchResponse<>(
                pageNumber,
                pageSize,
                paging.getNumberOfElements(),
                totalData,
                (long) Math.ceil((double) totalData / pageSize),
                paging.getStartData(),
                paging.getEndData(),
                orderResponses,
                CommonConstant.SUCCESS_CODE,
                "Orders retrieved successfully"
        );
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(i -> OrderItemResponse.builder()
                        .id(i.getId())
                        .bookId(i.getBook().getId())
                        .title(i.getBook().getTitle())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .build())
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().name())
                .items(itemResponses)
                .createdAt(order.getCreatedAt())
                .paidAt(order.getPaidAt())
                .build();
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
