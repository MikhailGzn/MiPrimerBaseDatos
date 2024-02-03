module com.company.lab2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;    
    opens com.company.lab2 to javafx.fxml;
    opens com.company.lab2.controllers to javafx.fxml;
    exports com.company.lab2;
    exports com.company.lab2.objects; 
}
