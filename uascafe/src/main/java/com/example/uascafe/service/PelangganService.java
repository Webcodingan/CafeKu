package com.example.uascafe.service;

import com.example.uascafe.entity.Pelanggan;
import com.example.uascafe.repository.PelangganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class PelangganService {

    @Autowired
    private PelangganRepository pelangganRepository;

    // Method to hash password using MD5
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            return number.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Kesalahan hashing password");
        }
    }

    // Method to validate password (compare hashed password)
    public boolean validatePassword(String rawPassword, String hashedPassword) {
        String hashedRawPassword = hashPassword(rawPassword); // Hash password yang dimasukkan
        return hashedRawPassword.equals(hashedPassword); // Bandingkan dengan password yang di-hash di database
    }

    // Method to get all Pelanggan
    public List<Pelanggan> getAllPelanggan() {
        return pelangganRepository.findAll();
    }

    // Method to find Pelanggan by email
    public Pelanggan findByEmail(String email) {
        return pelangganRepository.findByEmail(email);
    }

    public Optional<Pelanggan> findById(String id) {
        return pelangganRepository.findById(id);
    }

    // Method to save Pelanggan
    public void savePelanggan(Pelanggan pelanggan) {
        pelangganRepository.save(pelanggan);
    }
}
