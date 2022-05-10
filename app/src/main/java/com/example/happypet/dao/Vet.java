package com.example.happypet.dao;

public class Vet {
    public String fullName ,clinicName, clinicAddress, clinicPhone, clinicHrs, email, password;


    public Vet(){

    }

    public Vet(String fullName, String clinicName, String clinicAddress, String clinicPhone, String clinicHrs, String email, String password) {
        this.fullName = fullName;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.clinicPhone = clinicPhone;
        this.clinicHrs = clinicHrs;
        this.email = email;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicPhone() {
        return clinicPhone;
    }

    public void setClinicPhone(String clinicPhone) {
        this.clinicPhone = clinicPhone;
    }

    public String getClinicHrs() {
        return clinicHrs;
    }

    public void setClinicHrs(String clinicHrs) {
        this.clinicHrs = clinicHrs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
