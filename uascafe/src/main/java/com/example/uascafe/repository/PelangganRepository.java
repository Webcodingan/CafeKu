package com.example.uascafe.repository;

import com.example.uascafe.entity.Pelanggan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PelangganRepository extends JpaRepository<Pelanggan, String> {
        Pelanggan findByEmailAndPassword(String email, String password);
}
