/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesalandalus.ejemploclase4.modelo.Conexion;
import org.iesalandalus.ejemploclase4.modelo.dto.Credenciales;

/**
 * DAO para la gestión de persistencia de las credenciales de usuarios
 * en la tabla 'usuarios' de la base de datos.
 * 
 * @author jose
 */
public class CredencialesDAO {

    private static final Logger LOGGER = Logger.getLogger(CredencialesDAO.class.getName());

    private static final String SQL_VALIDAR = "SELECT COUNT(*) FROM usuarios WHERE login = ? AND password = ?";
    private static final String SQL_SELECT_BY_LOGIN = "SELECT id, login, password FROM usuarios WHERE login = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, login, password FROM usuarios ORDER BY login";
    private static final String SQL_INSERT = "INSERT INTO usuarios (login, password) VALUES (?, ?)";

    public CredencialesDAO() {}

    /**
     * Valida si un objeto Credenciales coincide con algún usuario registrado en la base de datos.
     * 
     * @param credenciales Credenciales a verificar (login y password).
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public boolean validar(Credenciales credenciales) {
        if (credenciales == null) {
            return false;
        }
        return validar(credenciales.getLogin(), credenciales.getPassword());
    }

    /**
     * Valida las credenciales comprobando login y password contra la tabla usuarios.
     * 
     * @param login Nombre de usuario.
     * @param password Contraseña en texto plano.
     * @return true si existe el usuario con dicha contraseña, false si no o si hay error.
     */
    public boolean validar(String login, String password) {
        if (login == null || password == null || login.trim().isEmpty() || password.isEmpty()) {
            return false;
        }

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_VALIDAR)) {

            ps.setString(1, login.trim());
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ERROR al validar credenciales en la base de datos: {0}", e.getMessage());
        }
        return false;
    }

    /**
     * Obtiene las credenciales de un usuario a partir de su login.
     * 
     * @param login Nombre de usuario a buscar.
     * @return Objeto Credenciales o null si no se encuentra.
     */
    public Credenciales obtenerPorLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            return null;
        }

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_LOGIN)) {

            ps.setString(1, login.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String userLogin = rs.getString("login");
                    String userPass = rs.getString("password");
                    return new Credenciales(id, userLogin, userPass);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ERROR al obtener credenciales por login: {0}", e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene la lista de todos los usuarios registrados.
     * 
     * @return Lista de Credenciales.
     */
    public List<Credenciales> obtenerTodos() {
        List<Credenciales> usuarios = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                usuarios.add(new Credenciales(id, login, password));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ERROR al cargar la lista de usuarios: {0}", e.getMessage());
        }
        return usuarios;
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     * 
     * @param credenciales Credenciales a registrar.
     * @return true si se insertó con éxito, false en caso contrario.
     */
    public boolean insertar(Credenciales credenciales) {
        if (credenciales == null || credenciales.getLogin() == null || credenciales.getPassword() == null) {
            return false;
        }

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, credenciales.getLogin().trim());
            ps.setString(2, credenciales.getPassword());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        credenciales.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ERROR al insertar usuario: {0}", e.getMessage());
        }
        return false;
    }
}
