/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase3.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesalandalus.ejemploclase3.modelo.dao.AlumnoDAO;
import org.iesalandalus.ejemploclase3.modelo.dto.Alumno;

/**
 * Controlador de lógica de negocio que gestiona la lista de alumnos
 * y coordina la persistencia mediante AlumnoDAO.
 *
 * @author jose
 */
public class LogicaNegocioAlumno {
    private static final Logger LOGGER = Logger.getLogger(LogicaNegocioAlumno.class.getName());
    
    private List<Alumno> listaAlumnos;
    private AlumnoDAO dao;
    
    public LogicaNegocioAlumno(){
        dao = new AlumnoDAO();
        recargarAlumnos();
    }

    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }
    
    /**
     * Inserta un alumno en la base de datos a través del DAO.
     * Si la inserción tiene éxito, se añade a la lista en memoria con su nuevo ID.
     * 
     * @param alumno Alumno a insertar.
     * @return true si se insertó en BD y en la lista, false en caso contrario.
     */
    public boolean insertarAlumno(Alumno alumno){
        if (alumno == null){
            return false;
        }
        boolean insertado = dao.insertar(alumno);
        if (insertado){
            listaAlumnos.add(alumno);
        } else {
            LOGGER.log(Level.WARNING, "No se pudo insertar el alumno en la base de datos.");
        }
        return insertado;
    }
    
    public int elementos(){
        return listaAlumnos.size();
    }
    
    public Alumno getAlumnoNumero(int i){
        if (i >= 0 && i < listaAlumnos.size()){
            return listaAlumnos.get(i);
        }
        return null;
    }
    
    /**
     * Actualiza un alumno en la base de datos y en la lista en memoria.
     * Conserva el ID del registro original si el objeto recibido no lo contiene.
     * 
     * @param alumno Alumno con los nuevos datos.
     * @param i Índice en la lista del alumno a editar.
     * @return true si se actualizó en la BD, false en caso contrario.
     */
    public boolean editarAlumno(Alumno alumno, int i){
        if (alumno == null || i < 0 || i >= listaAlumnos.size()){
            return false;
        }
        
        Alumno original = listaAlumnos.get(i);
        // Si el alumno recibido no tiene el ID asignado, se preserva el ID original
        if (alumno.getId() <= 0){
            alumno.setId(original.getId());
        }
        
        boolean actualizado = dao.actualizar(alumno);
        if (actualizado){
            listaAlumnos.set(i, alumno);
        } else {
            LOGGER.log(Level.WARNING, "No se pudo actualizar el alumno en la base de datos.");
        }
        return actualizado;
    }

    /**
     * Elimina un alumno de la base de datos y de la lista en memoria.
     * 
     * @param i Índice en la lista del alumno a eliminar.
     * @return true si se eliminó en la BD, false en caso contrario.
     */
    public boolean eliminarAlumno(int i){
        if (i < 0 || i >= listaAlumnos.size()){
            return false;
        }
        Alumno alumno = listaAlumnos.get(i);
        boolean eliminado = dao.eliminar(alumno.getId());
        if (eliminado){
            listaAlumnos.remove(i);
        } else {
            LOGGER.log(Level.WARNING, "No se pudo eliminar el alumno de la base de datos.");
        }
        return eliminado;
    }

    /**
     * Elimina un alumno por su objeto.
     * 
     * @param alumno Alumno a eliminar.
     * @return true si se eliminó, false en caso contrario.
     */
    public boolean eliminarAlumno(Alumno alumno){
        if (alumno == null){
            return false;
        }
        boolean eliminado = dao.eliminar(alumno.getId());
        if (eliminado){
            listaAlumnos.remove(alumno);
        }
        return eliminado;
    }

    /**
     * Busca un alumno directamente en la base de datos por su ID.
     * 
     * @param id Identificador del alumno.
     * @return Alumno o null si no existe.
     */
    public Alumno getAlumnoPorId(int id){
        return dao.obtenerPorId(id);
    }

    /**
     * Vuelve a cargar todos los alumnos desde la base de datos.
     */
    public final void recargarAlumnos(){
        listaAlumnos = dao.obtenerTodos();
        if (listaAlumnos == null){
            listaAlumnos = new ArrayList<>();
        }
    }

    public AlumnoDAO getDao() {
        return dao;
    }
}
