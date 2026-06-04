/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DAOAdmin;
import DAOInterface.IDAOAdmin;
import Model.Admin;
import View.PengaturanForm;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ControllerAdmin {
    PengaturanForm frmAdmin;
    IDAOAdmin iAdmin;

    public ControllerAdmin(PengaturanForm frmAdmin) {
        this.frmAdmin = frmAdmin;
        try {
            iAdmin = new DAOAdmin();
            // Langsung isi data ke field saat controller dibuat
            isiField();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmAdmin, "Koneksi Gagal: " + ex.getMessage());
        }
    }

    // Fungsi untuk menampilkan data dari DB ke TextField
    public void isiField() {
        Admin a = iAdmin.getAdmin();
        if (a != null) {
            frmAdmin.gettxtUsername().setText(a.getUsername());
            frmAdmin.gettxtPassword().setText(a.getPassword());
        } else {
            JOptionPane.showMessageDialog(frmAdmin, "Data Admin tidak ditemukan di Database!");
        }
    }

    // Fungsi untuk memproses update data
    public void update() {
        // 1. Validasi input kosong
        if (frmAdmin.gettxtUsername().getText().trim().isEmpty() || 
            frmAdmin.gettxtPassword().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frmAdmin, "Username dan Password tidak boleh kosong!");
            return;
        }

        // 2. Masukkan data dari form ke object Model Admin
        Admin a = new Admin();
        a.setUsername(frmAdmin.gettxtUsername().getText());
        a.setPassword(frmAdmin.gettxtPassword().getText());

        // 3. Panggil DAO untuk update ke database
        try {
            iAdmin.update(a);
            JOptionPane.showMessageDialog(frmAdmin, "Pengaturan Akun Berhasil Diperbarui!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frmAdmin, "Gagal Update: " + e.getMessage());
        }
    }
}
