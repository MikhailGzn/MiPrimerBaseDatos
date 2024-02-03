package com.company.lab2.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BuscarObjetosEntreFechaController {
    private String fechaInicial;
    private String fechaFinal;

    @FXML
    private TextField fechaInicialPicker;
    @FXML
    private TextField fechaFinalPicker;
    @FXML
    private void handleButtomBuscar(ActionEvent event){
        fechaInicial = fechaInicialPicker.getText();
        fechaFinal = fechaFinalPicker.getText();
        Stage dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();

    }
    public String getFechaInicial(){
        return fechaInicial;
    }
    public String getFechaFinal(){
        return fechaFinal;
    }
}
