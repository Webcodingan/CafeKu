package com.example.uascafe.repository;
import com.example.uascafe.entity.Karyawan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface KaryawanRepository extends JpaRepository<Karyawan, String> {
        Karyawan findByEmailAndPassword(String email, String password);
}
