/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase3.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesalandalus.ejemploclase3.modelo.Conexion;
import org.iesalandalus.ejemploclase3.modelo.dto.Alumno;

/**
 *
 * @author jose
 */
public class AlumnoDAO {
    private static final Logger LOGGER = Logger.getLogger(AlumnoDAO.class.getName());
    
    private static final String SQL_SELECT_ALL = "SELECT id, nombre, apellidos, edad FROM alumnos ORDER by id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nombre, apellidos, edad FROM alumnos WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO alumnos (nombre, apellidos, edad) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE alumnos SET nombre = ?, apellidos = ?, edad = ? WHERE id = ?";
    private static final String SQL_DELETE ="DELETE FROM alumnos WHERE id=?";
    
    public AlumnoDAO(){}
    
    /**
     * Recupera todos los alumnos de la base de datos.
     * 
     * @return Lista de alumnos.
     */
    public List<Alumno> obtenerTodos(){
        List<Alumno> alumnos = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()){
            
            while (rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                int edad = rs.getInt("edad");
                
                alumnos.add(new Alumno(id, nombre, apellidos, edad));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al cargar los alumnos: {0}", e.getMessage());
        } 
        return alumnos;
    }

    /**
     * Obtiene un alumno por su identificador primario.
     * 
     * @param id Identificador del alumno.
     * @return Alumno encontrado o null si no existe.
     */
    public Alumno obtenerPorId(int id){
        Alumno alumno = null;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)){
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    int edad = rs.getInt("edad");
                    alumno = new Alumno(id, nombre, apellidos, edad);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al buscar el alumno por id: {0}", e.getMessage());
        }
        return alumno;
    }

    /**
     * Inserta un nuevo alumno en la base de datos y le asigna el ID generado.
     * 
     * @param alumno Alumno a insertar.
     * @return true si se insertó correctamente, false en caso contrario.
     */
    public boolean insertar(Alumno alumno){
        if (alumno == null){
            return false;
        }
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellidos());
            ps.setInt(3, alumno.getEdad());
            
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0){
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()){
                        alumno.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al insertar el alumno: {0}", e.getMessage());
        }
        return false;
    }

    /**
     * Actualiza los datos de un alumno existente en la base de datos.
     * 
     * @param alumno Alumno con los datos actualizados (debe tener un ID válido).
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    public boolean actualizar(Alumno alumno){
        if (alumno == null || alumno.getId() <= 0){
            return false;
        }
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)){
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellidos());
            ps.setInt(3, alumno.getEdad());
            ps.setInt(4, alumno.getId());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al actualizar el alumno: {0}", e.getMessage());
        }
        return false;
    }

    /**
     * Elimina un alumno de la base de datos dado su ID.
     * 
     * @param id Identificador del alumno a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminar(int id){
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE)){
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al eliminar el alumno: {0}", e.getMessage());
        }
        return false;
    }

    /**
     * Sobrecarga para eliminar un alumno a partir de su objeto Alumno.
     * 
     * @param alumno Alumno a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminar(Alumno alumno){
        if (alumno == null){
            return false;
        }
        return eliminar(alumno.getId());
    }
}
