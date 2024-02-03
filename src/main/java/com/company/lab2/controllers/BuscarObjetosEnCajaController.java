package com.company.lab2.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class BuscarObjetosEnCajaController {
    private String codigoCaja;

    @FXML
    private TextField codigoTextField;
    @FXML
    private void handleButtomBuscar(ActionEvent event){
        codigoCaja = codigoTextField.getText();
        Stage dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();

    }
    public String getCodigoCaja(){
        return codigoCaja;
    }

}
