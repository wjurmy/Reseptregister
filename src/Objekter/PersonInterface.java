/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objekter;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Benjamin Aarstad Nygård
 */
public interface PersonInterface extends Serializable{
    
    
    /**
     * returnerer 
     * @return ID
     */
    public long getID();
    public String getFornavn();
    public String getMellomnavn();
    public String getEtternavn();
    public String getNavn();
    
    public void setFornavn(String fornavn);
    public void setMellomnavn(String mellomnavn);
    public void setEtternavn(String etternavn);
    //public void addResept(Resept resept); 
    
    public int antallA();
    public int antallB();
    public int antallC();
    public int antallTotalt();
    public int getAntallPreparatnavn(String preparat);
    public int getAntallATC(String ATC);
    public int getAntallATC_NAVN(String ATC, String navn);
    
    public boolean harResept(Preparat preparat);
    
    
    //public LinkedList getResepter();
    public LinkedList getResepter_navn(String navn);
    public LinkedList getResepter_kode(String kode);
    public LinkedList getResepter_gruppe(String gruppe);
    public LinkedList getReseptlisteUink();
    
    
    
    
}
