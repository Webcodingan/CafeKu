package com.example.uascafe.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pelanggan extends User {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    private String rating;

        @OneToMany(mappedBy = "pelanggan", cascade = CascadeType.ALL)
    private List<Pesanan> pesananList;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    
    @Override
    public String notifikasiLogin() {
        return "Karyawan " + getNama() + " telah berhasil login.";
    }
}
