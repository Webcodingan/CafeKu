package com.example.uascafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.uascafe.entity.Pelanggan;
import com.example.uascafe.service.PelangganService;
import java.util.List;
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

        Pelanggan pelanggan = pelangganService.findByEmail(email);

        if (pelanggan != null && pelangganService.validatePassword(password, pelanggan.getPassword())) {
            // Simpan data login ke session
            session.setAttribute("pelangganId", pelanggan.getId());
            session.setAttribute("pelangganNama", pelanggan.getNama());
            session.setAttribute("loggedInUserEmail", pelanggan.getEmail()); // Menyimpan email ke session

            String notifikasi = pelanggan.notifikasiLogin();
            model.addAttribute("notifikasi", notifikasi);

            return "redirect:/Da'cof"; // Redirect ke halaman dashboard
        }

        model.addAttribute("error", "Email atau password salah!");
        return "login-pelanggan.html";
    }

    @GetMapping("/Da'cof")
    public String pelangganDashboard(HttpSession session, Model model) {
        // Periksa apakah pelanggan sudah login
        String pelangganId = (String) session.getAttribute("pelangganId");

        if (pelangganId == null) {
            return "redirect:/pelanggan/login"; // Redirect ke login jika belum login
        }

        String pelangganNama = (String) session.getAttribute("pelangganNama");
        model.addAttribute("nama", pelangganNama); // Tampilkan nama di dashboard
        model.addAttribute("success", "Anda berhasil login!");
        return "index.html"; // Template dashboard
    }

    // Route untuk logout
    @PostMapping("/pelanggan/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Hapus semua data di session
        return "index.html"; // Redirect ke halaman login
    }

    // Menampilkan semua rating pelanggan
    @GetMapping("/rating")
    public String listRating(Model model) {
        List<Pelanggan> pelangganList = pelangganService.getAllPelanggan();
        model.addAttribute("pelangganList", pelangganList);
        return "Dashboard/rating"; // Mengarah ke template Dashboard/rating.html
    }

    // Menampilkan detail rating berdasarkan ID pelanggan
    @GetMapping("/rating/{id}")
    public String detailRating(@PathVariable("id") String id, Model model) {
        Pelanggan pelanggan = pelangganService.findById(id)
                .orElseThrow(() -> new RuntimeException("Pelanggan dengan ID " + id + " tidak ditemukan."));

        model.addAttribute("pelanggan", pelanggan);
        return "Dashboard/detailRating"; // Mengarah ke template Dashboard/detailRating.html
    }
    
    @PostMapping("/rating/hapus/{id}")
    public String hapusRating(@PathVariable("id") String id) {
        pelangganService.hapusPelanggan(id);
        return "redirect:/rating"; // Redirect ke halaman daftar rating setelah menghapus
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

        // Validasi apakah email sudah digunakan
        if (pelangganService.findByEmail(email) != null) {
            model.addAttribute("error", "Email sudah digunakan! Silahkan isi dengan alamat email lain.");
            return "register-pelanggan.html";
        }

        // Hash password menggunakan MD5
        String hashedPassword = pelangganService.hashPassword(password);

        // Generate ID baru untuk pelanggan
        String newId = pelangganService.generateId();

        // Membuat objek pelanggan
        Pelanggan pelanggan = new Pelanggan();
        pelanggan.setId(newId); // Set ID baru
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
