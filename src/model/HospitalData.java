package model;

import java.io.Serializable;
import java.util.*;

public class HospitalData implements Serializable {
    public List<User> users = new ArrayList<>();
    public List<Entities.DoctorPatient> doctorPatients = new ArrayList<>();
    public List<Entities.Appointment> appointments = new ArrayList<>();
    public List<Entities.MedicalRecord> records = new ArrayList<>();
    public List<Entities.Payment> payments = new ArrayList<>();
}
