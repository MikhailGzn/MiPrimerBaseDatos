package com.company.lab2.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.layout.GridPane;

import com.company.lab2.ManejoDataBase;
import com.company.lab2.objects.*;


import java.util.List;


public class PrimaryController implements Initializable {
    ManejoDataBase manejoDataBase;
    //@FXML
    @FXML

    @Override
    public void initialize(URL u, ResourceBundle rb){
        String url = "jdbc:postgresql://localhost:5432/";
        String databaseName = "arqueologia";
        String username = "postgres";
        String password = "0197";
        String dbUrl = url + databaseName;
        String sqlFilePath = "src/main/java/com/company/lab2/insertSQL/insert.sql";         
        try {
            System.out.println("Iniciando la aplicación...");
            manejoDataBase = new ManejoDataBase(url, databaseName, username, password, sqlFilePath);            
            boolean databaseExists = manejoDataBase.checkDatabaseExists();            
            if (!databaseExists) {                
                System.out.println("La base de datos no existe.");
                // Crear la base de datos, la inicializa y ejecuta el archivo sql
                manejoDataBase.createDatabase();
                manejoDataBase.inicializarDataBase();
                manejoDataBase.ejecutaArchivoSQL(); // aca de pasada agregamos lo
            }              
        }       
        catch(Exception e){
            System.out.println(e.getMessage());
        }    
    }
                    ///////////////////////////////////////////////////////
                    //                       EJERCICIO 1                 //
                    ///////////////////////////////////////////////////////

