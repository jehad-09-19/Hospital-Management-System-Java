# 🏥 HOSPITAL MANAGEMENT SYSTEM

**Topic:** HOSPITAL MANAGEMENT SYSTEM

**Assignment:** CSE282 JAVA GROUP PROJECT

**Course:** CSE 282.4 - PROGRAMMING LANGUAGE II LAB (JAVA)

👥 **Group Members**

| SL | Student Name | ID |
|---|---|---|
| 1 | Farhana Akter Sumaiya | 2024200000023 |
| 2 | Lamia Nusrat Mim | 2023200000431 |
| 3 | MD FOZLY UDDIN | 2024200000219 |
| 4 | Jannatul Ferdus | 2023100000682 |
| 5 | Irfan Ul Hoque | 2023200000050 |

---

📄 **Project Overview**

The Hospital Management System is a Java-based desktop application designed to manage essential hospital activities through a centralized and user-friendly graphical interface.

The system supports five different user roles:

- Admin
- Doctor
- Patient
- Receptionist
- Nurse

The system provides role-based access to hospital information and allows users to manage doctors, patients, nurses, appointments, medical records, payments, and personal profiles.

The application uses Java Swing for the graphical user interface and file-based persistence for storing hospital data.

---

📄 **Project Objectives**

The main objectives of the Hospital Management System are:

- Create a centralized system for managing hospital information.
- Implement role-based access for Admin, Doctor, Patient, Receptionist, and Nurse.
- Apply all four major OOP principles: Encapsulation, Abstraction, Inheritance, and Polymorphism.
- Provide a simple and user-friendly graphical interface using Java Swing.
- Manage appointments, medical records, and payments efficiently.
- Provide Admin with user management and CRUD operations.
- Validate user input and handle errors using custom exceptions.
- Store application data using file persistence.
- Improve the organization and accessibility of hospital-related information.

---

⚙️ **System Theory**

Traditional hospital management can involve separate records for doctors, patients, appointments, medical information, and payments. Managing these records manually can make information difficult to organize and update.

The Hospital Management System addresses these problems through an object-oriented software architecture.

**Object-Oriented Architecture:**  
The system uses classes such as `User`, `Admin`, `Doctor`, `Patient`, `Receptionist`, and `Nurse` to represent different users and their responsibilities.

**Role-Based Access:**  
Different users receive different system functions according to their roles. For example, Admin can manage users, while Doctors and Nurses have access to relevant medical information.

**Structured Data Flow:**  
User input → Validation → Service Layer → Hospital Data → File Persistence.

**Encapsulated Modules:**  
The project separates model, service, exception, and GUI components to keep the system organized and reduce unnecessary coupling.

---

### OOP PRINCIPLES AND IMPLEMENTATION

| PRINCIPLES | IMPLEMENTATION |
|---|---|
| Encapsulation | Private fields with getters and setters are used in `User` and role-specific classes. |
| Abstraction | `User` is an abstract class that defines common user information and the abstract `getRole()` method. |
| Inheritance | `Admin`, `Doctor`, `Patient`, `Receptionist`, and `Nurse` extend the `User` class. |
| Polymorphism | Each role overrides the `getRole()` method to return its specific role. |

---

📄 **Methodology**

### 1. Requirement Analysis

The system requirements were identified based on the needs of a hospital management environment.

The system defines five user roles:

- Admin
- Doctor
- Patient
- Receptionist
- Nurse

The major functional requirements include user authentication, profile management, user CRUD operations, appointment management, medical record management, and payment management.

### 2. System Design

**Frontend:**  
The graphical user interface is developed using Java Swing. The system contains a login interface and a role-based main dashboard.

**Backend / Service Layer:**  
The `HospitalManager` class handles authentication, user management, appointments, medical records, payments, validation, and data persistence.

**Data Storage:**  
Hospital information is stored using Java serialization in the file:

`data/hospital.dat`

**Exception Handling:**  
Custom exceptions are used for invalid data, duplicate IDs, and missing records.

---

### 3. Object Model

