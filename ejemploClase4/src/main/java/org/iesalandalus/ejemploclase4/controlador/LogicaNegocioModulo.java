/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesalandalus.ejemploclase4.modelo.dao.ModuloDAO;
import org.iesalandalus.ejemploclase4.modelo.dto.Modulo;

/**
 * Controlador de lógica de negocio que gestiona la lista de módulos
 * y coordina la persistencia mediante ModuloDAO.
 * 
 * @author jose
 */
public class LogicaNegocioModulo {
    private static final Logger LOGGER = Logger.getLogger(LogicaNegocioModulo.class.getName());
    
    private List<Modulo> listaModulos;
    private ModuloDAO dao;
    
    public LogicaNegocioModulo() {
        dao = new ModuloDAO();
        recargarModulos();
    }

    public List<Modulo> getListaModulos() {
        return listaModulos;
    }
    
    /**
     * Inserta un módulo en la base de datos a través del DAO.
     * Si la inserción tiene éxito, se añade a la lista en memoria.
     * 
     * @param modulo Módulo a insertar.
     * @return true si se insertó en BD y en la lista, false en caso contrario.
     */
    public boolean insertarModulo(Modulo modulo) {
        if (modulo == null) {
            return false;
        }
        boolean insertado = dao.insertar(modulo);
        if (insertado) {
            listaModulos.add(modulo);
        } else {
            LOGGER.log(Level.WARNING, "No se pudo insertar el módulo en la base de datos.");
        }
        return insertado;
    }
    
    public int elementos() {
        return (listaModulos != null) ? listaModulos.size() : 0;
    }
    
    public Modulo getModuloNumero(int i) {
        if (listaModulos != null && i >= 0 && i < listaModulos.size()) {
            return listaModulos.get(i);
        }
        return null;
    }
    
    /**
     * Actualiza un módulo en la base de datos y en la lista en memoria.
     * 
     * @param modulo Módulo con los nuevos datos.
     * @param i Índice en la lista del módulo a editar.
     * @return true si se actualizó en la BD, false en caso contrario.
     */
    public boolean editarModulo(Modulo modulo, int i) {
        if (modulo == null || listaModulos == null || i < 0 || i >= listaModulos.size()) {
            return false;
        }
        
        boolean actualizado = dao.actualizar(modulo);
        if (actualizado) {
            listaModulos.set(i, modulo);
        } else {
            LOGGER.log(Level.WARNING, "No se pudo actualizar el módulo en la base de datos.");
        }
        return actualizado;
    }

    private LogicaNegocioMatricula lnmat;

    public void setLnmat(LogicaNegocioMatricula lnmat) {
        this.lnmat = lnmat;
    }

    /**
     * Elimina un módulo de la base de datos y de la lista en memoria por su índice,
     * eliminando en cascada todas sus matrículas asociadas.
     * 
     * @param i Índice en la lista del módulo a eliminar.
     * @return true si se eliminó en la BD, false en caso contrario.
     */
    public boolean eliminarModulo(int i) {
        if (listaModulos == null || i < 0 || i >= listaModulos.size()) {
            return false;
        }
        Modulo modulo = listaModulos.get(i);
        return eliminarModulo(modulo);
    }

    /**
     * Elimina un módulo por su objeto, eliminando en cascada sus matrículas.
     * 
     * @param modulo Módulo a eliminar.
     * @return true si se eliminó, false en caso contrario.
     */
    public boolean eliminarModulo(Modulo modulo) {
        if (modulo == null || listaModulos == null) {
            return false;
        }
        String siglas = modulo.getSiglas();
        boolean eliminado = dao.eliminar(siglas);
        if (eliminado) {
            listaModulos.remove(modulo);
            if (lnmat != null) {
                lnmat.eliminarMatriculasPorModulo(siglas);
            }
        } else {
            LOGGER.log(Level.WARNING, "No se pudo eliminar el módulo de la base de datos.");
        }
        return eliminado;
    }


    /**
     * Busca un módulo directamente en la base de datos por sus siglas.
     * 
     * @param siglas Siglas identificativas del módulo.
     * @return Modulo o null si no existe.
     */
    public Modulo getModuloPorSiglas(String siglas) {
        return dao.obtenerPorSiglas(siglas);
    }

    /**
     * Vuelve a cargar todos los módulos desde la base de datos.
     */
    public final void recargarModulos() {
        listaModulos = dao.obtenerTodos();
        if (listaModulos == null) {
            listaModulos = new ArrayList<>();
        }
    }

    public ModuloDAO getDao() {
        return dao;
    }
}
