package com.example.uascafe.service;

import com.example.uascafe.entity.Menu;
import com.example.uascafe.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    // Mendapatkan semua menu
    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    // Mendapatkan menu berdasarkan ID
    public Optional<Menu> getMenuById(int idMenu) {
        return menuRepository.findById(idMenu);
    }

    // Membuat atau memperbarui menu
    public Menu createOrUpdateMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    // Menghapus menu berdasarkan ID
    public void deleteMenu(int idMenu) {
        if (!menuRepository.existsById(idMenu)) {
            throw new IllegalArgumentException("Menu not found with ID: " + idMenu);
        }
        menuRepository.deleteById(idMenu);
    }
}
