package com.example.utazasbeadandojavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FeladatController implements Initializable {

    @FXML private Button soapClientButton;
    @FXML private Button adatbazisButton;
    @FXML private Button parhuzamosButton;
    @FXML private Button forexButton;
    @FXML private AnchorPane ContetntView;

    @FXML
    public void loadAdatbazis(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane newView = FXMLLoader.load(getClass().getResource("Adatbazis_main.fxml"));
        ContetntView.getChildren().setAll(newView);
    }
    @FXML
    public void loadSoapClient(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane newView = FXMLLoader.load(getClass().getResource("SoapClient_main.fxml"));
        ContetntView.getChildren().setAll(newView);
    }
    @FXML
    public void loadParhuzamos(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane newView = FXMLLoader.load(getClass().getResource("Parhuzamos_main.fxml"));
        ContetntView.getChildren().setAll(newView);
    }
    @FXML
    public void loadForex(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane newView = FXMLLoader.load(getClass().getResource("Forex_main.fxml"));
        ContetntView.getChildren().setAll(newView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}