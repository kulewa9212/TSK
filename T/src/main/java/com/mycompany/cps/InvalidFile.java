/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cps;

import javafx.scene.control.Alert;

/**
 *
 * @author Ewa Kulesza
 */
public class InvalidFile extends Exception{
    
    
    public class InvalidName extends Exception{
       public InvalidName() {
        super("Zły format pliku   ! ");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Zły format pliku   !");

        alert.showAndWait();
    }
    
    
    
    
    
    
    
    
}}
