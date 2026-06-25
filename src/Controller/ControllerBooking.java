/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DAOBooking;
import DAO.DAOPeminjam;
import DAO.DAORuangan;
import DAOInterface.IDAOBooking;
import DAOInterface.IDAOPeminjam;
import DAOInterface.IDAORuangan;
import Model.Booking;
import Model.Peminjam;
import Model.Ruangan;
import Model.TabelModelBooking;
import View.BookingForm;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class ControllerBooking {
    BookingForm frame; 
    IDAOBooking iBooking;
    IDAOPeminjam iPeminjam;
    IDAORuangan iRuangan;
    List<Booking> lstBo;
    private int selectedId = 0;
    private final BookingForm frmBooking;
    
    public ControllerBooking(BookingForm frmBooking) throws SQLException {
        this.frmBooking = frmBooking;
        iBooking = new DAOBooking();
        iPeminjam = new DAOPeminjam(); 
        iRuangan = new DAORuangan();  
        
        isiComboPeminjam();
        isiComboRuangan();
        
        kondisiAwal();
    }
    
    private void isiComboPeminjam() {
        // "Ngobrol" dengan DAOPeminjam untuk ambil semua nama
        List<Peminjam> list = iPeminjam.getAll(); 
        frmBooking.getcbPeminjam().removeAllItems();
        frmBooking.getcbPeminjam().addItem("- Pilih Peminjam -");
        for (Peminjam p : list) {
            frmBooking.getcbPeminjam().addItem(p.getNama_peminjam());
        }
    }

    private void isiComboRuangan() {
    // Ambil semua data ruangan dari database lewat DAO
        List<Ruangan> list = iRuangan.getAll();

        //Bersihkan dulu ComboBox-nya
        frmBooking.getcbRuangan().removeAllItems();
        frmBooking.getcbRuangan().addItem("- Pilih Ruangan -");

        //Lakukan perulangan
        for (Ruangan r : list) {
            if (r.getStatus().equalsIgnoreCase("Bisa Digunakan")) {
                frmBooking.getcbRuangan().addItem(r.getNama_ruangan());
            }
        }
    }
    
    public void isiTable(){
        lstBo = iBooking.getAll();
        TabelModelBooking tabelBo = new TabelModelBooking(lstBo);
        frmBooking.getTabelData().setModel(tabelBo);
    }
    
    public void isiField(int row) {
        selectedId = lstBo.get(row).getId_booking();
        frmBooking.gettxtKodeBooking().setText(lstBo.get(row).getKode_booking());
        frmBooking.getcbPeminjam().setSelectedItem(lstBo.get(row).getNama_peminjam());
        frmBooking.getcbRuangan().setSelectedItem(lstBo.get(row).getNama_ruangan());
        frmBooking.gettxtTanggalBooking().setText(lstBo.get(row).getTanggal_booking());
        frmBooking.gettxtTujuan().setText(lstBo.get(row).getTujuan());

        // Atur tombol
        frmBooking.getbuttonSimpan().setEnabled(false);
        frmBooking.getbuttonUbah().setEnabled(true);
        frmBooking.getbuttonHapus().setEnabled(true);
        frmBooking.getbuttonReset().setEnabled(true);
    }
    
    public void reset(){
        frmBooking.gettxtKodeBooking().setText("");
        frmBooking.getcbPeminjam().setSelectedIndex(0);
        frmBooking.getcbRuangan().setSelectedIndex(0);
        frmBooking.gettxtTanggalBooking().setText("yyyy-mm-dd");
        frmBooking.gettxtTujuan().setText("");
        selectedId = 0;
        
        frmBooking.getbuttonSimpan().setEnabled(true);
        frmBooking.getbuttonUbah().setEnabled(false);
    }
    
    public void kondisiAwal() {
        reset();
        frmBooking.gettxtTanggalBooking().setText("yyyy-mm-dd");
        frmBooking.getbuttonSimpan().setEnabled(true);
        frmBooking.getbuttonUbah().setEnabled(false);
        frmBooking.getbuttonHapus().setEnabled(true);
        frmBooking.getbuttonReset().setEnabled(true);
    }
    
    public boolean cekValidasi() {
        // 1. Ambil data dari Form Booking
        String kode = frmBooking.gettxtKodeBooking().getText();
        String peminjam = frmBooking.getcbPeminjam().getSelectedItem().toString();
        String ruangan = frmBooking.getcbRuangan().getSelectedItem().toString();
        String tanggal = frmBooking.gettxtTanggalBooking().getText();
        String tujuan = frmBooking.gettxtTujuan().getText();

        // 2. Cek apakah ada yang kosong atau belum dipilih
        if (kode.trim().isEmpty() || 
            peminjam.equals("- Pilih Peminjam -") || 
            ruangan.equals("- Pilih Ruangan -") || 
            tanggal.trim().isEmpty() || 
            tujuan.trim().isEmpty()) {

            javax.swing.JOptionPane.showMessageDialog(frmBooking, "Semua data harus diisi dan dipilih!");
            return false; // Validasi gagal
        }

        return true; // Validasi berhasil
    }
    
    public void insert() {
        // 1. Validasi Input Kosong (Memanggil fungsi cekValidasi yang baru dibuat)
        if (!cekValidasi()) {
            return; // Berhenti jika ada data kosong
        }

        // 2. Ambil data dari Form untuk pengecekan ke Database
        String kodeInput = frmBooking.gettxtKodeBooking().getText();
        String ruanganTerpilih = frmBooking.getcbRuangan().getSelectedItem().toString();
        String tglInput = frmBooking.gettxtTanggalBooking().getText();

        // 3. Validasi Database Lapis 1: Cek Kode Booking Duplikat
        if (iBooking.cekKode(kodeInput)) {
            JOptionPane.showMessageDialog(frmBooking, "Kode Booking '" + kodeInput + "' sudah digunakan!");
            return;
        }

        // 4. Validasi Database Lapis 2: Cek Tabrakan Jadwal (Anti Bentrok)
        if (iBooking.isTerpakai(ruanganTerpilih, tglInput)) {
            JOptionPane.showMessageDialog(frmBooking, "Maaf, " + ruanganTerpilih + " sudah dipesan untuk tanggal " + tglInput + "!");
            return;
        }

        // 5. Jika LOLOS SEMUA VALIDASI, baru masukkan ke Model
        Booking bo = new Booking();
        bo.setKode_booking(kodeInput);
        bo.setNama_peminjam(frmBooking.getcbPeminjam().getSelectedItem().toString());
        bo.setNama_ruangan(ruanganTerpilih);
        bo.setTanggal_booking(tglInput);
        bo.setTujuan(frmBooking.gettxtTujuan().getText());

        // 6. Eksekusi Simpan ke Database
        if (iBooking.insert(bo)) {
            JOptionPane.showMessageDialog(frmBooking, "Booking Berhasil Disimpan!");

            // 7. Refresh Tabel & Reset Form
            isiTable();
            reset();
        } else {
            JOptionPane.showMessageDialog(frmBooking, "Gagal menyimpan data ke database.");
        }
    }
    
    public void update() {
        // 1. Validasi: Apakah sudah pilih data di tabel?
        if (selectedId == 0) {
            JOptionPane.showMessageDialog(frmBooking, "Pilih data yang akan diupdate dahulu!");
            return;
        }

        // 2. Ambil data dari field
        String kodeBaru = frmBooking.gettxtKodeBooking().getText().trim();
        String ruanganTerpilih = frmBooking.getcbRuangan().getSelectedItem().toString();
        String tglInput = frmBooking.gettxtTanggalBooking().getText().trim();
        String peminjam = frmBooking.getcbPeminjam().getSelectedItem().toString();
        String tujuan = frmBooking.gettxtTujuan().getText().trim();

        // 3. Cari data lama dari list berdasarkan selectedId
        Booking dataLama = null;
        for (Booking b : lstBo) {
            if (b.getId_booking() == selectedId) {
                dataLama = b;
                break;
            }
        }

        if (dataLama != null) {
            // --- VALIDASI A: CEK KODE DUPLIKAT ---
            // Jika kode diubah, cek apakah kode baru sudah ada di database
            if (!kodeBaru.equals(dataLama.getKode_booking())) {
                if (iBooking.cekKode(kodeBaru)) {
                    JOptionPane.showMessageDialog(frmBooking, "Kode Booking '" + kodeBaru + "' sudah ada!");
                    frmBooking.gettxtKodeBooking().requestFocus();
                    return;
                }
            }

            // --- VALIDASI B: CEK RUANGAN (BENTROK) ---
            // Cek jika Ruangan ATAU Tanggal berubah
            if (!ruanganTerpilih.equals(dataLama.getNama_ruangan()) || !tglInput.equals(dataLama.getTanggal_booking())) {
                // Gunakan parameter selectedId agar tidak membenturkan dengan dirinya sendiri
                if (iBooking.isTerpakai(ruanganTerpilih, tglInput)) { 
                    JOptionPane.showMessageDialog(frmBooking, "Maaf, " + ruanganTerpilih + " sudah dipesan untuk tanggal " + tglInput + "!");
                    return;
                }
            }

            // --- VALIDASI C: CEK APAKAH ADA PERUBAHAN ---
            boolean isSama = 
                    kodeBaru.equals(dataLama.getKode_booking()) &&
                    peminjam.equals(dataLama.getNama_peminjam()) &&
                    ruanganTerpilih.equals(dataLama.getNama_ruangan()) &&
                    tglInput.equals(dataLama.getTanggal_booking()) &&
                    tujuan.equals(dataLama.getTujuan());

            if (isSama) {
                JOptionPane.showMessageDialog(frmBooking, "Tidak ada data yang berubah");
                return;
            }
        }

        // 4. Validasi input kosong (biar yyyy-mm-dd tidak lolos)
        if (!cekValidasi()) return;

        // 5. Eksekusi Update
        try {
            Booking bo = new Booking();
            bo.setId_booking(selectedId);
            bo.setKode_booking(kodeBaru);
            bo.setNama_peminjam(peminjam);
            bo.setNama_ruangan(ruanganTerpilih);
            bo.setTanggal_booking(tglInput);
            bo.setTujuan(tujuan);

            iBooking.update(bo);

            JOptionPane.showMessageDialog(frmBooking, "Update Booking berhasil!");

            // Refresh dan Reset
            isiTable();
            kondisiAwal(); // Pastikan di sini selectedId diset kembali ke 0

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frmBooking, "Gagal update: " + e.getMessage());
        }
    }
    
    public void delete() {
        // 1. JIKA ADA DATA YANG DIPILIH (Hapus satu)
        if (selectedId != 0) {
            int confirm = JOptionPane.showConfirmDialog(frmBooking, "Yakin ingin menghapus data ini?", "Konfirmasi Hapus", 
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    iBooking.delete(selectedId); 
                    JOptionPane.showMessageDialog(frmBooking, "Data berhasil dihapus");
                    isiTable();      
                    kondisiAwal(); 
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmBooking, "Gagal hapus: " + e.getMessage());
                }
            }
        } 
        // 2. JIKA selectedId == 0 (Tidak ada data dipilih di tabel)
        else {
            // CEK: Apakah user sedang mengetik sesuatu di textfield?
            // Kita cek field Nama, kalau tidak kosong berarti user lagi mau INPUT
            boolean lagiInput = !frmBooking.gettxtKodeBooking().getText().trim().isEmpty();

            if (lagiInput) {
                JOptionPane.showMessageDialog(frmBooking, "Anda sedang mengisi data baru. Jika ingin menghapus, pastikan form kosong atau pilih data di tabel!");
                return; // Berhenti, jangan tawarin hapus semua
            }

            // CEK: Apakah tabel memang kosong?
            if (lstBo == null || lstBo.isEmpty()) {
                JOptionPane.showMessageDialog(frmBooking, "Tabel masih kosong, tidak ada data yang bisa dihapus!");
                return;
            }

            // Kalau form kosong DAN tabel ada isinya, baru tawarin Hapus Semua
            int confirmAll = JOptionPane.showConfirmDialog(frmBooking, 
                    "Tidak ada data dipilih. Apakah Anda ingin menghapus SELURUH data di tabel?", 
                    "Konfirmasi Hapus Semua", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmAll == JOptionPane.YES_OPTION) {
                try {
                    iBooking.deleteAll();
                    JOptionPane.showMessageDialog(frmBooking, "Semua data berhasil dihapus!");
                    isiTable();
                    kondisiAwal();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmBooking, "Gagal hapus semua: " + e.getMessage());
                }
            }
        }
    }
    
   public void cari() {
    // 1. Ambil keyword dari textfield cari di Form Booking
        String keyword = frmBooking.gettxtCariKodeBooking().getText().trim();

        // 2. Validasi kalau textbox kosong, tampilkan semua data lagi
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(frmBooking, "Ketikkan kata kunci (Kode/Nama) terlebih dahulu!");
            isiTable(); // Panggil fungsi isiTable yang sudah ada untuk refresh data
            return;
        }

        // 3. Cari data menggunakan DAO Booking
        // Karena di DAO tadi kita buat dua parameter (kode, nama), kita kirim keyword yang sama ke keduanya
        lstBo = iBooking.getAllByName(keyword, keyword);

        // 4. Jika data tidak ditemukan
        if (lstBo.isEmpty()) {
            JOptionPane.showMessageDialog(frmBooking, "Data booking tidak ditemukan!");
            isiTable(); // Kembalikan ke tampilan awal jika tidak ketemu
        } else {
            // 5. Jika ketemu, tampilkan hasil pencarian ke tabel
            TabelModelBooking tabelBo = new TabelModelBooking(lstBo);
            frmBooking.getTabelData().setModel(tabelBo);
        }
    }
}

    

