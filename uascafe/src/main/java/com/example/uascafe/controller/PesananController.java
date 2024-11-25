package com.example.uascafe.controller;

import com.example.uascafe.entity.Pesanan;
// import com.example.uascafe.entity.Menu;
// import com.example.uascafe.entity.Pembayaran;
import com.example.uascafe.service.PesananService;
// import com.example.uascafe.service.MenuService;
// import com.example.uascafe.service.PembayaranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PesananController {

    @Autowired
    private PesananService pesananService;

    // @Autowired
    // private MenuService menuService;

    // @Autowired
    // private PembayaranService pembayaranService;

    // Menampilkan daftar pesanan
    @GetMapping("/pesanan")
    public String listPesanan(Model model) {
        List<Pesanan> pesananList = pesananService.getAllPesanan();
        model.addAttribute("pesananList", pesananList);
        return "Dashboard/pesanan-list";
    }

    // Menampilkan detail pesanan berdasarkan id
    @GetMapping("/pesanan/{id}")
    public String detailPesanan(@PathVariable("id") int id, Model model) {
        Optional<Pesanan> pesanan = pesananService.getPesananById(id);
        if (pesanan.isPresent()) {
            model.addAttribute("pesanan", pesanan.get());
            return "Dashboard/detail-pesanan";
        }
        return "redirect:/pesanan";
    }

    // Menghapus pesanan
    @GetMapping("/pesanan/delete/{id}")
    public String deletePesanan(@PathVariable("id") int id, Model model) {
        try {
            pesananService.deletePesanan(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/pesanan";
    }
}
