module com.example.walletmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires org.kordamp.ikonli.javafx;
//    requires org.postgresql.jdbc;


    exports com.example.walletmanagementsystem.service;
    opens com.example.walletmanagementsystem to javafx.fxml;
    opens com.example.walletmanagementsystem.Controller to javafx.fxml;
    opens com.example.walletmanagementsystem.dao to javafx.fxml;
    opens com.example.walletmanagementsystem.model to javafx.base; // for TableView bindings

    exports com.example.walletmanagementsystem;
}
