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

    // // Menampilkan form untuk membuat pesanan baru
    // @GetMapping("/pesanan/create")
    // public String createPesananForm(Model model) {
    //     model.addAttribute("pesanan", new Pesanan());
    //     // Menampilkan daftar menu dan pembayaran untuk dipilih
    //     model.addAttribute("menuList", menuService.getAllMenu());
    //     model.addAttribute("pembayaranList", pembayaranService.getAllPembayaran());
    //     return "Dashboard/createPesanan";
    // }

    // // Menyimpan pesanan baru
    // @PostMapping("/pesanan/save")
    // public String savePesanan(
    //         @ModelAttribute Pesanan pesanan,
    //         @RequestParam("menuId") int menuId,
    //         @RequestParam("pembayaranId") int pembayaranId,
    //         Model model) {
    //     // Mendapatkan Menu dan Pembayaran berdasarkan ID
    //     Optional<Menu> menu = menuService.getMenuById(menuId);
    //     Optional<Pembayaran> pembayaran = pembayaranService.getPembayaranById(pembayaranId);

    //     if (menu.isPresent() && pembayaran.isPresent()) {
    //         pesanan.setMenu(menu.get());
    //         pesanan.setPembayaran(pembayaran.get());
    //         pesanan.setTanggal(LocalDateTime.now()); // Tetapkan tanggal otomatis di backend
    //         pesananService.createPesanan(pesanan);
    //         return "redirect:/pesanan";
    //     }

    //     model.addAttribute("errorMessage", "Menu atau Pembayaran tidak ditemukan");
    //     model.addAttribute("menuList", menuService.getAllMenu());
    //     model.addAttribute("pembayaranList", pembayaranService.getAllPembayaran());
    //     return "Dashboard/createPesanan";
    // }

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
