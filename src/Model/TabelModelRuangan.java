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
public class TabelModelRuangan extends AbstractTableModel{

    List<Ruangan> lstRu ;
    public TabelModelRuangan(List<Ruangan> lstRu){
        this.lstRu = lstRu;
    }
    

    @Override
    public int getRowCount() {
        return this.lstRu.size();
    }
    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public String getColumnName(int column){
         return switch (column) {
             case 0 -> "ID";
             case 1 -> "Nama Ruangan";
             case 2 -> "Kapasitas";
             case 3 -> "Status";
             case 4 -> "Fasilitas";
             case 5 -> "Lokasi";
             default -> null;
         };    
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         return switch (columnIndex) {
             case 0 -> lstRu.get(rowIndex).getId_ruangan();
             case 1 -> lstRu.get(rowIndex).getNama_ruangan();
             case 2 -> lstRu.get(rowIndex).getKapasitas();
             case 3 -> lstRu.get(rowIndex).getStatus();
             case 4 -> lstRu.get(rowIndex).getFasilitas();
             case 5 -> lstRu.get(rowIndex).getLokasi();
             default -> null;
         };
    }
    
}
