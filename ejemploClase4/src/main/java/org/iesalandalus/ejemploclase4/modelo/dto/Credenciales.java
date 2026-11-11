/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.iesalandalus.ejemploclase4.modelo.dto;

import java.util.Objects;

/**
 * DTO que representa las credenciales de un usuario del sistema.
 * 
 * @author jose
 */
public class Credenciales {
    
    private int id;
    private String login;
    private String password;

    public Credenciales(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Credenciales(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Credenciales cred) {
        if (cred == null) {
            return false;
        }
        return Objects.equals(this.login, cred.getLogin()) && Objects.equals(this.password, cred.getPassword());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Credenciales other = (Credenciales) obj;
        return Objects.equals(this.login, other.login) && Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return "Credenciales{" + "id=" + id + ", login=" + login + ", password=******" + '}';
    }
}
