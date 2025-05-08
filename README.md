# Employee-Department-Manager

# 👥 Personnel Management Service

[![CI](https://github.com/your-username/personnel-management-service/actions/workflows/ci.yml/badge.svg)](https://github.com/your-username/personnel-management-service/actions)

A Spring Boot microservice for managing employees, departments, and managers with JWT-based security, PostgreSQL, Kafka event streaming, and observability support.

---

## 🏗️ Architecture Decisions

| Layer              | Tech Stack                                 |
|-------------------|---------------------------------------------|
| Framework         | Spring Boot                                 |
| Security          | Spring Security + JWT                       |
| Database          | PostgreSQL via Spring Data JPA              |
| Messaging         | Kafka (wurstmeister)                        |
| Observability     | Micrometer, Prometheus                      |
| Migrations        | Flyway                                      |
| Testing           | JUnit 5, Mockito, Testcontainers            |
| Documentation     | OpenAPI (Swagger)                           |
| Deployment        | Docker Compose                              |

- **Domain Events** are published to the Kafka topic `hr.domain-events`.
- **SSE Endpoint** planned for consuming domain events downstream.
- **Entity design** supports many-to-many relationships between Employees and Departments via `EmployeeDepartment`.

---

## ▶️ Local Setup

### 1. Clone the Repository

```bash
git clone https://github.com/AshaBPatil/Employee-Department-Manager.git



# 1. Clone the Repository
git clone https://github.com/your-username/personnel-management-service.git
cd personnel-management-service

# 2. Start PostgreSQL and Kafka with Docker Compose
docker-compose up -d

# 3. Run the Spring Boot Application
./mvnw spring-boot:run
# Or run via your IDE: PersonnelManagementServiceApplication.java

# 4. Access Swagger UI
# Open in browser:
# http://localhost:8080/swagger-ui.html

# 5. Get JWT Token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin", "password":"admin123"}'

# 6. Create an Employee (Replace <your_token> with real token)
curl -X POST http://localhost:8080/api/employees \
  -H "Authorization: Bearer <your_token>" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Alice",
        "lastName": "Smith",
        "email": "alice.smith@example.com",
        "departmentIds": [1, 2]
      }'

# 7. Get All Employees
curl -X GET http://localhost:8080/api/employees \
  -H "Authorization: Bearer <your_token>"

# 8. Run All Tests
./mvnw test

# 9. Run Full Build with Coverage
./mvnw verify
# Then open: target/site/jacoco/index.html in your browser

# 10. View Kafka Events
docker exec -it kafka-container \
  kafka-console-consumer.sh \
  --bootstrap-server localhost:9093 \
  --topic hr.domain-events --from-beginning
