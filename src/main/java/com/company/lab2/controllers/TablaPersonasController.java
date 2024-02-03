package com.company.lab2.controllers;

import com.company.lab2.objects.Persona;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;

import java.util.List;

public class TablaPersonasController {
    @FXML
    private TableView<Persona> tableView;
    @FXML
    private TableColumn<Persona, Integer> dniColumn;
    @FXML
    private TableColumn<Persona, String> nombreColumn;
    @FXML
    private TableColumn<Persona, String> apellidoColumn;
    @FXML
    private TableColumn<Persona, String> emailColumn;
    @FXML
    private TableColumn<Persona, String> telefonoColumn;
    
    @FXML
    public void setPersonas(List<Persona> personas) {              
        tableView.setItems(FXCollections.observableArrayList(personas));        
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }
}