    @FXML
    private void handleButtomMostrarPersonas(){
        try{            
            List<Persona> personas = manejoDataBase.getAllPersons();                                                                       
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/tablaPersonas.fxml"));
            Parent root = loader.load();
            TablaPersonasController tablaPersonasController = loader.getController();
            tablaPersonasController.setPersonas(personas); 
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage()); //Cambiar por cr
        }
        
    }    
    @FXML
    private void handleButtomMostrarCajas(){
        try{            
            List<Caja> cajas = manejoDataBase.getAllBoxes();                                                                       
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/tablaCajas.fxml"));
            Parent root = loader.load();
            TablaCajasController tablaCajasController = loader.getController();
            tablaCajasController.setCajas(cajas); 
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage()); //Cambiar por cr
        }
    }
                    ///////////////////////////////////////////////////////
                    //                       EJERCICIO 2                 //
                    ///////////////////////////////////////////////////////
    @FXML
    private void handleButtomAgregarObjeto(){                                                                       
        try{                                    
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/ingresoObjeto.fxml"));
            Parent root = loader.load();
            IngresoObjetoController ingresoObjetoController = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            
        // Mostrar el diálogo y esperar a que se cierre
            dialogStage.showAndWait();
            Objeto o = ingresoObjetoController.getObjeto();


            Object testing = manejoDataBase.getObjetoConCod(o.getCodigo());                             
            if (testing == null){
                testing = manejoDataBase.getCuadriculaConCod(o.getCuCodAsocia());
                if(testing != null){
                   testing = manejoDataBase.getCajaConCod(o.getCaCodContiene());
                    if(testing != null){
                        testing = manejoDataBase.getPersonaConDni(o.getPDniIngresa());
                        if(testing != null){                                                                                                                   
                            manejoDataBase.insertarObjeto(o);
                        }
                        else{
                            System.out.print("El arqueolo no existe"
                                    + "desea agregarlo?\n"); // cambiar por cartel
                        }
                    }
                    else{
                        System.out.print("La caja no existe desea crearla?\n");                                
                    }                            
                }
                else{
                    System.out.print("No existe la cuadricula"
                            + " desea agregarla?");
                }
            }
            else{//CAMBIAR POR CARTEL 
                System.out.print("Codigo de objeto repetido");
            }
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
                    ///////////////////////////////////////////////////////
                    //                       EJERCICIO 3                 //
                    ///////////////////////////////////////////////////////
    @FXML
    private void handleButtomEliminarCaja(){       
        try{       
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/eliminarCaja.fxml"));
            Parent root = loader.load();
            EliminarCajaController eliminarCajaController = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            
            // Mostrar el diálogo y esperar a que se cierre
            dialogStage.showAndWait();
            String ca_cod = eliminarCajaController.getCodigoCaja();            
            Caja caja = manejoDataBase.getCajaConCod(ca_cod);
            if(caja == null){
                FXMLLoader loaderCartel = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
                Parent rootCartel = loaderCartel.load();
                WarningController warningController = loaderCartel.getController();
                Stage dialogStageCartel = new Stage();
                dialogStageCartel.initModality(Modality.APPLICATION_MODAL);
                dialogStageCartel.setScene(new Scene(rootCartel));
                warningController.mostrarAdvertencia("La caja no existe");            
                dialogStageCartel.showAndWait();    
            }
            else{
                if(manejoDataBase.getCantObjetosEnCaja(ca_cod) != 0){
                    FXMLLoader loaderCartel = new FXMLLoader(getClass().getResource("/com/company/lab2/warnigSiNo.fxml"));
                    GridPane rootCartel = loaderCartel.load();  
                    WarningSiNoController warningController = loaderCartel.getController();
                    warningController.mostrarAdvertencia("La caja contiene objetos, desea eliminarla?");            
                    Stage stage = new Stage();
                    stage.setScene(new Scene(rootCartel));
                    stage.initModality(Modality.APPLICATION_MODAL); // Configura el modal para bloquear la ventana principal
                    warningController.setStage(stage);
                    stage.showAndWait();
                    if(warningController.getRespuesta()){
                        FXMLLoader loaderCartel2 = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
                        Parent rootCartel2 = loaderCartel2.load();
                        WarningController warningController2 = loaderCartel2.getController();
                        Stage dialogStageCartel = new Stage();
                        dialogStageCartel.initModality(Modality.APPLICATION_MODAL);
                        dialogStageCartel.setScene(new Scene(rootCartel2));
                        manejoDataBase.eliminarCaja(ca_cod);
                        warningController2.mostrarAdvertencia("La caja se eliminó correctamente");            
                        dialogStageCartel.showAndWait();    
                                        
                    }
                }
                else{                                            
                    FXMLLoader loaderCartel = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
                    Parent rootCartel = loaderCartel.load();
                    WarningController warningController = loaderCartel.getController();
                    Stage dialogStageCartel = new Stage();
                    dialogStageCartel.initModality(Modality.APPLICATION_MODAL);
                    dialogStageCartel.setScene(new Scene(rootCartel));
                    warningController.mostrarAdvertencia("La caja se eliminó correctamente");            
                    dialogStageCartel.showAndWait();    
                    manejoDataBase.eliminarCaja(ca_cod);
                }
            }
        }catch(Exception e){
            System.out.print(e.getMessage()); //cambiar por cartel
        }
    }
               ///////////////////////////////////////////////////////
               //                       EJERCICIO 4                 //
               ///////////////////////////////////////////////////////
    @FXML
    private void handleButtomObjetosAlmacenadosEnCaja(){                
        try{            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/buscarObjetosEnCaja.fxml"));
            Parent root = loader.load();
            BuscarObjetosEnCajaController buscarObjetosEnCajaController = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            
            // Mostrar el diálogo y esperar a que se cierre
            dialogStage.showAndWait();
            String ca_cod = buscarObjetosEnCajaController.getCodigoCaja();            

            Caja caja = manejoDataBase.getCajaConCod(ca_cod);
            if(caja == null){
                FXMLLoader loaderCartel = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
                Parent rootCartel = loaderCartel.load();
                WarningController warningController = loaderCartel.getController();
                Stage dialogStageCartel = new Stage();
                dialogStageCartel.initModality(Modality.APPLICATION_MODAL);
                dialogStageCartel.setScene(new Scene(rootCartel));
                warningController.mostrarAdvertencia("La caja no existe");            
                dialogStageCartel.showAndWait();                    
            }
            else{
                List<Objeto> objetos = manejoDataBase.getAllObjectsEnCaja(ca_cod);
                if(objetos != null){                
                    loader = new FXMLLoader(getClass().getResource("/com/company/lab2/tablaObjetos.fxml"));
                    root = loader.load();
                    TablaObjetosController tablaObjetosController = loader.getController();
                    tablaObjetosController.setObjetos(objetos); 
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                else{                        
                    FXMLLoader loaderCartel = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
                    Parent rootCartel = loaderCartel.load();
                    WarningController warningController = loaderCartel.getController();
                    Stage dialogStageCartel = new Stage();
                    dialogStageCartel.initModality(Modality.APPLICATION_MODAL);
                    dialogStageCartel.setScene(new Scene(rootCartel));
                    warningController.mostrarAdvertencia("La caja esta vacia");            
                    dialogStageCartel.showAndWait();                    
                    }    
            }
        }catch(Exception e){
            System.out.print(e.getMessage()); //cambiar por cartel
        }
    }
                    ///////////////////////////////////////////////////////
                    //                       EJERCICIO 5                 //
                    ///////////////////////////////////////////////////////
    @FXML
    private void handleButtomObjetosEntreFecha(){
        try{            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/buscarObjetosEntreFecha.fxml"));
            Parent root = loader.load();
            BuscarObjetosEntreFechaController buscarObjetosEntreFechaController = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            
            // Mostrar el diálogo y esperar a que se cierre
            dialogStage.showAndWait();
            String fechaInicial = buscarObjetosEntreFechaController.getFechaInicial();
            String fechaFinal = buscarObjetosEntreFechaController.getFechaFinal();
            if(!(fechaInicial.equals("") || fechaFinal.equals(""))){
                List<Objeto> objetos = manejoDataBase.getAllObjectsEntreFechas(fechaInicial,fechaFinal);
                if(objetos.size() > 0){                
                    loader = new FXMLLoader(getClass().getResource("/com/company/lab2/tablaObjetosCodigoNombre.fxml"));
                    root = loader.load();
                    TablaObjetosCodigoNombreController tablaObjetosCodigoNombreController = loader.getController();
                    tablaObjetosCodigoNombreController.setObjetos(objetos); 
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                else{                        
                    FXMLLoader loaderCartel = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
                    Parent rootCartel = loaderCartel.load();
                    WarningController warningController = loaderCartel.getController();
                    Stage dialogStageCartel = new Stage();
                    dialogStageCartel.initModality(Modality.APPLICATION_MODAL);
                    dialogStageCartel.setScene(new Scene(rootCartel));
                    warningController.mostrarAdvertencia("No hay objetos entre esas fechas");            
                    dialogStageCartel.showAndWait();                    
                    }
            }            
        }catch(Exception e){
            System.out.print(e.getMessage()); //cambiar por cartel
        }
    }
                    ///////////////////////////////////////////////////////
                    //                       EJERCICIO 6                 //
                    ///////////////////////////////////////////////////////
    @FXML
    private void handleButtomObjetosXTipo(){
        try{
            List<Integer> objetosXTipo = manejoDataBase.getCantObjetosXTipo();            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
            Parent root = loader.load();
            WarningController warningController = loader.getController();
            String mensaje = "Cantidad de cerámicos: " + objetosXTipo.get(0) + "\n" +
                                "Cantidad de líticos: " + objetosXTipo.get(1);                                 
            warningController.mostrarAdvertencia(mensaje);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
                    ///////////////////////////////////////////////////////
                    //                       EJERCICIO 7                 //
                    ///////////////////////////////////////////////////////
    @FXML
    private void handleButtomResumen(){
        try{            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
            Parent root = loader.load();
            WarningController warningController = loader.getController();
            String mensaje = "Cantidad de personas: " + manejoDataBase.getCantPersonas() + "\n" +
                                "Cantidad de cuadriculas: " + manejoDataBase.getCantCuadriculas() + "\n" +
                                "Cantidad de objetos: " + manejoDataBase.getCantObjetos() + "\n" +
                                "Cantidad de cajas: " + manejoDataBase.getCantCajas();
            warningController.mostrarAdvertencia(mensaje);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
                  
    }
                    ///////////////////////////////////////////////////////
                    //                       EJERCICIO 8                 //
                    ///////////////////////////////////////////////////////
    @FXML
    private void handleButtomArqueologoCantObjetos(){
        try{
            List<Persona> personas = manejoDataBase.getPersonasMasCantObjetos();
            if(personas.size() > 0){                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/tablaPersonasCantObjetos.fxml"));
                Parent root = loader.load();
                TablaPersonasCantObjetosController tablaPersonasCantObjetosController = loader.getController();
                tablaPersonasCantObjetosController.setPersonas(personas); 
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
            else{                        
                FXMLLoader loaderCartel = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
                Parent rootCartel = loaderCartel.load();
                WarningController warningController = loaderCartel.getController();
                Stage dialogStageCartel = new Stage();
                dialogStageCartel.initModality(Modality.APPLICATION_MODAL);
                dialogStageCartel.setScene(new Scene(rootCartel));
                warningController.mostrarAdvertencia("No hay personas cargadas");            
                dialogStageCartel.showAndWait();                    
                }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
                    ///////////////////////////////////////////////////////
                    //                       EJERCICIO 9                 //
                    ///////////////////////////////////////////////////////
    @FXML
    private void handleButtomCajasVacias(){
        try{
            List<Caja> cajas = manejoDataBase.getCajasVacias();
            if(cajas.size() > 0){                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/tablaCajasSinFecha.fxml"));
                Parent root = loader.load();
                TablaCajasSinFechaController tablaCajasSinFechaController = loader.getController();
                tablaCajasSinFechaController.setCajas(cajas); 
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
            else{                        
                FXMLLoader loaderCartel = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
                Parent rootCartel = loaderCartel.load();
                WarningController warningController = loaderCartel.getController();
                Stage dialogStageCartel = new Stage();
                dialogStageCartel.initModality(Modality.APPLICATION_MODAL);
                dialogStageCartel.setScene(new Scene(rootCartel));
                warningController.mostrarAdvertencia("No hay cajas vacias cargadas");            
                dialogStageCartel.showAndWait();                    
                }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
      
    }
                    ///////////////////////////////////////////////////////
                    //                       EJERCICIO 10                //
                    ///////////////////////////////////////////////////////
    @FXML
    private void handleButtomMaxMinAVG(){
        try{
            List<Float> maxMinAVG = manejoDataBase.getMaxMinAvgPesoObjetos();
            FXMLLoader loaderCartel = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
            Parent rootCartel = loaderCartel.load();
            WarningController warningController = loaderCartel.getController();
            Stage dialogStageCartel = new Stage();
            dialogStageCartel.initModality(Modality.APPLICATION_MODAL);
            dialogStageCartel.setScene(new Scene(rootCartel));
            String mensaje = "Maximo: " + maxMinAVG.get(0) + "\n" +
                                "Minimo: " + maxMinAVG.get(1) + "\n" +
                                "Promedio: " + maxMinAVG.get(2);
            warningController.mostrarAdvertencia(mensaje);            
            dialogStageCartel.showAndWait();                    
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
                    ///////////////////////////////////////////////////////
                    //                       EJERCICIO 11                //
                    ///////////////////////////////////////////////////////
    @FXML
    private void handleButtomPesoXCaja(){
        try{
            List<Caja> cajas = manejoDataBase.getCajasMasPeso();
            if(cajas.size() > 0){                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/lab2/tablaCajasPeso.fxml"));
                Parent root = loader.load();
                TablaCajasPesoController tablaController = loader.getController();
                tablaController.setCajas(cajas);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
            else{                        
                FXMLLoader loaderCartel = new FXMLLoader(getClass().getResource("/com/company/lab2/warning.fxml"));
                Parent rootCartel = loaderCartel.load();
                WarningController warningController = loaderCartel.getController();
                Stage dialogStageCartel = new Stage();
                dialogStageCartel.initModality(Modality.APPLICATION_MODAL);
                dialogStageCartel.setScene(new Scene(rootCartel));
                warningController.mostrarAdvertencia("No hay cajas cargadas");            
                dialogStageCartel.showAndWait();                    
                }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }         
    }
}
