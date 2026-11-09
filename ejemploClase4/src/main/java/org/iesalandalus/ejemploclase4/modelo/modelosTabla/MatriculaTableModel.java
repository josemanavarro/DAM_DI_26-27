/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.modelo.modelosTabla;

import java.time.LocalDate;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.iesalandalus.ejemploclase4.controlador.LogicaNegocioMatricula;
import org.iesalandalus.ejemploclase4.modelo.dto.Matricula;

/**
 * TableModel para la visualización y gestión de matrículas en un JTable.
 * 
 * @author jose
 */
public class MatriculaTableModel extends AbstractTableModel {

    private LogicaNegocioMatricula ln;
    private List<Matricula> listaMatriculas;

    public MatriculaTableModel() {
        this((LogicaNegocioMatricula) null);
    }

    public MatriculaTableModel(LogicaNegocioMatricula ln) {
        this.ln = ln;
    }

    public MatriculaTableModel(List<Matricula> listaMatriculas) {
        this.listaMatriculas = listaMatriculas;
    }

    @Override
    public int getRowCount() {
        if (ln != null) {
            return ln.elementos();
        }
        if (listaMatriculas != null) {
            return listaMatriculas.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return Matricula.getColumnasArray().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Matricula matricula = getMatriculaAt(rowIndex);
        if (matricula == null) {
            return null;
        }
        switch (columnIndex) {
            case 0 -> {
                return matricula.getId();
            }
            case 1 -> {
                return matricula.getAlumnoId();
            }
            case 2 -> {
                return matricula.getModuloSiglas();
            }
            case 3 -> {
                return matricula.getFechaMatricula();
            }
            case 4 -> {
                return matricula.getCursoAcademico();
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return Matricula.getColumnasArray()[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0, 1 -> {
                return Integer.class;
            }
            case 2, 4 -> {
                return String.class;
            }
            case 3 -> {
                return LocalDate.class;
            }
            default -> {
                return Object.class;
            }
        }
    }

    public Matricula getMatriculaAt(int rowIndex) {
        if (ln != null) {
            return ln.getMatriculaNumero(rowIndex);
        }
        if (listaMatriculas != null && rowIndex >= 0 && rowIndex < listaMatriculas.size()) {
            return listaMatriculas.get(rowIndex);
        }
        return null;
    }

    public LogicaNegocioMatricula getLn() {
        return ln;
    }

    public void setLn(LogicaNegocioMatricula ln) {
        this.ln = ln;
        this.listaMatriculas = null;
        fireTableDataChanged();
    }

    public List<Matricula> getListaMatriculas() {
        if (ln != null) {
            return ln.getListaMatriculas();
        }
        return listaMatriculas;
    }

    public void setListaMatriculas(List<Matricula> listaMatriculas) {
        this.listaMatriculas = listaMatriculas;
        this.ln = null;
        fireTableDataChanged();
    }
}
