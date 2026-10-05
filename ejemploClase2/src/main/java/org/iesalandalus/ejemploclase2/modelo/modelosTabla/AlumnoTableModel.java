/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase2.modelo.modelosTabla;

import javax.swing.table.AbstractTableModel;
import org.iesalandalus.ejemploclase2.controlador.LogicaNegocio;
import org.iesalandalus.ejemploclase2.modelo.Alumno;

/**
 *
 * @author jose
 */
public class AlumnoTableModel extends AbstractTableModel{

    private LogicaNegocio ln;
    
    
    public AlumnoTableModel(LogicaNegocio ln){
        this.ln = ln;
    }
    
    @Override
    public int getRowCount() {
        return ln.elementos();
    }

    @Override
    public int getColumnCount() {
        return Alumno.getColumnasArray().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0 -> {
                return ln.getAlumnoNumero(rowIndex).getNombre();
            }
            case 1 -> {
                return ln.getAlumnoNumero(rowIndex).getApellidos();
            }
            case 2 -> {
                return ln.getAlumnoNumero(rowIndex).getEdad();  
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return Alumno.getColumnasArray()[column];
    }
        
}
