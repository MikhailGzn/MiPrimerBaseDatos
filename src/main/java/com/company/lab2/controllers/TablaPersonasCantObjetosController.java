package com.company.lab2.controllers;
import com.company.lab2.objects.Persona;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TablaPersonasCantObjetosController {
    @FXML
    private TableView<Persona> tableView;
    @FXML
    private TableColumn<Persona, String> nombreColumn;
    @FXML
    private TableColumn<Persona, String> apellidoColumn;
    @FXML
    private TableColumn<Persona, Integer> cantObjetosColumn;
    
    @FXML
    public void setPersonas(List<Persona> personas) {              
        tableView.setItems(FXCollections.observableArrayList(personas));        
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        cantObjetosColumn.setCellValueFactory(new PropertyValueFactory<>("cantObjetos"));
    }    
}
