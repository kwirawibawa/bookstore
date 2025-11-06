# Bookstore
📚 Online Bookstore

A backend system for managing an online bookstore, built with Java 21, Spring Boot 3.x, and PostgreSQL. This application allows user registration, book & category management, order transactions with multi-item support, simulated payment, and admin reporting.

## 📚 Table of Contents
- [Features](#features)
- [Technical Stack](#technical-stack)
- [Architecture Overview](#architecture-overview)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)

---

## ✨ Features
* **Authentication & Authorization:** JWT-based login/registration, role-based access (user & admin)
* **Category Management:** CRUD categories (admin only)
* **Book Management:** CRUD books (admin only)
* **Order Management:** Create multi-item orders, validate stock, calculate total price, simulate payment
* **Reporting (Admin Only):**

  * Total revenue & total books sold
  * Top 3 best-selling books
  * Book price statistics (max, min, avg)
* **Pagination & Filtering:** List books with search and category filter
* **RESTful API:** Standardized JSON responses
* **Transactional Integrity:** Stock updates & order payments handled atomically

---

## 🧰 Technical Stack
- **Language:** Java 21  
- **Framework:** Spring Boot 3.x  
- **Database:** PostgreSQL 
- **Build Tool:** Maven  
- **Version Control:** Git  
- **Libraries:**
  - Lombok
  - Spring Data JPA
  - Jakarta Validation
  - SLF4J Logging
  - Spring Security (JWT)
 
---

## 🧱 Architecture Overview

### 📊 High-Level Design
```
        +----------------------------+
        |        Client (API)        |
        +-------------+--------------+
                      |
                      v
          +-----------+-----------+
          |       Controller      |   <-- REST layer (HTTP endpoints)
          +-----------+-----------+
                      |
                      v
          +-----------+-----------+
          |        Service         |   <-- Business logic (validation, transaction)
          +-----------+-----------+
                      |
                      v
          +-----------+-----------+
          |       Repository       |   <-- Data access via Spring Data JPA
          +-----------+-----------+
                      |
                      v
          +-----------+-----------+
          |         Database       |   <-- PostgreSQL
          +------------------------+
```
---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3.x
- Git
- PostgreSQL (make sure it is installed and running)

### Database setup
* Create your own database, e.g., `bookstore_db`
* Configure your `application.properties` or `application.yml` with your PostgreSQL credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
* **Note:** All tables will be automatically created when the application runs using the schema provided in `resources/schema.sql`.

### Steps
```bash
# Clone the repository
git clone https://github.com/kwirawibawa/bookstore.git
cd bookstore

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### Access Points
- API Base URL → `http://localhost:8080`
- JWT Token sent via header → `Authorization: Bearer <token>`
  
---

## 🔌 API Endpoints

### **Auth**

* `POST /admin/create-admin` → Create the first admin account
* `POST /register` → Register a new user
* `POST /login` → Login and obtain a JWT token

### **Categories (Admin Only)**

* `POST /categories` → Create a new category
* `GET /categories` → Get all categories
* `PUT /categories/{id}` → Update a category
* `DELETE /categories/{id}` → Delete a category

### **Books**

* `POST /books` → Create a new book (Admin only, includes Base64 image upload)
* `GET /books` → Get list of books (supports pagination, search, and category filtering)
* `GET /books/{id}` → Get book details by ID
* `PUT /books/{id}` → Update book data (Admin only)
* `DELETE /books/{id}` → Delete a book (Admin only)

### **Orders & Payment**

* `POST /orders` → Create an order with multiple items
* `POST /orders/{id}/pay` → Simulate payment for an order
* `GET /orders` → Get order list

  * User: only their own orders
  * Admin: all orders
* `GET /orders/{id}` → Get order details

  * User: only their own
  * Admin: all orders

### **Reporting (Admin Only)**

* `GET /reports/sales` → Get total revenue and total books sold
* `GET /reports/bestseller` → Get top 3 best-selling books
* `GET /reports/prices` → Get book price statistics (max, min, average)

---

**👨‍💻 Author:** Kresna Wirawibawa  

