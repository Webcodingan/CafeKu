package com.example.uascafe.entity;

// import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "menu") // Sesuaikan dengan nama tabel
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    @Column(name = "id_menu")
    private int idMenu;

    @Column(name = "nama_menu")
    private String namaMenu;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipe")
    private Tipe tipe;

    @Enumerated(EnumType.STRING)
    @Column(name = "kategori")
    private Kategori kategori;

    @Column(name = "harga")
    private int harga;

    // Enums for Tipe and Kategori
    public enum Tipe {
        TEA, COFFEE, JUICE, MILK, DESSERT
    }

    public enum Kategori {
        SWEET, FRESH, BOLD, LIGHT
    }

    // Getters and Setters
    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public Tipe getTipe() {
        return tipe;
    }

    public void setTipe(Tipe tipe) {
        this.tipe = tipe;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}