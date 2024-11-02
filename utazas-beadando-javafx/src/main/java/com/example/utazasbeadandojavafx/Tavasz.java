package com.example.utazasbeadandojavafx;

import jakarta.persistence.*;

@Entity
@Table(name = "tavasz")
public class Tavasz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sorszam")
    private int Sorszam;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "szalloda_az")
    public Szalloda szalloda;

    @Column(name = "indulas")
    private String Indulas;

    @Column(name = "idotartam")
    private int Idotartam;

    @Column(name = "ar")
    private int Ar;

    public Tavasz() {
    }

    public Tavasz(Szalloda szalloda, String indulas, int idotartam, int ar) {
        this.szalloda = szalloda;
        Indulas = indulas;
        Idotartam = idotartam;
        Ar = ar;
    }

    public int getSorszam() {
        return Sorszam;
    }

    public void setSorszam(int sorszam) {
        Sorszam = sorszam;
    }

    public Szalloda getSzalloda() {
        return szalloda;
    }

    public void setSzalloda(Szalloda szalloda) {
        this.szalloda = szalloda;
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
}
