package com.example.uascafe.service;

// import com.example.uascafe.entity.Menu;
import com.example.uascafe.entity.Pesanan;
import com.example.uascafe.repository.PesananRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PesananService {

    @Autowired
    private PesananRepository pesananRepository;

    // Ambil semua pesanan
    public List<Pesanan> getAllPesanan() {
        return pesananRepository.findAll();
    }

    // Ambil detail pesanan berdasarkan ID
    public Optional<Pesanan> getPesananById(int id) {
        return pesananRepository.findById(id);
    }

    // Simpan pesanan baru
    public Pesanan createPesanan(Pesanan pesanan) {
        pesanan.setTanggal(LocalDateTime.now());
        return pesananRepository.save(pesanan);
    }

    // Hapus pesanan berdasarkan ID
    public void deletePesanan(int id) {
        if (!pesananRepository.existsById(id)) {
            throw new IllegalArgumentException("Pesanan tidak ditemukan dengan ID: " + id);
        }
        pesananRepository.deleteById(id);
    }
}
