/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.iesalandalus.ejemploclase1.controlador;

import com.formdev.flatlaf.FlatLightLaf;
import org.iesalandalus.ejemploclase1.vista.JFrameVentanaPrincipal;

/**
 *
 * @author jose
 */
public class EjemploClase1 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        FlatLightLaf.setup();
        JFrameVentanaPrincipal jfvp = new JFrameVentanaPrincipal();
        jfvp.setVisible(true);
    }
}
