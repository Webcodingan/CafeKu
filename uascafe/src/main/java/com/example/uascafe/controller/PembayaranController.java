package com.example.uascafe.controller;

import com.example.uascafe.entity.Pembayaran;
import com.example.uascafe.entity.Pesanan;
import com.example.uascafe.repository.PesananRepository;
import com.example.uascafe.service.PembayaranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PembayaranController {

    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private PesananRepository pesananRepository;

    // Menampilkan daftar pembayaran
    @GetMapping("/pembayaran")
    public String listPembayaran(Model model) {
        List<Pembayaran> pembayaranList = pembayaranService.getAllPembayaran();
        model.addAttribute("pembayaranList", pembayaranList);
        return "Dashboard/listPembayaran"; // Sesuaikan dengan nama template
    }

    // Menampilkan form untuk membuat pembayaran baru
    @GetMapping("/pembayaran/create")
    public String createPembayaranForm(Model model) {
        model.addAttribute("pembayaran", new Pembayaran());
        model.addAttribute("pesananList", pesananRepository.findAll()); // Daftar pesanan untuk relasi
        return "Dashboard/pembayaran-form"; // Sesuaikan dengan nama template
    }

    // Menyimpan pembayaran baru
    @PostMapping("/pembayaran/save")
    public String savePembayaran(@ModelAttribute Pembayaran pembayaran, @RequestParam("idOrder") int idOrder) {
        Optional<Pesanan> pesanan = pesananRepository.findById(idOrder);
        if (pesanan.isPresent()) {
            pembayaran.setPesanan(pesanan.get());
            pembayaranService.savePembayaran(pembayaran);
        }
        return "redirect:/pembayaran";
    }

    // Menampilkan detail pembayaran berdasarkan ID
    @GetMapping("/pembayaran/{id}")
    public String detailPembayaran(@PathVariable("id") int id, Model model) {
        Optional<Pembayaran> pembayaran = pembayaranService.getPembayaranById(id);
        if (pembayaran.isPresent()) {
            model.addAttribute("pembayaran", pembayaran.get());
            return "Dashboard/detailPembayaran"; // Sesuaikan dengan nama template
        }
        return "redirect:/pembayaran";
    }

    // Menghapus pembayaran berdasarkan ID
    @GetMapping("/pembayaran/delete/{id}")
    public String deletePembayaran(@PathVariable("id") int id) {
        pembayaranService.deletePembayaran(id);
        return "redirect:/pembayaran";
    }
}
