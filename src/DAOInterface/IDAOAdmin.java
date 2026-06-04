/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAOInterface;

import Model.Admin;

/**
 *
 * @author ASUS
 */
public interface IDAOAdmin {
    public Admin getAdmin(); // Untuk baca data (Read)
    public void update(Admin a); // Untuk ubah data (Update)
    public boolean cekLogin(String user, String pass); // Untuk login
}
