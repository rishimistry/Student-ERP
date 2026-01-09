# Student Mini ERP / Student Portal

A production-quality Student Portal System built with Spring Boot, Thymeleaf, and MySQL.

## Features

### Student Features
- Dashboard with attendance percentage, subjects, and recent notices
- View attendance history by subject
- View marks with progress bars and grades
- Download study notes (PDF)
- Read notices and announcements
- View profile information

### Admin Features
- Dashboard with system statistics
- Manage students (CRUD operations)
- Manage subjects
- Mark attendance by subject and date
- Enter/update student marks
- Upload study notes (PDF)
- Post notices and announcements

## Tech Stack

- **Backend:** Java 17 + Spring Boot 3.2
- **Architecture:** MVC (Controller → Service → Repository)
- **ORM:** JPA / Hibernate
- **Database:** MySQL
- **Auth:** Session-based authentication with Spring Security
- **Frontend:** Thymeleaf + HTML + CSS
- **Build Tool:** Maven

## Design System

- **Primary Color:** #4F46E5 (Indigo)
- **Sidebar:** #1E293B (Slate Dark)
- **Background:** #F8FAFC
- **Font:** Inter

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Setup Instructions

### 1. Database Setup

Create a MySQL database (the application will auto-create tables):

```sql
CREATE DATABASE student_erp;
```

### 2. Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_erp
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

### 4. Default Credentials

The system initializes with sample data:

| Role    | Username  | Password    |
|---------|-----------|-------------|
| Admin   | admin     | admin123    |
| Student | student1  | student123  |

## Project Structure

```
src/main/java/com/studentportal/
├── config/          # Security and app configuration
├── controller/      # MVC Controllers
├── dto/             # Data Transfer Objects
├── model/           # JPA Entities
├── repository/      # Spring Data JPA Repositories
├── service/         # Business Logic Services
└── StudentErpApplication.java

src/main/resources/
├── static/css/      # Stylesheets
├── templates/       # Thymeleaf templates
│   ├── admin/       # Admin pages
│   ├── student/     # Student pages
│   └── fragments/   # Reusable components
└── application.properties
```

## API Endpoints

### Authentication
- `GET /login` - Login page
- `POST /login` - Process login
- `POST /logout` - Logout

### Admin Routes (`/admin/*`)
- `/admin/dashboard` - Admin dashboard
- `/admin/students` - Student management
- `/admin/subjects` - Subject management
- `/admin/attendance` - Attendance management
- `/admin/marks` - Marks management
- `/admin/notes` - Notes management
- `/admin/notices` - Notice management

### Student Routes (`/student/*`)
- `/student/dashboard` - Student dashboard
- `/student/attendance` - View attendance
- `/student/marks` - View marks
- `/student/notes` - View/download notes
- `/student/notices` - View notices
- `/student/profile` - View profile

## File Upload

Notes (PDF files) are stored in the `uploads/notes` directory. Configure the path in `application.properties`:

```properties
app.upload.dir=uploads/notes
```

## Security

- BCrypt password hashing
- Session-based authentication
- Role-based access control (ADMIN, STUDENT)
- CSRF protection enabled

## License

MIT License
