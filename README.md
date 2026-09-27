# Hospital Management System
Southeast University, Department of CSE — Course 282, Section 4.

This implementation follows the supplied Hospital Management System proposal: five roles (Admin, Doctor, Patient, Receptionist, Nurse), common login/profile functions, role-specific functions, and the listed entities User, Admin, Doctor, Patient, Receptionist, Nurse, DoctorPatient, Appointment, MedicalRecords and Payments.

## OOP
- Encapsulation: private fields and getters/setters.
- Abstraction: abstract `User`.
- Inheritance: all five roles extend `User`.
- Polymorphism: each role overrides `getRole()`.

## Main features
Login/logout, password reset, profile update, role-based menus, Admin user CRUD, doctor/patient/nurse lists, appointments, medical records, payments, custom exceptions, try-catch, Swing GUI and file persistence.

## Demo accounts
admin / 1234
D001 / 1234
P001 / 1234
R001 / 1234
N001 / 1234

## Run in VS Code
Install JDK 17+ and Java Extension Pack. Open the project folder and run `src/Main.java`.

Windows PowerShell:
`javac -d out src\model\*.java src\exception\*.java src\service\*.java src\gui\*.java src\Main.java`
`java -cp out Main`

Linux/macOS:
`mkdir -p out && javac -d out src/model/*.java src/exception/*.java src/service/*.java src/gui/*.java src/Main.java && java -cp out Main`

Data is saved in `data/hospital.dat`.
