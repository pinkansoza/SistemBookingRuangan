/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAOInterface.IDAOBooking;
import Koneksi.KoneksiDB;
import Model.Booking;
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
public class DAOBooking implements IDAOBooking{
    
    Connection con;
    //SQL Query
    String strRead = "select * from booking;";
    String strInsert = "insert into booking(kode_booking, nama_peminjam, nama_ruangan , tanggal_booking, tujuan) values (?,?,?,?,?);";
    String strCekKode = "SELECT * FROM booking WHERE kode_booking = ?";
    String strUpdate = "update booking set kode_booking=?, nama_peminjam=?, nama_ruangan=?, tanggal_booking=?, tujuan=? where id_booking=?";
    String strDelete = "delete from booking where id_booking=?";
    String strDeleteAll = "DELETE FROM booking";
    String strSearch = "select * from booking where kode_booking like ? or nama_peminjam like ?;";
    String strTerpakai = "select * from booking where nama_ruangan = ? and tanggal_booking = ?";
    
    public DAOBooking() throws SQLException {
        con = KoneksiDB.getConnection();
    }

    @Override
    public List<Booking> getAll() {
        List<Booking> lstBo = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(strRead);
             
            while(rs.next()){
                Booking bo = new Booking();
                bo.setId_booking(rs.getInt("id_booking"));
                bo.setKode_booking(rs.getString("kode_booking"));
                bo.setNama_peminjam(rs.getString("nama_peminjam"));
                bo.setNama_ruangan(rs.getString("nama_ruangan"));
                bo.setTanggal_booking(rs.getString("tanggal_booking")); 
                bo.setTujuan(rs.getString("tujuan"));
                lstBo.add(bo);
            }
        } catch(SQLException e) {
            System.out.println("Error saat getAll: " + e.getMessage());
        }
        return lstBo;
    }

    @Override
    public boolean insert(Booking b) {
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(strInsert);
            statement.setString(1, b.getKode_booking());
            statement.setString(2, b.getNama_peminjam());
            statement.setString(3, b.getNama_ruangan());
            statement.setString(4, b.getTanggal_booking());
            statement.setString(5, b.getTujuan());
            
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
    public boolean cekKode(String kode_booking) {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = con.prepareStatement(strCekKode);
            statement.setString(1, kode_booking);

            rs = statement.executeQuery();

            return rs.next(); // true = nama sudah ada

        } catch (SQLException ex) {
            System.out.println("Error cek nama: " + ex.getMessage());
        }

        return false;
    }

    @Override
    public void update(Booking b) {
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(strUpdate);

            statement.setString(1, b.getKode_booking());
            statement.setString(2, b.getNama_peminjam());
            statement.setString(3, b.getNama_ruangan());
            statement.setString(4, b.getTanggal_booking());
            statement.setString(5, b.getTujuan());
            statement.setInt(6, b.getId_booking()); 

            statement.executeUpdate();
            System.out.println("Data Booking Berhasil Diperbarui");

        } catch (SQLException ex) {
            System.out.println("Gagal Update Booking: " + ex.getMessage());
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
    public void delete(int id_booking) {
        PreparedStatement statement = null;
    try {
        statement = con.prepareStatement(strDelete);
        statement.setInt(1, id_booking);
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
            System.out.println("Semua data booking berhasil dikosongkan.");
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
    public List<Booking> getAllByName(String kode_booking, String nama_peminjam) {
        List<Booking> lstBo = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement(strSearch);
            st.setString(1, "%"+kode_booking+"%");
            st.setString(2, "%" + nama_peminjam + "%");
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                Booking bo = new Booking();
                bo.setId_booking(rs.getInt("id_booking"));
                bo.setKode_booking(rs.getString("kode_booking")); 
                bo.setNama_peminjam(rs.getString("nama_peminjam")); 
                bo.setNama_ruangan(rs.getString("nama_ruangan"));
                bo.setTanggal_booking(rs.getString("tanggal_booking"));
                bo.setTujuan(rs.getString("tujuan"));
                lstBo.add(bo);
            }
        } catch(SQLException e) {
            System.out.println("Error saat getAll: " + e.getMessage());
        }
        return lstBo;
    }

    @Override
    public boolean isTerpakai(String ruanganTerpilih, String tglInput) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean terpakai = false;

        try {
            statement = con.prepareStatement(strTerpakai);
            statement.setString(1, ruanganTerpilih);
            statement.setString(2, tglInput);

            rs = statement.executeQuery();

            // Jika rs.next() bernilai true, berarti data ditemukan (sudah dipesan)
            if (rs.next()) {
                terpakai = true;
            }

        } catch (SQLException ex) {
            System.out.println("Error cek validasi bentrok: " + ex.getMessage());
        } finally {
            // Tutup resource agar tidak membebani database
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }

        return terpakai;
    }
    
}
