/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.modelo.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO que representa la matrícula de un alumno en un módulo.
 * 
 * @author jose
 */
public class Matricula {
    private int id;
    private int alumnoId;
    private String moduloSiglas;
    private LocalDate fechaMatricula;
    private String cursoAcademico;
    
    // Referencias opcionales a las entidades completas
    private Alumno alumno;
    private Modulo modulo;

    public Matricula() {
        this.id = -1;
    }

    public Matricula(int alumnoId, String moduloSiglas, LocalDate fechaMatricula, String cursoAcademico) {
        this.id = -1;
        this.alumnoId = alumnoId;
        this.moduloSiglas = moduloSiglas;
        this.fechaMatricula = fechaMatricula;
        this.cursoAcademico = cursoAcademico;
    }

    public Matricula(int id, int alumnoId, String moduloSiglas, LocalDate fechaMatricula, String cursoAcademico) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.moduloSiglas = moduloSiglas;
        this.fechaMatricula = fechaMatricula;
        this.cursoAcademico = cursoAcademico;
    }

    public Matricula(int alumnoId, String moduloSiglas, java.sql.Date fechaMatricula, String cursoAcademico) {
        this(alumnoId, moduloSiglas, fechaMatricula != null ? fechaMatricula.toLocalDate() : null, cursoAcademico);
    }

    public Matricula(int id, int alumnoId, String moduloSiglas, java.sql.Date fechaMatricula, String cursoAcademico) {
        this(id, alumnoId, moduloSiglas, fechaMatricula != null ? fechaMatricula.toLocalDate() : null, cursoAcademico);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getModuloSiglas() {
        return moduloSiglas;
    }

    public void setModuloSiglas(String moduloSiglas) {
        this.moduloSiglas = moduloSiglas;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getCursoAcademico() {
        return cursoAcademico;
    }

    public void setCursoAcademico(String cursoAcademico) {
        this.cursoAcademico = cursoAcademico;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
        if (alumno != null) {
            this.alumnoId = alumno.getId();
        }
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
        if (modulo != null) {
            this.moduloSiglas = modulo.getSiglas();
        }
    }

    public String[] getToStringArray() {
        String[] s = new String[5];
        s[0] = Integer.toString(id);
        s[1] = Integer.toString(alumnoId);
        s[2] = moduloSiglas;
        s[3] = (fechaMatricula != null) ? fechaMatricula.toString() : "";
        s[4] = cursoAcademico;
        return s;
    }

    public static String[] getColumnasArray() {
        return new String[]{"ID", "ID Alumno", "Siglas Módulo", "Fecha Matrícula", "Curso Académico"};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matricula = (Matricula) o;
        if (id > 0 && matricula.id > 0) {
            return id == matricula.id;
        }
        return alumnoId == matricula.alumnoId &&
               Objects.equals(moduloSiglas, matricula.moduloSiglas) &&
               Objects.equals(cursoAcademico, matricula.cursoAcademico);
    }

    @Override
    public int hashCode() {
        if (id > 0) {
            return Objects.hash(id);
        }
        return Objects.hash(alumnoId, moduloSiglas, cursoAcademico);
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", alumnoId=" + alumnoId +
                ", moduloSiglas='" + moduloSiglas + '\'' +
                ", fechaMatricula=" + fechaMatricula +
                ", cursoAcademico='" + cursoAcademico + '\'' +
                '}';
    }
}
