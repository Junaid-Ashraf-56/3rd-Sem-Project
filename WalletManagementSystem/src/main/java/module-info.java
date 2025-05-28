module com.example.walletmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens com.example.walletmanagementsystem to javafx.fxml;
    exports com.example.walletmanagementsystem;
}