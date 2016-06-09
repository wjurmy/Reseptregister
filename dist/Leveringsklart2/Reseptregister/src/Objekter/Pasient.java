/**
 * Representerer en pasient, inneholder datafeltet fastlege
 */

package Objekter;

import java.util.LinkedList;

/**
 * Sist endret: 12.05.14
 * @author Benjamin Aarstad Nygård
 */

public class Pasient extends Person{
    private Lege fastlege;
    
    /**
     * Konstruktør som initialiserer alle datafeltene
     * @param ID Pasienten sin ID
     * @param fornavn Pasienten sitt fornavn
     * @param mellomnavn Pasienten sitt mellomnavn
     * @param etternavn Pasienten sitt etternavn
     */
    public Pasient(String ID, String fornavn, String mellomnavn, String etternavn) {
        super(ID, fornavn, mellomnavn, etternavn);
       
        fastlege = null;
    }
    
    /**
     * get-metode fastlege
     * @return fastlege -Fastlegen til pasienten
     */
    public Lege getFastlege(){
        return fastlege;
    }
    
    /**
     * Endrer fastlegen til pasienten
     * @param lege  ny fastlege for pasienten
     */
    public void setFastlege(Lege lege){
        if(lege == null){
            throw new IllegalArgumentException("Ugyldig lege! Kan ikke være null");
        }
        if(fastlege == null){
            fastlege = lege;
            fastlege.add(this);
        }else{
            fastlege.fjernPasient(this);
            fastlege = lege;
            fastlege.add(this);
        }
        
    }

    /**
     * Fjerner fastlegen for pasienten.
     */
    public void fjernFastlege(){
        fastlege.fjernPasient(this);
        fastlege = null;
        
    }

}//class Pasient
