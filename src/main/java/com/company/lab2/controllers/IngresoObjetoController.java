package com.company.lab2.controllers;
import com.company.lab2.objects.Objeto;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class IngresoObjetoController {
    Objeto objeto;

    @FXML
    private TextField codigoTextField;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField tipoExtraccionTextField;
    @FXML
    private TextField altoTextField;
    @FXML
    private TextField largoTextField;
    @FXML
    private TextField espesorTextField;
    @FXML
    private TextField pesoTextField;
    @FXML
    private TextField cantidadTextField;
    @FXML
    private TextField fechaRegistroTextField;
    @FXML
    private TextField descripcionTextField;
    @FXML
    private TextField origenTextField;
    @FXML
    private TextField codigoAsociadoTextField;
    @FXML
    private TextField codigoContenidoTextField;
    @FXML
    private TextField dniIngresaTextField;
    @FXML
    private TextField oesTextField;

    @FXML
    private void handleButtomGuardar(ActionEvent event){
        guardarObjeto();    
        Stage dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }
    @FXML
    private void guardarObjeto() {        
        String codigo = codigoTextField.getText();
        String nombre = nombreTextField.getText();
        String tipoExtraccion = tipoExtraccionTextField.getText();
        Integer alto = Integer.parseInt(altoTextField.getText());
        Integer largo = Integer.parseInt(largoTextField.getText());
        Integer espesor = Integer.parseInt(espesorTextField.getText());
        Integer peso = Integer.parseInt(pesoTextField.getText());
        Integer cantidad = Integer.parseInt(cantidadTextField.getText());
        String fechaRegistro = fechaRegistroTextField.getText();
        String descripcion = descripcionTextField.getText();
        String origen = origenTextField.getText();
        String cuCodAsocia = codigoAsociadoTextField.getText();
        String caCodContiene = codigoContenidoTextField.getText();
        Integer pDniIngresa = Integer.parseInt(dniIngresaTextField.getText());
        Character oEs = oesTextField.getText().charAt(0);

        // Aquí puedes realizar las acciones necesarias para guardar el objeto con los valores ingresados

        // Ejemplo: Crear un nuevo objeto con los valores ingresados
        this.objeto = new Objeto(codigo, nombre, tipoExtraccion, alto, largo, espesor, peso, cantidad, fechaRegistro, descripcion, origen, cuCodAsocia, caCodContiene, pDniIngresa, oEs);
        
        // Realizar otras acciones como guardar el objeto en la base de datos, mostrar un mensaje de éxito, etc.
    }
    public Objeto getObjeto() {
        return objeto;
    } 
}
