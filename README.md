# Blog Management System - Spring Boot

This project is a CRUD-based Spring Boot backend for a Blog Management System.

## Main Features

- CRUD for Users
- CRUD for Posts
- Add comments to posts
- Get all comments for a post
- Like a post
- DTOs
- Validation using `@Valid`
- Global exception handling
- JSON REST API responses
- Layered architecture: Controller, Service, Repository

## Technologies

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 Database
- Maven
- Lombok

## How to Run

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

H2 console:

```text
http://localhost:8080/h2-console
```

JDBC URL:

```text
jdbc:h2:mem:blogdb
```

## API Endpoints

### Users

```http
POST /api/users
GET /api/users
GET /api/users/{id}
PUT /api/users/{id}
DELETE /api/users/{id}
```

Example create user:

```json
{
  "name": "Chanaka",
  "email": "chanaka@example.com"
}
```

### Posts

```http
POST /api/posts
GET /api/posts
GET /api/posts/{id}
PUT /api/posts/{id}
DELETE /api/posts/{id}
PATCH /api/posts/{id}/like
```

Example create post:

```json
{
  "title": "My First Blog Post",
  "content": "This is my blog post content.",
  "userId": 1
}
```

### Comments

```http
POST /api/posts/{postId}/comments
GET /api/posts/{postId}/comments
PUT /api/comments/{commentId}
DELETE /api/comments/{commentId}
```

Example add comment:

```json
{
  "content": "Nice post!"
}
```

## Testing Order in Postman

1. Create a user
2. Create a post using the created user ID
3. Add comments to the post
4. Get all comments for the post
5. Like the post
6. Test update and delete endpoints
