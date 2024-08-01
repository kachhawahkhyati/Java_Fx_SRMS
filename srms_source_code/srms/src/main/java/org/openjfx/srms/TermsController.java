/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openjfx.srms;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 *
 *
 */
public class TermsController {
    
    @FXML
    private void switchtoHome() throws IOException {
        App.setRoot("Home");
    }
    
    @FXML
    private void switchtoAddPlan() throws IOException {
        App.setRoot("addplan");
    }
}
