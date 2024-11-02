package com.example.utazasbeadandojavafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Ajanlatok {
    public String Orszag;
    public String Nev;
    public int Besorolas;
    public String Ellatas;
    public String Indulas;
    public int Idotartam;
    public int Ar;
    public int Sorszam;

    public Ajanlatok(int sorszam, String orszag, String nev, int besorolas, boolean ellatas, String indulas, int idotartam, int ar) {
        Orszag = orszag;
        Nev = nev;
        Besorolas = besorolas;
        if(ellatas) {
            Ellatas = "Félpanzió";
        }else{
            Ellatas = "Ellátás nélkül";
        }
        Indulas = indulas;
        Idotartam = idotartam;
        Ar = ar;
        Sorszam = sorszam;
    }

    public String getOrszag() {
        return Orszag;
    }

    public void setOrszag(String orszag) {
        Orszag = orszag;
    }

    public String getNev() {
        return Nev;
    }

    public void setNev(String nev) {
        Nev = nev;
    }

    public int getBesorolas() {
        return Besorolas;
    }

    public void setBesorolas(int besorolas) {
        Besorolas = besorolas;
    }

    public String getEllatas() {
        return Ellatas;
    }

    public void setEllatas(boolean ellatas) {
        if (ellatas) {
            Ellatas = "Félpanzió";

        }else {
            Ellatas = "Ellátás nélkül";
        }
    }

    public String getIndulas() {
        return Indulas;
    }

    public void setIndulas(String indulas) {
        Indulas = indulas;
    }

    public int getIdotartam() {
        return Idotartam;
    }

    public void setIdotartam(int idotartam) {
        Idotartam = idotartam;
    }

    public int getAr() {
        return Ar;
    }

    public void setAr(int ar) {
        Ar = ar;
    }

    public int getSorszam() {
        return Sorszam;
    }

    public void setSorszam(int sorszam) {
        Sorszam = sorszam;
    }
}
