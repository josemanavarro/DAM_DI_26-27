/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesalandalus.ejemploclase4.modelo.Conexion;
import org.iesalandalus.ejemploclase4.modelo.dto.Modulo;

/**
 * DAO para la gestión de persistencia de la entidad Modulo.
 * 
 * @author jose
 */
public class ModuloDAO {
    private static final Logger LOGGER = Logger.getLogger(ModuloDAO.class.getName());
    
    private static final String SQL_SELECT_ALL = "SELECT siglas, nombre, curso, horas FROM modulos ORDER BY siglas";
    private static final String SQL_SELECT_BY_SIGLAS = "SELECT siglas, nombre, curso, horas FROM modulos WHERE siglas = ?";
    private static final String SQL_INSERT = "INSERT INTO modulos (siglas, nombre, curso, horas) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE modulos SET nombre = ?, curso = ?, horas = ? WHERE siglas = ?";
    private static final String SQL_DELETE = "DELETE FROM modulos WHERE siglas = ?";
    private static final String SQL_DELETE_MATRICULAS = "DELETE FROM matricula WHERE modulo_siglas = ?";
    
    public ModuloDAO() {}
    
    /**
     * Recupera todos los módulos de la base de datos.
     * 
     * @return Lista de módulos.
     */
    public List<Modulo> obtenerTodos() {
        List<Modulo> modulos = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String siglas = rs.getString("siglas");
                String nombre = rs.getString("nombre");
                int curso = rs.getInt("curso");
                int horas = rs.getInt("horas");
                
                modulos.add(new Modulo(siglas, nombre, curso, horas));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al cargar los módulos: {0}", e.getMessage());
        }
        return modulos;
    }

    /**
     * Obtiene un módulo por sus siglas (clave primaria).
     * 
     * @param siglas Siglas identificativas del módulo.
     * @return Modulo encontrado o null si no existe.
     */
    public Modulo obtenerPorSiglas(String siglas) {
        if (siglas == null || siglas.isBlank()) {
            return null;
        }
        Modulo modulo = null;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_SIGLAS)) {
            ps.setString(1, siglas);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    int curso = rs.getInt("curso");
                    int horas = rs.getInt("horas");
                    modulo = new Modulo(siglas, nombre, curso, horas);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al buscar el módulo por siglas: {0}", e.getMessage());
        }
        return modulo;
    }

    /**
     * Inserta un nuevo módulo en la base de datos.
     * 
     * @param modulo Módulo a insertar.
     * @return true si se insertó correctamente, false en caso contrario.
     */
    public boolean insertar(Modulo modulo) {
        if (modulo == null || modulo.getSiglas() == null || modulo.getSiglas().isBlank()) {
            return false;
        }
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
            ps.setString(1, modulo.getSiglas());
            ps.setString(2, modulo.getNombre());
            ps.setInt(3, modulo.getCurso());
            ps.setInt(4, modulo.getHoras());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al insertar el módulo: {0}", e.getMessage());
        }
        return false;
    }

    /**
     * Actualiza los datos de un módulo existente (nombre, curso, horas) a partir de sus siglas.
     * 
     * @param modulo Módulo con los datos actualizados.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    public boolean actualizar(Modulo modulo) {
        if (modulo == null || modulo.getSiglas() == null || modulo.getSiglas().isBlank()) {
            return false;
        }
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, modulo.getNombre());
            ps.setInt(2, modulo.getCurso());
            ps.setInt(3, modulo.getHoras());
            ps.setString(4, modulo.getSiglas());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al actualizar el módulo: {0}", e.getMessage());
        }
        return false;
    }

    /**
     * Elimina un módulo de la base de datos por sus siglas.
     * 
     * @param siglas Siglas del módulo a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminar(String siglas) {
        if (siglas == null || siglas.isBlank()) {
            return false;
        }
        try (Connection con = Conexion.getConexion()){
            boolean autoCommitOriginal = con.getAutoCommit();
            con.setAutoCommit(false);
            try {
                // Borrado en cascada explícito de las matrículas asociadas al módulo
                try (PreparedStatement psMat = con.prepareStatement(SQL_DELETE_MATRICULAS)) {
                    psMat.setString(1, siglas);
                    psMat.executeUpdate();
                }

                int filasAfectadas;
                try (PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
                    ps.setString(1, siglas);
                    filasAfectadas = ps.executeUpdate();
                }

                con.commit();
                return filasAfectadas > 0;
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(autoCommitOriginal);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al eliminar el módulo y sus matrículas en cascada: {0}", e.getMessage());
        }
        return false;
    }


    /**
     * Sobrecarga para eliminar un módulo a partir de su objeto Modulo.
     * 
     * @param modulo Módulo a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminar(Modulo modulo) {
        if (modulo == null) {
            return false;
        }
        return eliminar(modulo.getSiglas());
    }

    /**
     * Comprueba si un módulo existe en la base de datos dado su identificador de siglas.
     * 
     * @param siglas Siglas del módulo a verificar.
     * @return true si existe, false en caso contrario.
     */
    public boolean existe(String siglas) {
        return obtenerPorSiglas(siglas) != null;
    }
}
