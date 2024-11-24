package com.example.uascafe.service;

import com.example.uascafe.entity.Pelanggan;
import com.example.uascafe.repository.PelangganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PelangganService {

    @Autowired
    private PelangganRepository pelangganRepository;

    // Method to hash password using MD5
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString(); // Return the MD5 hashed password
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    // Method to get all Pelanggan
    public List<Pelanggan> getAllPelanggan() {
        return pelangganRepository.findAll();
    }

    // Method to find Pelanggan by email
    public Pelanggan findByEmail(String email) {
        return pelangganRepository.findByEmail(email);
    }

    // Method to save Pelanggan
    public void savePelanggan(Pelanggan pelanggan) {
        pelangganRepository.save(pelanggan);
    }
}
