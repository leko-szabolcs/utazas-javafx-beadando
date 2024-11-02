package com.example.utazasbeadandojavafx;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "szalloda")
public class Szalloda {
    @Id
    @Column(name = "az")
    public String Az;

    @Column(name = "nev")
    public String Nev;

    @Column(name = "besorolas")
    public int Besorolas;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "helyseg_az")
    public Helyseg helyseg;

    @Column(name = "tengerpart_tav")
    public int Tengerpart_Tav;

    @Column(name = "repter_tav")
    public int Repter_Tav;

    @Column(name = "felpanzio")
    public boolean Felpanzio;

    @OneToMany(mappedBy = "szalloda", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH })
    private List<Tavasz> TavaszList;

    public Szalloda() {
    }

    public Szalloda(String az, String nev, int besorolas, Helyseg helyseg, int tengerpart_Tav, int repter_Tav, boolean felpanzio) {
        Az = az;
        Nev = nev;
        Besorolas = besorolas;
        this.helyseg = helyseg;
        Tengerpart_Tav = tengerpart_Tav;
        Repter_Tav = repter_Tav;
        Felpanzio = felpanzio;
    }

    public String getAz() {
        return Az;
    }

    public void setAz(String az) {
        Az = az;
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

    public Helyseg getHelyseg() {
        return helyseg;
    }

    public void setHelyseg(Helyseg helyseg) {
        this.helyseg = helyseg;
    }

    public int getTengerpart_Tav() {
        return Tengerpart_Tav;
    }

    public void setTengerpart_Tav(int tengerpart_Tav) {
        Tengerpart_Tav = tengerpart_Tav;
    }

    public int getRepter_Tav() {
        return Repter_Tav;
    }

    public void setRepter_Tav(int repter_Tav) {
        Repter_Tav = repter_Tav;
    }

    public boolean isFelpanzio() {
        return Felpanzio;
    }

    public void setFelpanzio(boolean felpanzio) {
        Felpanzio = felpanzio;
    }
}

