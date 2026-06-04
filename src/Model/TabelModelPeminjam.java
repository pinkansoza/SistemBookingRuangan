/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ASUS
 */
public class TabelModelPeminjam extends AbstractTableModel {

    List<Peminjam> lstPmj ;
    public TabelModelPeminjam(List<Peminjam> lstPmj){
        this.lstPmj = lstPmj;
    }
    

    @Override
    public int getRowCount() {
        return this.lstPmj.size();
    }
    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public String getColumnName(int column){
         return switch (column) {
             case 0 -> "ID";
             case 1 -> "Nama Peminjam";
             case 2 -> "NIM/NIP";
             case 3 -> "No Telp";
             case 4 -> "Email";
             case 5 -> "Status Peminjam";
             default -> null;
         };    
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         return switch (columnIndex) {
             case 0 -> lstPmj.get(rowIndex).getId_peminjam();
             case 1 -> lstPmj.get(rowIndex).getNama_peminjam();
             case 2 -> lstPmj.get(rowIndex).getNim_nip();
             case 3 -> lstPmj.get(rowIndex).getNo_telp();
             case 4 -> lstPmj.get(rowIndex).getEmail();
             case 5 -> lstPmj.get(rowIndex).getStatus_peminjam();
             default -> null;
         };
    }
    
}
