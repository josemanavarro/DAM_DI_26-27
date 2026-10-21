/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase3.modelo.dto;

/**
 *
 * @author jose
 */
public class Alumno {
    String nombre;
    String apellidos;
    int edad;
    int id;

    public Alumno(String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        id = -1;
    }

    public Alumno(String nombre, String apellidos, int edad, int id) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.id = id;
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
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public String[] getToStringArray() {
        String[] s = new String[3];
        s[0] = nombre;
        s[1] = apellidos;
        Integer e = edad;
        s[2] = e.toString();
        return s;
    }
    
    public static String[] getColumnasArray(){
        String[] columnas = {"Nombre","Apellidos","Edad"};
        return columnas;
    }
}
