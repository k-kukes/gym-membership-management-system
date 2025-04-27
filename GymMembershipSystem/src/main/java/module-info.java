module org.example.gymmembershipsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.gymmembershipsystem to javafx.fxml;
    exports org.example.gymmembershipsystem;
}