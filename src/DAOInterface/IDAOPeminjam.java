/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAOInterface;

import Model.Peminjam;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IDAOPeminjam {
    //READ DATA
    public List<Peminjam> getAll();
    //INSERT DATA
    public boolean insert(Peminjam b);
    //cek nama peminjam
    public boolean cekNama(String nama_peminjam);
    //UPDATE DATA
    public void update(Peminjam b);
    //DELETE DATA
    public void delete(int id_peminjam);
    //delete all data
    public void deleteAll();
    //SEARCH DATA
    public List<Peminjam> getAllByName(String nama_peminjam);
}
