package com.example.utazasbeadandojavafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdatbazisController implements Initializable {

    private static Connection con;

    @FXML private TableView<Ajanlatok> dbOlvasTableView;
    @FXML private TableColumn<Ajanlatok,String> dbOlvasOrszagTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvasNevTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvasBesorolasTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvasEllatasTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvasIndulasTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvasIdotartamTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvasArTc;

    private ObservableList<Ajanlatok> data;

    //Olvas2
    @FXML private ComboBox<String> dbOlvas2ComboBox;
    private ObservableList<String> orszagList;

    @FXML private Slider dbOlvas2Slider;
    int dbOlvas2SliderValue;
    @FXML private Label dbOlvas2SliderLabel;

    @FXML private CheckBox dbOlvas2NelkulCheckbox;
    @FXML private CheckBox dbOlvas2FelCheckbox;
    @FXML private RadioButton dbOlvas2FiveRadioButton;
    @FXML private DatePicker dbOlvas2StartDatePicker;
    @FXML private TextField dbOlvas2SzallasTextField;

    @FXML private TableView<Ajanlatok> dbOlvas2TableView;
    @FXML private TableColumn<Ajanlatok,String> dbOlvas2OrszagTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvas2NevTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvas2BesorolasTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvas2EllatasTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvas2IndulasTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvas2IdotartamTc;
    @FXML private TableColumn<Ajanlatok,String> dbOlvas2ArTc;
    private ObservableList<Ajanlatok> keresData;
    @FXML private Button dbOlvas2KeresesButton;

    //Ir menü
    @FXML private ComboBox<String> dbIrSzallodaAzComboBox;
    private ObservableList<String> szallodaAzList;
    @FXML private DatePicker dbIrIndulasDatePicker;
    @FXML private TextField dbIrArTextField;
    @FXML private Slider dbIrNapokSlider;
    private int dbIrNapokSliderValue;
    @FXML private Button dbIrBevitelButton;

    @FXML private Label dbIrNapokLabel;
    @FXML private Label dbIrSzallodaAzErrorLabel;
    @FXML private Label dbIrIndulasErroLabel;
    @FXML private Label dbIrArErrorLabel;
    @FXML private Label dbIrSikeresLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbOlvasOrszagTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("orszag"));
        dbOlvasNevTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("nev"));
        dbOlvasBesorolasTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("besorolas"));
        dbOlvasEllatasTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("ellatas"));
        dbOlvasIndulasTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("indulas"));
        dbOlvasIdotartamTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("idotartam"));
        dbOlvasArTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("ar"));

        data = FXCollections.observableArrayList();
        try{
            con = DriverManager.getConnection("jdbc:sqlite:/c:/adatbazis/adatok.db");
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT orszag, szalloda.nev, szalloda.besorolas, felpanzio, tavasz.indulas, tavasz.idotartam, tavasz.ar FROM (tavasz inner join szalloda on tavasz.szalloda_az = szalloda.az) inner join helyseg on helyseg.az = szalloda.helyseg_az ") ;
            while(rs.next()){
                String az = rs.getString("orszag");
                String nev = rs.getString("nev");
                int besorolas = rs.getInt("besorolas");
                boolean felpanzio = rs.getBoolean("felpanzio");
                String tavasz = rs.getString("indulas");
                int idotartam = rs.getInt("idotartam");
                int ar = rs.getInt("ar");
                Ajanlatok newAjanalat = new Ajanlatok(az,nev,besorolas,felpanzio,tavasz,idotartam,ar);
                data.add(newAjanalat);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        dbOlvasTableView.setItems(data);

        //Olvas2 Menu Incializalas
        int MaxAr = 0;
        dbOlvas2OrszagTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("orszag"));
        dbOlvas2NevTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("nev"));
        dbOlvas2BesorolasTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("besorolas"));
        dbOlvas2EllatasTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("ellatas"));
        dbOlvas2IndulasTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("indulas"));
        dbOlvas2IdotartamTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("idotartam"));
        dbOlvas2ArTc.setCellValueFactory(new PropertyValueFactory<Ajanlatok,String>("ar"));
        orszagList = FXCollections.observableArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT orszag FROM helyseg group by orszag");
            while(rs.next()){
                String  newOrszag = rs.getString("orszag");
                orszagList.add(newOrszag);
            }
            dbOlvas2ComboBox.setItems(orszagList);

            rs = stmt.executeQuery("SELECT MAX(ar) FROM tavasz");
            while(rs.next()){
                MaxAr = rs.getInt("MAX(ar)");
            }
            dbOlvas2Slider.setMax(MaxAr);
            dbOlvas2SliderLabel.setText("0 HUF - "+String.format("%,d", MaxAr)+ " HUF");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        dbOlvas2Slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                dbOlvas2SliderValue = (int) dbOlvas2Slider.getValue();
                dbOlvas2SliderLabel.setText("0 HUF - "+String.format("%,d", dbOlvas2SliderValue)+ " HUF");
            }
        });

        //Ir menu incializalas
        szallodaAzList = FXCollections.observableArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT az FROM szalloda");
            while(rs.next()){
                String  newAz = rs.getString("az");
                szallodaAzList.add(newAz);
            }
            dbIrSzallodaAzComboBox.setItems(szallodaAzList);

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        dbIrNapokSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                dbIrNapokSliderValue = (int) dbIrNapokSlider.getValue();
                String napok = String.valueOf(dbIrNapokSliderValue);
                String ejjelek = String.valueOf(dbIrNapokSliderValue-1);
                dbIrNapokLabel.setText("Napok száma: "+napok+" Nap ("+ejjelek+" Éjszaka)");
            }
        });
    }

    public void dbOlvas2Kereses(){
        String SQLString = "";
        String MaxAr = String.valueOf(dbOlvas2Slider.getValue());

        String Orszag = ((dbOlvas2ComboBox.getValue() == null) ? "" : "ORSZAG = '"+dbOlvas2ComboBox.getValue()+"'" );
        SQLString = ((Orszag.isEmpty())? SQLString+="" : SQLString+" AND "+Orszag );

        String Nelkul = ((dbOlvas2NelkulCheckbox.isSelected()) ? "felpanzio = 0 " : "");
        String Fel = ((dbOlvas2FelCheckbox.isSelected()) ? "felpanzio = 1 " : "");

        if(!Nelkul.isEmpty() && !Fel.isEmpty()){
            SQLString += " AND ("+Nelkul+" OR "+Fel+") ";
        } else if (!Nelkul.isEmpty()) {
            SQLString += " AND "+Nelkul;
        }else if (!Fel.isEmpty()){
            SQLString += " AND "+Fel;
        }

        String Five = ((dbOlvas2FiveRadioButton.isSelected()) ? "besorolas = 5 " : "");
        SQLString = ((Five.isEmpty())? SQLString+="" : SQLString+" AND "+Five );

        LocalDate startDate = dbOlvas2StartDatePicker.getValue();
        String startDateString;
        if (startDate != null) {
            startDateString = startDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            SQLString += " AND indulas = '"+startDateString+"'";
        }

        if (!dbOlvas2SzallasTextField.getText().isEmpty()) {
            SQLString += " AND UPPER(szalloda.nev) Like '%"+dbOlvas2SzallasTextField.getText().toUpperCase()+"%'";
        }

        keresData = FXCollections.observableArrayList();
        try{

            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT orszag, szalloda.nev, szalloda.besorolas, felpanzio, tavasz.indulas, tavasz.idotartam, tavasz.ar FROM (tavasz inner join szalloda on tavasz.szalloda_az = szalloda.az) inner join helyseg on helyseg.az = szalloda.helyseg_az " +
                    "WHERE tavasz.ar < "+MaxAr+SQLString);
            while(rs.next()){
                String az = rs.getString("orszag");
                String nev = rs.getString("nev");
                int besorolas = rs.getInt("besorolas");
                boolean felpanzio = rs.getBoolean("felpanzio");
                String tavasz = rs.getString("indulas");
                int idotartam = rs.getInt("idotartam");
                int ar = rs.getInt("ar");
                Ajanlatok newAjanalat = new Ajanlatok(az,nev,besorolas,felpanzio,tavasz,idotartam,ar);
                keresData.add(newAjanalat);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        dbOlvas2TableView.setItems(keresData);
    }

    public void dbIrBevitel(){
        String SQLString = "INSERT INTO tavasz (szalloda_az, indulas,idotartam, ar )VALUES(";
        boolean SQLError = false;
        String sorszam;
        String szalloda_az = dbIrSzallodaAzComboBox.getValue();
        if( szalloda_az == null || szalloda_az.isEmpty() ){
            dbIrSzallodaAzErrorLabel.setText("Hibás azonosító!");
            return;
        }
        String indulas;
        LocalDate startDate = dbIrIndulasDatePicker.getValue();
        String startDateString;
        if (startDate != null) {
            indulas = startDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }else{
            dbIrIndulasErroLabel.setText("Hibás indulási érték!");
            return;
        }
        String idotartam = String.valueOf(dbIrNapokSlider.getValue());
        int ar;
        try{
            ar = Integer.valueOf(dbIrArTextField.getText());
        }catch(NumberFormatException e){
            dbIrArErrorLabel.setText("Hibás érték!");
            return;
        }

        boolean resultInsert = false;
        SQLString +="'"+szalloda_az+"','"+indulas+"','"+idotartam+"','"+ar+"')";
        try {
            Statement stmt = con.createStatement();
            resultInsert = stmt.execute(SQLString);
            dbIrSikeresLabel.setText("Sikers Adatbevitel!");
        }catch (Exception e){
            System.out.println(e.getMessage());
            dbIrSikeresLabel.setText("Adatbázis Hiba!");
        }
    }
}
