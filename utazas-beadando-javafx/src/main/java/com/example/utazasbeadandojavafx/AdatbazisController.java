package com.example.utazasbeadandojavafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdatbazisController implements Initializable {
    //DATABASE HIBERNATE JAP
    static Session session;
    static SessionFactory factory;

    //Olvas1
    private int olvasPage = 0;
    private boolean isPageMax = false;
    @FXML private TableView<Tavasz> olvasTableView;
    @FXML private TableColumn<Tavasz,String> olvasSorszamTc;
    @FXML private TableColumn<Tavasz,String> olvasOrszagTc;
    @FXML private TableColumn<Tavasz,String> olvasNevTc;
    @FXML private TableColumn<Tavasz,String> olvasBesorolasTc;
    @FXML private TableColumn<Tavasz,String> olvasEllatasTc;
    @FXML private TableColumn<Tavasz,String> olvasIndulasTc;
    @FXML private TableColumn<Tavasz,String> olvasIdotartamTc;
    @FXML private TableColumn<Tavasz,String> olvasArTc;

    @FXML private Label olvasPageLabel;
    @FXML private Button olvasNextButton;
    @FXML private Button olvasPrevButton;

    //Olvas2
    @FXML private ComboBox<String> olvas2ComboBox;
    private ObservableList<String> orszagList;

    @FXML private Slider olvas2Slider;
    int olvas2SliderValue;
    @FXML private Label olvas2SliderLabel;

    @FXML private CheckBox olvas2NelkulCheckbox;
    @FXML private CheckBox olvas2FelCheckbox;
    @FXML private RadioButton olvas2Csillag5RadioButton;
    @FXML private DatePicker olvas2StartDatePicker;
    @FXML private TextField olvas2SzallasTextField;

    @FXML private TableView<Tavasz> olvas2TableView;
    @FXML private TableColumn<Tavasz,String> olvas2SorszamTc;
    @FXML private TableColumn<Tavasz,String> olvas2OrszagTc;
    @FXML private TableColumn<Tavasz,String> olvas2NevTc;
    @FXML private TableColumn<Tavasz,String> olvas2BesorolasTc;
    @FXML private TableColumn<Tavasz,String> olvas2EllatasTc;
    @FXML private TableColumn<Tavasz,String> olvas2IndulasTc;
    @FXML private TableColumn<Tavasz,String> olvas2IdotartamTc;
    @FXML private TableColumn<Tavasz,String> olvas2ArTc;

    //Ir
    @FXML private ComboBox<String> irSzallodaAzComboBox;
    @FXML private DatePicker irIndulasDatePicker;
    @FXML private TextField irArTextField;
    @FXML private Slider irNapokSlider;
    private int irNapokSliderValue;

    @FXML private Label irNapokLabel;
    @FXML private Label irSzallodaAzErrorLabel;
    @FXML private Label irIndulasErroLabel;
    @FXML private Label irArErrorLabel;
    @FXML private Label irSikeresLabel;

    //Modosit
    @FXML private TableView<Szalloda> modositTableView;
    @FXML private TableColumn<Szalloda,String> modositAzTc;
    @FXML private TableColumn<Szalloda,String> modositNevTc;
    @FXML private TableColumn<Szalloda,String> modositErtekelesTc;
    @FXML private TableColumn<Szalloda,String> modositHelysegTc;
    @FXML private TableColumn<Szalloda,String> modositTengerpartTc;
    @FXML private TableColumn<Szalloda,String> modositRepterTc;
    @FXML private TableColumn<Szalloda,String> modositEllatasTc;

    @FXML private ComboBox<String> modositSzallodaAzComboBox;
    @FXML private TextField modositNevTextField;
    @FXML private Slider modositErtekelesSlider;

    @FXML private ComboBox<String> modositHelysegAzComboBox;
    private List<String> modositHelysegAzNameList;

    @FXML private TextField modositTengerpartTavTextField;
    @FXML private TextField modositRepterTavTextField;
    @FXML private RadioButton modositFelpanzioRadioButton;

    @FXML private Label modositRespondLabel;

    //Torol
    @FXML private TableView<Tavasz> torolTableView;
    @FXML private TableColumn<Tavasz,String> torolSorszamTc;
    @FXML private TableColumn<Tavasz,String> torolSzallodaAzTc;
    @FXML private TableColumn<Tavasz,String> torolIndulasTc;
    @FXML private TableColumn<Tavasz,String> torolIdotartamTc;
    @FXML private TableColumn<Tavasz,String> torolArTc;

    @FXML private ComboBox<Integer> torolTavaszSorszamComboBox;

    @FXML private Label torolRespondLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ADATBAZIS HIBERNATE JAP Connect
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        factory = cfg.buildSessionFactory();
        Transaction t;

        //OLVAS1 menu Inicializalsa
        olvasSorszamTc.setCellValueFactory(new PropertyValueFactory<Tavasz,String>("sorszam"));
        olvasOrszagTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSzalloda().getHelyseg().getOrszag()));
        olvasNevTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSzalloda().getNev()));
        olvasBesorolasTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf("* ".repeat(Math.max(0, cellData.getValue().getSzalloda().getBesorolas())))));
        olvasEllatasTc.setCellValueFactory(cellData -> {
            boolean felpanzio = cellData.getValue().getSzalloda().isFelpanzio();
            String displayValue = felpanzio ? "Félpanzió" : "Ellátás nélkül";
            return new SimpleStringProperty(displayValue);
        });
        olvasIndulasTc.setCellValueFactory(new PropertyValueFactory<Tavasz,String>("indulas"));
        olvasIdotartamTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIdotartam()+" nap, "+(Integer.valueOf(cellData.getValue().getIdotartam())-1)+" éjszaka"));
        olvasArTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%,d",cellData.getValue().getAr()) + " HUF"));
        Olvas1();

        //OLVAS2 Menu Incializalas
        olvas2SorszamTc.setCellValueFactory(new PropertyValueFactory<Tavasz,String>("sorszam"));
        olvas2OrszagTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSzalloda().getHelyseg().getOrszag()));
        olvas2NevTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSzalloda().getNev()));
        olvas2BesorolasTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf("* ".repeat(Math.max(0, cellData.getValue().getSzalloda().getBesorolas())))));
        olvas2EllatasTc.setCellValueFactory(cellData -> {
            boolean felpanzio = cellData.getValue().getSzalloda().isFelpanzio();
            String displayValue = felpanzio ? "Félpanzió" : "Ellátás nélkül";
            return new SimpleStringProperty(displayValue);
        });
        olvas2IndulasTc.setCellValueFactory(new PropertyValueFactory<Tavasz,String>("indulas"));
        olvas2IdotartamTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIdotartam()+" nap, "+(Integer.valueOf(cellData.getValue().getIdotartam())-1)+" éjszaka"));
        olvas2ArTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%,d",cellData.getValue().getAr()) + " HUF"));

        olvas2Slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                olvas2SliderValue = (int) olvas2Slider.getValue();
                olvas2SliderLabel.setText("0 HUF - "+String.format("%,d", olvas2SliderValue)+ " HUF");
            }
        });

        //Ir menu incializalas
        irNapokSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                irNapokSliderValue = (int) irNapokSlider.getValue();
                String napok = String.valueOf(irNapokSliderValue);
                String ejjelek = String.valueOf(irNapokSliderValue-1);
                irNapokLabel.setText("Napok száma: "+napok+" Nap ("+ejjelek+" Éjszaka)");
            }
        });

        //Modositas menu
        modositAzTc.setCellValueFactory(new PropertyValueFactory<Szalloda,String>("Az"));
        modositNevTc.setCellValueFactory(new PropertyValueFactory<Szalloda,String>("Nev"));
        modositErtekelesTc.setCellValueFactory(new PropertyValueFactory<Szalloda,String>("Besorolas"));
        modositHelysegTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getHelyseg().getNev()));
        modositTengerpartTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTengerpart_Tav() + " m"));
        modositRepterTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRepter_Tav() + " m"));
        modositEllatasTc.setCellValueFactory(cellData -> {
            boolean felpanzio = cellData.getValue().isFelpanzio();
            String displayValue = felpanzio ? "Félpanzió" : "Ellátás nélkül";
            return new SimpleStringProperty(displayValue);
        });

        modositSzallodaAzComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                Szalloda szalloda = new Szalloda();
                String SelectedSzallodaAz = modositSzallodaAzComboBox.getValue();
                if (SelectedSzallodaAz == null||SelectedSzallodaAz.isEmpty()){
                    return;
                }
                session = factory.openSession();
                Transaction t = session.beginTransaction();
                szalloda = session.createQuery("FROM Szalloda WHERE Az = '"+SelectedSzallodaAz+"'",Szalloda.class).getSingleResult();
                t.commit();
                session.close();
                if(szalloda != null) {
                    ObservableList<Szalloda> selectedSzalloda = FXCollections.observableArrayList(szalloda);
                    modositTableView.setItems(selectedSzalloda);
                }
                modositNevTextField.setText(szalloda.getNev());
                modositErtekelesSlider.setValue(szalloda.getBesorolas());
                modositHelysegAzComboBox.setValue(modositHelysegAzNameList.get(szalloda.getHelyseg().getAz()-1));
                modositTengerpartTavTextField.setText(String.valueOf(szalloda.getTengerpart_Tav()));
                modositRepterTavTextField.setText(String.valueOf(szalloda.getRepter_Tav()));
                modositFelpanzioRadioButton.setSelected(szalloda.isFelpanzio());
            }
        });

        //Torol menu inicializalas
        torolSorszamTc.setCellValueFactory(new PropertyValueFactory<Tavasz,String>("Sorszam"));
        torolIndulasTc.setCellValueFactory(new PropertyValueFactory<Tavasz,String>("Indulas"));
        torolIdotartamTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIdotartam()+" nap, "+(Integer.valueOf(cellData.getValue().getIdotartam())-1)+" éjszaka"));
        torolSzallodaAzTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSzalloda().getAz()));
        torolArTc.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%,d",cellData.getValue().getAr()) + " HUF"));

        PopulateDbTorolComboBox();
        torolTavaszSorszamComboBox.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                LoadSelectedTavaszSorszam(t1);
            }
        });

    }

    public void Olvas1(){
        olvasTableView.getItems().clear();
        if (factory == null){
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            factory = cfg.buildSessionFactory();
        }
        session = factory.openSession();
        Transaction t = session.beginTransaction();
        List<Tavasz> lista = session.createQuery("From Tavasz").setFirstResult(olvasPage*25).setMaxResults(25).list();
        t.commit();
        session.close();
        ObservableList<Tavasz> data = FXCollections.observableArrayList(lista);
        olvasTableView.setItems(data);

        if (lista.size()%25 != 0 || lista.isEmpty()) {
            isPageMax = true;
            olvasNextButton.setDisable(true);
        }
    }

    public void NextOlvasPage(){
        if (isPageMax){
            return;
        }
        olvasPage ++;
        olvasPrevButton.setDisable(false);
        Olvas1();
        olvasPageLabel.setText((olvasPage+1)+" / "+(olvasPage*25+1)+" - "+(olvasPage*25+olvasTableView.getItems().size()));
    }

    public void PrevOlvasPage(){
        if (olvasPage < 1 ){
            return;
        }
        olvasPage --;
        Olvas1();
        isPageMax = false;
        olvasNextButton.setDisable(false);
        olvasPageLabel.setText((olvasPage+1)+" / "+(olvasPage*25+1)+" - "+(olvasPage*25+olvasTableView.getItems().size()));
    }

    public void Olvas2SelectorLoad(){
        session = factory.openSession();
        Transaction t = session.beginTransaction();
        List<String> orszagok = session.createQuery("select Orszag from Helyseg  group by  Orszag").list();
        t.commit();
        session.close();
        orszagList = FXCollections.observableArrayList(orszagok);
        olvas2ComboBox.setItems(orszagList);

        session = factory.openSession();
        t = session.beginTransaction();
        int MaxAr = session.createQuery("Select max(Ar) from Tavasz ", int.class).getSingleResult();
        t.commit();
        session.close();
        olvas2Slider.setMax(MaxAr);
        olvas2SliderLabel.setText("0 HUF - "+String.format("%,d", MaxAr)+ " HUF");
    }

    public void Olvas2(){
        String HQLString = "From Tavasz where Ar < ";
        String MaxAr = String.valueOf(olvas2Slider.getValue());
        HQLString += MaxAr;

        String OrszagString = ((olvas2ComboBox.getValue() == null) ? "" : " szalloda.helyseg.Orszag = '"+olvas2ComboBox.getValue()+"'" );
        HQLString = ((OrszagString.isEmpty())? HQLString+= " " : HQLString + " AND " + OrszagString );

        String EllatasNelkulString = ((olvas2NelkulCheckbox.isSelected()) ? "szalloda.Felpanzio = false " : "");
        String FelpanzioStrin = ((olvas2FelCheckbox.isSelected()) ? "szalloda.Felpanzio = true " : "");
        if(!EllatasNelkulString.isEmpty() && !FelpanzioStrin.isEmpty()){
            HQLString += " AND ("+EllatasNelkulString+" OR "+FelpanzioStrin+") ";
        } else if (!EllatasNelkulString.isEmpty()) {
            HQLString += " AND "+EllatasNelkulString;
        }else if (!FelpanzioStrin.isEmpty()){
            HQLString += " AND "+FelpanzioStrin;
        }

        String BesoroloasString = ((olvas2Csillag5RadioButton.isSelected()) ? "szalloda.Besorolas = 5 " : "");
        HQLString = ((BesoroloasString.isEmpty())? HQLString+=" " : HQLString + " AND " + BesoroloasString );

        LocalDate startDate = olvas2StartDatePicker.getValue();
        String startDateString;
        if (startDate != null) {
            startDateString = startDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            HQLString += " AND Indulas = '"+startDateString+"'";
        }

        if (!olvas2SzallasTextField.getText().isEmpty()) {
            HQLString += " AND UPPER(szalloda.Nev) Like '%"+olvas2SzallasTextField.getText().toUpperCase()+"%'";
        }

        session = factory.openSession();
        Transaction t = session.beginTransaction();
        List<Tavasz> lista = session.createQuery(HQLString).list();
        ObservableList<Tavasz> keresData = FXCollections.observableArrayList(lista);
        t.commit();
        session.close();
        olvas2TableView.setItems(keresData);
    }

    public void IrSelectorLoad(){
        session = factory.openSession();
        Transaction t = session.beginTransaction();
        List<String> szallodaAzonositok = session.createQuery("select Az from Szalloda").list();
        t.commit();
        session.close();
        ObservableList szallodaAzList = FXCollections.observableArrayList(szallodaAzonositok);
        irSzallodaAzComboBox.setItems(szallodaAzList);
    }

    public void IrAdd(){
        irSzallodaAzErrorLabel.setText("");
        irIndulasErroLabel.setText("");
        irArErrorLabel.setText("");
        irSikeresLabel.setText("");//

        Transaction t;
        Szalloda insertSzalloda = new Szalloda();

        String szalloda_az = irSzallodaAzComboBox.getValue();
        boolean Error = false;
        if( szalloda_az == null || szalloda_az.isEmpty() ){
            irSzallodaAzErrorLabel.setText("Hibás azonosító!");
            Error = true;
        }else{
            session = factory.openSession();
            t = session.beginTransaction();
            insertSzalloda = session.createQuery("From Szalloda where Az = '" + szalloda_az + "' ", Szalloda.class).getSingleResult();
            t.commit();
            session.close();
            if(insertSzalloda == null){
                irSzallodaAzErrorLabel.setText("Hibás azonosító!");
                Error = true;
            }
        }

        String insertIndulas = "";
        LocalDate startDate = irIndulasDatePicker.getValue();
        if (startDate != null) {
            try{
                insertIndulas = startDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            }catch (Exception e){
                System.out.println(e.getMessage());
                irIndulasErroLabel.setText("Hibás indulási érték!");
                Error = true;
            }
        }else{
            irIndulasErroLabel.setText("Hibás indulási érték!");
            Error = true;
        }

        int insertIdotartam = 0 ;
        insertIdotartam = (int)(irNapokSlider.getValue());
        if (insertIdotartam <= 0 ){
            Error = true;
        }
        int ar = 0;
        try{
            ar = Integer.parseInt(irArTextField.getText());
        }catch(NumberFormatException e){
            irArErrorLabel.setText("Hibás érték!");
            Error = true;
        }
        if (ar <= 0){
            Error = true;
            irArErrorLabel.setText("Hibás érték!");
        }

        if(Error){
            irSikeresLabel.setText("Sikertelen Adatbevitel!");
            return;
        }

        session = factory.openSession();
        t = session.beginTransaction();
        Tavasz insertTavasz = new Tavasz();
        insertTavasz.setSzalloda(insertSzalloda);
        insertTavasz.setIndulas(insertIndulas);
        insertTavasz.setIdotartam(insertIdotartam);
        insertTavasz.setAr(ar);
        session.save(insertTavasz);
        t.commit();
        session.close();
        irSikeresLabel.setText("Sikers Adatbevitel!");
    }

    public void ModositSelectorLoad(){
        session = factory.openSession();
        Transaction t = session.beginTransaction();
        List<Helyseg> Helysegek = session.createQuery("FROM Helyseg").list();
        modositHelysegAzNameList = new ArrayList<>();
        for(Helyseg item : Helysegek){
            String text = item.getAz()+" ("+item.getNev()+")";
            modositHelysegAzNameList.add(text);
        }
        t.commit();
        session.close();
        ObservableList<String> helysegAzList = FXCollections.observableArrayList(modositHelysegAzNameList);
        modositHelysegAzComboBox.setItems(helysegAzList);

        session = factory.openSession();
        t = session.beginTransaction();
        List<String> szallodaAzonositok = session.createQuery("select Az from Szalloda").list();
        t.commit();
        session.close();
        ObservableList<String> szallodaAzList = FXCollections.observableArrayList(szallodaAzonositok);
        modositSzallodaAzComboBox.setItems(szallodaAzList);
    }

    public void LoadSelectedSzalloda(){
        Szalloda szalloda = new Szalloda();
        session = factory.openSession();
        Transaction t = session.beginTransaction();
        String SelectedSzallodaAz = modositSzallodaAzComboBox.getValue();
        szalloda = session.createQuery("FROM Szalloda WHERE Az = '"+SelectedSzallodaAz+"'",Szalloda.class).getSingleResult();
        t.commit();
        session.close();
        if(szalloda != null) {
            ObservableList<Szalloda> selectedSzalloda = FXCollections.observableArrayList(szalloda);
            modositTableView.setItems(selectedSzalloda);
        }
    }

    public void ModositMositas(){
        boolean Error  = false;

        String szallodaAz = modositSzallodaAzComboBox.getValue();
        if (szallodaAz == null || szallodaAz.isEmpty() ){
            Error = true;
        }

        String newNev = modositNevTextField.getText();
        if(newNev.isEmpty()){
            Error = true;
        }

        int newErtekeles = (int) modositErtekelesSlider.getValue();
        if(newErtekeles <= 0 ){
            Error = true;
        }
        int newHelysegAz = 0;
        String helysegAzName = "";
        helysegAzName = modositHelysegAzComboBox.getValue();
        if (helysegAzName == null || helysegAzName.isEmpty() ){
            Error = true;
        }else{
            newHelysegAz = Integer.parseInt(helysegAzName.split(" ",0)[0]);
        }

        Helyseg newhelyseg = new Helyseg();
        session = factory.openSession();
        Transaction t = session.beginTransaction();
        try{
            newhelyseg = session.createQuery("from Helyseg where Az = "+newHelysegAz,Helyseg.class).getSingleResult();
        }catch (Exception e){
            Error = true;
        }
        t.commit();
        session.close();

        String TengerpartTav = modositTengerpartTavTextField.getText();
        if(TengerpartTav.isEmpty()){
            Error = true;
        }else{

        }

        int newTengerpartTav = (modositTengerpartTavTextField.getText().isEmpty()) ? -1 :  Integer.parseInt(modositTengerpartTavTextField.getText());
        if(newTengerpartTav < 0 )Error = true;

        int newRepterTav = (modositRepterTavTextField.getText().isEmpty())? -1 : Integer.parseInt(modositRepterTavTextField.getText()) ;
        if(newRepterTav < 0 )Error = true;


        boolean newEllatas = modositFelpanzioRadioButton.isSelected();
        if (Error){
            modositRespondLabel.setText("Hibas vagy hiányzó adatok!");
            return;
        }

        session = factory.openSession();
        t = session.beginTransaction();
        Szalloda szalloda = session.load(Szalloda.class, szallodaAz);
        szalloda.setNev(newNev);
        szalloda.setBesorolas(newErtekeles);
        if(newhelyseg.Az != szalloda.getHelyseg().Az){
            szalloda.setHelyseg(newhelyseg);
        }
        szalloda.setTengerpart_Tav(newTengerpartTav);
        szalloda.setRepter_Tav(newRepterTav);
        szalloda.setFelpanzio(newEllatas);
        session.update(szalloda);
        t.commit();
        session.close();

        modositRespondLabel.setText("Sikeres Adatmódosítás Történt!");
        LoadSelectedSzalloda();
    }

    public void PopulateDbTorolComboBox(){
        session = factory.openSession();
        Transaction t = session.beginTransaction();
        List<Tavasz> tavaszList = session.createQuery("FROM Tavasz").list();
        List<Integer> tavaszAzList = new ArrayList<>();
        for(Tavasz item : tavaszList){
            int sorszam = item.getSorszam();
            tavaszAzList.add(sorszam);
        }
        t.commit();
        session.close();
        ObservableList<Integer> TorolTavaszSorszamList = FXCollections.observableArrayList(tavaszAzList);
        torolTavaszSorszamComboBox.setItems(TorolTavaszSorszamList);
    }

    public void LoadSelectedTavaszSorszam(int sorszam){
        session = factory.openSession();
        Transaction t = session.beginTransaction();
        Tavasz tavasz = session.createQuery("FROM Tavasz WHERE Sorszam = "+sorszam,Tavasz.class).getSingleResult();
        t.commit();
        session.close();
        if(tavasz != null) {
            ObservableList<Tavasz> tavaszTorol = FXCollections.observableArrayList(tavasz);
            torolTableView.setItems(tavaszTorol);
        }
    }

    public void TorolTorles(){
        Integer sorszam = torolTavaszSorszamComboBox.getValue();
        if(sorszam == null){
            torolRespondLabel.setText("Nincs kiválasztott sorszám!");
            return;
        }
        Tavasz tavasz = new Tavasz();
        session = factory.openSession();
        Transaction t = session.beginTransaction();
        try{
            tavasz = session.load(Tavasz.class, sorszam);
        }catch (Exception e){
            torolRespondLabel.setText("Adatbázis Hiba!");
            t.commit();
            session.close();
            return;
        }
        tavasz.setSzalloda(null);
        session.delete(tavasz);
        torolRespondLabel.setText("Sikeres Törlés! Sorszam: "+sorszam);
        t.commit();
        session.close();
        torolTableView.getItems().clear();


    }

}
