package com.example.uascafe.service;

import com.example.uascafe.entity.Pembayaran;
import com.example.uascafe.repository.PembayaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PembayaranService {

    @Autowired
    private PembayaranRepository pembayaranRepository;

    // Mendapatkan semua pembayaran
    public List<Pembayaran> getAllPembayaran() {
        return pembayaranRepository.findAll();
    }

    // Mendapatkan pembayaran berdasarkan ID
    public Optional<Pembayaran> getPembayaranById(int id) {
        return pembayaranRepository.findById(id);
    }

    // Membuat atau memperbarui pembayaran
    public Pembayaran savePembayaran(Pembayaran pembayaran) {
        if (pembayaran.getPajak() != 0.11) {
            pembayaran.setPajak(0.11);
        }
        return pembayaranRepository.save(pembayaran);
    }

    // Menghapus pembayaran berdasarkan ID
    public void deletePembayaran(int id) {
        pembayaranRepository.deleteById(id);
    }
}
