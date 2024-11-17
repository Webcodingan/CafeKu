package com.example.uascafe.entity;

import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.Column;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "idUser")
public class Karyawan extends User{

    private String shift;
    private String alamat;

    // Getters and setters

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
