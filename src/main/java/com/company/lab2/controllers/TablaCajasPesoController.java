package com.company.lab2.controllers;

import com.company.lab2.objects.Caja;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;


import java.util.List;

public class TablaCajasPesoController {
    @FXML
    private TableView<Caja> tableView;
    @FXML
    private TableColumn<Caja, String> codigoColumn;
    @FXML
    private TableColumn<Caja, Integer> pesoColumn;    
    
    @FXML
    public void setCajas(List<Caja> cajas) {              
        tableView.setItems(FXCollections.observableArrayList(cajas));        
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("pesoTotal"));
    }
}