module com.example.walletmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;

    opens com.example.walletmanagementsystem.Controller to javafx.fxml;

    exports com.example.walletmanagementsystem;
}
