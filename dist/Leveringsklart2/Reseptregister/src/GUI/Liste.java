/**
 * Liste er en klasse for å hente ut alle preparatnavnenen i preparatregisteret
 */

package GUI;

import Registerklasser.Preparatregister;
import java.util.LinkedList;

/**
 * siste endring: 12.05.14
 * @author Benjamin Aarstad Nygård og Walid Jurmy
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
     * @return preparatnavn - LinkedList med alle preparatnavnene i registeret
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
