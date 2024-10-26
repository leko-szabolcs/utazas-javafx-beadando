package com.example.utazasbeadandojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

public class FeladatApplication extends Application {
    //static Session session;
    //static SessionFactory factory;
    /*
    public static void Read(){
        System.out.println("Read()........");
        Transaction t = session.beginTransaction();
        List<Helyseg> lista = session.createQuery("from helyseg").list();
        for(Helyseg dolg:lista){
            System.out.print("Az: " + dolg.getAz());
            System.out.print(" Név: " + dolg.getOrszag());
        }
        System.out.println();
        t.commit();
    }
    */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FeladatApplication.class.getResource("main_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Utazas-JavaFX");
        stage.setScene(scene);
        stage.show();
        /*
        try{
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(registry).buildMetadata();
            factory = metadata.getSessionFactoryBuilder().build();
            session = factory.openSession();
            System.out.println("run");
            Read();
            factory.close();
        }
        catch(Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }*/


    }

    public static void main(String[] args) {
        launch();
    }
}