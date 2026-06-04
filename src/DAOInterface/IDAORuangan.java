/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAOInterface;

import Model.Ruangan;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IDAORuangan {
    //READ DATA
    public List<Ruangan> getAll();
    //INSERT DATA
    public boolean insert(Ruangan b);
    //cek nama ruangan
    public boolean cekNama(String nama_ruangan);
    //UPDATE DATA
    public void update(Ruangan b);
    //DELETE DATA
    public void delete(int id_ruangan);
    //delete all data
    public void deleteAll();
    //SEARCH DATA
    public List<Ruangan> getAllByName(String nama_ruangan);

}
