# Car Rental System

A Java-based console application to manage vehicle rentals using JDBC and MySQL.

## Features

### Customer Management
- Add, update, find, list, and remove customers

### Vehicle Management
- Add new cars, remove cars, update availability status
- List available and rented vehicles

### Lease Management
- Create lease with customer and vehicle
- Return car
- View active leases and lease history

### Payment Handling
- Record payments
- View all payment history

## Technologies Used
- Java 17
- JDBC
- MySQL
- Maven
- Lombok
- JUnit (for testing)

## Setup

1. Create a MySQL database named `car_rental`.
2. Import the SQL schema (tables: `vehicle`, `customer`, `lease`, `payment`).
3. Add your database details in `src/main/resources/db.properties`.

```properties
db.url=jdbc:mysql://localhost:3306/car_rental
db.user=root
db.password=yourpassword
