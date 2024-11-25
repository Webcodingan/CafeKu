package com.example.uascafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import com.example.uascafe.entity.Menu;
import com.example.uascafe.entity.Pelanggan;
import com.example.uascafe.entity.Pesanan;
import com.example.uascafe.entity.Pembayaran;
import com.example.uascafe.service.MenuService;
import com.example.uascafe.service.PembayaranService;
import com.example.uascafe.service.PesananService;
import com.example.uascafe.service.PelangganService;

// import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;
import java.time.LocalDateTime;

@Controller
public class HomeController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private PesananService pesananService;

    @Autowired
    private PelangganService pelangganService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/allmenu")
    public String menu(Model model, HttpSession session) {
        // Check if the user is logged in by verifying session
        if (session.getAttribute("pelangganId") == null) {
            return "redirect:/pelanggan/login"; // Redirect to login if not logged in
        }

        List<Menu> menuList = menuService.getAllMenu(); // Mengambil data dari service yang sama
        model.addAttribute("menuList", menuList); // Menambahkan data ke model
        return "menu"; // Mengarahkan ke halaman menu.html
    }

    // Menampilkan detail Menu berdasarkan id
    @GetMapping("/menu/detail/{id}")
    public String detailMenu(@PathVariable("id") int id, Model model, HttpSession session) {
        // Check if the user is logged in by verifying session
        if (session.getAttribute("pelangganId") == null) {
            return "redirect:/pelanggan/login"; // Redirect to login if not logged in
        }

        Optional<Menu> menu = menuService.getMenuById(id); // Mengambil menu berdasarkan ID
        if (menu.isPresent()) {
            model.addAttribute("menu", menu.get()); // Menambahkan menu ke model
            return "detailMenu"; // Mengarahkan ke detailMenu.html
        }
        return "redirect:/allmenu"; // Jika menu tidak ditemukan, redirect ke halaman daftar menu
    }

    @GetMapping("/pesanan/create")
    public String createPesananForm(Model model, HttpSession session) {
        // Cek apakah pelanggan sudah login dengan memeriksa session
        if (session.getAttribute("pelangganId") == null) {
            return "redirect:/pelanggan/login"; // Redirect ke login jika tidak login
        }

        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");

        if (loggedInUserEmail == null) {
            // Jika email tidak ada dalam session, redirect ke login
            return "redirect:/pelanggan/login";
        }

        // Mencari pelanggan berdasarkan email
        Pelanggan loggedInPelanggan = pelangganService.findByEmail(loggedInUserEmail);

        if (loggedInPelanggan == null) {
            // Jika pelanggan tidak ditemukan, redirect ke login
            return "redirect:/pelanggan/login";
        }

        // Menambahkan objek pesanan, menu, dan pembayaran ke model
        model.addAttribute("pesanan", new Pesanan());
        model.addAttribute("menuList", menuService.getAllMenu());
        model.addAttribute("pembayaranList", pembayaranService.getAllPembayaran());
        model.addAttribute("loggedInPelanggan", loggedInPelanggan);

        System.out.println("Nama Pelanggan: " + loggedInPelanggan.getNama());

        return "createPesanan.html";
    }

    // // Menyimpan pesanan baru
    @PostMapping("/pesanan/save")
    public String savePesanan(
            @ModelAttribute Pesanan pesanan,
            @RequestParam("menuId") int menuId,
            @RequestParam("pembayaranId") int pembayaranId,
            @RequestParam("userId") String userId, // Pastikan ini sesuai dengan form
            Model model) {

        // Cari pelanggan berdasarkan userId
        Optional<Pelanggan> pelanggan = pelangganService.findById(userId);

        if (pelanggan.isPresent()) {
            pesanan.setPelanggan(pelanggan.get()); // Tetapkan pelanggan ke pesanan
        } else {
            model.addAttribute("errorMessage", "Pelanggan tidak ditemukan");
            return "createPesanan"; // Kembali ke form jika pelanggan tidak ditemukan
        }

        // Mendapatkan Menu dan Pembayaran berdasarkan ID
        Optional<Menu> menu = menuService.getMenuById(menuId);
        Optional<Pembayaran> pembayaran = pembayaranService.getPembayaranById(pembayaranId);

        if (menu.isPresent() && pembayaran.isPresent()) {
            pesanan.setMenu(menu.get());
            pesanan.setPembayaran(pembayaran.get());
            pesanan.setTanggal(LocalDateTime.now());
            pesanan = pesananService.createPesanan(pesanan); // Simpan pesanan ke database

            // Redirect ke halaman detailPembayaran dengan ID pesanan
            return "redirect:/detailPembayaran?idPesanan=" + pesanan.getIdOrder(); // Sertakan idPesanan
        }

        // Jika menu atau pembayaran tidak ditemukan, kembalikan error
        model.addAttribute("errorMessage", "Menu atau Pembayaran tidak ditemukan");
        model.addAttribute("menuList", menuService.getAllMenu());
        model.addAttribute("pembayaranList", pembayaranService.getAllPembayaran());
        return "createPesanan";
    }

    @GetMapping("/detailPembayaran")
    public String detailPembayaran(@RequestParam("idPesanan") int idPesanan, Model model) {
        // Cari pesanan berdasarkan ID
        Optional<Pesanan> pesananOptional = pesananService.getPesananById(idPesanan);

        if (pesananOptional.isPresent()) {
            Pesanan pesanan = pesananOptional.get();

            // Hitung total harga
            int totalHarga = pesanan.getMenu().getHarga() * pesanan.getKuantitas();

            // Tambahkan atribut ke model untuk ditampilkan di view
            model.addAttribute("customerName", pesanan.getPelanggan().getNama());
            model.addAttribute("menuName", pesanan.getMenu().getNamaMenu());
            model.addAttribute("price", totalHarga);
            model.addAttribute("note", pesanan.getNote());
            model.addAttribute("quantity", pesanan.getKuantitas());
            model.addAttribute("rating", pesanan.getPelanggan().getRating());

            // Render halaman detail pembayaran
            return "detailPembayaran"; // Pastikan nama file HTML sesuai
        }

        // Jika pesanan tidak ditemukan
        model.addAttribute("errorMessage", "Pesanan tidak ditemukan");
        return "redirect:/allmenu"; // Redirect ke menu jika error
    }

    @GetMapping("/menu/dessert")
    public String dessert(Model model) {
        List<Menu> menuList = menuService.getAllMenu();
        List<Menu> dessertList = menuList.stream()
                .filter(menu -> menu.getTipe() == Menu.Tipe.DESSERT)
                .collect(Collectors.toList());

        model.addAttribute("menuList", dessertList);
        return "dessert";
    }

    @GetMapping("/menu/drink")
    public String drink(Model model) {
        List<Menu> menuList = menuService.getAllMenu();
        List<Menu> drinkList = menuList.stream()
                .filter(menu -> menu.getTipe() == Menu.Tipe.COFFEE)
                .collect(Collectors.toList());

        model.addAttribute("drinkList", drinkList);
        return "drink";
    }

    @GetMapping("/menu/fresh")
    public String fresh(Model model) {
        List<Menu> menuList = menuService.getAllMenu();
        List<Menu> drinkList = menuList.stream()
                .filter(menu -> menu.getKategori() == Menu.Kategori.FRESH)
                .collect(Collectors.toList());

        model.addAttribute("drinkList", drinkList);
        return "fresh.html";
    }

    @GetMapping("/menu/bold")
    public String bold(Model model) {
        List<Menu> menuList = menuService.getAllMenu();
        List<Menu> drinkList = menuList.stream()
                .filter(menu -> menu.getKategori() == Menu.Kategori.BOLD)
                .collect(Collectors.toList());

        model.addAttribute("drinkList", drinkList);
        return "bold.html";
    }

    @GetMapping("/menu/sweet")
    public String sweet(Model model) {
        List<Menu> menuList = menuService.getAllMenu();
        List<Menu> drinkList = menuList.stream()
                .filter(menu -> menu.getKategori() == Menu.Kategori.SWEET)
                .collect(Collectors.toList());

        model.addAttribute("drinkList", drinkList);
        return "sweet.html";
    }

    @GetMapping("/menu/light")
    public String light(Model model) {
        List<Menu> menuList = menuService.getAllMenu();
        List<Menu> drinkList = menuList.stream()
                .filter(menu -> menu.getKategori() == Menu.Kategori.LIGHT)
                .collect(Collectors.toList());

        model.addAttribute("drinkList", drinkList);
        return "light.html";
    }
}
