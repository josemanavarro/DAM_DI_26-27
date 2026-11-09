/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesalandalus.ejemploclase4.modelo.dao.MatriculaDAO;
import org.iesalandalus.ejemploclase4.modelo.dto.Matricula;

/**
 * Controlador de lógica de negocio que gestiona la lista de matrículas
 * y coordina la persistencia mediante MatriculaDAO.
 * 
 * @author jose
 */
public class LogicaNegocioMatricula {
    private static final Logger LOGGER = Logger.getLogger(LogicaNegocioMatricula.class.getName());
    
    private List<Matricula> listaMatriculas;
    private MatriculaDAO dao;
    
    public LogicaNegocioMatricula() {
        dao = new MatriculaDAO();
        recargarMatriculas();
    }

    public List<Matricula> getListaMatriculas() {
        return listaMatriculas;
    }
    
    /**
     * Inserta una matrícula en la base de datos a través del DAO.
     * Si la inserción tiene éxito, se añade a la lista en memoria con su nuevo ID.
     * 
     * @param matricula Matrícula a insertar.
     * @return true si se insertó en BD y en la lista, false en caso contrario.
     */
    public boolean insertarMatricula(Matricula matricula) {
        if (matricula == null) {
            return false;
        }
        boolean insertado = dao.insertar(matricula);
        if (insertado) {
            listaMatriculas.add(matricula);
        } else {
            LOGGER.log(Level.WARNING, "No se pudo insertar la matrícula en la base de datos.");
        }
        return insertado;
    }
    
    public int elementos() {
        return (listaMatriculas != null) ? listaMatriculas.size() : 0;
    }
    
    public Matricula getMatriculaNumero(int i) {
        if (listaMatriculas != null && i >= 0 && i < listaMatriculas.size()) {
            return listaMatriculas.get(i);
        }
        return null;
    }
    
    /**
     * Actualiza una matrícula en la base de datos y en la lista en memoria.
     * Conserva el ID del registro original si el objeto recibido no lo contiene.
     * 
     * @param matricula Matrícula con los nuevos datos.
     * @param i Índice en la lista de la matrícula a editar.
     * @return true si se actualizó en la BD, false en caso contrario.
     */
    public boolean editarMatricula(Matricula matricula, int i) {
        if (matricula == null || listaMatriculas == null || i < 0 || i >= listaMatriculas.size()) {
            return false;
        }
        
        Matricula original = listaMatriculas.get(i);
        if (matricula.getId() <= 0) {
            matricula.setId(original.getId());
        }
        
        boolean actualizado = dao.actualizar(matricula);
        if (actualizado) {
            listaMatriculas.set(i, matricula);
        } else {
            LOGGER.log(Level.WARNING, "No se pudo actualizar la matrícula en la base de datos.");
        }
        return actualizado;
    }

    /**
     * Elimina una matrícula de la base de datos y de la lista en memoria por su índice.
     * 
     * @param i Índice en la lista de la matrícula a eliminar.
     * @return true si se eliminó en la BD, false en caso contrario.
     */
    public boolean eliminarMatricula(int i) {
        if (listaMatriculas == null || i < 0 || i >= listaMatriculas.size()) {
            return false;
        }
        Matricula matricula = listaMatriculas.get(i);
        boolean eliminado = dao.eliminar(matricula.getId());
        if (eliminado) {
            listaMatriculas.remove(i);
        } else {
            LOGGER.log(Level.WARNING, "No se pudo eliminar la matrícula de la base de datos.");
        }
        return eliminado;
    }

    /**
     * Elimina una matrícula por su objeto.
     * 
     * @param matricula Matrícula a eliminar.
     * @return true si se eliminó, false en caso contrario.
     */
    public boolean eliminarMatricula(Matricula matricula) {
        if (matricula == null || listaMatriculas == null) {
            return false;
        }
        boolean eliminado = dao.eliminar(matricula.getId());
        if (eliminado) {
            listaMatriculas.remove(matricula);
        }
        return eliminado;
    }

    /**
     * Busca una matrícula directamente en la base de datos por su ID.
     * 
     * @param id Identificador de la matrícula.
     * @return Matricula o null si no existe.
     */
    public Matricula getMatriculaPorId(int id) {
        return dao.obtenerPorId(id);
    }

    /**
     * Obtiene las matrículas de un alumno por su ID.
     * 
     * @param alumnoId ID del alumno.
     * @return Lista de matrículas del alumno.
     */
    public List<Matricula> getMatriculasPorAlumno(int alumnoId) {
        return dao.obtenerPorAlumno(alumnoId);
    }

    /**
     * Obtiene las matrículas asociadas a un módulo.
     * 
     * @param moduloSiglas Siglas del módulo.
     * @return Lista de matrículas del módulo.
     */
    public List<Matricula> getMatriculasPorModulo(String moduloSiglas) {
        return dao.obtenerPorModulo(moduloSiglas);
    }

    /**
     * Vuelve a cargar todas las matrículas desde la base de datos.
     */
    public final void recargarMatriculas() {
        listaMatriculas = dao.obtenerTodos();
        if (listaMatriculas == null) {
            listaMatriculas = new ArrayList<>();
        }
    }

    public MatriculaDAO getDao() {
        return dao;
    }

    /**
     * Elimina en cascada todas las matrículas de un alumno, tanto en BD como en memoria.
     * 
     * @param alumnoId ID del alumno.
     * @return Número de matrículas eliminadas.
     */
    public int eliminarMatriculasPorAlumno(int alumnoId) {
        int eliminadas = dao.eliminarPorAlumno(alumnoId);
        if (listaMatriculas != null) {
            listaMatriculas.removeIf(m -> m.getAlumnoId() == alumnoId);
        }
        return eliminadas;
    }

    /**
     * Elimina en cascada todas las matrículas de un módulo, tanto en BD como en memoria.
     * 
     * @param moduloSiglas Siglas del módulo.
     * @return Número de matrículas eliminadas.
     */
    public int eliminarMatriculasPorModulo(String moduloSiglas) {
        if (moduloSiglas == null || moduloSiglas.isBlank()) {
            return 0;
        }
        int eliminadas = dao.eliminarPorModulo(moduloSiglas);
        if (listaMatriculas != null) {
            listaMatriculas.removeIf(m -> moduloSiglas.equalsIgnoreCase(m.getModuloSiglas()));
        }
        return eliminadas;
    }
}

