# 📘 Book Management API

This is a simple RESTful Book Management API built using **Spring Boot**. It allows you to perform basic **CRUD (Create, Read, Update, Delete)** operations on a collection of books using an in-memory **H2 database**.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot 3.5.3
- Spring Data JPA
- H2 Database
- Lombok
- JUnit & Mockito (for testing)

---

## 📦 Features

- Add new books to the library
- Get a list of all books
- Retrieve a specific book by ID
- Update an existing book
- Delete a book

---

## 🏗️ Project Structure

bookmanagement
├── src
│ ├── main
│ │ ├── java
│ │ │ └── com.library.bookmanagement
│ │ │ ├── controller
│ │ │ ├── service
│ │ │ ├── repository
│ │ │ ├── model
│ │ │ └── exception
│ │ └── resources
│ │ └── application.properties
│ └── test
│ └── java
│ └── com.library.bookmanagement
└── pom.xml


---

## ⚙️ How to Run

### Prerequisites
- JDK 17 or above
- Maven installed

### Run Steps

```bash
# 1. Clone the repository
git clone https://github.com/rkhan343/bookmanagement.git
cd bookmanagement

# 2. Build the project
mvn clean install

# 3. Run the application
mvn spring-boot:run

The application will start on:
 http://localhost:8080

Running Tests
mvn test

| Method | Endpoint    | Description    |
| ------ | ----------- | -------------- |
| GET    |/fetch-all-book| Get all books  |
| GET    | /books/{id} | Get book by ID |
| POST   | /books      | Add a new book |
| PUT    | /books/{id} | Update a book  |
| DELETE | /books/{id} | Delete a book  |


To access the in-memory H2 DB console:
http://localhost:8080/h2-console

Make sure these settings are in application.properties:

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:bookdb

sample JSON:
POST /books
{
  "title": "YOU CAN WIN",
  "author": "shiv khere",
  "isbn": "9780061122411",
  "publishedDate": "2007-05-01"
}
Response
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "9780132350884"
}

Author
Rukhsar Khan


