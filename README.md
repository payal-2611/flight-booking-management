# Flight Booking Management System

Flight Booking Management System is a Spring Boot REST API project developed to manage flights, passengers, bookings, and payments.

The project uses Spring Data JPA and Hibernate for database operations and PostgreSQL for data storage. APIs are tested using Postman.

## Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* Postman
* Git & GitHub

## Features

### Flight

* Add a new flight
* Get flight details
* Get flight by ID
* Search flights by airline
* Update flight details
* Delete flight
* Check available seats
* Find flights based on different criteria
* Pagination and sorting

### Passenger

* Add passenger details
* Get passenger by ID
* Get passenger by contact number
* Update passenger details
* Delete passenger

### Booking

* Create a flight booking
* Associate passengers with a booking
* Associate payment with a booking
* Maintain booking status
* Update available seats after booking

### Payment

* Create payment details
* Store payment amount
* Store payment mode
* Maintain payment status
* Get payment details

## Project Structure

```text
src/main/java
└── jsp.springboot
    ├── controller
    ├── service
    ├── repository
    ├── entity
    ├── dto
    └── exception
```

The project follows a simple layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

## Database

PostgreSQL is used as the database.

Configure the database connection in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/LibDB
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Use your own PostgreSQL password in the local configuration.

## Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/payal-2611/flight-booking-management.git
```

### 2. Open the project

Import the project as a Maven project in Eclipse or IntelliJ IDEA.

### 3. Configure PostgreSQL

Create the required database and update the database username and password in `application.properties`.

### 4. Run the application

Run the Spring Boot main class.

The application runs on:

```text
http://localhost:8080
```

## API Endpoints

### Flight

```text
POST    /flight
GET     /flight
GET     /flight/{id}
PUT     /flight/{id}
DELETE  /flight/{id}
```

### Passenger

```text
POST    /passenger
GET     /passenger/{id}
PUT     /passenger/{id}
DELETE  /passenger/{id}
```

### Booking

```text
POST    /booking
GET     /booking/{id}
```

### Payment

```text
POST    /payment
GET     /payment/{id}
```

Additional search and filtering endpoints are available in the respective controllers.

## Testing

The APIs were tested using Postman.

The project covers common HTTP operations such as:

```text
GET
POST
PUT
DELETE
```

## Entity Relationship

```text
Flight
  |
  | 1 : Many
  ↓
Booking
  |       |
  |       |
  ↓       ↓
Passenger Payment
```

A booking is associated with a flight and can contain multiple passengers and a payment.

## What I Worked On

* Created REST APIs using Spring Boot
* Implemented CRUD operations
* Connected the application with PostgreSQL
* Used Spring Data JPA and Hibernate
* Implemented relationships between entities
* Added service and repository layers
* Handled exceptions and API responses
* Implemented flight seat availability logic
* Tested APIs using Postman

## Author

**Payal Sahu**

B.Tech - Computer Science and Engineering

GitHub: https://github.com/payal-2611
