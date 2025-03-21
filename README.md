# Shortify - URL Shortener Service

## Overview
Shortify is a robust URL shortening service built with Spring Boot and React. This application transforms long, unwieldy URLs into concise, shareable links while providing detailed analytics on usage patterns. The modern React frontend delivers an intuitive user experience, while the secure Spring Boot backend implements JWT authentication and leverages MySQL for data persistence.

## Build With
- SpringBoot, Spring Security with JWT implementation
- React
- MySQL
  
## Features
- Create shortened URLs with custom expiration dates
- Secure registration and login using JWT
- RESTful API for programmatic access to all features
- Automatic redirection from shortened URLs to original targets

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate and receive JWT token

### URL Management
- `POST /api/urls` - Create a new shortened URL
- `GET /api/urls` - Get all URLs (for authenticated users)
- `GET /api/urls/{shortCode}` - Get information about a specific URL
- `GET /s/{shortCode}` - Redirect to the original URL
- `GET /not-found` - Error page for invalid or expired URLs

## Setup and Installation

### Backend
1. Clone the repository
2. Configure application properties in `application.properties`:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/your_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   app.base-url=http://localhost:8080
   ```
3. Build the backend using Maven:
   ```
   mvn clean install
   ```
4. Run the Spring Boot application:
   ```
   mvn spring-boot:run
   ```

### Frontend
1. Navigate to the frontend directory
2. Install dependencies:
   ```
   npm install
   ```
3. Start the Vite development server:
   ```
   npm run dev
   ```
4. Access the application at `http://localhost:5173`
