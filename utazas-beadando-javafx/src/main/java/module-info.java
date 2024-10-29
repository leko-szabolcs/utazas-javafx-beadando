module com.example.utazasbeadandojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.naming;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.desktop;
    opens com.example.utazasbeadandojavafx to javafx.fxml;
    exports com.example.utazasbeadandojavafx;
}