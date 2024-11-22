package com.example.uascafe.controller;

import com.example.uascafe.entity.Menu;
import com.example.uascafe.entity.Pesanan;
import com.example.uascafe.repository.MenuRepository;
import com.example.uascafe.repository.PesananRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PesananController {

    @Autowired
    private PesananRepository pesananRepository;

    @Autowired
    private MenuRepository menuRepository;

    // Menampilkan daftar pesanan
    @GetMapping("/pesanan")
    public String listPesanan(Model model) {
        List<Pesanan> pesananList = pesananRepository.findAll();
        model.addAttribute("pesananList", pesananList);
        return "Dashboard/pesanan-list";
    }

    // Menampilkan detail pesanan berdasarkan id
    @GetMapping("/pesanan/{id}")
    public String detailPesanan(@PathVariable("id") int id, Model model) {
        Optional<Pesanan> pesanan = pesananRepository.findById(id);
        if (pesanan.isPresent()) {
            model.addAttribute("pesanan", pesanan.get());
            return "Dashboard/detail-pesanan";
        }
        return "redirect:/pesanan";
    }

    // Menampilkan form untuk membuat pesanan baru
    @GetMapping("/pesanan/create")
    public String createPesananForm(Model model) {
        model.addAttribute("pesanan", new Pesanan());
        model.addAttribute("menuList", menuRepository.findAll());
        return "pesanan-form"; // Sesuaikan dengan nama file template
    }

    // Menyimpan pesanan baru
    @PostMapping("/pesanan/save")
    public String savePesanan(@ModelAttribute Pesanan pesanan, @RequestParam("menuId") int menuId) {
        Menu menu = menuRepository.findById(menuId).orElse(null);
        if (menu != null) {
            pesanan.setMenu(menu);
            pesanan.setTanggal(LocalDateTime.now());
            pesananRepository.save(pesanan);
        }
        return "redirect:/pesanan";
    }

    // Menghapus pesanan
    @GetMapping("/pesanan/delete/{id}")
    public String deletePesanan(@PathVariable("id") int id) {
        pesananRepository.deleteById(id);
        return "redirect:/pesanan";
    }
}
