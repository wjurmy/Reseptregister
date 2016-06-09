package Objekter;

import java.io.Serializable;

/*
 * @author Benjamin Aarstad Nygård
 * 
 */

public class Preparat implements Serializable{

    private String preparatnavn;
    private String produsent;
    private String reseptgruppe;
    private String ATC_kode;
    private String kategori;

    public Preparat() {
    }

    /**
     * Konstruktør for Preparat. Initialiserer datafelter
     * @param preparatnavn
     * @param produsent
     * @param reseptgruppe
     * @param ATC_kode
     * @param kategori 
     */
    
    public Preparat(String preparatnavn, String produsent, String reseptgruppe, String ATC_kode, String kategori) {
        this.preparatnavn = preparatnavn;
        this.produsent = produsent;
        this.reseptgruppe = reseptgruppe;
        this.ATC_kode = ATC_kode;
        this.kategori = kategori;
    }

    /**
     * Endrer/setter preparatnavn
     * @param preparatnavn 
     */
    
    public void setPreparatnavn(String preparatnavn) {
        if(preparatnavn == null){
            throw new IllegalArgumentException("Ugyldig preparatnavn");
        }
        this.preparatnavn = preparatnavn;
    }

    /**
     * Endrer/setter preparat-produsent
     * @param produsent 
     */
    
    public void setProdusent(String produsent) {
        if(produsent == null){
            throw new IllegalArgumentException("Ugyldig produsent");
        }
        this.produsent = produsent;
    }

    /**
     * Endrer/setter reseptgruppe
     * @param reseptgruppe 
     */
    
    public void setReseptgruppe(String reseptgruppe) {
        if(reseptgruppe == null){
            throw new IllegalArgumentException("Ugyldig reseptgruppe");
        }
        this.reseptgruppe = reseptgruppe;
    }

    /**
     * Endrer/setter ATC-kode
     * @param ATC_kode 
     */
    
    public void setATC_kode(String ATC_kode) {
        if(ATC_kode == null){
            throw new IllegalArgumentException("Ugyldig ATC-kode");
        }
        this.ATC_kode = ATC_kode;
    }

    /**
     * Endrer/setter kategori
     * @param kategori 
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
     * @return 
     */
    
    public String getPreparatnavn(){
        return preparatnavn;
    }

    /**
     * Henter produsent
     * @return 
     */
    
    public String getProdusent() {
        return produsent;
    }

    /**
     * Henter reseptgruppe
     * @return 
     */
    
    public String getReseptgruppe() {
        return reseptgruppe;
    }

    /**
     * Henter ATC-kode
     * @return 
     */
    
    public String getATC_kode() {
        return ATC_kode;
    }

    /**
     * Henter kategori
     * @return 
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
    

}
