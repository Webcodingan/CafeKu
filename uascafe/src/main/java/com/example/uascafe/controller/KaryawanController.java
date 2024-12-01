package com.example.uascafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.uascafe.entity.Karyawan;
import com.example.uascafe.service.KaryawanService;
import jakarta.servlet.http.HttpSession;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
public class KaryawanController {

    @Autowired
    private KaryawanService karyawanService;

    // Route untuk menampilkan daftar karyawan
    @GetMapping("/karyawan/list")
    public String getAllKaryawan(Model model) {
        model.addAttribute("karyawanList", karyawanService.getAllKaryawan());
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
            HttpSession session,
            Model model) {

        // Hash password using MD5
        String hashedPassword = hashPassword(password);

        Karyawan karyawan = karyawanService.findByEmail(email);
        // Validasi login dengan MD5 password comparison
        if (karyawan != null && karyawan.getPassword().equals(hashedPassword)) {
            // Simpan data login ke session
            session.setAttribute("karyawanId", karyawan.getId());
            session.setAttribute("karyawanNama", karyawan.getNama());

            // Ambil notifikasi login dan tambahkan ke Model
            String notifikasi = karyawan.notifikasiLogin();
            model.addAttribute("notifikasi", notifikasi);

            return "redirect:/karyawan-dashboard"; // Redirect ke halaman dashboard
        }
        model.addAttribute("error", "Email atau password salah, Silahkan coba kembali!");
        return "login-karyawan.html";
    }

    @GetMapping("/karyawan-dashboard")
    public String karyawanDashboard(HttpSession session, Model model) {
        // Periksa apakah karyawan sudah login
        String karyawanNama = (String) session.getAttribute("karyawanNama");

        if (karyawanNama == null) {
            return "redirect:/karyawan/login"; // Redirect ke login jika belum login
        }
        model.addAttribute("nama", karyawanNama); // Tampilkan nama di dashboard
        return "Dashboard/dashboard-karyawan.html"; // Template dashboard
    }

    // Route untuk logout
    @PostMapping("/karyawan/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Hapus semua data di session
        return "redirect:/karyawan/login"; // Redirect ke halaman login
    }

    // Method to hash password using MD5
    private String hashPassword(String password) {
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
}
