# HR Cabinet

This project was created as part of the rinhhack hackathon.

## Description

HR Cabinet is a web application designed to streamline HR operations and management.

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- PostgreSQL database

## Setup

### Database Configuration

1. Install and start PostgreSQL
2. Create a database named 'hr'
3. The database schema is included in the project

### Application Configuration

1. Update the `application.properties` file with your database connection details:
   - Database URL
   - Username
   - Password
2. Add your Telegram bot token and bot name to the configuration

### Running the Application

To run the application, execute the following command:

```bash
mvn spring-boot:run
```

## Technologies Used

- Spring Boot
- PostgreSQL
- Maven
- Java

## Features

- HR dashboard
- Employee management
- Telegram bot integration
