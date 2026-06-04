/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAOInterface.IDAOPeminjam;
import Koneksi.KoneksiDB;
import Model.Peminjam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class DAOPeminjam implements IDAOPeminjam {
    
    Connection con;
    //SQL Query
    String strRead = "select * from peminjam;";
    String strInsert = "insert into peminjam(nama_peminjam,nim_nip,no_telp,email,status_peminjam) values (?,?,?,?,?);";
    String strCekNama = "select * from peminjam where nama_peminjam = ?";
    String strUpdate = "update peminjam set nama_peminjam=?, nim_nip=?, no_telp=?, email=?, status_peminjam=? where id_peminjam=?";
    String strDelete = "delete from peminjam where id_peminjam=?";
    String strDeleteAll = "delete from peminjam";
    String strSearch = "select * from peminjam where nama_peminjam like ?;";
    
    public DAOPeminjam() throws SQLException {
        con = KoneksiDB.getConnection();
    }

    @Override
    public List<Peminjam> getAll() {
        List<Peminjam> lstPmj = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(strRead);
            
            while(rs.next()){
                Peminjam pmj = new Peminjam();
                pmj.setId_peminjam(rs.getInt("id_peminjam"));
                pmj.setNama_peminjam(rs.getString("nama_peminjam"));
                pmj.setNim_nip(rs.getString("nim_nip")); 
                pmj.setNo_telp(rs.getString("no_telp"));
                pmj.setEmail(rs.getString("email"));
                pmj.setStatus_peminjam(rs.getString("status_peminjam"));
                lstPmj.add(pmj);
            }
        } catch(SQLException e) {
            System.out.println("Error saat getAll: " + e.getMessage());
        }
        return lstPmj;
    }
    
    @Override
    public boolean cekNama(String nama_peminjam) {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = con.prepareStatement(strCekNama);
            statement.setString(1, nama_peminjam);

            rs = statement.executeQuery();

            return rs.next(); // true = nama sudah ada

        } catch (SQLException ex) {
            System.out.println("Error cek nama: " + ex.getMessage());
        }

        return false;
    }

    @Override
    public boolean insert(Peminjam b) {
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(strInsert);
            statement.setString(1, b.getNama_peminjam());
            statement.setString(2, b.getNim_nip());
            statement.setString(3, b.getNo_telp());
            statement.setString(4, b.getEmail());
            statement.setString(5, b.getStatus_peminjam());

            int rowsAffected = statement.executeUpdate();

            System.out.println("Data Berhasil Disimpan");
            return rowsAffected > 0; // Mengembalikan true jika ada data masuk

        } catch (SQLException ex) {
            // Kita tetap print di console untuk kita baca sendiri kalau ada error
            System.out.println("Gagal Input (DAO): " + ex.getMessage());
            return false; // Mengembalikan false agar Controller tahu ada masalah
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Gagal menutup statement: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void update(Peminjam b) {
        PreparedStatement statement = null;
    try {
        statement = con.prepareStatement(strUpdate);
        statement.setString(1, b.getNama_peminjam());
        statement.setString(2, b.getNim_nip());
        statement.setString(3, b.getNo_telp());
        statement.setString(4, b.getEmail());
        statement.setString(5, b.getStatus_peminjam());
        statement.setInt(6, b.getId_peminjam());
        statement.executeUpdate();
        
        System.out.println("Data Berhasil Disimpan");

    } catch (SQLException ex) {
        System.out.println("Gagal Update" + ex.getMessage());
    } finally {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Gagal menutup statement: " + e.getMessage());
            }
        }
    }
    }

    @Override
    public void delete(int id_peminjam) {
        PreparedStatement statement = null;
    try {
        statement = con.prepareStatement(strDelete);
        statement.setInt(1, id_peminjam);
        statement.executeUpdate();
       
    } catch (SQLException ex) {
        System.out.println("Berhasil Delete");
    } finally {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Gagal Delete: " + e.getMessage());
            }
        }
    }
    
    }
    
    @Override
    public void deleteAll() {
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(strDeleteAll);
            statement.executeUpdate();
            System.out.println("Semua data peminjam berhasil dikosongkan.");
        } catch (SQLException ex) {
            System.out.println("Gagal Delete All: " + ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // log error tutup statement
                }
            }
        }
    }

    @Override
    public List<Peminjam> getAllByName(String nama_peminjam) {
        List<Peminjam> lstPmj = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement(strSearch);
            st.setString(1, "%"+nama_peminjam+"%");
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                Peminjam pmj = new Peminjam();
                pmj.setId_peminjam(rs.getInt("id_peminjam"));
                pmj.setNama_peminjam(rs.getString("nama_peminjam")); 
                pmj.setNim_nip(rs.getString("nim_nip"));
                pmj.setNo_telp(rs.getString("no_telp"));
                pmj.setEmail(rs.getString("email"));
                pmj.setStatus_peminjam(rs.getString("status_peminjam"));
                lstPmj.add(pmj);
            }
        } catch(SQLException e) {
            System.out.println("Error saat getAll: " + e.getMessage());
        }
        return lstPmj;
    }
}
