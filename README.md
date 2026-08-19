# Mini-Jira Enterprise API 🚀

A robust, enterprise-grade Issue Tracking REST API built with modern Java and Spring Boot. This application simulates the backend architecture of tools like Jira, managing workflows between QA Testers, Developers, and Project Managers.

**🌍 Live API Documentation:** [https://mini-jira-backend-27ve.onrender.com/swagger-ui/index.html]

> ⏳ **Cold Start Notice:** This application is deployed on Render's free tier. If the service has been inactive, it may take **50–60 seconds** to spin up the instance for the initial request. Once awake, subsequent requests will be lightning fast thanks to the Redis distributed caching!

## ⚙️ Tech Stack
* **Core:** Java 26 (Virtual Threads), Spring Boot 4.1.0
* **Database & Caching:** PostgreSQL / Spring Data JPA (Hibernate), Redis (Upstash)
* **Messaging & Asynchronous Processing:** RabbitMQ
* **Security:** Spring Security, JSON Web Tokens (JWT), BCrypt
* **Cloud & DevOps:** Render (Deployment)
* **Documentation:** OpenAPI 3.0 (Swagger)

## 🏗️ Architectural Highlights
* **Event-Driven Processing:** Utilizes RabbitMQ for asynchronous background processing of system notifications and email triggers, effectively decoupling core services.
* **High Concurrency & Low Latency:** Integrates Redis-based distributed caching and leverages Java Virtual Threads to optimize high-traffic request handling.
* **Stateless Authentication:** Custom JWT Filter Chain prevents unauthorized access and spoofing by extracting user context directly from cryptographic tokens.
* **Role-Based Access Control (RBAC):** Strict endpoint isolation. Only Admins can create projects and manage users; QAs can create tickets; Developers can update statuses. 
* **Performance Optimization & Auditing:** Utilizes Spring Data JPA `Slice` for highly efficient, infinite-scroll style data fetching without executing heavy `COUNT` queries, backed by robust database audit logging.
* **Defensive Programming:** Implements strict DTO patterns to shield internal database entities from mass-assignment attacks, backed by global exception handling for clean HTTP responses.

## 📋 Prerequisites
Before running the project locally, ensure you have the following installed:
* **Java Development Kit (JDK) 26** or higher.
* **Maven** (for building and running the application).
* **PostgreSQL** server running locally (or via Docker) on port `5432`.
* **Redis** server running locally (or via Docker).
* **RabbitMQ** server running locally (or via Docker).
* **Git** (to clone the repository).

## 🚀 How to Run Locally

1. Clone the repository.
2. Ensure you have PostgreSQL, Redis, and RabbitMQ running.
3. Update the `application-dev.yml`with your PostgreSQL, Redis, and RabbitMQ credentials.
4. Set the `JWT_SECRET` environment variable with a 256-bit secure key.
5. Run the application via your IDE or `mvn spring-boot:run`.
6. Navigate to `http://localhost:8080/swagger-ui/index.html` to view the interactive API documentation and test endpoints!


> **Note:** The application includes a `DataSeeder` that automatically provisions an Admin, QA, and Developer account on startup for instant testing.

## 📸 API Documentation

### 1. Authentication (Login)

![Auth Request](assets/auth-login%20Request.png)
![Auth Response](assets//auth-login%20Response.png)
![Authorization Swagger](assets/authorize-token-login.png)

---

### 2. User Registration & Management

**1. Create New User** (`POST /users`)

![Register New User Request](assets/post-user-request.png)
![Register New User Response](assets/post-user-response.png)

**2. Get All Users** (`GET /users/all`)

![Get All Users Request](assets/get-users-all-request.png)
![Get All Users Response](assets/get-users-all-response.png)

---

### 3. Project Management

**1. Create Project** (`POST /projects`)

![Create Project Request](assets/post-projects-request.png)
![Create Project Response](assets/post-projects-response.png)

**2. Get All Projects** (`GET /projects/all`)

![Get All Projects Request](assets/get-projects-all-request.png)
![Get All Projects Response](assets/get-projects-all-response.png)

---

### 4. Ticket Management

**1. Add Ticket** (`POST /tickets`)

![Add Ticket Request](assets/post-tickets-request.png)
![Add Ticket Response](assets/post-tickets-response.png)

**2. Assign Developer to Ticket** (`PUT /tickets/{id}/assign/{devUserName}`)

![Assign Dev Request](assets/put-tickets-assignee-request.png)
![Assign Dev Response](assets/put-tickets-assignee-response.png)

**3. Update Ticket Status** (`PATCH /tickets/{id}/status`)

![Update Status Request](assets/patch-tickets-status-request.png)
![Update Status Response](assets/patch-tickets-status-response.png)

**4. Get Tickets for Project** (`GET /tickets/project/{projectId}`)

![Get Tickets by Project Request](assets/get-tickets-projectId-request.png)
![Get Tickets by Project Response](assets/get-tickets-projectId-response.png)

**5. Get Tickets for Assignee** (`GET /tickets/assignee/{assigneeUsername}`)

![Get Tickets by Assignee Request](assets/get-tickets-assigneeUsername-request.png)
![Get Tickets by Assignee Response](assets/get-tickets-assigneeUsername-response.png)

**6. Get My Assigned Tickets** (`GET /tickets/myAssigned`)

![Get My Assigned Tickets Request](assets/get-tickets-myassigned-request.png)
![Get My Assigned Tickets Response](assets/get-tickets-myassigned-response.png)

**7. Get Tickets for Reporter** (`GET /tickets/reporter/{reporterUsername}`)

![Get Tickets by Reporter Request](assets/get-tickets-reporterUsername-request.png)
![Get Tickets by Reporter Response](assets/get-tickets-reporterUsername-response.png)

**8. Get My Reported Tickets** (`GET /tickets/myReported`)

![Get My Reported Tickets Request](assets/get-tickets-myreported-request.png)
![Get My Reported Tickets Response](assets//get-tickets-myreported-resonse.png)

---

### 5. Comment Management

**1. Add Comment** (`POST /comments`)

![Add Comment Request](assets/post-comments-request.png)
![Add Comment Response](assets/post-comments-response.png)

**2. Get Comments by Ticket** (`GET /comments/ticket/{ticketId}`)

![Get Comments by Ticket Request](assets/get-comments-ticketId-request.png)
![Get Comments by Ticket Response](assets/get-comments-ticketId-response.png)

---

## 👨‍💻 Author

* **Created by:** Ayush Agarwal
* **Role:** Java Backend Developer
* **LinkedIn:** [Connect with me on LinkedIn](https://www.linkedin.com/in/ayush-agarwal-backend/)