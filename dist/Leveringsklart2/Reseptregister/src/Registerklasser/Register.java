/**
 * Register for leger og pasienterinnholder datafeltene pasientliste, legeliste, fjernedePasienter, fjernedeLeger
 * Leger og pasienter ligger i separate treemaps
 */
package Registerklasser;

import Objekter.Lege;
import Objekter.Pasient;
import Objekter.Person;
import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Sist endret: 12.05.14
 * @author Benjamin Aarstad Nygård, Vegard Lokreim, Walid Jurmy
 */
public class Register implements Serializable {

    private TreeMap<String, Pasient> pasientliste;
    private TreeMap<String, Lege> legeliste;
    private TreeMap<String, Pasient> fjernedePasienter;
    private TreeMap<String, Lege> fjernedeLeger;

    /**
     * Konstruktør som initialiserer datafeltene
     */
    public Register() {
        pasientliste = new TreeMap<>();
        legeliste = new TreeMap<>();

        fjernedePasienter = new TreeMap<>();
        fjernedeLeger = new TreeMap<>();

    }

    /**
     * Legger til person i registeret, metoden sjekker om det er lege eller pasient på hensyn av ID
     * @param person person som skal legges til
     * @return lagt til - returnerer true om person ble lagt til, returnerer false om person ikke ble lagt til
     */
    public boolean leggTil(Person person) {
        if (person instanceof Pasient) {
            if (!pasientliste.containsKey(person.getID())) {
                pasientliste.put(person.getID(), (Pasient) person);
                return true;
            } else {
                return false;
            }
        } else if (person instanceof Lege) {
            if (!legeliste.containsKey(person.getID())) {
                legeliste.put(person.getID(), (Lege) person);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Fjerner person fra registeret
     * @param person person som skal fjernes
     */
    public void fjern(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Ugyldig person");
        }

        if (person instanceof Pasient) {
            if (pasientliste.containsKey(person.getID())) {
                
                pasientliste.remove(person.getID());
                if (!fjernedePasienter.containsKey(person.getID())) {
                    person.setFjernet();
                    fjernedePasienter.put(person.getID(), (Pasient) person);

                }
            }
        } else if (person instanceof Lege) {
            if (legeliste.containsKey(person.getID())) {

                legeliste.remove(person.getID());
                if (!fjernedeLeger.containsKey(person.getID())) {
                    person.setFjernet();
                    fjernedeLeger.put(person.getID(), (Lege) person);

                }
            }
        }
    }

    /**
     * Henter person fra registeret, finner ut om det er lege eller pasient ut ifra ID
     * @param ID IDen til person (kan være lege eller pasient)
     * @return person - returnerer personen om IDen ble funnet, ellers blir det returnert null
     */
    public Person getPerson(String ID) {


        if (String.valueOf(ID).length() == 11) {
            if (pasientliste.containsKey(ID)) {
                return pasientliste.get(ID);
            } else if (fjernedePasienter.containsKey(ID)) {
                return fjernedePasienter.get(ID);
            } else {
                return null;
            }

        } else {
            if (legeliste.containsKey(ID)) {
                return legeliste.get(ID);
            } else if (fjernedeLeger.containsKey(ID)) {
                return fjernedeLeger.get(ID);
            } else {
                return null;
            }

        }

    }

    /**
     * Henter legelisten
     * @return new LinkedList<Lege>(legeliste.values()) - liste med alle leger
     */
    public LinkedList<Lege> getLegeliste() {

        return new LinkedList<Lege>(legeliste.values());
    }

    /**
     * Henter liste med alle pasienter
     * @return new LinkedList<Pasient>(pasientliste.values()) - liste med alle pasienter
     */
    public LinkedList<Pasient> getPasientliste() {
        return new LinkedList<Pasient>(pasientliste.values());
    }

    /**
     * Henter listen med fjernede pasienter.
     * @return new LinkedList<Pasient>(fjernedePasienter.values()) - liste med alle fjernede pasienter
     */
    public LinkedList<Pasient> getFjernedePasienter() {
        return new LinkedList<Pasient>(fjernedePasienter.values());
    }

    /**
     * Henter listen med fjernede leger
     * @return new LinkedList<Lege>(fjernedeLeger.values()) - liste over fjernede leger
     */
    public LinkedList<Lege> getFjernedeLeger() {
        return new LinkedList<>(fjernedeLeger.values());
    }
    
    /**
     * Henter alle leger, både aktive og inaktive
     * @return alle leger - liste med alle leger
     */
    public LinkedList<Lege> getAlleLeger(){
        LinkedList<Lege> aktive = new LinkedList<>(legeliste.values());
        LinkedList<Lege> fjernede = new LinkedList<>(fjernedeLeger.values());
        LinkedList<Lege> alleLeger = new LinkedList<>();
        
        
        alleLeger.addAll(aktive);
        alleLeger.addAll(fjernede);
        
        return alleLeger;
    }

    /**
     * Genererer legenummer til legene
     * @return String.valueOf(i) - returnerer generert legenummer
     */
    public String genererLegenummer() {
        long i = 1;

        while (legeliste.containsKey(String.valueOf(i)) || fjernedeLeger.containsKey(String.valueOf(i))) {
            i++;
        }
        return String.valueOf(i);
    }

}//class Register
