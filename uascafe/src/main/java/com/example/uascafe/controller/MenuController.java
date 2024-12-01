package com.example.uascafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.uascafe.entity.Menu;
import com.example.uascafe.service.MenuService;

@Controller
@RequestMapping("/menu") // Menambahkan base path
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public String getAllMenus(Model model) {
        List<Menu> menuList = menuService.getAllMenu();
        model.addAttribute("menuList", menuList);
        return "Dashboard/menu-list";
    }

    @GetMapping("/add")
    public String showAddMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("tipeOptions", Menu.Tipe.values());
        model.addAttribute("kategoriOptions", Menu.Kategori.values());
        return "Dashboard/menu-form";
    }

    @PostMapping("/save")
    public String saveMenu(@ModelAttribute("menu") Menu menu, Model model, RedirectAttributes redirectAttributes) {
        // Validasi input kosong
        if (menu.getNamaMenu() == null || menu.getNamaMenu().isEmpty() ||
                menu.getTipe() == null || menu.getKategori() == null) {

            model.addAttribute("errorMessage", "Semua field wajib diisi dan harga harus valid.");
            model.addAttribute("tipeOptions", Menu.Tipe.values());
            model.addAttribute("kategoriOptions", Menu.Kategori.values());
            return "Dashboard/menu-form";
        }

        try {
            Menu.Tipe.valueOf(menu.getTipe().name());
            Menu.Kategori.valueOf(menu.getKategori().name());

            menuService.createOrUpdateMenu(menu);
            redirectAttributes.addFlashAttribute("successMessage", "Menu berhasil disimpan!");

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Invalid enum value for Tipe or Kategori");
            model.addAttribute("tipeOptions", Menu.Tipe.values());
            model.addAttribute("kategoriOptions", Menu.Kategori.values());
            return "Dashboard/menu-form";
        }
        return "redirect:/menu";
    }

    @GetMapping("/edit/{idMenu}")
    public String showEditMenuForm(@PathVariable("idMenu") int idMenu, Model model, 
            RedirectAttributes redirectAttributes) {
        Menu menu = menuService.getMenuById(idMenu)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + idMenu));
        model.addAttribute("menu", menu);
        model.addAttribute("tipeOptions", Menu.Tipe.values());
        model.addAttribute("kategoriOptions", Menu.Kategori.values());
        redirectAttributes.addFlashAttribute("success", "Menu berhasil diedit!");
        return "Dashboard/menu-form";
    }

    @GetMapping("/delete/{idMenu}")
    public String deleteMenu(@PathVariable("idMenu") int idMenu, RedirectAttributes redirectAttributes) {
        try {
            menuService.deleteMenu(idMenu);
            redirectAttributes.addFlashAttribute("berhasil", "Menu berhasil dihapus!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("gagal", "Gagal menghapus menu. Silakan coba lagi.");
        }
        return "redirect:/menu";
    }

}
