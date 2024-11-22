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

    public List<Pembayaran> getAllPembayaran() {
        return pembayaranRepository.findAll();
    }

    public Optional<Pembayaran> getPembayaranById(int id) {
        return pembayaranRepository.findById(id);
    }

    public Pembayaran savePembayaran(Pembayaran pembayaran) {
        return pembayaranRepository.save(pembayaran);
    }

    public void deletePembayaran(int id) {
        pembayaranRepository.deleteById(id);
    }
}
