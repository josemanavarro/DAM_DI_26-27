/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase3.controlador;

import java.util.ArrayList;
import java.util.List;
import org.iesalandalus.ejemploclase3.modelo.dto.Alumno;

/**
 *
 * @author jose
 */
public class LogicaNegocio {
    private List<Alumno> listaAlumnos;
    
    public LogicaNegocio(){
        listaAlumnos = new ArrayList<>();
        // Datos basura
        listaAlumnos.add(new Alumno("Juan","Lopez",20));
        listaAlumnos.add(new Alumno("Ana","Gomez",25));
        listaAlumnos.add(new Alumno("Carlos","Perez",30));
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
    
    public void editarAlumno(Alumno alumno, int i){
        listaAlumnos.remove(i);
        listaAlumnos.add(alumno);
    }
}
