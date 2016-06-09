/**
 * Liste over alle preparater, inneholder datafeltene liste, atcliste og preparatliste.
 */

package Registerklasser;

import reseptregister.Reseptregister;
import Objekter.Preparat;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;


/* 
 * @author Benjamin Aarstad Nygård
 * Sist endret: 12.05.14
 */
public class Preparatregister implements Serializable {

    private LinkedList<Preparat> liste;
    private TreeMap<String, String> atcliste;
    private TreeMap<String, String> preparatnavnliste;

    /**
     * Konstruktør som initialiserer datafelter og lager lister
     */
    public Preparatregister() {
        liste = Reseptregister.getListe();
        atcliste = new TreeMap<>();
        preparatnavnliste = new TreeMap<>();
        lagATCListe();
        lagPreparatnavnliste();
    }

    /**
     * Lager liste basert på ATC-koder
     */
    public void lagATCListe() {
        for (Preparat preparat : liste) {
            String atc = preparat.getATC_kode();
            if (!atcliste.containsKey(atc)) {
                atcliste.put(atc, atc);
            }
        }
    }

    /**
     * Lager liste basert på preparatnavn
     */
    public void lagPreparatnavnliste() {
        for (Preparat preparat : liste) {
            String preparatnavn = preparat.getPreparatnavn();

            if (!preparatnavnliste.containsKey(preparatnavn)) {
                preparatnavnliste.put(preparatnavn, preparatnavn);
            }
        }
    }

    /**
     * henter ATC-listen
     * @return  new LinkedList<String>(atcliste.values()) - ny liste over ATC-koder
     */
    public LinkedList<String> getATCListe() {
        return new LinkedList<String>(atcliste.values());
    }

    /**
     * Henter liste basert på preparatnavn
     * @return new LinkedList<String>(preparatnavnliste.values()) - ny liste over preparatnavn
     */
    public LinkedList<String> getPreparatnavnliste() {
        return new LinkedList<String>(preparatnavnliste.values());
    }

    /**
     * Liste over alle preparatene 
     * @param liste listen med alle preparatnavnene
     */
    public Preparatregister(LinkedList<Preparat> liste) {
        this.liste = liste;
    }

    /**
     * Sjekker om ATC-koden finnes
     * @param kode kode som skal sjekkes
     * @return finnes - true eller false om ATC-koden finnes
     */
    public boolean finnes(String kode) {
        if (!liste.isEmpty()) {
            Iterator<Preparat> it = liste.iterator();
            while (it.hasNext()) {
                if (it.next().getATC_kode().equalsIgnoreCase(kode)) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Sjekker om det finnes et preparat som har samme kode, navn og produsent
     * @param kode kode som skal sjekkes
     * @param navn navn som skal sjekkes
     * @param produsent produsent som skal sjekkes
     * @return finnes - true eller false om preparatet finnes
     */
    public boolean finnes(String kode, String navn, String produsent) {
        if (finnes(kode)) {
            for (int i = 0; i < liste.size(); i++) {
                if (liste.get(i).getATC_kode().equalsIgnoreCase(kode) && liste.get(i).getPreparatnavn().equalsIgnoreCase(navn) && liste.get(i).getProdusent().equalsIgnoreCase(produsent)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sjekker om det finnes et preparat som har samme kode, navn og produsent
     * @param kode kode som skal sjekkes
     * @param navn navn som skal sjekkes
     * @param produsent produsent som skal sjekkes
     * @return liste.get(i) - dersom det finnes et preparat med kode, navn og produsent returneres dette preparatet
     * ellers returneres null
     */
    public Preparat finnes2(String kode, String navn, String produsent) {
        if (finnes(kode)) {
            for (int i = 0; i < liste.size(); i++) {
                if (liste.get(i).getATC_kode().equalsIgnoreCase(kode) && liste.get(i).getPreparatnavn().equalsIgnoreCase(navn) && liste.get(i).getProdusent().equalsIgnoreCase(produsent)) {
                    return liste.get(i);
                }
            }
        }
        return null;
    }
    
    /**
     * Henter preparat med ATC-kode ved kode som er gitt i parameteret
     * @param kode ATC-kode på preparatet vi vil hente
     * @return liste.get(i) - dersom det finnes et preparat med denne koden returneres dette, eller returneres null
     */
    public Preparat hent_preparat(String kode) {
        if (!liste.isEmpty()) {
            for (int i = 0; i < liste.size(); i++) {
                if (liste.get(i).getATC_kode().equalsIgnoreCase(kode)) {
                    System.out.println(i);
                    return liste.get(i);

                }
            }
        }
        return null;
    }

    /**
     * Henter preparatnavn ved navn som er gitt i parameteret
     * @param navn navnet til preparatet vi vil hente
     * @return liste.get(i) - Dersom preparatnavnet finnes vil dette preparatet returneres, ellers returneres null
     */
    public Preparat getPreparaNAVN(String navn) {
        if (!liste.isEmpty()) {
            for (int i = 0; i < liste.size(); i++) {
                if (liste.get(i).getPreparatnavn().equalsIgnoreCase(navn)) {
                    return liste.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Sjekke om det er flere enn 1 forekomst av ATC-kode
     * @param ATC ATC-kode som det skal sjekke forekomster på
     * @return flereEnnEn - Returnerer True om det finnes mer enn 1 forekomst, om det finnes mindre enn 
     * 1 forekomst returneres false
     */
    public boolean flereEnnEn(String ATC) {
        int teller = 0;
        if (finnes(ATC)) {
            for (int i = 0; i < liste.size(); i++) {
                if (liste.get(i).getATC_kode().equalsIgnoreCase(ATC)) {
                    teller++;
                    if (teller > 1) {
                        return true;
                    }
                }
            }

        }
        if (teller > 1) {
            return true;
        }
        return false;
    }

    /**
     * Henter ut størrelsen på listen
     * @return liste.size - størrelsen på listen
     */
    public int getSize() {
        return liste.size();
    }

}//Class Preparatregister
