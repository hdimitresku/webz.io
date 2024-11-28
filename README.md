
# Spring Boot Webz.io Project

This project serves as a marketplace application where users can sell their second hand clothes. It includes configuration for HTTP/2, PostgreSQL, Flyway migrations, JWT authentication, and Swagger documentation.

## Features
- HTTP/2 support for faster data transfer.
- Database integration with PostgreSQL using HikariCP connection pool.
- Flyway migrations for database version control.
- Swagger UI for API documentation.
- JWT-based authentication.
- Actuator endpoints for monitoring and management.

---

## Prerequisites
1. **Java Development Kit (JDK 11+)**
2. **PostgreSQL Database**
3. **Maven 3.8+**

---

## Database Setup (Windows)

### Step 1: Install PostgreSQL
1. Download and install PostgreSQL from [https://www.postgresql.org/download/](https://www.postgresql.org/download/).
2. During installation, set the default password for the `postgres` user to `root`.

### Step 2: Create Database
1. Open the **pgAdmin** or **SQL Shell (psql)** provided with PostgreSQL.
2. Run the following command to create the `webzio` database:

   ```sql
   CREATE DATABASE webzio;
   ```

### Step 3: Verify Connection
Ensure the `application.yml` file is correctly configured to point to your database.

---

## How to Run the Application

1. **Build the Project**  
   Open a terminal in the project directory and run:
   ```bash
   mvn clean install
   ```

2. **Run the Application**  
   Start the application with:
   ```bash
   mvn spring-boot:run
   ```

3. **Access the Application**
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - Actuator Endpoints: [http://localhost:8080/actuator](http://localhost:8080/actuator)

---

## Troubleshooting

- **Database Connection Issues:**  
  Ensure PostgreSQL is running and the credentials in `application.yml` are correct.
- **Build Failures:**  
  Check that all dependencies are downloaded and the `application.yml` syntax is correct.

---

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](https://www.apache.org/licenses/LICENSE-2.0.html) file for details.
