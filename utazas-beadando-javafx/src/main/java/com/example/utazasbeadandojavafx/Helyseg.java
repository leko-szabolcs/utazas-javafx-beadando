package com.example.utazasbeadandojavafx;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "helyseg")
public class Helyseg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary key, Auto_Increment
    @Column(name = "az")
    public int Az;
    @Column(name = "nev")
    public String Nev;
    @Column(name = "orszag")
    public String Orszag;

    @OneToMany(mappedBy = "helyseg", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH })
    private List<Szalloda> Szallodak;


    public Helyseg() {
    }

    public Helyseg(String nev, String orszag) {
        Nev = nev;
        Orszag = orszag;
    }

    public int getAz() {
        return Az;
    }

    public void setAz(int az) {
        Az = az;
    }

    public String getNev() {
        return Nev;
    }

    public void setNev(String nev) {
        Nev = nev;
    }

    public String getOrszag() {
        return Orszag;
    }

    public void setOrszag(String orszag) {
        Orszag = orszag;
    }
}
