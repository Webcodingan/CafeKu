package com.example.uascafe.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Id;

@MappedSuperclass
public abstract class User {
    @Id
    private String id;
    private String nama;
    private String email;
    private String password;
    private String notelp;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getnotelp() {
        return notelp;
    }

    public void setnotelp(String notelp) {
        this.notelp = notelp;
    }

    // Abstract method
    public abstract String notifikasiLogin();
}
