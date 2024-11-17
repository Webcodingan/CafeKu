package com.example.uascafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.uascafe.service.UserService;
import com.example.uascafe.entity.Karyawan;
import com.example.uascafe.entity.Pelanggan;
import com.example.uascafe.entity.User;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/CafeKu")
    public String Layoutweb() {
        return "index.html"; 
    }

    // Form login untuk karyawan
    @GetMapping("/login-karyawan")
    public String showLoginKaryawan() {
        return "login-karyawan.html"; // Return ke template login-karyawan.html
    }

    // Form login untuk pelanggan
    @GetMapping("/login-pelanggan")
    public String showLoginPelanggan() {
        return "login-pelanggan.html"; // Return ke template login-pelanggan.html
    }

    @PostMapping("/login-karyawan")
    public String loginKaryawan(String email, String password, Model model) {
        User user = userService.login(email, password);
        if (user == null) {
            model.addAttribute("error", "Invalid credentials");
            return "login-karyawan";
        }

        if (user instanceof Karyawan) {
            return "redirect:/dashboard-karyawan";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login-karyawan";
        }
    }

    @PostMapping("/login-pelanggan")
    public String loginPelanggan(String email, String password, Model model) {
        User user = userService.login(email, password);
        if (user == null) {
            model.addAttribute("error", "Invalid credentials");
            return "login-pelanggan";
        }

        if (user instanceof Pelanggan) {
            return "redirect:/dashboard-pelanggan";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login-pelanggan";
        }
    }

    // Dashboard untuk karyawan
    @GetMapping("/dashboard-karyawan")
    public String showDashboardKaryawan(Model model) {
        return "dashboard-karyawan.html"; // Mengarahkan ke dashboard-karyawan.html
    }

    // Dashboard untuk pelanggan
    @GetMapping("/dashboard-pelanggan")
    public String showDashboardPelanggan(Model model) {
        return "dashboard-pelanggan.html"; // Mengarahkan ke dashboard-pelanggan.html
    }

    // Proses logout
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login-karyawan"; // Redirect ke halaman login setelah logout
    }
}
