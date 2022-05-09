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

}
