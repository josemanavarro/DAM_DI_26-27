/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.iesalandalus.ejemploclase4.controlador;

import com.formdev.flatlaf.FlatLightLaf;
import org.iesalandalus.ejemploclase4.vista.JFrameVentanaPrincipal;

/**
 *
 * @author jose
 */
public class EjemploClase4 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        FlatLightLaf.setup();
        LogicaNegocioAlumno ln = new LogicaNegocioAlumno();
        JFrameVentanaPrincipal jfvp = new JFrameVentanaPrincipal(ln);
        jfvp.setVisible(true);
    }
}
