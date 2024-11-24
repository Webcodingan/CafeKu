package com.example.uascafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.uascafe.entity.Pelanggan;
import com.example.uascafe.service.PelangganService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PelangganController {

    @Autowired
    private PelangganService pelangganService;

    // Route untuk menampilkan daftar pelanggan
    @GetMapping("/pelanggan/list")
    public String getAllPelanggan(Model model) {
        model.addAttribute("pelangganList", pelangganService.getAllPelanggan());
        return "Dashboard/listPelanggan"; // Mengarah ke template listPelanggan.html
    }

    // Route untuk menampilkan form login pelanggan
    @GetMapping("/pelanggan/login")
    public String showLoginForm() {
        return "login-pelanggan.html"; // Mengarah ke template login-pelanggan.html
    }

    // Route untuk proses login pelanggan
    @PostMapping("/pelanggan/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        // Hash password using MD5
        String hashedPassword = pelangganService.hashPassword(password);

        Pelanggan pelanggan = pelangganService.findByEmail(email);
        // Validasi login dengan MD5 password comparison
        if (pelanggan != null && pelanggan.getPassword().equals(hashedPassword)) {
            // Simpan data login ke session
            session.setAttribute("pelangganId", pelanggan.getId());
            session.setAttribute("pelangganNama", pelanggan.getNama());
            return "redirect:/Da'cof"; // Redirect ke halaman dashboard
        }
        model.addAttribute("error", "Email atau password salah!");
        return "login-pelanggan.html";
    }

    @GetMapping("/Da'cof")
    public String pelangganDashboard(HttpSession session, Model model) {
        // Periksa apakah pelanggan sudah login
        String pelangganNama = (String) session.getAttribute("pelangganNama");

        if (pelangganNama == null) {
            return "redirect:/pelanggan/login"; // Redirect ke login jika belum login
        }
        model.addAttribute("nama", pelangganNama); // Tampilkan nama di dashboard
        return "index.html"; // Template dashboard
    }

    // Route untuk logout
    @PostMapping("/pelanggan/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Hapus semua data di session
        return "index.html"; // Redirect ke halaman login
    }

    // Route untuk menampilkan form register pelanggan
    @GetMapping("/pelanggan/register")
    public String showRegisterForm() {
        return "register-pelanggan.html"; // Mengarah ke template register-pelanggan.html
    }

    // Route untuk proses register pelanggan
    @PostMapping("/pelanggan/register")
    public String register(
            @RequestParam String nama,
            @RequestParam String email,
            @RequestParam String notelp,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        // Validasi jika password dan confirm password tidak sama
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Password dan Confirm Password tidak cocok!");
            return "register-pelanggan.html";
        }

        // Hash password menggunakan MD5
        String hashedPassword = pelangganService.hashPassword(password);

        // Membuat objek pelanggan dan menyimpannya
        Pelanggan pelanggan = new Pelanggan();
        pelanggan.setNama(nama);
        pelanggan.setEmail(email);
        pelanggan.setnotelp(notelp);
        pelanggan.setPassword(hashedPassword);

        // Simpan pelanggan ke database
        pelangganService.savePelanggan(pelanggan);

        model.addAttribute("success", "Registrasi berhasil! Silakan login.");
        return "login-pelanggan.html"; // Mengarah ke halaman login setelah registrasi berhasil
    }
}
