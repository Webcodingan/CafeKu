package com.example.uascafe.entity;

import jakarta.persistence.*;

@Entity

@Table(name = "pembayaran")
public class Pembayaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pembayaran")
    private int idPembayaran;

    @Column(name = "metode_payment", nullable = false)
    private String metodePayment;

    @Column(name = "total_harga", nullable = false)
    private int totalHarga;

    @OneToOne
    @JoinColumn(name = "id_order", nullable = false)
    private Pesanan pesanan;

    // Getters and Setters
    public int getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(int idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public String getMetodePayment() {
        return metodePayment;
    }

    public void setMetodePayment(String metodePayment) {
        this.metodePayment = metodePayment;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public Pesanan getPesanan() {
        return pesanan;
    }

    public void setPesanan(Pesanan pesanan) {
        this.pesanan = pesanan;
    }
}
