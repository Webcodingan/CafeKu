package com.example.uascafe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pelanggan extends User {
    private String rating;

        @OneToMany(mappedBy = "pelanggan", cascade = CascadeType.ALL)
    private List<Pesanan> pesananList;

    // Getters and Setters
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
