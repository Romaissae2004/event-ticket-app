# Event Ticket App
Event Ticket Platform is a full-stack application built with Spring Boot and React. It allows organizers to create and manage events, attendees to purchase tickets, and staff to scan QR codes for ticket validation at events. Authentication and authorization are handled by Keycloak, with a Vite-powered React frontend.
## What's In This Repo
| Directory | Description |
|---|---|
| `tickets/` | Spring Boot backend providing the REST API, business logic, authentication, ticket management, and QR code validation |
| `frontend/` | React frontend built with Vite for browsing events, managing events, purchasing tickets, and validating tickets |
## Tech Stack
### Backend
- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- Spring Security
- PostgreSQL
- MapStruct
- Maven
### Frontend
- React 19
- TypeScript
- Vite
- React Router
- Tailwind CSS
- OIDC / JWT authentication
- QR code scanner
### Authentication
- Keycloak
- OAuth 2.0 / OpenID Connect
- JWT
## Features
### Organizers
- Create and manage events
- Create and manage ticket types
- View and manage events
### Attendees
- Browse published events
- View event details
- Purchase tickets
- View purchased tickets
### Staff
- Validate tickets using QR codes
- Prevent duplicate ticket validation
## Running the Application
### Prerequisites
Make sure you have installed:
- JDK 21
- Node.js
- PostgreSQL
- Keycloak
### 1. Backend
 Navigate to the backend directory :
   ```bash
   cd tickets
  ```
 Copy: src/main/resources/application.properties.example 
 To: src/main/resources/application.properties
Then update application.properties with your PostgreSQL credentials.
Start the Spring Boot application : 
 on Linux/macOS :
  ```bash
  ./mvnw spring-boot:run
  ```
 On Windows :
  ```bash
  mvnw.cmd spring-boot:run
  ```
 The backend runs on : `http://localhost:8081`
### 2. Frontend
  Open another terminal and run:
   ```bash
   cd frontend 
   npm install 
   npm run dev
  ```
The frontend runs on : `http://localhost:5173`
### Database
The application uses PostgreSQL.
The database configuration is located in: tickets/src/main/resources/application.properties
For security reasons, this file is not committed to the repository.
An example configuration is provided in: tickets/src/main/resources/application.properties.example
### Keycloak
The application uses Keycloak for authentication and authorization.
The backend expects the Keycloak issuer to be: `http://localhost:9090/realms/event-ticket-platform`
Make sure Keycloak is running on port 9090 and that the event-ticket-platform realm is configured.
## Project Structure
```text
event-ticket-app/
├── frontend/     # React + TypeScript frontend
├── tickets/      # Spring Boot backend
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
└── README.md
```
## Author
**Merzak Romaissae**
Engineering student in Computer Science — ENSAO
