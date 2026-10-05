/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase1.dto;

import java.util.Vector;

/**
 *
 * @author jose
 */
public class Alumno {
    String nombre;
    String apellidos;
    int edad;

    public Alumno(String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String[] getToStringArray() {
        String[] s = new String[3];
        s[0] = nombre;
        s[1] = apellidos;
        Integer e = edad;
        s[2] = e.toString();
        return s;
    }
    
    
}
