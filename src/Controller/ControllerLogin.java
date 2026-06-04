/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DAOAdmin;
import DAOInterface.IDAOAdmin;
import View.Dashboard;
import View.LoginForm;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ControllerLogin {
    LoginForm frmLogin;
    IDAOAdmin iAdmin;

    public ControllerLogin(LoginForm frmLogin) {
        this.frmLogin = frmLogin;
        try {
            iAdmin = new DAOAdmin();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmLogin, "Koneksi Gagal: " + ex.getMessage());
        }
    }

    public void login() {
        // 1. Ambil data dari View
        String user = frmLogin.gettxtUsername().getText().trim();
        String pass = new String(frmLogin.gettxtPassword().getPassword());

        // 2. VALIDASI: Harus diisi dulu (Tidak boleh kosong)
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(frmLogin, "Username dan Password wajib diisi!");
            return; // Berhenti di sini, jangan lanjut ke bawah
        }

        // 3. VALIDASI: Cek kebenaran data ke Database (lewat DAO)
        if (iAdmin.cekLogin(user, pass)) {
            // Jika data BENAR
            JOptionPane.showMessageDialog(frmLogin, "Login Berhasil!");

            Dashboard db = new Dashboard();
            db.setVisible(true);

            frmLogin.setVisible(false);
            frmLogin.dispose();
        } else {
            // Jika data SALAH
            JOptionPane.showMessageDialog(frmLogin, "Username atau Password salah! Periksa kembali.");

            // Opsional: Kosongkan field password biar user ketik ulang
            frmLogin.gettxtPassword().setText("");
            frmLogin.gettxtUsername().requestFocus();
        }
    }
}