| CLASS | DESCRIPTION |
|---|---|
| `Main` | The main driver class that starts the Hospital Management System. |
| `User (abstract)` | Abstract base class containing common user information such as ID, password, name, email, and contact number. |
| `Admin` | Extends `User` and provides administrator-specific information and privileges. |
| `Doctor` | Extends `User` and contains specialist and educational information. |
| `Patient` | Extends `User` and contains gender, age, and address information. |
| `Receptionist` | Extends `User` and contains receptionist ID and address information. |
| `Nurse` | Extends `User` and contains nurse ID, qualification, and department information. |
| `DoctorPatient` | Represents the relationship between a doctor and a patient. |
| `Appointment` | Stores appointment ID, doctor, patient, date, and appointment status. |
| `MedicalRecord` | Stores medical record ID, patient ID, and medical details. |
| `Payment` | Stores payment ID, doctor ID, patient ID, and payment amount. |
| `HospitalData` | Stores collections of users, doctor-patient relationships, appointments, medical records, and payments. |
| `HospitalManager` | Handles system operations, validation, authentication, CRUD operations, and file persistence. |
| `LoginFrame` | Provides the login and password reset interface. |
| `MainFrame` | Provides the main dashboard, role-based menus, and system operations. |
| `Exceptions` | Contains custom exceptions such as `DuplicateId`, `NotFound`, and `InvalidData`. |

### 4. Implementation

The project is implemented in Java using object-oriented programming principles.

The system uses:

- Java Swing for GUI development.
- Abstract classes and inheritance for user roles.
- Encapsulation through private fields and getters/setters.
- Polymorphism through overridden `getRole()` methods.
- `HospitalManager` as the main service layer.
- Java serialization for file-based data persistence.
- Try-catch blocks and custom exceptions for error handling.
- Role-based menus for controlling available functions.

### 5. Testing & Validation

The system validates important user operations and handles common error conditions.

Testing includes:

- Valid and invalid login attempts.
- Duplicate user ID detection.
- Invalid user information validation.
- Doctor and patient verification before creating appointments.
- Duplicate appointment detection.
- Medical record validation.
- Payment validation.
- Invalid payment amount handling.
- User not found handling.
- Profile update functionality.
- Appointment cancellation.

---

🧩 **Functional Modules**

**User Authentication** — Users can log in using their User ID and password. The system also provides password reset functionality.

**Admin Dashboard** — Admin users can add, update, and delete users and manage hospital-related information.

**Doctor Management** — The system stores doctor information including specialist and educational information.

**Patient Management** — Patient information such as gender, age, and address can be managed.

**Nurse Management** — Nurse information including qualification and department is maintained.

**Receptionist Management** — Receptionist information and role-based access are supported.

**Appointment Management** — Users can schedule appointments between doctors and patients and update appointment status.

**Medical Records** — Medical records can be added, updated, and displayed for patients.

**Payment Management** — Doctor-patient payment information and payment amounts can be recorded.

**Profile Management** — Users can update their password, name, email, and contact information.

**Role-Based Dashboard** — Different users receive different menus and system access according to their roles.

---

💡 **Exception Handling**

Custom exceptions are used to ensure data validity and provide meaningful error messages.

**DuplicateId** → Used when an existing User, Appointment, Medical Record, or Payment ID is entered.

**NotFound** → Used when a requested user, doctor, patient, appointment, or medical record cannot be found.

**InvalidData** → Used when required information is missing or invalid, such as an invalid patient age or payment amount.

The system uses try-catch blocks to handle exceptions and display user-friendly error messages through `JOptionPane`.

---

📜 **Conclusion**

The Hospital Management System successfully demonstrates the use of Java and object-oriented programming principles to solve a real-world hospital management problem.

The system provides an organized solution for managing users, doctors, patients, nurses, appointments, medical records, and payments.

By using Java Swing, role-based access, custom exception handling, and file persistence, the project demonstrates practical application of software design and OOP concepts.

This project also provides a structured and user-friendly platform for managing essential hospital operations efficiently.

---

⏳ **Future Enhancements**

- Integration with MySQL or another relational database.
- Online appointment booking and scheduling.
- Email or SMS notifications for appointments.
- Advanced medical record management.
- Doctor and patient search and filtering.
- Secure password encryption.
- Online payment integration.
- Report generation for hospital activities.
- Development of a web or mobile version for remote accessibility.
- Addition of stronger authentication and security features.

---

### Demo Accounts

| Role | User ID | Password |
|---|---|---|
| Admin | admin | 1234 |
| Doctor | D001 | 1234 |
| Patient | P001 | 1234 |
| Receptionist | R001 | 1234 |
| Nurse | N001 | 1234 |

### Technologies Used

- Java
- Java Swing
- Object-Oriented Programming (OOP)
- Java Serialization
- File Persistence
- Exception Handling
