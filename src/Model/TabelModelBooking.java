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
public class TabelModelBooking extends AbstractTableModel {

    List<Booking> lstBo ;
    public TabelModelBooking(List<Booking> lstBo){
        this.lstBo = lstBo;
    }
    

    @Override
    public int getRowCount() {
        return this.lstBo.size();
    }
    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public String getColumnName(int column){
         return switch (column) {
             case 0 -> "ID";
             case 1 -> "Kode Booking";
             case 2 -> "Nama Peminjam";
             case 3 -> "Nama Ruangan";
             case 4 -> "Tanggal Booking";
             case 5 -> "Tujuan";
             default -> null;
         };    
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         return switch (columnIndex) {
             case 0 -> lstBo.get(rowIndex).getId_booking();
             case 1 -> lstBo.get(rowIndex).getKode_booking();
             case 2 -> lstBo.get(rowIndex).getNama_peminjam();
             case 3 -> lstBo.get(rowIndex).getNama_ruangan();
             case 4 -> lstBo.get(rowIndex).getTanggal_booking();
             case 5 -> lstBo.get(rowIndex).getTujuan();
             default -> null;
         };
    }
    
}
