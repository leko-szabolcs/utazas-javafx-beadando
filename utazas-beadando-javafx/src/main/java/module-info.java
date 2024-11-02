module com.example.utazasbeadandojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires java.naming;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires org.slf4j;
    opens com.example.utazasbeadandojavafx to javafx.fxml, org.hibernate.orm.core;
    exports com.example.utazasbeadandojavafx;
}