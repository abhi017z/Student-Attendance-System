# Student Attendance System

A Spring Boot backend application for managing students and tracking their attendance.

## Features

### Student Management
- ✅ Add a new student (name, email)
- ✅ View all students
- ✅ Get student by ID

### Attendance Management
- ✅ Mark attendance for a student (PRESENT or ABSENT) for the current date
- ✅ View all attendance records
- ✅ View attendance records for a specific student
- ✅ View attendance by date

## Technology Stack

- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Maven**

## Project Structure

```
src/main/java/com/attendance/
├── AttendanceApplication.java          # Main Spring Boot Application
├── controller/
│   ├── StudentController.java          # Student REST endpoints
│   └── AttendanceController.java       # Attendance REST endpoints
├── dto/
│   ├── StudentRequest.java             # Student creation request
│   ├── StudentResponse.java            # Student response
│   └── AttendanceResponse.java         # Attendance response
├── entity/
│   ├── Student.java                    # Student entity
│   ├── Attendance.java                 # Attendance entity
│   └── AttendanceStatus.java           # Enum for PRESENT/ABSENT
├── exception/
│   ├── StudentNotFoundException.java   # Custom exception
│   ├── AttendanceException.java        # Custom exception
│   └── GlobalExceptionHandler.java     # Global error handling
├── repository/
│   ├── StudentRepository.java          # Student data access
│   └── AttendanceRepository.java       # Attendance data access
└── service/
    ├── StudentService.java             # Student business logic
    └── AttendanceService.java          # Attendance business logic
```

## API Endpoints

### Student Endpoints

#### 1. Create Student
```http
POST /students
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com"
}
```

#### 2. Get All Students
```http
GET /students
```

#### 3. Get Student by ID
```http
GET /students/{id}
```

### Attendance Endpoints

#### 1. Mark Attendance
```http
POST /attendance/{studentId}?status=PRESENT
```
Status can be: `PRESENT` or `ABSENT`

#### 2. Get All Attendance Records
```http
GET /attendance
```

#### 3. Get Attendance by Student
```http
GET /attendance/student/{studentId}
```

#### 4. Get Attendance by Date
```http
GET /attendance/date?date=2024-01-15
```

## How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Steps

1. **Navigate to project directory**
   ```bash
   cd attendance
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Server runs on: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:attendancedb`
     - Username: `sa`
     - Password: `password`

## Testing with cURL

### Create a Student
```bash
curl -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"John Doe\",\"email\":\"john@example.com\"}"
```

### Get All Students
```bash
curl http://localhost:8080/students
```

### Mark Attendance
```bash
curl -X POST "http://localhost:8080/attendance/1?status=PRESENT"
```

### Get Attendance for Student
```bash
curl http://localhost:8080/attendance/student/1
```

### Get All Attendance Records
```bash
curl http://localhost:8080/attendance
```

## Database Schema

### Student Table
- `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- `name` (VARCHAR(100), NOT NULL)
- `email` (VARCHAR(100), NOT NULL, UNIQUE)

### Attendance Table
- `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- `date` (DATE, NOT NULL)
- `status` (VARCHAR(20), NOT NULL) - PRESENT or ABSENT
- `student_id` (BIGINT, FOREIGN KEY → Student.id)

## Key Features

✅ **Clean Architecture**: Follows layered architecture (Controller → Service → Repository → Entity)
✅ **Validation**: Input validation using Jakarta Validation
✅ **Exception Handling**: Global exception handling with meaningful error responses
✅ **REST Best Practices**: Proper HTTP methods, status codes, and RESTful endpoints
✅ **Transaction Management**: Declarative transaction management with `@Transactional`
✅ **DTO Pattern**: Separate request/response objects for better API design
✅ **Relationships**: Many-to-One relationship between Attendance and Student
✅ **Business Rules**: 
  - Validates student exists before marking attendance
  - Prevents duplicate attendance for the same student on the same day
  - Only accepts PRESENT or ABSENT as valid status values

## Configuration

The application uses an in-memory H2 database by default. Configuration is in `application.properties`:

- Server Port: 8080
- Database: H2 (in-memory)
- JPA: Auto-update schema
- H2 Console: Enabled at `/h2-console`
