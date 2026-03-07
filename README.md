Coupons Management API – Monk Commerce Backend Task
📖 Overview

This project is a RESTful API built using Spring Boot for managing and applying different types of discount coupons in an e-commerce platform.

The system allows creating coupons and dynamically applying them to shopping carts based on defined rules.

Supported Coupon Types

Cart-wise coupons

Product-wise coupons

BXGY (Buy X Get Y)

The system is designed to be extensible, allowing new coupon types to be added without modifying existing business logic.

🛠 Tech Stack

Java 21

Spring Boot 3.2.5

Spring Data JPA

MySQL

Maven

Jackson (JSON Mapping)

🗂 Architecture & Design

The application follows a layered architecture:

Controller → Service → Repository
Strategy Pattern

Coupon rule evaluation uses the Strategy Pattern so each coupon type can define its own logic.

Example interface:

public interface CouponEligibility {
    boolean isApplicable(CartRequest cart, Coupon coupon);
    double calculateDiscount(CartRequest cart, Coupon coupon);
}

Each coupon type implements this interface:

CartWiseStrategy

ProductWiseStrategy

BXGYStrategy

This ensures clean separation of business rules and extensibility.

📦 Database Schema
Coupon Table
Column	Type
coupon_id	bigint (Primary Key)
active	bit(1)
coupon_details	text (JSON)
coupon_expire_date	datetime
type	enum('BXGY','CART_WISE','PRODUCT_WISE')

Coupon rules are stored in JSON format inside coupon_details.

Example:

{
  "threshold": 500,
  "discountPercentage": 15
}
🚀 API Endpoints
1️⃣ Create Coupon
POST /coupons
2️⃣ Get All Coupons
GET /coupons
3️⃣ Get Coupon by ID
GET /coupons/{id}
4️⃣ Update Coupon
PUT /coupons/{id}
5️⃣ Delete Coupon
DELETE /coupons/{id}
6️⃣ Get Applicable Coupons
POST /coupons/applicable-coupons
Request
{
  "cart": {
    "items": [
      {"product_id": 1, "quantity": 6, "price": 50},
      {"product_id": 2, "quantity": 3, "price": 30},
      {"product_id": 3, "quantity": 2, "price": 25}
    ]
  }
}
Response
{
  "applicable_coupons": [
    {
      "coupon_id": 1,
      "type": "cart-wise",
      "discount": 40
    },
    {
      "coupon_id": 3,
      "type": "bxgy",
      "discount": 50
    }
  ]
}
✅ Implemented Cases
🔹 Cart-wise Coupon

Applies discount if cart total exceeds a threshold.

Example:

Threshold = 300
Discount = 10%
Cart Total = 400
Discount = 40
🔹 Product-wise Coupon

Applies discount on a specific product.

Condition:

Product exists in cart

Minimum quantity requirement met

Example:

Product ID = 1
Min Quantity = 3
Discount = 20%
🔹 BXGY Coupon

Buy certain products and receive others for free.

Example:

Buy 3 of Product X or Y
Get 1 of Product Z free
Repetition limit = 2

Discount is calculated based on the price of the free product.

🧠 Edge Cases Considered

Coupon inactive

Coupon expired

Threshold not met

Product not present in cart

Quantity insufficient

Repetition limit exceeded

Multiple coupons applicable simultaneously

Invalid JSON in coupon_details

❌ Unimplemented / Partially Implemented Cases

Due to time constraints, the following advanced features were not implemented:

Coupon stacking priority rules

Maximum discount cap

Flat discount (only percentage supported)

Coupon usage limit per user

Coupon usage tracking

Concurrency handling

Distributed locking

Multi-currency support

Tax calculations before/after discount

Partial item discount splitting logic

⚠️ Assumptions

Cart prices are final (tax included)

No user-specific coupons

Coupons are global

Only percentage discounts implemented

Coupon stacking allowed (no exclusivity logic)

No authentication required

No coupon usage history stored

🚧 Limitations

Coupon rules stored as JSON → no database validation

No index optimization for large-scale usage

No caching layer (Redis not implemented)

No rate limiting

No pagination on GET /coupons

No transaction rollback logic for apply-coupon

No unit tests

🔮 Future Improvements could be

Add coupon priority and exclusivity rules

Add coupon usage limits per user

Add Redis caching

Add unit and integration tests

Add Docker support

Add authentication and role-based coupon management

Add performance optimization for large carts

Introduce rule engine abstraction

🧪 Bonus Features

Expiration date support

Strategy pattern for extensibility

Clean separation of business rules

MySQL persistence

JSON mapping using Jackson

🏁 How to Run
1️⃣ Clone Repository
git clone https://github.com/adityachauhan564/Task_Monk_Commerce.git
2️⃣ Configure Database

Update application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/monk_commerce
spring.datasource.username=root
spring.datasource.password=your_password
3️⃣ Run Application
mvn clean install
mvn spring-boot:run

Server runs at:

http://localhost:8080
👨‍💻 Author

Aditya Chauhan
Backend Developer – Java & Spring Boot

GitHub:
https://github.com/adityachauhan564
