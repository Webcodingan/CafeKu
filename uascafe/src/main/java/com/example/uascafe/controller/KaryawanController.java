package com.example.uascafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.uascafe.entity.Karyawan;
import com.example.uascafe.repository.KaryawanRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class KaryawanController {

    @Autowired
    private KaryawanRepository karyawanRepository;

    private Map<String, String> activeSessions = new HashMap<>();

    // Route untuk menampilkan daftar karyawan
    @GetMapping("/karyawan/list")
    public String getAllKaryawan(Model model) {
        List<Karyawan> karyawanList = karyawanRepository.findAll();
        model.addAttribute("karyawanList", karyawanList);
        return "Dashboard/listKaryawan"; // Mengarah ke template listKaryawan.html
    }

    // Route untuk menampilkan form login karyawan
    @GetMapping("/karyawan/login")
    public String showLoginForm() {
        return "login-karyawan.html"; // Mengarah ke template login-karyawan.html
    }

    // Route untuk proses login karyawan
    @PostMapping("/karyawan/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            Model model) {
        Karyawan karyawan = karyawanRepository.findByEmail(email);

        if (karyawan != null && karyawan.getPassword().equals(password)) {
            activeSessions.put(karyawan.getId(), karyawan.getNama());
            model.addAttribute("nama", karyawan.getNama());
            return "redirect:/karyawan-dashboard";
        }
        model.addAttribute("error", "Invalid email or password!");
        return "login-karyawan.html";
    }

    @GetMapping("/karyawan-dashboard")
    public String showKaryawanDashboard(Model model) {
        // Logika untuk menampilkan dashboard jika diperlukan
        return "Dashboard/dashboard-karyawan.html"; 
    }

    // Route untuk logout karyawan
    @GetMapping("/karyawan/logout")
    public String logout(@RequestParam(required = false) String id, Model model) {
        if (id != null && activeSessions.containsKey(id)) {
            activeSessions.remove(id);
            model.addAttribute("message", "Logout successful!");
        }
        return "index.html"; // Mengarah ke halaman index
    }
}
