module com.example.utazasbeadandojavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.utazasbeadandojavafx to javafx.fxml;
    exports com.example.utazasbeadandojavafx;
}