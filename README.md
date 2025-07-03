# 🍓 Fruits API — Spring Boot RESTful Service

A clean and well-layered REST API built with **Spring Boot 3** for managing fruits. This project demonstrates strong principles of clean architecture, validation, error handling, and automated testing using Spring’s ecosystem.

---

## 📁 Project Structure

```
src/
├── main/
│   └── java/
│       └── cat.itacademy.s04.t02.n01/
│           ├── controllers/        # REST controllers (FruitController)
│           ├── dto/                # DTOs for input/output (FruitDTO, FruitResponseDTO)
│           ├── exception/          # Global exception handling and custom exceptions
│           ├── model/              # Domain models (Fruit)
│           ├── repository/         # Spring Data JPA repositories (FruitRepository)
│           └── service/            # Business logic layer (FruitService)
├── test/
│   └── java/
│       └── cat.itacademy.s04.t02.n01/
│           ├── FruitControllerTest.java
│           ├── FruitRepositoryTest.java
│           └── GlobalExceptionHandlerTest.java
```

---

## ✅ Features

- 🔄 CRUD endpoints for fruit entities
- 🧼 Validation using Jakarta Bean Validation
- 🧱 Separation of concerns via layered architecture
- 🚨 Centralized global error handling (`@RestControllerAdvice`)
- 🧪 Unit and integration tests (controller, repository, exceptions)
- 💾 In-memory H2 database for quick testing

---

## 🚀 Getting Started

### Requirements

- Java 17+
- Maven

### Run the app

```bash
./mvnw spring-boot:run
```

Default URL: `http://localhost:8080`
In this project: `http://localhost:9002`

---

## 🔌 API Endpoints

### ➕ Create Fruit

```http
POST /fruit
Content-Type: application/json

{
  "name": "Apple",
  "quantityKG": 5
}
```

Response:
```json
{
  "id": 1,
  "name": "Apple",
  "quantityKG": 5
}
```

### 📋 Get All Fruits

```http
GET /fruit
```

### 🔍 Get Fruit by ID

```http
GET /fruit/{id}
```

### ❌ Delete Fruit

```http
DELETE /fruit/{id}
```

---

## ⚠️ Validations

| Field       | Constraint                |
|-------------|---------------------------|
| `name`      | `@NotBlank`               |
| `quantityKG`| `@NotNull`, `@Min(1)`     |

---

## 🔥 Error Handling

The application provides centralized error handling using `@RestControllerAdvice` and custom exceptions.

### Common Error Responses

#### 🔴 Resource Not Found (404)

Occurs when a requested resource (e.g., fruit by ID) does not exist.

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Fruit not found with id: 99"
}
```

#### 🟠 Validation Error (400)

Triggered when a request body fails validation constraints defined in the DTO.

Example: Sending empty `name` or negative `quantityKG`.

```json
{
  "status": 400,
  "error": "Validation Error",
  "message": "Name is required"
}
```

Another example with quantity:

```json
{
  "status": 400,
  "error": "Validation Error",
  "message": "Quantity must be at least 1 KG"
}
```

#### 🔴 Invalid JSON (400 Bad Request)

Occurs when the body sent to the endpoint is malformed or not valid JSON.

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Malformed JSON request"
}
```

#### 🔴 Unsupported Media Type (415)

When the content type is not set to `application/json`.

```json
{
  "status": 415,
  "error": "Unsupported Media Type",
  "message": "Content type 'text/plain' not supported"
}
```

#### 🔴 Method Not Allowed (405)

When the HTTP method is not allowed for the requested endpoint.

```json
{
  "status": 405,
  "error": "Method Not Allowed",
  "message": "Request method 'PUT' not supported"
}
```

---

## 🧪 Testing

### Controller Layer

- ✅ `FruitControllerTest.java`: Ensures endpoint behavior and input validation.

### Repository Layer

- ✅ `FruitRepositoryTest.java`: Validates persistence logic using Spring Data JPA and H2.

### Exception Handling

- ✅ `GlobalExceptionHandlerTest.java`: Verifies custom exception responses.

To run tests:

```bash
./mvnw test
```

---

## 🧠 Key Concepts

- **DTO Pattern**: Isolates exposed data from the internal model
- **Validation**: Avoids bad data entering the system
- **Layered Architecture**: Follows Controller → Service → Repository pattern
- **Custom Exception**: `ResourceNotFoundException` for semantic 404 responses
- **Spring Boot Testing**: `@WebMvcTest`, `@DataJpaTest`, and `MockMvc`

---

## 📌 Author

Developed by **Alejandro Redondo Charro**  
📚 For educational purposes and Spring Boot practice.

