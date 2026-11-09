package org.iesalandalus.ejemploclase3.modelo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion{
    
    private static final Logger LOGGER = Logger.getLogger(Conexion.class.getName());
    private static final String FICHERO = "db.properties";
    private static Properties properties = null;
    private static Connection conexion = null;
    
    public Conexion(){}
    
    private static synchronized void cargarProperties(){
        if (properties == null){
            try (InputStream input = Conexion.class.getClassLoader().getResourceAsStream(FICHERO)){
                if (input == null){
                    LOGGER.log(Level.SEVERE,"No se encontro el fichero de properties");
                    return;
                }
                
                properties = new Properties();
                properties.load(input);
            } catch (IOException e){
                LOGGER.log(Level.SEVERE, () -> "ERROR: Fallo la conexión "+e.getMessage());
            }
        }
    }
    
    public static Connection getConexion() throws SQLException{
        cargarProperties();
        if (properties == null){
            throw new SQLException("No se pudo cargar la configuración desde " + FICHERO);
        }
        
        String user = properties.getProperty("db.user");
        if (user == null || user.isBlank()){
            user = properties.getProperty("db.username");
        }
        /*
        Mejorar el tema errores
        if (user == null || user.isBlank() || user.isEmpty())
            user = "root";
        */
        String password = properties.getProperty("db.password");
        String url = properties.getProperty("db.url");
        
        conexion = DriverManager.getConnection(url, user, password);
        return conexion;
    }
    
    public static void cerrarConexion(){
        try{
            if (conexion != null)
                    conexion.close();
        } catch(SQLException e){
            LOGGER.log(Level.SEVERE, () -> "ERROR: Fallo el cierre de la conexión: "+e.getMessage());
        }
    }
}
        
