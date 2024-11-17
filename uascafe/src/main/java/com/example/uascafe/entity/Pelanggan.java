package com.example.uascafe.entity;

import jakarta.persistence.Entity;

@Entity
public class Pelanggan extends User{

    private String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
