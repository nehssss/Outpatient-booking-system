package entity;

import entity.Patient;

import java.util.*;

/**
 * 专家情况
 * name：名字
 * position：职称
 * specialty：专长
 * registrationFee：费用
 * worktime：工作时间
 */
public class Specialist {
   private String name;
    private String position;
    private String specialty;
    private  int registrationFee;
    private String worktime;
    private int patientnum;
    private String patdb;
    private Set<Patient> patients=new HashSet<>();

    public Specialist(String name){this.name=name;}
    public Specialist(){}
    public Specialist(String name, String position, String specialty, int registrationFee) {
        this.name = name;
        this.position = position;
        this.specialty = specialty;
        this.registrationFee = registrationFee;
    }

    public String getPatdb() {
        return patdb;
    }

    public void setPatdb(String patdb) {
        this.patdb = patdb;
    }

    public int getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(int registrationFee) {
        this.registrationFee = registrationFee;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public int getPatientnum() {
        return patientnum;
    }

    public void setPatientnum(int patientnum) {
        this.patientnum = patientnum;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Patient patient) {
        this.patients.add(patient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialist)) return false;
        Specialist that = (Specialist) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
