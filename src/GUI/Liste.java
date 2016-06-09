/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Registerklasser.Preparatregister;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Guest
 */

public class Liste {

    private LinkedList<String> preparatnavn;

    /**
     * Oppretter en liste for preparatnavn
     */
    public Liste() {
        preparatnavn = new LinkedList<>();

        lagListe();
    }//Liste ferdig

    /**
     * Henter liste med preparater
     * @return 
     */
    public LinkedList<String> getListe() {
        return preparatnavn;
    }//getListe ferdig

    /**
     * Legger til preparater i preparatlisten
     */
    public void lagListe() {
        preparatnavn =  new Preparatregister().getPreparatnavnliste();
    }//lagListe ferdig

}//Liste ferdig
