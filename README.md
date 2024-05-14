# Todo List API Documentation

## Overview
This document serves as a guide to understand the Todo List API developed using Java Spring Boot. The API provides functionalities to manage todo items, including creation, retrieval, updating, and deletion.

## Table of Contents
1. [Project Structure](#project-structure)
2. [Endpoints](#endpoints)
3. [Persistence](#persistence)

## Project Structure
```
todoapi/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── todoapi/
│   │   │           ├── api/
│   │   │           │   ├── controller/
│   │   │           │   ├── mapper/
│   │   │           │   ├── model/
│   │   │           │   ├── repository/
│   │   │           │   └── service/
│   │   │           └── TodoapiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/
│       │   └── com/
│       │       ├── integrationtest/
│       │       └── todoapi/
│       │           ├── api/
│       │           │   ├── controller/
│       │           │   ├── repository/
│       │           │   └── service/
│       │           └── TodoapiApplicationTests.java
│       └── resources/
│           └── application.properties
```

## Endpoints
### Todo Resource
- **Todo Model**: Defines properties like id, description, completion status.

### RESTful API Endpoints
- `GET /todos`: Retrieve all todos.
- `GET /todos/{id}`: Retrieve a single todo.
- `POST /todos`: Create a todo.
- `PATCH /todos/{id}`: Update a todo.
- `DELETE /todos/{id}`: Delete a todo.

## Persistence
- **Database**: Uses an SQLite database for persistence.
- **Database Interaction**: JDBC and SQL are used for database interactions.

## Running the Application
To run the application locally, follow these steps:
1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Clone the repository to your local machine.
3. Navigate to the project directory.
4. Execute the command `./mvnw spring-boot:run` to start the Spring Boot application.

## Testing
The project includes unit tests and integration test to ensure the correctness of functionality. 

You can run tests using the command `./mvnw test`.

Feel free to reach out for any further assistance or clarification.

**Alex Molina**

