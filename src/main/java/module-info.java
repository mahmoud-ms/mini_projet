module com.example.petshop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.petshop to javafx.fxml;
    exports com.example.petshop;
}