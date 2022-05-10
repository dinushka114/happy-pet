package com.example.happypet.dao;

public class AppointmentDataModel {
    public String date, time, petName, petOwner, petOwnerPhone, reason;

    public AppointmentDataModel(){

    }

    public AppointmentDataModel(String date, String time, String petName, String petOwner, String petOwnerPhone, String reason) {
        this.date = date;
        this.time = time;
        this.petName = petName;
        this.petOwner = petOwner;
        this.petOwnerPhone = petOwnerPhone;
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(String petOwner) {
        this.petOwner = petOwner;
    }

    public String getPetOwnerPhone() {
        return petOwnerPhone;
    }

    public void setPetOwnerPhone(String petOwnerPhone) {
        this.petOwnerPhone = petOwnerPhone;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

