/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objekter;

import java.util.LinkedList;

/**
 *
 * @author Benjamin Aarstad Nygård
 */

public class Pasient extends Person{
    private Lege fastlege;
    
    
    public Pasient(String ID, String fornavn, String mellomnavn, String etternavn) {
        super(ID, fornavn, mellomnavn, etternavn);
       
        fastlege = null;
    }
    
    public Lege getFastlege(){
        return fastlege;
    }
    
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
    public void fjernFastlege(){
        fastlege.fjernPasient(this);
        fastlege = null;
        
    }

    
    
}
