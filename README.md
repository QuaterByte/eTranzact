# eTranzact

A simple financial application built with **Java 22** and **Spring Boot 4.x**. The project demonstrates the implementation of modern Java and Spring technologies for managing basic financial transactions through a clean, scalable, and maintainable architecture.

## Features

* User account management
* Secure authentication and authorization
* Deposit and withdrawal operations
* Fund transfers between accounts
* Transaction history
* Account balance management
* RESTful API architecture
* Input validation and exception handling
* Database persistence using Spring Data JPA

## Technology Stack

| Technology      | Version                           |
| --------------- | --------------------------------- |
| Java            | 22                                |
| Spring Boot     | 4.1.0                             |
| Spring Data JPA | Latest                            |
| Spring Security | Latest                            |
| Maven           | Latest                            |
| Database        | H2 Database                       |
| Hibernate       | Latest                            |



## API Overview

| Method | Endpoint                 | Description              |
| ------ | ------------------------ | ------------------------ |
| POST   | `/api/auth/register`     | Register a new user      |
| POST   | `/api/auth/login`        | Authenticate user        |
| GET    | `/api/accounts/{id}`     | Retrieve account details |
| POST   | `/api/accounts/deposit`  | Deposit funds            |
| POST   | `/api/accounts/withdraw` | Withdraw funds           |
| POST   | `/api/accounts/transfer` | Transfer funds           |
| GET    | `/api/transactions`      | View transaction history |



## Future Improvements

* Email notifications
* SMS transaction alerts
* Two-factor authentication (2FA)
* Scheduled payments
* Currency conversion
* Docker support
* Kubernetes deployment
* API documentation using OpenAPI/Swagger
* Monitoring with Prometheus and Grafana

Developed using Java 22 and Spring Boot 4.x as a demonstration of a modern financial transaction system.
