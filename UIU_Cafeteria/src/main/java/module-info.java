module org.example.uiu_cafeteria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.uiu_cafeteria to javafx.fxml;
    exports org.example.uiu_cafeteria;
}