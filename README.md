# Online Bookstore REST API - Spring Boot 📚

A comprehensive Spring Boot application demonstrating RESTful services for an online bookstore system.

## Features 🌟

- ✨ Complete CRUD operations for books and customers
- 🔐 JWT-based authentication
- ⚠️ Exception handling
- 🔄 DTO pattern implementation
- 🔗 HATEOAS support
- 📝 Content negotiation
- 📚 API documentation with Swagger
- 🧪 Comprehensive testing suite
- 📊 Monitoring with Spring Boot Actuator

## Project Setup ⚙️

### Prerequisites
- ☕ Java 17 or higher
- 🔧 Maven/Gradle
- 💻 Your favorite IDE

### Dependencies
- 🌱 Spring Boot 3.x
- 🕸️ Spring Web
- 🛠️ Spring Boot DevTools
- 🏗️ Lombok
- 🔒 Spring Security
- 🔗 Spring HATEOAS
- 📝 SpringDoc/Swagger
- 🧪 JUnit & Mockito
- 📊 Spring Boot Actuator

## API Endpoints 🛣️

### Books 📚
- `GET /books` - Get all books
- `GET /books/{id}` - Get book by ID
- `POST /books` - Create new book
- `PUT /books/{id}` - Update existing book
- `DELETE /books/{id}` - Delete book
- Query parameters supported for filtering by title and author

### Customers 👥
- `POST /customers` - Register new customer
- `GET /customers/{id}` - Get customer details
- Form data and JSON request body supported for registration

## Key Components 🔑

### Security 🔒
- JWT-based authentication
- CORS configuration
- Role-based authorization

### Data Transfer 🔄
- BookDTO and CustomerDTO implementations
- MapStruct/ModelMapper integration
- Custom serialization/deserialization with Jackson

### Exception Handling ⚠️
- Global exception handler
- Custom error responses
- Proper HTTP status codes

### Validation ✅
- Input validation using annotations
- Optimistic locking for concurrent updates

### Monitoring 📊
- Spring Boot Actuator integration
- Custom metrics
- Health checks

### Documentation 📝
- Swagger/SpringDoc integration
- API documentation UI (available at `/swagger-ui.html`)
- Detailed endpoint documentation

## Project Structure 🏗️
```
src/
├── main/
│   ├── java/
│   │   └── com/bookstore/
│   │       ├── controller/
│   │       ├── model/
│   │       ├── dto/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── security/
│   │       ├── exception/
│   │       └── config/
│   └── resources/
│       └── application.properties
```

## Content Negotiation 🔄
- Supports JSON and XML formats
- Content type determined by Accept header
- Flexible response formatting

## HATEOAS Implementation 🔗
- Hypermedia-driven API
- Resource linking
- Navigation through API endpoints

## Monitoring 📈

Access actuator endpoints at:
- `/actuator/health` - System health
- `/actuator/metrics` - Application metrics
- `/actuator/info` - Application info
