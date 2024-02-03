package com.company.lab2.controllers;
import com.company.lab2.objects.Objeto;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import java.util.List;
public class TablaObjetosCodigoNombreController {

    @FXML
    private TableView<Objeto> tableView;
    @FXML
    private TableColumn<Objeto, String> codigoColumn;
    @FXML
    private TableColumn<Objeto, String> nombreColumn;    
    
    @FXML
    public void setObjetos(List<Objeto> objetos) {              
        tableView.setItems(FXCollections.observableArrayList(objetos));        
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }
}
