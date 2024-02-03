package com.company.lab2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class WarningController {

    @FXML
    private Label warningLabel;
    @FXML
    private Button buttonAceptar;

    public void mostrarAdvertencia(String mensaje) {
        warningLabel.setText(mensaje);
    }
    @FXML
    public void handleButtonAceptar(ActionEvent event) {
        Stage dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

}
