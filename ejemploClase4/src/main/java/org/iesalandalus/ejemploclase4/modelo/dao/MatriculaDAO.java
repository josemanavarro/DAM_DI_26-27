/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesalandalus.ejemploclase4.modelo.Conexion;
import org.iesalandalus.ejemploclase4.modelo.dto.Matricula;

/**
 * DAO para la gestión de persistencia de la entidad Matricula.
 * 
 * @author jose
 */
public class MatriculaDAO {
    private static final Logger LOGGER = Logger.getLogger(MatriculaDAO.class.getName());

    private static final String SQL_SELECT_ALL = "SELECT id, alumno_id, modulo_siglas, fecha_matricula, curso_academico FROM matricula ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, alumno_id, modulo_siglas, fecha_matricula, curso_academico FROM matricula WHERE id = ?";
    private static final String SQL_SELECT_BY_ALUMNO = "SELECT id, alumno_id, modulo_siglas, fecha_matricula, curso_academico FROM matricula WHERE alumno_id = ? ORDER BY fecha_matricula DESC";
    private static final String SQL_SELECT_BY_MODULO = "SELECT id, alumno_id, modulo_siglas, fecha_matricula, curso_academico FROM matricula WHERE modulo_siglas = ? ORDER BY id";
    private static final String SQL_INSERT = "INSERT INTO matricula (alumno_id, modulo_siglas, fecha_matricula, curso_academico) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE matricula SET alumno_id = ?, modulo_siglas = ?, fecha_matricula = ?, curso_academico = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM matricula WHERE id = ?";
    private static final String SQL_DELETE_BY_ALUMNO = "DELETE FROM matricula WHERE alumno_id = ?";
    private static final String SQL_DELETE_BY_MODULO = "DELETE FROM matricula WHERE modulo_siglas = ?";

    public MatriculaDAO() {}

    /**
     * Mapea un registro del ResultSet a un objeto Matricula.
     * 
     * @param rs ResultSet posicionado en la fila actual.
     * @return Objeto Matricula mapeado.
     * @throws Exception Si ocurre algún error en la lectura.
     */
    private Matricula mapearMatricula(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        int alumnoId = rs.getInt("alumno_id");
        String moduloSiglas = rs.getString("modulo_siglas");
        Date sqlDate = rs.getDate("fecha_matricula");
        LocalDate fechaMatricula = (sqlDate != null) ? sqlDate.toLocalDate() : null;
        String cursoAcademico = rs.getString("curso_academico");

        return new Matricula(id, alumnoId, moduloSiglas, fechaMatricula, cursoAcademico);
    }

    /**
     * Recupera todas las matrículas de la base de datos.
     * 
     * @return Lista de matrículas.
     */
    public List<Matricula> obtenerTodos() {
        List<Matricula> matriculas = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                matriculas.add(mapearMatricula(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al cargar las matrículas: {0}", e.getMessage());
        }
        return matriculas;
    }

    /**
     * Obtiene una matrícula por su identificador primario.
     * 
     * @param id Identificador de la matrícula.
     * @return Matricula encontrada o null si no existe.
     */
    public Matricula obtenerPorId(int id) {
        Matricula matricula = null;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    matricula = mapearMatricula(rs);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al buscar la matrícula por id: {0}", e.getMessage());
        }
        return matricula;
    }

    /**
     * Recupera todas las matrículas asociadas a un alumno.
     * 
     * @param alumnoId Identificador del alumno.
     * @return Lista de matrículas del alumno.
     */
    public List<Matricula> obtenerPorAlumno(int alumnoId) {
        List<Matricula> matriculas = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ALUMNO)) {
            ps.setInt(1, alumnoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    matriculas.add(mapearMatricula(rs));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al buscar matrículas por alumno: {0}", e.getMessage());
        }
        return matriculas;
    }

    /**
     * Recupera todas las matrículas asociadas a un módulo específico.
     * 
     * @param moduloSiglas Siglas del módulo.
     * @return Lista de matrículas del módulo.
     */
    public List<Matricula> obtenerPorModulo(String moduloSiglas) {
        List<Matricula> matriculas = new ArrayList<>();
        if (moduloSiglas == null || moduloSiglas.isBlank()) {
            return matriculas;
        }
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_MODULO)) {
            ps.setString(1, moduloSiglas);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    matriculas.add(mapearMatricula(rs));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al buscar matrículas por módulo: {0}", e.getMessage());
        }
        return matriculas;
    }

    /**
     * Inserta una nueva matrícula en la base de datos y le asigna el ID generado.
     * 
     * @param matricula Matrícula a insertar.
     * @return true si se insertó correctamente, false en caso contrario.
     */
    public boolean insertar(Matricula matricula) {
        if (matricula == null) {
            return false;
        }
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, matricula.getAlumnoId());
            ps.setString(2, matricula.getModuloSiglas());
            ps.setDate(3, matricula.getFechaMatricula() != null ? Date.valueOf(matricula.getFechaMatricula()) : null);
            ps.setString(4, matricula.getCursoAcademico());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        matricula.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al insertar la matrícula: {0}", e.getMessage());
        }
        return false;
    }

    /**
     * Actualiza los datos de una matrícula existente en la base de datos.
     * 
     * @param matricula Matrícula con los datos actualizados (debe tener un ID válido).
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    public boolean actualizar(Matricula matricula) {
        if (matricula == null || matricula.getId() <= 0) {
            return false;
        }
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, matricula.getAlumnoId());
            ps.setString(2, matricula.getModuloSiglas());
            ps.setDate(3, matricula.getFechaMatricula() != null ? Date.valueOf(matricula.getFechaMatricula()) : null);
            ps.setString(4, matricula.getCursoAcademico());
            ps.setInt(5, matricula.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al actualizar la matrícula: {0}", e.getMessage());
        }
        return false;
    }

    /**
     * Elimina una matrícula de la base de datos dado su ID.
     * 
     * @param id Identificador de la matrícula a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminar(int id) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al eliminar la matrícula: {0}", e.getMessage());
        }
        return false;
    }

    /**
     * Sobrecarga para eliminar una matrícula a partir de su objeto Matricula.
     * 
     * @param matricula Matrícula a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminar(Matricula matricula) {
        if (matricula == null) {
            return false;
        }
        return eliminar(matricula.getId());
    }

    /**
     * Elimina todas las matrículas asociadas a un alumno específico.
     * 
     * @param alumnoId Identificador del alumno.
     * @return Número de matrículas eliminadas.
     */
    public int eliminarPorAlumno(int alumnoId) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE_BY_ALUMNO)) {
            ps.setInt(1, alumnoId);
            return ps.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al eliminar las matrículas del alumno: {0}", e.getMessage());
        }
        return 0;
    }

    /**
     * Elimina todas las matrículas asociadas a un módulo específico.
     * 
     * @param moduloSiglas Siglas del módulo.
     * @return Número de matrículas eliminadas.
     */
    public int eliminarPorModulo(String moduloSiglas) {
        if (moduloSiglas == null || moduloSiglas.isBlank()) {
            return 0;
        }
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE_BY_MODULO)) {
            ps.setString(1, moduloSiglas);
            return ps.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR: Error al eliminar las matrículas del módulo: {0}", e.getMessage());
        }
        return 0;
    }
}

