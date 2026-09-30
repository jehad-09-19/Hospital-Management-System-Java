package model;

import java.io.Serializable;

public class Entities {
    public static class DoctorPatient implements Serializable {
        public String doctorPatientId, doctorId, patientId;

        public DoctorPatient(String a, String b, String c) {
            doctorPatientId = a;
            doctorId = b;
            patientId = c;
        }
    }

    public static class Appointment implements Serializable {
        public String appointmentId, doctorId, patientId, date, status;

        public Appointment(String a, String b, String c, String d, String e) {
            appointmentId = a;
            doctorId = b;
            patientId = c;
            date = d;
            status = e;
        }
    }

    public static class MedicalRecord implements Serializable {
        public String medicalRecordsId, patientId, details;

        public MedicalRecord(String a, String b, String c) {
            medicalRecordsId = a;
            patientId = b;
            details = c;
        }
    }

    public static class Payment implements Serializable {
        public String paymentId, doctorId, patientId;
        public double amount;

        public Payment(String a, String b, String c, double d) {
            paymentId = a;
            doctorId = b;
            patientId = c;
            amount = d;
        }
    }
}
