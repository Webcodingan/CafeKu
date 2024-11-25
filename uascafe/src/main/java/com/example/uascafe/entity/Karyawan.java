package com.example.uascafe.entity;

import jakarta.persistence.Entity;

@Entity
public class Karyawan extends User {
    private String shift;
    private String alamat;

    // Getters and Setters
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

    @Override
    public String notifikasiLogin() {
        return "Karyawan " + getNama() + " telah berhasil login.";
    }
}
