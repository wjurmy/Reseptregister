/**
 * Representerer en lege, inneholder datafeltene arbeidssted, passord, bevilgning A/B/C.
 * 
 */
package Objekter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Siste endret: 12.05.14
 * @author Benjamin Aarstad Nygård
 */
public class Lege extends Person {

    //initialiserer datafelter
    private TreeMap<String, Pasient> pasientliste;

    private String arbeidssted;
    private String passord;
    private boolean bevA;
    private boolean bevB;
    private boolean bevC;

    private final int finnesIkke = -1;

    /**
     * Konstruktør for Lege. Initialiserer datafelter.
     *
     * @param ID IDen til legen
     * @param fornavn fornavnet til legen
     * @param mellomnavn mellomnavnet til legen
     * @param etternavn etternavnet til legen
     * @param passord passordet til legen
     */
    public Lege(String ID, String fornavn, String mellomnavn, String etternavn, String passord) {
        super(ID, fornavn, mellomnavn, etternavn);
        
        pasientliste = new TreeMap<>();

        arbeidssted = "";
        this.passord = passord;
        bevA = true;
        bevB = true;
        bevC = true;
    }

    /**
     * Metode som returnerer om lege har bevilgning til å skrive ut a-resepter
     *
     * @return bevA - legens bevilgning til å skrive ut A-resepter
     */
    public boolean isBevA() {
        return bevA;
    }

    /**
     * Metode som returnerer om lege har bevilgning til å skrive ut b-resepter
     *
     * @return bevB - legens bevilgning til å skrive ut B-resepter
     */
    public boolean isBevB() {
        return bevB;
    }

    /**
     * Metode som returnerer om lege har bevilgning til å skrive ut c-resepter
     * @return - legens bevilgning til å skrive ut C-resepter
     */
    public boolean isBevC() {
        return bevC;
    }

    /**
     * set-metode for bevilgning av a-resepter
     * @param bevA - ny verdi for legens a-bevilgning. true eller false
     */
    public void setBevA(boolean bevA) {
        this.bevA = bevA;
    }

    /**
     * set-metode for bevilgning av b-resepter
     * @param bevB - ny verdi for legens b-bevilgning. true eller false
     */
    public void setBevB(boolean bevB) {
        this.bevB = bevB;
    }

    /**
     * set-metode for bevC
     * @param bevC - ny verdi for legens c-bevilgning true eller false
     */
    public void setBevC(boolean bevC) {
        this.bevC = bevC;
    }

    /**
     * get-metode for passord
     * @return passord - legens passord for å logge seg inn i systemet
     */
    public String getPassord() {
        return passord;
    }

    /**
     * setter nytt passord til legen
     *
     * @param passord - det nye passordet til legen
     */
    public void setPassord(String passord) {
        if (passord == null) {
            throw new IllegalArgumentException("Ikke gyldig passord");
        }
        this.passord = passord;
    }

    /**
     * Returnerer indeks til pasienten dersom den finnes, dersom den ikke finnes
     * returneres finnesIkke (-1)
     *
     * @param ID ID som det skal undersøkes om finnes i listen
     * @return indeks - indeks til funnet pasient, dersom pasienten ikke finnes returneres finnesIkke (-1)
     *
     */
    public int pasientFinnes(String ID) {

        Iterator it = pasientliste.values().iterator();

        int teller = 0;

        while (it.hasNext()) {
            Pasient pasient = (Pasient) it.next();

            if (pasient.getID() == ID) {
                return teller;
            }
            teller++;
        }

        return finnesIkke;

    }

    /**
     * get-metode for arbeidssted
     * @return arbeidssted - legens nåværende arbeidssted
     */
    public String getArbeidssted() {

        return arbeidssted;

    }

    /**
     * set-metode for arbeidssted
     *
     * @param arbeidssted - det nye arbeidsstedet til legen
     */
    public void setArbeidssted(String arbeidssted) {
        if (arbeidssted == null) {
            throw new IllegalArgumentException("Ikke gyldig arbeidssted");
        }
        this.arbeidssted = arbeidssted;
    }

    /**
     * Legger til pasient i fastpasientlsiten til en lege
     *
     * @param pasient - pasient som skal legges til i pasientlisten til legen
     */
    public void add(Pasient pasient) {
        if (pasient == null) {
            throw new IllegalArgumentException("Ikke gyldig pasient");
        }
        if (!pasientliste.containsKey(pasient.getID())) {
            pasientliste.put(pasient.getID(), pasient);
        }
    }

    /**
     * returnerer pasienten som har samme ID som String ID, dersom pasienten ikke
     * finnes returneres null
     *
     * @param ID ID til pasienten som skal returneres
     * @return - pasient/null - dersom pasienten ikke finnes, returneres null
     */
    public Pasient getPasient(String ID) {

       
        if (pasientliste.containsKey(ID)) {
            return pasientliste.get(ID);
        }
        return null;
    }

    /**
     * Fjerner mottatt pasient fram pasientlisten til legen
     *
     * @param pasient pasient som skal fjernes
     */
    public void fjernPasient(Pasient pasient) {
        if (pasient == null) {
            throw new IllegalArgumentException("Ikke gyldig pasient");
        }
        int indeks = pasientFinnes(pasient.getID());

        if (indeks != finnesIkke) {
            pasientliste.remove(pasient.getID());
        }

    }

    /**
     * get-metode for legens pasientliste
     *
     * @return pasientliste - pasientlisten til legen
     */
    public LinkedList<Pasient> getPasientliste() {
        return new LinkedList<Pasient>(pasientliste.values());
    }

}//class Lege
