package com.example.uascafe.interfaces;

public interface CetakStruk {
    // Konstanta
    String kasir = "Kasir 1"; 
    String antrian = "001"; 
    double pajak = 0.1;

    // Metode
    double hasil(); // Menghitung hasil akhir (total harga + pajak)

    void struk(); // Mencetak struk
}
