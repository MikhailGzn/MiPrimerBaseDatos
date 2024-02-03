package com.company.lab2.controllers;
import com.company.lab2.objects.Objeto;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import java.util.List;
public class TablaObjetosController {

    @FXML
    private TableView<Objeto> tableView;
    @FXML
    private TableColumn<Objeto, String> codigoColumn;
    @FXML
    private TableColumn<Objeto, String> nombreColumn;
    @FXML
    private TableColumn<Objeto, String> tipoExtraccionColumn;
    @FXML
    private TableColumn<Objeto, Integer> altoColumn;
    @FXML
    private TableColumn<Objeto, Integer> largoColumn;
    @FXML
    private TableColumn<Objeto, Integer> espesorColumn;
    @FXML
    private TableColumn<Objeto, Integer> pesoColumn;
    @FXML
    private TableColumn<Objeto, Integer> cantidadColumn;
    @FXML
    private TableColumn<Objeto, String> fechaRegistroColumn;
    @FXML
    private TableColumn<Objeto, String> descripcionColumn;
    @FXML
    private TableColumn<Objeto, String> origenColumn;
    @FXML
    private TableColumn<Objeto, String> codigoCuadriculaAsociaColumn;
    @FXML
    private TableColumn<Objeto, String> codigoCajaContieneColumn;
    @FXML
    private TableColumn<Objeto, Integer> dniPersonaIngresaColumn;
    @FXML
    private TableColumn<Objeto, Character> esColumn;
    
    @FXML
    public void setObjetos(List<Objeto> objetos) {              
        tableView.setItems(FXCollections.observableArrayList(objetos));        
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoExtraccionColumn.setCellValueFactory(new PropertyValueFactory<>("tipoExtracion"));
        altoColumn.setCellValueFactory(new PropertyValueFactory<>("alto"));
        largoColumn.setCellValueFactory(new PropertyValueFactory<>("largo"));
        espesorColumn.setCellValueFactory(new PropertyValueFactory<>("expesor"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fechaRegistroColumn.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        origenColumn.setCellValueFactory(new PropertyValueFactory<>("origen"));
        codigoCuadriculaAsociaColumn.setCellValueFactory(new PropertyValueFactory<>("cuCodAsocia"));
        codigoCajaContieneColumn.setCellValueFactory(new PropertyValueFactory<>("caCodContiene"));
        dniPersonaIngresaColumn.setCellValueFactory(new PropertyValueFactory<>("pDniIngresa"));
        esColumn.setCellValueFactory(new PropertyValueFactory<>("oEs"));
    }
}
