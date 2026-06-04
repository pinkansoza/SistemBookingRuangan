/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAOInterface;

import Model.Booking;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IDAOBooking {
    //READ DATA
    public List<Booking> getAll();
    //INSERT DATA
    public boolean insert(Booking b);
    //cek kode booking
    public boolean cekKode(String kode_booking);
    //UPDATE DATA
    public void update(Booking b);
    //DELETE DATA
    public void delete(int id_boooking);
    //delete all data
    public void deleteAll();
    //SEARCH DATA
    public List<Booking> getAllByName(String kode_booking, String nama_peminjam);
    // cek ruangan dipakai atau tidak
    public boolean isTerpakai(String ruanganTerpilih, String tglInput);
}
