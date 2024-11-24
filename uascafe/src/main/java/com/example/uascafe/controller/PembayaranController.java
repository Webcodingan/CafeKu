package com.example.uascafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.uascafe.entity.Pembayaran;
import com.example.uascafe.service.PembayaranService;

import java.util.List;

@Controller
public class PembayaranController {

    @Autowired
    private PembayaranService pembayaranService;

    // Menampilkan daftar pembayaran
    @GetMapping("/pembayaran")
    public String getAllPembayaran(Model model) {
        List<Pembayaran> listPembayaran = pembayaranService.getAllPembayaran();
        model.addAttribute("listPembayaran", listPembayaran);
        return "Dashboard/listPembayaran"; // Mengarah ke file listPembayaran.html
    }

    // Menampilkan form untuk membuat pembayaran baru
    @GetMapping("/pembayaran/create")
    public String showCreatePembayaranForm(Model model) {
        model.addAttribute("pembayaran", new Pembayaran());
        return "Dashboard/createPembayaran"; // Mengarah ke file createPembayaran.html
    }

    // Menangani proses pembuatan pembayaran baru
    @PostMapping("/pembayaran/create")
    public String createPembayaran(@ModelAttribute Pembayaran pembayaran) {
        pembayaranService.savePembayaran(pembayaran); // Pajak otomatis diset di service
        return "redirect:/pembayaran"; // Kembali ke daftar pembayaran
    }

    // Menampilkan detail pembayaran berdasarkan ID
    @GetMapping("/pembayaran/{id}")
    public String getPembayaranById(@PathVariable int id, Model model) {
        return pembayaranService.getPembayaranById(id).map(pembayaran -> {
            model.addAttribute("pembayaran", pembayaran);
            return "Dashboard/detailPembayaran"; // Jika pembayaran ditemukan
        }).orElseGet(() -> {
            model.addAttribute("error", "Pembayaran dengan ID " + id + " tidak ditemukan.");
            return "redirect:/pembayaran"; // Jika pembayaran tidak ditemukan
        });
    }

    // Menghapus pembayaran berdasarkan ID
    @GetMapping("/pembayaran/delete/{id}")
    public String deletePembayaran(@PathVariable int id, Model model) {
        pembayaranService.deletePembayaran(id);
        model.addAttribute("message", "Pembayaran dengan ID " + id + " berhasil dihapus.");
        return "redirect:/pembayaran"; // Mengarahkan kembali ke daftar pembayaran
    }
}
