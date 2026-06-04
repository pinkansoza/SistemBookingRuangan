/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import DAO.DAORuangan;
import DAOInterface.IDAORuangan;
import Model.Ruangan;
import Model.TabelModelRuangan;
import View.RuanganForm;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class ControllerRuangan {
    RuanganForm frame; 
    IDAORuangan iRuangan;
    List<Ruangan> lstRu;
    private int selectedId = 0;
    private final RuanganForm frmRuangan;
    
    public ControllerRuangan(RuanganForm frmRuangan) throws SQLException {
        this.frmRuangan= frmRuangan;
        iRuangan = new DAORuangan();
        
        kondisiAwal();
    }
    
    public void isiTable(){
        lstRu = iRuangan.getAll();
        TabelModelRuangan tabelRu = new TabelModelRuangan(lstRu);
        frmRuangan.getTabelData().setModel(tabelRu);
    }
    
    public void isiField(int row) {
            // 1. Ambil data dan tampilkan di textfield
            selectedId = Integer.parseInt(frmRuangan.getTabelData().getValueAt(row, 0).toString());
            frmRuangan.gettxtNamaRuangan().setText(lstRu.get(row).getNama_ruangan());
            frmRuangan.gettxtKapasitas().setText(lstRu.get(row).getKapasitas());
            frmRuangan.getComboBoxStatus().setSelectedItem(lstRu.get(row).getStatus());
            frmRuangan.gettxtFasilitas().setText(lstRu.get(row).getFasilitas());
            frmRuangan.gettxtLokasi().setText(lstRu.get(row).getLokasi());
            
            
            // 3. ATUR TOMBOL (Pastikan logikanya begini)
            frmRuangan.getbuttonSimpan().setEnabled(false);
            frmRuangan.getbuttonUbah().setEnabled(true); // MATIKAN simpan (karena bukan input baru)
            frmRuangan.getbuttonHapus().setEnabled(true);    // NYALAKAN ubah
            frmRuangan.getbuttonReset().setEnabled(true);   // NYALAKAN hapus
        }
    
    public void reset(){
            frmRuangan.gettxtNamaRuangan().setText("");
            frmRuangan.gettxtKapasitas().setText("");
            frmRuangan.getComboBoxStatus().setSelectedItem("");
            frmRuangan.gettxtFasilitas().setText("");
            frmRuangan.gettxtLokasi().setText("");
            selectedId = 0;
            
            frmRuangan.getbuttonSimpan().setEnabled(true);
            frmRuangan.getbuttonUbah().setEnabled(false);
        }
    
    public void kondisiAwal() {
        reset();

        frmRuangan.getbuttonSimpan().setEnabled(true);
        frmRuangan.getbuttonUbah().setEnabled(false);
        frmRuangan.getbuttonHapus().setEnabled(true);
        frmRuangan.getbuttonReset().setEnabled(true);

    }
    
    public boolean cekValidasi() {
        // Ambil data dari View (Frame)
        String nama_ruangan = frmRuangan.gettxtNamaRuangan().getText();
        String kapasitas = frmRuangan.gettxtKapasitas().getText();

        // Cek apakah ada yang kosong
        if (nama_ruangan.trim().isEmpty() || kapasitas.trim().isEmpty()) {
            // Tampilkan Pesan Peringatan
            javax.swing.JOptionPane.showMessageDialog(frmRuangan, "Data Masih ada yang Kosong");
            return false; // Validasi gagal
        }
        return true; // Validasi berhasil
    }
    
    public void insert() {
        String nama = frmRuangan.gettxtNamaRuangan().getText();
        
        if (iRuangan.cekNama(nama)) {
            JOptionPane.showMessageDialog(frmRuangan, "Nama ruangan sudah terdaftar!");
            return;
        }

        // 2. Cek validasi input kosong
        if (!cekValidasi()) return; 

        // 3. Siapkan objek mahasiswa
        Ruangan b = new Ruangan();
        b.setNama_ruangan(frmRuangan.gettxtNamaRuangan().getText());
        b.setKapasitas(frmRuangan.gettxtKapasitas().getText());
        b.setStatus(frmRuangan.getComboBoxStatus().getSelectedItem().toString());
        b.setFasilitas(frmRuangan.gettxtFasilitas().getText());
        b.setLokasi(frmRuangan.gettxtLokasi().getText());

        // 4. Panggil DAO dan langsung cek hasilnya
        boolean isSuccess = iRuangan.insert(b);

        if (isSuccess) {
            // Jika TRUE (Berhasil)
            JOptionPane.showMessageDialog(frmRuangan, "Input berhasil");
            kondisiAwal(); 
        } else {
            // Jika FALSE (Gagal, biasanya karena ID Duplikat)
            JOptionPane.showMessageDialog(frmRuangan, "Gagal Input/Duplikat");
        }
    }
    
    public void update() {
        // 1. Cek apakah sudah pilih data
        if (selectedId == 0) {
            JOptionPane.showMessageDialog(frmRuangan, "Pilih data yang akan diupdate dahulu!");
            return;
        }

        String namaBaru = frmRuangan.gettxtNamaRuangan().getText().trim();

        // 2. Cari data lama berdasarkan ID di list
        Ruangan dataLama = null;
        for (Ruangan p : lstRu) {
            if (p.getId_ruangan() == selectedId) {
                dataLama = p;
                break;
            }
        }

        if (dataLama != null) {
            // 3. LOGIKA IS_SAMA (Cek perubahan)
            // Lakukan ini DULU sebelum cekNama ke database
            boolean isSama =
                    namaBaru.equals(dataLama.getNama_ruangan()) &&
                    frmRuangan.gettxtKapasitas().getText().equals(dataLama.getKapasitas()) &&
                    frmRuangan.getComboBoxStatus().getSelectedItem().toString().equals(dataLama.getStatus()) &&
                    frmRuangan.gettxtFasilitas().getText().equals(dataLama.getFasilitas()) &&
                    frmRuangan.gettxtLokasi().getText().equals(dataLama.getLokasi());

            if (isSama) {
                JOptionPane.showMessageDialog(frmRuangan, "Tidak ada data yang berubah");
                return;
            }

            // 4. VALIDASI NAMA TERDAFTAR
            // Cek nama HANYA JIKA user mengubah nama ruangannya
            if (!namaBaru.equalsIgnoreCase(dataLama.getNama_ruangan())) {
                if (iRuangan.cekNama(namaBaru)) {
                    JOptionPane.showMessageDialog(frmRuangan, "Nama ruangan sudah terdaftar!");
                    frmRuangan.gettxtNamaRuangan().requestFocus();
                    return;
                }
            }
        }

        // 5. Validasi input kosong
        if (!cekValidasi()) return;

        // 6. Eksekusi Update
        try {
            Ruangan b = new Ruangan();
            b.setId_ruangan(selectedId); // Pastikan ini setId_ruangan sesuai modelmu
            b.setNama_ruangan(namaBaru);
            b.setKapasitas(frmRuangan.gettxtKapasitas().getText());
            b.setStatus(frmRuangan.getComboBoxStatus().getSelectedItem().toString());
            b.setFasilitas(frmRuangan.gettxtFasilitas().getText());
            b.setLokasi(frmRuangan.gettxtLokasi().getText());

            iRuangan.update(b);
            JOptionPane.showMessageDialog(frmRuangan, "Update berhasil!");

            isiTable(); // Jangan lupa refresh tabel
            kondisiAwal();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frmRuangan, "Gagal update: " + e.getMessage());
        }
    }
    
    public void delete() {
        // 1. JIKA ADA DATA YANG DIPILIH (Hapus satu)
        if (selectedId != 0) {
            int confirm = JOptionPane.showConfirmDialog(frmRuangan, "Yakin ingin menghapus data ini?", "Konfirmasi Hapus", 
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    iRuangan.delete(selectedId); 
                    JOptionPane.showMessageDialog(frmRuangan, "Data berhasil dihapus");
                    isiTable();      
                    kondisiAwal(); 
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmRuangan, "Gagal hapus: " + e.getMessage());
                }
            }
        } 
        // 2. JIKA selectedId == 0 (Tidak ada data dipilih di tabel)
        else {
            // CEK: Apakah user sedang mengetik sesuatu di textfield?
            // Kita cek field Nama, kalau tidak kosong berarti user lagi mau INPUT
            boolean lagiInput = !frmRuangan.gettxtNamaRuangan().getText().trim().isEmpty();

            if (lagiInput) {
                JOptionPane.showMessageDialog(frmRuangan, "Anda sedang mengisi data baru. Jika ingin menghapus, pastikan form kosong atau pilih data di tabel!");
                return; // Berhenti, jangan tawarin hapus semua
            }

            // CEK: Apakah tabel memang kosong?
            if (lstRu == null || lstRu.isEmpty()) {
                JOptionPane.showMessageDialog(frmRuangan, "Tabel masih kosong, tidak ada data yang bisa dihapus!");
                return;
            }

            // Kalau form kosong DAN tabel ada isinya, baru tawarin Hapus Semua
            int confirmAll = JOptionPane.showConfirmDialog(frmRuangan, 
                    "Tidak ada data dipilih. Apakah Anda ingin menghapus SELURUH data di tabel?", 
                    "Konfirmasi Hapus Semua", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmAll == JOptionPane.YES_OPTION) {
                try {
                    iRuangan.deleteAll();
                    JOptionPane.showMessageDialog(frmRuangan, "Semua data berhasil dihapus!");
                    isiTable();
                    kondisiAwal();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmRuangan, "Gagal hapus semua: " + e.getMessage());
                }
            }
        }
    }
    
    public void cari() {

        String keyword = frmRuangan.gettxtCariNamaRuangan().getText().trim();

        // validasi kalau textbox kosong
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(frmRuangan,"Ketikkan nama ruangan yang ingin dicari terlebih dahulu!");
            lstRu = iRuangan.getAll();
        }

        // cari data
        lstRu = iRuangan.getAllByName(keyword);

        // kalau data tidak ditemukan
        if (lstRu.isEmpty()) {
            JOptionPane.showMessageDialog(frmRuangan,"Data tidak ditemukan");
            lstRu = iRuangan.getAll();
        }

        // tampilkan ke tabel
        TabelModelRuangan tabelRu =new TabelModelRuangan(lstRu);
        frmRuangan.getTabelData().setModel(tabelRu);
    }
}
