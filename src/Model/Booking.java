/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ASUS
 */
public class Booking {
    
    private Integer id_booking;
    private String kode_booking;
    private String nama_peminjam;
    private String nama_ruangan;
    private String tanggal_booking;
    private String tujuan;

    /**
     * @return the id_booking
     */
    public Integer getId_booking() {
        return id_booking;
    }

    /**
     * @param id_booking the id_booking to set
     */
    public void setId_booking(Integer id_booking) {
        this.id_booking = id_booking;
    }

    /**
     * @return the kode_booking
     */
    public String getKode_booking() {
        return kode_booking;
    }

    /**
     * @param kode_booking the kode_booking to set
     */
    public void setKode_booking(String kode_booking) {
        this.kode_booking = kode_booking;
    }

    /**
     * @return the nama_peminjam
     */
    public String getNama_peminjam() {
        return nama_peminjam;
    }

    /**
     * @param nama_peminjam the nama_peminjam to set
     */
    public void setNama_peminjam(String nama_peminjam) {
        this.nama_peminjam = nama_peminjam;
    }

    /**
     * @return the nama_ruangan
     */
    public String getNama_ruangan() {
        return nama_ruangan;
    }

    /**
     * @param nama_ruangan the nama_ruangan to set
     */
    public void setNama_ruangan(String nama_ruangan) {
        this.nama_ruangan = nama_ruangan;
    }

    /**
     * @return the tanggal_booking
     */
    public String getTanggal_booking() {
        return tanggal_booking;
    }

    /**
     * @param tanggal_booking the tanggal_booking to set
     */
    public void setTanggal_booking(String tanggal_booking) {
        this.tanggal_booking = tanggal_booking;
    }

    /**
     * @return the tujuan
     */
    public String getTujuan() {
        return tujuan;
    }

    /**
     * @param tujuan the tujuan to set
     */
    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }
    
}
