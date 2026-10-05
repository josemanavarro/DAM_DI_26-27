/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase2.controlador;

import java.util.ArrayList;
import java.util.List;
import org.iesalandalus.ejemploclase2.modelo.Alumno;

/**
 *
 * @author jose
 */
public class LogicaNegocio {
    private List<Alumno> listaAlumnos;
    
    public LogicaNegocio(){
        listaAlumnos = new ArrayList<>();
        
        listaAlumnos.add(new Alumno("Prueba1","En clase",20));
        listaAlumnos.add(new Alumno("Prueba2","En clase",25));
    }

    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }
    
    public void insertarAlumno(Alumno alumno){
        listaAlumnos.add(alumno);
    }
    
    public int elementos(){
        return listaAlumnos.size();
    }
    
    public Alumno getAlumnoNumero(int i){
        return listaAlumnos.get(i);
    }
}
