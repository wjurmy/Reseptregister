/**
 * Representerer et preparat, inneholder datafeltene preparatnavn, produsent, reseptgruppe, ATC_kode og kategori
 */
package Objekter;

import java.io.Serializable;

/*
 * @author Benjamin Aarstad Nygård
 * Sist endret 12.05.14
 */

public class Preparat implements Serializable{

    private String preparatnavn;
    private String produsent;
    private String reseptgruppe;
    private String ATC_kode;
    private String kategori;

    /**
     * Konstruktør for Preparat. Initialiserer datafelter
     * @param preparatnavn preparatets navn
     * @param produsent preparatets produsent
     * @param reseptgruppe preparatets reseptgruppe
     * @param ATC_kode preparatets ATC-kode
     * @param kategori preparatets kategori
     */
    
    public Preparat(String preparatnavn, String produsent, String reseptgruppe, String ATC_kode, String kategori) {
        this.preparatnavn = preparatnavn;
        this.produsent = produsent;
        this.reseptgruppe = reseptgruppe;
        this.ATC_kode = ATC_kode;
        this.kategori = kategori;
    }

    /**
     * Endrer/setter kategori
     * @param kategori kategoriens nye navn
     */
    
    public void setKategori(String kategori) {
        if(kategori == null){
            throw new IllegalArgumentException("Ugyldig kategori");
        }
        if(!kategori.equals("")){
             this.kategori = kategori;
        }else{
            this.kategori = "Uspesifisert";
        }
       
    }

    /**
     * Henter preparatnavn
     * @return preparatnavn - preparatets navn
     */
    public String getPreparatnavn(){
        return preparatnavn;
    }

    /**
     * Henter produsent
     * @return produsent - preparatets produsent
     */
    
    public String getProdusent() {
        return produsent;
    }

    /**
     * Henter reseptgruppe
     * @return reseptgruppe - preparatets reseptgruppe 
     */
    
    public String getReseptgruppe() {
        return reseptgruppe;
    }

    /**
     * Henter ATC-kode
     * @return ATC_kode - preparatets ATC-kode
     */
    
    public String getATC_kode() {
        return ATC_kode;
    }

    /**
     * Henter kategori
     * @return kategori - preparatets kategori
     */
    
    public String getKategori() {
        return kategori;
    }

    /**
     * toString med utskrift av all info av preparat
     * @return all info om preparat
     */
    
    @Override
    public String toString() {
        return "Preparat{" + "preparatnavn=" + preparatnavn + ", produsent=" + produsent + ", reseptgruppe=" + reseptgruppe + ", ATC_kode=" + ATC_kode + ", kategori=" + kategori + '}';
    }
    

}//class Preparat
