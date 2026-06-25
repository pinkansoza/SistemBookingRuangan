/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ASUS
 */
public class Ruangan {

    /**
     * @return the fakultas
     */
    public String getFakultas() {
        return fakultas;
    }

    /**
     * @param fakultas the fakultas to set
     */
    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }
    private Integer id_ruangan;
    private String nama_ruangan;
    private String kapasitas;
    private String status;
    private String fasilitas;
    private String lokasi;
    private String fakultas;

    /**
     * @return the fasilitas
     */
    public String getFasilitas() {
        return fasilitas;
    }

    /**
     * @param fasilitas the fasilitas to set
     */
    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    /**
     * @return the lokasi
     */
    public String getLokasi() {
        return lokasi;
    }

    /**
     * @param lokasi the lokasi to set
     */
    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
    

    /**
     * @return the id_ruangan
     */
    public Integer getId_ruangan() {
        return id_ruangan;
    }

    /**
     * @param id_ruangan the id_ruangan to set
     */
    public void setId_ruangan(Integer id_ruangan) {
        this.id_ruangan = id_ruangan;
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
     * @return the kapasitas
     */
    public String getKapasitas() {
        return kapasitas;
    }

    /**
     * @param kapasitas the kapasitas to set
     */
    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
