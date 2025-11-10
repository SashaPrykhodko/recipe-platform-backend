# Recipe Platform Backend

A Spring Boot backend application for a culinary blog and recipe sharing platform. Users can share recipes, leave comments, and rate recipes.

## Features

- Recipe management (CRUD operations)
- User authentication and authorization
- Recipe comments and ratings
- RESTful API design
- Database migrations with Flyway
- Comprehensive testing with TestContainers

## Tech Stack

- **Java 21** - Latest LTS version
- **Spring Boot 3.5.6** - Main framework
- **Spring Data JPA** - Database abstraction
- **PostgreSQL** - Primary database
- **Flyway** - Database migrations
- **Docker** - Containerization
- **JUnit 5** - Testing framework
- **TestContainers** - Integration testing

## Prerequisites

- Java 21 or higher
- Docker and Docker Compose
- Gradle (wrapper included)

## Quick Start

1. **Clone the repository:**
   ```bash
   git clone https://github.com/SashaPrykhodko/recipe-platform-backend.git
   cd recipe-platform-backend
   ```

2. **Set up environment variables:**
   ```bash
   cp .env.example .env
   # Edit .env file with your configuration
   ```

3. **Start PostgreSQL database:**
   ```bash
   docker-compose up -d
   ```

4. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```

The application will start on `http://localhost:8080`

## Configuration

The application uses environment variables for configuration. Copy `.env.example` to `.env` and update the values:

```env
DATABASE_URL=jdbc:postgresql://localhost:5432/recipes
DATABASE_USERNAME=admin
DATABASE_PASSWORD=your-password-here
JWT_SECRET=your-jwt-secret-here
JWT_EXPIRATION=86400000
```

## Development

### Running Tests

```bash
./gradlew test
```

Tests use TestContainers to spin up a real PostgreSQL instance for integration testing.

### Database Migrations

Flyway handles database migrations automatically on startup. Migration files are located in `src/main/resources/db/migration/`.

### API Documentation

The application includes Spring Boot Actuator endpoints for monitoring and health checks:
- Health: `http://localhost:8080/actuator/health`

## Project Structure

```
src/
├── main/
│   ├── java/com/oprykhodko/recipeplatformbackend/
│   │   ├── entity/          # JPA entities
│   │   ├── repository/      # Data access layer with JPA repositories
│   │   └── RecipeplatformbackendApplication.java
│   └── resources/
│       └── db/migration/    # Flyway migrations
└── test/
    └── java/               # Test classes
```

## Repository Layer

The application includes custom repository methods to handle lazy loading relationships:

- **UserRepository**: Includes `findUserWithRecipes()` and `findAllUsersWithRecipes()` methods using JOIN FETCH to eagerly load user recipes
- **RecipeRepository**: Includes `findByIdWithUser()` and `findAllWithUsers()` methods using JOIN FETCH to eagerly load recipe authors

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests to ensure everything works
5. Submit a pull request

## License

This project is licensed under the MIT License.
