/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.modelo.modelosTabla;

import javax.swing.table.AbstractTableModel;
import org.iesalandalus.ejemploclase4.controlador.LogicaNegocioAlumno;
import org.iesalandalus.ejemploclase4.modelo.dto.Alumno;


/**
 *
 * @author jose
 */
public class AlumnoTableModel extends AbstractTableModel{

    private LogicaNegocioAlumno ln;
    
    
    public AlumnoTableModel(){
        this(null);
    }

    public AlumnoTableModel(LogicaNegocioAlumno ln){
        this.ln = ln;
    }
    
    @Override
    public int getRowCount() {
        return (ln != null) ? ln.elementos() : 0;
    }

    @Override
    public int getColumnCount() {
        return Alumno.getColumnasArray().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (ln == null) {
            return null;
        }
        Alumno alumno = ln.getAlumnoNumero(rowIndex);
        if (alumno == null) {
            return null;
        }
        switch (columnIndex){
            case 0 -> {
                return alumno.getNombre();
            }
            case 1 -> {
                return alumno.getApellidos();
            }
            case 2 -> {
                return alumno.getEdad();  
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return Alumno.getColumnasArray()[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0, 1 -> {
                return String.class;
            }
            case 2 -> {
                return Integer.class;
            }
            default -> {
                return Object.class;
            }
        }
    }

    public Alumno getAlumnoAt(int rowIndex) {
        if (ln != null) {
            return ln.getAlumnoNumero(rowIndex);
        }
        return null;
    }

    public LogicaNegocioAlumno getLn() {
        return ln;
    }

    public void setLn(LogicaNegocioAlumno ln) {
        this.ln = ln;
        fireTableDataChanged();
    }
}
