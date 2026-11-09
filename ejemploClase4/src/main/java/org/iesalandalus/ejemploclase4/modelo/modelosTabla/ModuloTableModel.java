/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.modelo.modelosTabla;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.iesalandalus.ejemploclase4.controlador.LogicaNegocioModulo;
import org.iesalandalus.ejemploclase4.modelo.dto.Modulo;

/**
 * TableModel para la visualización y gestión de módulos en un JTable.
 * 
 * @author jose
 */
public class ModuloTableModel extends AbstractTableModel {

    private LogicaNegocioModulo ln;
    private List<Modulo> listaModulos;

    public ModuloTableModel() {
        this((LogicaNegocioModulo) null);
    }

    public ModuloTableModel(LogicaNegocioModulo ln) {
        this.ln = ln;
    }

    public ModuloTableModel(List<Modulo> listaModulos) {
        this.listaModulos = listaModulos;
    }

    @Override
    public int getRowCount() {
        if (ln != null) {
            return ln.elementos();
        }
        if (listaModulos != null) {
            return listaModulos.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return Modulo.getColumnasArray().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Modulo modulo = getModuloAt(rowIndex);
        if (modulo == null) {
            return null;
        }
        switch (columnIndex) {
            case 0 -> {
                return modulo.getSiglas();
            }
            case 1 -> {
                return modulo.getNombre();
            }
            case 2 -> {
                return modulo.getCurso();
            }
            case 3 -> {
                return modulo.getHoras();
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return Modulo.getColumnasArray()[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0, 1 -> {
                return String.class;
            }
            case 2, 3 -> {
                return Integer.class;
            }
            default -> {
                return Object.class;
            }
        }
    }

    public Modulo getModuloAt(int rowIndex) {
        if (ln != null) {
            return ln.getModuloNumero(rowIndex);
        }
        if (listaModulos != null && rowIndex >= 0 && rowIndex < listaModulos.size()) {
            return listaModulos.get(rowIndex);
        }
        return null;
    }

    public LogicaNegocioModulo getLn() {
        return ln;
    }

    public void setLn(LogicaNegocioModulo ln) {
        this.ln = ln;
        this.listaModulos = null;
        fireTableDataChanged();
    }

    public List<Modulo> getListaModulos() {
        if (ln != null) {
            return ln.getListaModulos();
        }
        return listaModulos;
    }

    public void setListaModulos(List<Modulo> listaModulos) {
        this.listaModulos = listaModulos;
        this.ln = null;
        fireTableDataChanged();
    }
}
