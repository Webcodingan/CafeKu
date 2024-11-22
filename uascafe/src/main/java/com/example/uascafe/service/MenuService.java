package com.example.uascafe.service;

import com.example.uascafe.entity.Menu;
import com.example.uascafe.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(int idMenu) {
        return menuRepository.findById(idMenu).orElse(null);
    }

    public Menu createOrUpdateMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public void deleteMenu(int idMenu) {
        menuRepository.deleteById(idMenu);
    }
}
