/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.modelo.dto;

import java.util.Objects;

/**
 * DTO que representa un módulo profesional.
 * 
 * @author jose
 */
public class Modulo {
    private String siglas;
    private String nombre;
    private int curso;
    private int horas;

    public Modulo() {
    }

    public Modulo(String siglas, String nombre, int curso, int horas) {
        this.siglas = siglas;
        this.nombre = nombre;
        this.curso = curso;
        this.horas = horas;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String[] getToStringArray() {
        String[] s = new String[4];
        s[0] = siglas;
        s[1] = nombre;
        s[2] = Integer.toString(curso);
        s[3] = Integer.toString(horas);
        return s;
    }

    public static String[] getColumnasArray() {
        return new String[]{"Siglas", "Nombre", "Curso", "Horas"};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modulo modulo = (Modulo) o;
        return Objects.equals(siglas, modulo.siglas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(siglas);
    }

    @Override
    public String toString() {
        return siglas + " - " + nombre;
    }
}
