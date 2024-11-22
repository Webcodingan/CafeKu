package com.example.uascafe.controller;

import com.example.uascafe.entity.Menu;
import com.example.uascafe.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    public String getAllMenus(Model model) {
        List<Menu> menuList = menuService.getAllMenus();
        model.addAttribute("menuList", menuList);
        return "Dashboard/menu-list";
    }

    @GetMapping("/menu/add")
    public String showAddMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("tipeOptions", Menu.Tipe.values());
        model.addAttribute("kategoriOptions", Menu.Kategori.values());
        return "Dashboard/menu-form";
    }

    @PostMapping("/menu/save")
    public String saveMenu(@ModelAttribute("menu") Menu menu) {
        try {
            Menu.Tipe.valueOf(menu.getTipe().name());
            Menu.Kategori.valueOf(menu.getKategori().name());

            menuService.createOrUpdateMenu(menu);
        } catch (IllegalArgumentException e) {
            return "redirect:/menu/add?error=invalid_enum";
        }

        return "redirect:/menu";
    }

    @GetMapping("/menu/edit/{idMenu}")
    public String showEditMenuForm(@PathVariable("idMenu") int idMenu, Model model) {
        Menu menu = menuService.getMenuById(idMenu);
        model.addAttribute("menu", menu);
        model.addAttribute("tipeOptions", Menu.Tipe.values());
        model.addAttribute("kategoriOptions", Menu.Kategori.values());
        return "Dashboard/menu-form";
    }

    @GetMapping("/menu/delete/{idMenu}")
    public String deleteMenu(@PathVariable("idMenu") int idMenu) {
        menuService.deleteMenu(idMenu);
        return "redirect:/menu";
    }
}
