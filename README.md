# Coupons Management API – Monk Commerce Backend Task

## 📖 Overview

This project is a RESTful API built using Spring Boot for managing and applying different types of discount coupons in an e-commerce platform.

Supported coupon types:
- Cart-wise coupons
- Product-wise coupons
- BXGY (Buy X Get Y)

The system is designed to be extensible so that new coupon types can be added easily without modifying existing business logic.

---

## 🛠 Tech Stack

- Java 21
- Spring Boot 3.2.5
- Spring Data JPA
- MySQL
- Maven
- Jackson (JSON mapping)

---

## 🗂 Architecture & Design

The system follows:

- Controller → Service → Repository structure
- Strategy Pattern for coupon rules
- Each coupon type has its own rule class implementing a common interface:
  
  ```java
  public interface CouponEligibility {
      boolean isApplicable(CartRequest cart, Coupon coupon);
      double calculateDiscount(CartRequest cart, Coupon coupon);
  }
***************************************************************** Here are the Other important details for this task here are the limitations ********************************

📦 Database Schema
Coupon Table
Column	Type
coupon_id	bigint (PK)
active	bit(1)
coupon_details	text (JSON)
coupon_expire_date	datetime
type	enum('BXGY','CART_WISE','PRODUCT_WISE')

Coupon rules are stored in JSON format inside coupon_details.

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

Request:

{
  "cart": {
    "items": [
      {"product_id": 1, "quantity": 6, "price": 50},
      {"product_id": 2, "quantity": 3, "price": 30},
      {"product_id": 3, "quantity": 2, "price": 25}
    ]
  }
}

Response:

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

Applies discount if cart total exceeds threshold.

Supports percentage discount.

Example:

Threshold = 300

Discount = 10%

Cart Total = 400

Discount = 40

🔹 Product-wise Coupon

Applies discount on specific product.

Condition: product exists in cart with minimum quantity.

Example:

Product ID = 1

Min Quantity = 3

Discount = 20%

🔹 BXGY Coupon

Buy products from “buy array”

Get products from “get array”

Supports repetition limit

Calculates discount based on free product price

Example:

Buy 3 of Product X or Y

Get 1 of Product Z free

Repetition limit = 2

🧠 Edge Cases Considered

Coupon inactive

Coupon expired

Threshold not met

Product not in cart

Quantity insufficient

Repetition limit exceeded

Multiple coupons applicable simultaneously

Invalid JSON in coupon_details

❌ Unimplemented / Partially Implemented Cases

Due to time constraints:

Stacking priority rules between coupons

Maximum discount cap

Flat discount (only percentage supported)

Coupon usage limit per user

Coupon usage tracking in DB

Concurrency handling

Distributed locking

Multi-currency support

Tax calculations before/after discount

Partial item discount splitting logic

⚠️ Assumptions

Cart prices are final (tax included).

No user-specific coupons.

Coupons are global.

Only percentage discounts implemented.

Coupon stacking allowed (no exclusivity logic).

No authentication required.

No coupon usage history stored.

🚧 Limitations

Coupon details stored as JSON → no DB-level validation.

No index optimization for high scale.

No caching layer (Redis not implemented).

No rate limiting.

No Swagger documentation.

No pagination on GET /coupons.

No transaction rollback logic for apply-coupon.

No unit tests (can be added as enhancement).

🔮 Future Improvements

Add coupon priority & exclusivity rules

Add usage limit per user

Add Redis caching

Add Swagger (OpenAPI)

Add unit + integration tests

Add Docker support

Add authentication & role-based coupon creation

Add performance optimization for large carts

Add rule engine abstraction layer

🧪 Bonus Features

Expiration date support

Strategy pattern for extensibility

Clean separation of business rules

MySQL integration

JSON mapping with Jackson

🏁 How to Run

Clone repository

Configure MySQL in application.properties

Run:

mvn clean install
mvn spring-boot:run

Application runs at:

http://localhost:8080
👨‍💻 Author

Aditya Chauhan
Backend Developer – Java & Spring Boot


---

# 🔥 This README Covers

✔ Implemented cases  
✔ Unimplemented cases  
✔ Limitations  
✔ Assumptions  
✔ Extensibility design  
✔ API structure  
✔ Database schema  
✔ Edge cases  

This matches exactly what the task PDF expects :contentReference[oaicite:1]{index=1}.

---

If you want, I can now:

- 🔥 Upgrade this to **“interview-winning professional README”**
- 📊 Add architecture diagram section
- 🧪 Add unit test explanation
- 🐳 Add Docker section
- 📦 Generate a polished GitHub project description

  
