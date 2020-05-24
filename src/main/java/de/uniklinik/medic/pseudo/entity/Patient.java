package de.uniklinik.medic.pseudo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Jonathan on 26.Apr.2020 . 11:10 AM
 */
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String vorname;
    private String nachname;
    private String geburtsname;
    private String geburtstag;
    private String geburtsmonat;
    private String geburtsjahr;
    private String plz;
    private String ort;

    public Patient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getGeburtsname() {
        return geburtsname;
    }

    public void setGeburtsname(String geburtsname) {
        this.geburtsname = geburtsname;
    }

    public String getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(String geburtstag) {
        this.geburtstag = geburtstag;
    }

    public String getGeburtsmonat() {
        return geburtsmonat;
    }

    public void setGeburtsmonat(String geburtsmonat) {
        this.geburtsmonat = geburtsmonat;
    }

    public String getGeburtsjahr() {
        return geburtsjahr;
    }

    public void setGeburtsjahr(String geburtsjahr) {
        this.geburtsjahr = geburtsjahr;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
}
