package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Department {
    private Set<Specialist> doctors=new HashSet<>(); //所属医生
   private String name;
   private int totalfee; //科室总收费
    public Department(){}
    public Department(Set<Specialist> doctors, String name) {
        this.doctors = doctors;
        this.name = name;
    }

    public Set<Specialist> getDoctors() {
        return doctors;
    }

    public void setDoctors(Specialist doctor) {
         doctors.add(doctor);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(int totalfee) {
        this.totalfee = totalfee;
    }
}
