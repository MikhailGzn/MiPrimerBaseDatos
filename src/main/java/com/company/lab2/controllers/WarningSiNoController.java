package com.company.lab2.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WarningSiNoController {
    @FXML
    private Label mensajeLabel;
    @FXML
    private Button siButton;
    @FXML
    private Button noButton;
    @FXML
    private Button cancelarButton;

    private Stage stage;
    private boolean respuesta;

    public void initialize() {
        respuesta = false;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean getRespuesta() {
        return respuesta;
    }

    public void mostrarAdvertencia(String mensaje) {
        mensajeLabel.setText(mensaje);
    }

    @FXML
    private void siButtonAction() {
        respuesta = true;
        stage.close();
    }

    @FXML
    private void noButtonAction() {
        respuesta = false;
        stage.close();
    }

    @FXML
    private void cancelarButtonAction() {
        stage.close();
    }
}