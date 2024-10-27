package com.example.utazasbeadandojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FeladatApplication extends Application {
    /*
    static Session session;
    static SessionFactory factory;
    public static void Read(){
        System.out.println("Read()........");
        Transaction t = session.beginTransaction();
        List<Helyseg> lista = session.createQuery("FROM Helyseg").list();
        for(Helyseg dolg:lista){
            System.out.print("Az: " + dolg.getAz());
            System.out.print(" Név: " + dolg.getNev());
        }
        System.out.println("READ IN ACTION");
        t.commit();
    }*/


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FeladatApplication.class.getResource("main_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Utazas-JavaFX");
        stage.setScene(scene);
        stage.show();

        /*
        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/c:/adatbazis/adatok.db");
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT orszag, szalloda.nev, szalloda.besorolas, felpanzio, tavasz.indulas, tavasz.idotartam, tavasz.ar FROM (tavasz inner join szalloda on tavasz.szalloda_az = szalloda.az) inner join helyseg on helyseg.az = szalloda.helyseg_az ") ;
            while(rs.next()){
                String orszag = rs.getString("orszag");
                String nev = rs.getString("nev");
                int besorolas = rs.getInt("besorolas");
                boolean felpanzio = rs.getBoolean("felpanzio");
                String tavasz = rs.getString("indulas");
                int idotartam = rs.getInt("idotartam");
                int ar = rs.getInt("ar");
                System.out.println(orszag+" "+nev+" "+idotartam+" "+ar);
                Ajanlatok newAjanalat = new Ajanlatok(orszag,nev,besorolas,felpanzio,tavasz,idotartam,ar);
            }
        }catch (Exception e){

        }
        */

        /*
        try{
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
            factory = metadata.getSessionFactoryBuilder();
            session = factory.openSession();
            System.out.println("run");
            Read();
            session.close();
            factory.close();
        }
        catch(Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
         */

    }

    public static void main(String[] args) {
        launch();
    }
}