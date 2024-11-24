package com.example.uascafe.service;

import com.example.uascafe.entity.Karyawan;
import com.example.uascafe.repository.KaryawanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KaryawanService {

    @Autowired
    private KaryawanRepository karyawanRepository;

    // Ambil semua karyawan
    public List<Karyawan> getAllKaryawan() {
        return karyawanRepository.findAll();
    }

    // Cari karyawan berdasarkan email
    public Karyawan findByEmail(String email) {
        return karyawanRepository.findByEmail(email);
    }

    // Simpan atau update karyawan
    public Karyawan saveKaryawan(Karyawan karyawan) {
        return karyawanRepository.save(karyawan);
    }

    // Hapus karyawan berdasarkan ID
    public void deleteKaryawan(String id) {
        karyawanRepository.deleteById(id);
    }
}
