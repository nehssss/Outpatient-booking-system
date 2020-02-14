package entity;

import java.util.Objects;

public class Patient {
    private String ID;
    private String name;
    private String ptime;
    private int pfee; //应收费用
    public Patient(){}
    public Patient(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public int getPfee() {
        return pfee;
    }

    public void setPfee(int pfee) {
        this.pfee = pfee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(ID, patient.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
