/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistembookingruangan;

import Koneksi.KoneksiDB;
import View.LoginForm;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class SistemBookingRuangan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        KoneksiDB.getConnection();
        new LoginForm().setVisible(true);
    }
    
}
