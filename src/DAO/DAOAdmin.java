/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAOInterface.IDAOAdmin;
import Koneksi.KoneksiDB;
import Model.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ASUS
 */
public class DAOAdmin implements IDAOAdmin {
    
    Connection con;
    
    // SQL Queries
    String strRead = "select * from admin where id_admin = 1";
    String strUpdate = "update admin set username=?, password=? where id_admin = 1";
    String strLogin = "select * from admin where username=? and password=?";

    public DAOAdmin() throws SQLException {
        con = KoneksiDB.getConnection();
    }

    @Override
    public Admin getAdmin() {
        Admin a = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(strRead);
            if (rs.next()) {
                a = new Admin();
                a.setId_admin(rs.getInt("id_admin"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    @Override
    public void update(Admin a) {
        try {
            PreparedStatement st = con.prepareStatement(strUpdate);
            st.setString(1, a.getUsername());
            st.setString(2, a.getPassword());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean cekLogin(String user, String pass) {
        try {
            PreparedStatement st = con.prepareStatement(strLogin);
            st.setString(1, user);
            st.setString(2, pass);
            ResultSet rs = st.executeQuery();
            return rs.next(); // True kalau ketemu, False kalau salah
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
}
