/**
 * Superklasse for pasient og lege, inneholder datafeltene ID, fornavn, mellomnavn, etternavn, finnes, reseptliste
 */
package Objekter;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Sist endret: 12.05.14
 * @author Benjamin Aarstad Nygård
 */
public class Person implements Serializable {

    private final String ID;
    private String fornavn;
    private String mellomnavn;
    private String etternavn;
    private boolean finnes;
    private LinkedList<Resept> reseptliste;

    /**
     * Konstruktør for Person, initialiserer datafelter.
     *
     * @param ID person sin ID
     * @param fornavn person sitt fornavn
     * @param mellomnavn person sitt mellomnavn
     * @param etternavn person sitt etternavn
     */
    public Person(String ID, String fornavn, String mellomnavn, String etternavn) {
        this.ID = ID;
        this.fornavn = fornavn;
        this.mellomnavn = mellomnavn;
        this.etternavn = etternavn;
        reseptliste = new LinkedList<>();
        finnes = true; 

    }

    /**
     * Henter person sin ID
     *
     * @return ID -personen sin ID
     */
    public String getID() {
        return ID;
    }
   
    /**
     * Setter status for personene til fjernet
     */
    public void setFjernet(){
        this.finnes = false;
    }
    
    /**
     * metode som returnerer om denne personen er aktiv i registeret, dvs at personen fortsatt er norsk statsborger og er levende
     * @return finnes - boolsk variabel som forteller om personen finnes i personlista
     */
    public boolean isFinnes(){
        return finnes;
    }

    /**
     * Henter Person sitt fornavn
     *
     * @return fornavn - fornavnet til person
     */
    public String getFornavn() {
        return fornavn;
    }

    /**
     * Henter person sitt mellomnavn
     *
     * @return mellomnavn - person sitt mellomnavn
     */
    public String getMellomnavn() {
        if (mellomnavn == null) {
            return "";
        }
        return mellomnavn;
    }

    /**
     * Henter Person sitt etternavn
     *
     * @return etternavn - person sitt etternavn
     */
    public String getEtternavn() {
        return etternavn;
    }

    /**
     * Henter Person sitt fulle navn
     *
     * @return fornavn + " " + mellomnavn + " " + etternavn - personens fulle navn
     */
    public String getNavn() {
        if (mellomnavn != null && mellomnavn.length() == 0) {
            return fornavn + " " + etternavn;
        } else if (mellomnavn == null) {
            return fornavn + " " + etternavn;
        } else {
            return fornavn + " " + mellomnavn + " " + etternavn;
        }

    }

    /**
     * Endrer/gir fornavn til Person
     *
     * @param fornavn - nye fornavnet til person
     */
    public void setFornavn(String fornavn) {
        if (fornavn == null) {
            throw new IllegalArgumentException("Ikke gyldig fornavn");
        }
        this.fornavn = fornavn;
    }

    /**
     * Endrer/gir mellomnavn til Person
     *
     * @param mellomnavn - nye mellomnavnet til person
     */
    public void setMellomnavn(String mellomnavn) {
        if (mellomnavn == null) {
            throw new IllegalArgumentException("Ikke gyldig mellomnavn");
        }
        this.mellomnavn = mellomnavn;
    }

    /**
     * Endrer/gir etternavn til Person
     *
     * @param etternavn - nye etternavnet til person
     */
    public void setEtternavn(String etternavn) {
        if (etternavn == null) {
            throw new IllegalArgumentException("Ikke gyldig etternavn");
        }
        this.etternavn = etternavn;
    }

    /**
     * toString
     *
     * @return fullt navn og ID
     */
    public String toString() {
        return getNavn() + " ID: " + ID;
    }

}//class Person
