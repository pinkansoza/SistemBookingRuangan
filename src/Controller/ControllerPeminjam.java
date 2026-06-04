/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DAOPeminjam;
import DAOInterface.IDAOPeminjam;
import Model.Peminjam;
import Model.TabelModelPeminjam;
import View.PeminjamForm;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class ControllerPeminjam {
    PeminjamForm frame; 
    IDAOPeminjam iPeminjam;
    List<Peminjam> lstPmj;
    private int selectedId = 0;
    private final PeminjamForm frmPeminjam;
    
    public ControllerPeminjam(PeminjamForm frmPeminjam) throws SQLException {
        this.frmPeminjam = frmPeminjam;
        iPeminjam = new DAOPeminjam();
        
        kondisiAwal();
    }
    
    public void isiTable(){
        lstPmj = iPeminjam.getAll();
        TabelModelPeminjam tabelPmj = new TabelModelPeminjam(lstPmj);
        frmPeminjam.getTabelData().setModel(tabelPmj);
    }
    
    public void isiField(int row) {
            // 1. Ambil data dan tampilkan di textfiel
            selectedId = Integer.parseInt(frmPeminjam.getTabelData().getValueAt(row, 0).toString());
            frmPeminjam.gettxtNamaPeminjam().setText(lstPmj.get(row).getNama_peminjam());
            frmPeminjam.gettxtNIM_NIP().setText(lstPmj.get(row).getNim_nip());
            frmPeminjam.gettxtNoTelp().setText(lstPmj.get(row).getNo_telp());
            frmPeminjam.gettxtEmail().setText(lstPmj.get(row).getEmail());
            frmPeminjam.getComboBoxStatusPeminjam().setSelectedItem(lstPmj.get(row).getStatus_peminjam());

            // 3. ATUR TOMBOL (Pastikan logikanya begini)
            frmPeminjam.getbuttonSimpan().setEnabled(false);
            frmPeminjam.getbuttonUbah().setEnabled(true); // MATIKAN simpan (karena bukan input baru)
            frmPeminjam.getbuttonHapus().setEnabled(true);    // NYALAKAN ubah
            frmPeminjam.getbuttonReset().setEnabled(true);   // NYALAKAN hapus
        }
    
    public void reset(){
            frmPeminjam.gettxtNamaPeminjam().setText("");
            frmPeminjam.gettxtNIM_NIP().setText("");
            frmPeminjam.gettxtNoTelp().setText("");
            frmPeminjam.gettxtEmail().setText("");
            frmPeminjam.getComboBoxStatusPeminjam().setSelectedItem("");
            selectedId = 0;
            
            frmPeminjam.getbuttonSimpan().setEnabled(true);
            frmPeminjam.getbuttonUbah().setEnabled(false);
        }
    
    public void kondisiAwal() {
        reset();

        frmPeminjam.getbuttonSimpan().setEnabled(true);
        frmPeminjam.getbuttonUbah().setEnabled(false);
        frmPeminjam.getbuttonHapus().setEnabled(true);
        frmPeminjam.getbuttonReset().setEnabled(true);

    }
    
    public boolean cekValidasi() {
        // Ambil data dari View (Frame)
        String nama_peminjam = frmPeminjam.gettxtNamaPeminjam().getText();
        String nim_nip = frmPeminjam.gettxtNIM_NIP().getText();
        String no_telp = frmPeminjam.gettxtNoTelp().getText();

        // Cek apakah ada yang kosong
        if (nama_peminjam.trim().isEmpty() || nim_nip.trim().isEmpty() || no_telp.trim().isEmpty()) {
            // Tampilkan Pesan Peringatan
            javax.swing.JOptionPane.showMessageDialog(frmPeminjam, "Data Masih ada yang Kosong");
            return false; // Validasi gagal
        }
        return true; // Validasi berhasil
    }
    
    public void insert() {
        String nama = frmPeminjam.gettxtNamaPeminjam().getText();
        
        if (iPeminjam.cekNama(nama)) {
            JOptionPane.showMessageDialog(frmPeminjam, "Nama peminjam sudah terdaftar!");
            return;
        }

        // 2. Cek validasi input kosong
        if (!cekValidasi()) return; 

        // 3. Siapkan objek mahasiswa
        Peminjam b = new Peminjam();
        b.setNama_peminjam(frmPeminjam.gettxtNamaPeminjam().getText());
        b.setNim_nip(frmPeminjam.gettxtNIM_NIP().getText());
        b.setNo_telp(frmPeminjam.gettxtNoTelp().getText());
        b.setEmail(frmPeminjam.gettxtEmail().getText());
        b.setStatus_peminjam(frmPeminjam.getComboBoxStatusPeminjam().getSelectedItem().toString());

        // 4. Panggil DAO dan langsung cek hasilnya
        boolean isSuccess = iPeminjam.insert(b);

        if (isSuccess) {
            // Jika TRUE (Berhasil)
            JOptionPane.showMessageDialog(frmPeminjam, "Input berhasil");
            kondisiAwal(); 
        } else {
            // Jika FALSE (Gagal, biasanya karena ID Duplikat)
            JOptionPane.showMessageDialog(frmPeminjam, "Gagal Input/Duplikat");
        }
    }
    
    public void update() {
        // 1. Cek apakah sudah pilih data
        if (selectedId == 0) {
            JOptionPane.showMessageDialog(frmPeminjam, "Pilih data yang akan diupdate dahulu!");
            return;
        }

        String namaBaru = frmPeminjam.gettxtNamaPeminjam().getText().trim();

        // 2. Cari data lama berdasarkan ID di list
        Peminjam dataLama = null;
        for (Peminjam p : lstPmj) {
            if (p.getId_peminjam() == selectedId) {
                dataLama = p;
                break;
            }
        }

        if (dataLama != null) {
            // 3. LOGIKA IS_SAMA (Cek perubahan)
            // Lakukan ini DULU sebelum cekNama ke database
            boolean isSama =
                    namaBaru.equals(dataLama.getNama_peminjam()) &&
                    frmPeminjam.gettxtNIM_NIP().getText().equals(dataLama.getNim_nip()) &&
                    frmPeminjam.gettxtNoTelp().getText().equals(dataLama.getNo_telp()) &&
                    frmPeminjam.gettxtEmail().getText().equals(dataLama.getEmail()) &&
                    frmPeminjam.getComboBoxStatusPeminjam().getSelectedItem().toString().equals(dataLama.getStatus_peminjam());

            if (isSama) {
                JOptionPane.showMessageDialog(frmPeminjam, "Tidak ada data yang berubah");
                return;
            }

            // 4. VALIDASI NAMA TERDAFTAR
            // Cek nama HANYA JIKA user mengubah nama ruangannya
            if (!namaBaru.equalsIgnoreCase(dataLama.getNama_peminjam())) {
                if (iPeminjam.cekNama(namaBaru)) {
                    JOptionPane.showMessageDialog(frmPeminjam, "Nama peminjam sudah terdaftar!");
                    frmPeminjam.gettxtNamaPeminjam().requestFocus();
                    return;
                }
            }
        }

        // 5. Validasi input kosong
        if (!cekValidasi()) return;

        // 6. Eksekusi Update
        try {
            Peminjam b = new Peminjam();
            b.setId_peminjam(selectedId); // Pastikan ini setId_ruangan sesuai modelmu
            b.setNama_peminjam(namaBaru);
            b.setNim_nip(frmPeminjam.gettxtNIM_NIP().getText());
            b.setNo_telp(frmPeminjam.gettxtNoTelp().getText());
            b.setEmail(frmPeminjam.gettxtEmail().getText());
            b.setStatus_peminjam(frmPeminjam.getComboBoxStatusPeminjam().getSelectedItem().toString());

            iPeminjam.update(b);
            JOptionPane.showMessageDialog(frmPeminjam, "Update berhasil!");

            isiTable(); // Jangan lupa refresh tabel
            kondisiAwal();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frmPeminjam, "Gagal update: " + e.getMessage());
        }
    }
    
    public void delete() {
        // 1. JIKA ADA DATA YANG DIPILIH (Hapus satu)
        if (selectedId != 0) {
            int confirm = JOptionPane.showConfirmDialog(frmPeminjam, "Yakin ingin menghapus data ini?", "Konfirmasi Hapus", 
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    iPeminjam.delete(selectedId); 
                    JOptionPane.showMessageDialog(frmPeminjam, "Data berhasil dihapus");
                    isiTable();      
                    kondisiAwal(); 
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmPeminjam, "Gagal hapus: " + e.getMessage());
                }
            }
        } 
        // 2. JIKA selectedId == 0 (Tidak ada data dipilih di tabel)
        else {
            // CEK: Apakah user sedang mengetik sesuatu di textfield?
            // Kita cek field Nama, kalau tidak kosong berarti user lagi mau INPUT
            boolean lagiInput = !frmPeminjam.gettxtNamaPeminjam().getText().trim().isEmpty();

            if (lagiInput) {
                JOptionPane.showMessageDialog(frmPeminjam, "Anda sedang mengisi data baru. Jika ingin menghapus, pastikan form kosong atau pilih data di tabel!");
                return; // Berhenti, jangan tawarin hapus semua
            }

            // CEK: Apakah tabel memang kosong?
            if (lstPmj == null || lstPmj.isEmpty()) {
                JOptionPane.showMessageDialog(frmPeminjam, "Tabel masih kosong, tidak ada data yang bisa dihapus!");
                return;
            }

            // Kalau form kosong DAN tabel ada isinya, baru tawarin Hapus Semua
            int confirmAll = JOptionPane.showConfirmDialog(frmPeminjam, 
                    "Tidak ada data dipilih. Apakah Anda ingin menghapus SELURUH data di tabel?", 
                    "Konfirmasi Hapus Semua", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmAll == JOptionPane.YES_OPTION) {
                try {
                    iPeminjam.deleteAll();
                    JOptionPane.showMessageDialog(frmPeminjam, "Semua data berhasil dihapus!");
                    isiTable();
                    kondisiAwal();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmPeminjam, "Gagal hapus semua: " + e.getMessage());
                }
            }
        }
    }
    
    public void cari() {

        String keyword = frmPeminjam.gettxtCariNamaPeminjam().getText().trim();

        // validasi kalau textbox kosong
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(frmPeminjam,"Ketikkan nama yang ingin dicari terlebih dahulu!");
            lstPmj = iPeminjam.getAll();
        }

        // cari data
        lstPmj = iPeminjam.getAllByName(keyword);

        // kalau data tidak ditemukan
        if (lstPmj.isEmpty()) {
            JOptionPane.showMessageDialog(frmPeminjam,"Data tidak ditemukan");
            lstPmj = iPeminjam.getAll();
        }

        // tampilkan ke tabel
        TabelModelPeminjam tabelPmj =new TabelModelPeminjam(lstPmj);
        frmPeminjam.getTabelData().setModel(tabelPmj);
    }
}
