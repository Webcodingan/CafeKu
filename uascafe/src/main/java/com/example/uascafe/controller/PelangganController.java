package com.example.uascafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.uascafe.entity.Pelanggan;
import com.example.uascafe.repository.PelangganRepository;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/pelanggan")
public class PelangganController {

    @Autowired
    private PelangganRepository pelangganRepository;

    private Map<String, String> activeSessions = new HashMap<>(); // Simulasi sesi login

    @GetMapping
    public List<Pelanggan> getAllPelanggan() {
        return pelangganRepository.findAll();
    }

    @PostMapping
    public Pelanggan createPelanggan(@RequestBody Pelanggan pelanggan) {
        return pelangganRepository.save(pelanggan);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        Pelanggan pelanggan = pelangganRepository.findByEmail(email);

        if (pelanggan != null && pelanggan.getPassword().equals(password)) {
            activeSessions.put(pelanggan.getId(), pelanggan.getNama());
            return "Login successful for Pelanggan: " + pelanggan.getNama();
        }
        return "Invalid email or password!";
    }

    @PostMapping("/logout")
    public String logout(@RequestParam String id) {
        if (activeSessions.containsKey(id)) {
            activeSessions.remove(id);
            return "Logout successful!";
        }
        return "No active session found!";
    }
}
