/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAOInterface.IDAORuangan;
import Koneksi.KoneksiDB;
import Model.Ruangan;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class DAORuangan implements IDAORuangan {
    
    Connection con;
    //SQL Query
    String strRead = "select * from ruangan;";
    String strInsert = "insert into ruangan(nama_ruangan,kapasitas,status,fasilitas,lokasi) values (?,?,?,?,?);";
    String strCekNama = "SELECT * FROM ruangan WHERE nama_ruangan = ?";
    String strUpdate = "update ruangan set nama_ruangan=?, kapasitas=?, status=?, fasilitas=?, lokasi=? where id_ruangan=?";
    String strDelete = "delete from ruangan where id_ruangan=?";
    String strDeleteAll = "DELETE FROM ruangan";
    String strSearch = "select * from ruangan where nama_ruangan like ?;";
    
    public DAORuangan() throws SQLException {
        con = KoneksiDB.getConnection();
    }

    @Override
    public List<Ruangan> getAll() {
        List<Ruangan> lstRu = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(strRead);
            
            while(rs.next()){
                Ruangan ru = new Ruangan();
                ru.setId_ruangan(rs.getInt("id_ruangan"));
                ru.setNama_ruangan(rs.getString("nama_ruangan"));
                ru.setKapasitas(rs.getString("kapasitas")); 
                ru.setStatus(rs.getString("status"));
                ru.setFasilitas(rs.getString("fasilitas"));
                ru.setLokasi(rs.getString("lokasi"));
                lstRu.add(ru);
            }
        } catch(SQLException e) {
            System.out.println("Error saat getAll: " + e.getMessage());
        }
        return lstRu;
    }

    @Override
    public boolean cekNama(String nama_ruangan) {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = con.prepareStatement(strCekNama);
            statement.setString(1, nama_ruangan);

            rs = statement.executeQuery();

            return rs.next(); // true = nama sudah ada

        } catch (SQLException ex) {
            System.out.println("Error cek nama: " + ex.getMessage());
        }

        return false;
    }

    @Override
    public boolean insert(Ruangan b) {
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(strInsert);
            statement.setString(1, b.getNama_ruangan());
            statement.setString(2, b.getKapasitas());
            statement.setString(3, b.getStatus());
            statement.setString(4, b.getFasilitas());
            statement.setString(5, b.getLokasi());

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
    public void update(Ruangan b) {
        PreparedStatement statement = null;
    try {
        statement = con.prepareStatement(strUpdate);
        statement.setString(1, b.getNama_ruangan());
        statement.setString(2, b.getKapasitas());
        statement.setString(3, b.getStatus());
        statement.setString(4, b.getFasilitas());
        statement.setString(5, b.getLokasi());
        statement.setInt(6, b.getId_ruangan());
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
    public void delete(int id_ruangan) {
        PreparedStatement statement = null;
    try {
        statement = con.prepareStatement(strDelete);
        statement.setInt(1, id_ruangan);
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
    public List<Ruangan> getAllByName(String nama_ruangan) {
        List<Ruangan> lstRu = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement(strSearch);
            st.setString(1, "%"+nama_ruangan+"%");
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                Ruangan ru = new Ruangan();
                ru.setId_ruangan(rs.getInt("id_ruangan"));
                ru.setNama_ruangan(rs.getString("nama_ruangan")); 
                ru.setKapasitas(rs.getString("kapasitas"));
                ru.setStatus(rs.getString("status"));
                ru.setFasilitas(rs.getString("fasilitas"));
                ru.setLokasi(rs.getString("lokasi"));
                lstRu.add(ru);
            }
        } catch(SQLException e) {
            System.out.println("Error saat getAll: " + e.getMessage());
        }
        return lstRu;
    }
    
}
