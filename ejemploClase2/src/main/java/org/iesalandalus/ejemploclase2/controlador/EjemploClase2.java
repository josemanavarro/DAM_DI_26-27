/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.iesalandalus.ejemploclase2.controlador;

import com.formdev.flatlaf.FlatLightLaf;
import org.iesalandalus.ejemploclase2.vista.JFrameVentanaPrincipal;

/**
 *
 * @author jose
 */
public class EjemploClase2 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        FlatLightLaf.setup();
        LogicaNegocio ln = new LogicaNegocio();
        JFrameVentanaPrincipal jfvp = new JFrameVentanaPrincipal(ln);
        jfvp.setVisible(true);
    }
}
