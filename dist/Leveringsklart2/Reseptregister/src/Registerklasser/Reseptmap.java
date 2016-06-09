/**
 * Et TreeMap med alle leger og pasienter og deres tilhørende reseptlister
 */
package Registerklasser;

import Objekter.Resept;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Sist endret: 12.05.14
 * @author Vegard Lokreim, Benjamin Aarstad Nygård, Walid Jurmy
 */
public class Reseptmap implements Serializable {

    private TreeMap<String, LinkedList<Resept>> reseptregister;
    private SimpleDateFormat datoformat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Konstruktør som initialiserer datafelt
     */
    public Reseptmap() {
        reseptregister = new TreeMap<>();
    }

    /**
     * oppretter en reseptliste med nøkkel ID, en ID kan kun ha en resept liste
     * @param ID person sin ID
     */
    public void leggTilPerson(String ID) {
        if (!ID.matches("[0-9]+")) {
            throw new IllegalArgumentException("Ugyldig ID");
        }
        if (!reseptregister.containsKey(ID)) {
            reseptregister.put(ID, new LinkedList<Resept>());
        }
    }
    
    /**
     * Henter reseptlisten til person med ID sendt som er med i parameteret
     * @param ID ID til person man skal finne reseptliste til
     * @return reseptregister.get(ID) - person sitt reseptregister blir returnert hvis man finner personen med denne 
     * IDen. Om person med denne IDen ikke finnes kastes det en NoSuchElementException med beskjed om at 
     * personen ikke finnes.
     */
    public LinkedList<Resept> getReseptliste(String ID) {

        if (reseptregister.containsKey(ID)) {
            return reseptregister.get(ID);
        }
        throw new NoSuchElementException("ID finnes ikke i reseptregister");
    }

    /**
     * Legger til reseptlisten til person med gitt ID
     * @param ID person sin ID
     * @param resept resepten som skal legges til i listen til person
     * @return addResept - True om resepten blir lagt til, false hvis den ikke blir lagt til.
     */
    public boolean addResept(String ID, Resept resept) {

        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> reseptliste = reseptregister.get(ID);
            if (resept == null) {
                throw new IllegalArgumentException("Ugyldig resept");
            }
            reseptliste.add(resept);
            return true;
        }
        return false;
    }

    /**
     * Finner antall resepter i reseptgruppe a for person med gitt ID 
     * @param ID person sin ID
     * @return teller - Antall resepter fra reseptgruppe a 
     */
    public int antallA(String ID) {

        int teller = 0;
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> reseptliste = reseptregister.get(ID);
            for (Resept resept : reseptliste) {
                if (resept.getReseptrgruppe().startsWith("Reseptgruppe A.")) {
                    teller++;
                }
            }
            return teller;
        }
        return teller;
    }

    /**
     * Finner antall resepter i reseptgruppe b for person med gitt ID
     * @param ID person sin ID
     * @return teller - Antall resepter fra reseptgruppe b
     */
    public int antallB(String ID) {

        int teller = 0;
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> reseptliste = reseptregister.get(ID);
            for (Resept resept : reseptliste) {
                if (resept.getReseptrgruppe().startsWith("Reseptgruppe B.")) {
                    teller++;
                }
            }
            return teller;
        }
        return teller;
    }

    /**
     * Finner antall resepter i reseptgruppe c for person med gitt ID
     * @param ID person sin ID
     * @return teller - Antall resepter fra reseptgruppe c
     */
    public int antallC(String ID) {

        int teller = 0;
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> reseptliste = reseptregister.get(ID);
            for (Resept resept : reseptliste) {
                if (resept.getReseptrgruppe().startsWith("Reseptgruppe C.")) {
                    teller++;
                }
            }
            return teller;
        }
        return teller;
    }

    /**
     * Henter antall resepter totalt for person med gitt ID
     * @param ID person sin ID
     * @return reseptregister.get(ID).size() - antall resepter totalt for person med gitt ID. 
     * returnerer 0 om personen ikke finnes.
     */
    public int getAntallTotalt(String ID) {
        if (reseptregister.containsKey(ID)) {
            return reseptregister.get(ID).size();
        }
        return 0;
    }

    /**
     * sjekker om mottatt string allerede finnes i listen som ble mottatt
     *
     * @param medikament medikament som skal sjekkes
     * @param liste liste med medikamentnavn
     * @return true/false - true om det medikamentet allerede finnes i listen, false hvis ikke.
     */
    public boolean reseptFinnes(String medikament, LinkedList liste) { //sjekker om resept finnes for angitt medikament
        if (medikament == null || liste == null) {
            throw new IllegalArgumentException("Ugyldig medikament eller liste");
        }
        Iterator it = liste.iterator(); //oppretter en iterator

        while (it.hasNext()) { //så lenge it.hasNext()
            String navn = (String) it.next(); //navn hentes ut fra lista

            if (navn.equalsIgnoreCase(medikament)) { //sjekker om navn er det samme som medikament
                return true; //returnerer true dersom navn er det samme som mottatt medikament
            }

        }
        return false;
    }

    /**
     * Lager liste over forskjellige unike medikamenter som det er skrevet
     * resept på
     *
     * @return reseptliste med unike medikamenter
     */
    public LinkedList getReseptlisteUink(String ID) { //henter ut reseptliiste med unike verdier
        if (reseptregister.containsKey(ID)) {
            LinkedList<String> liste = new LinkedList<>(); //oppretter en linked liste som heter liste
            for (Resept resept : reseptregister.get(ID)) {
                if (liste.isEmpty()) { //dersom lista er tom
                    liste.add(resept.getMedikament()); //legg til resept dersom lista er tom
                }
                if (!reseptFinnes(resept.getMedikament(), liste)) { //hvis lista ikker er tom, sjekk at resepten ikke finnes i lista, dersom den ikke finnes, legg den til i liste
                    liste.add(resept.getMedikament());
                }
            }
            return liste;
        }
        throw new NoSuchElementException("ID finnes ikke i reseptregister");

    }

    /**
     * Henter resepter fra og med gitt dato til og med gitt dato for person med gitt ID
     *
     * @param ID person sin ID
     * @param fra startdato
     * @param til sluttdato
     * @return listen med resepter innenfor gitt tidsperiode
     */
    public LinkedList<Resept> getLinkedListFraTil(String ID, Date fra, Date til) {
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> alleResepter = reseptregister.get(ID);
            LinkedList<Resept> fraTil = new LinkedList<>();
            for (Resept resept : alleResepter) {

                Date utskrift = resept.getUtskriftsdato();

                String utskriftstring = datoformat.format(utskrift);

                String fraString = datoformat.format(fra);
                String tilString = datoformat.format(til);

                if ((fra.before(utskrift) || fraString.equalsIgnoreCase(utskriftstring)) && (til.after(utskrift) || tilString.equalsIgnoreCase(utskriftstring))) {
                    fraTil.add(resept);
                }
            }
            return fraTil;
        }
        throw new NoSuchElementException("ID finnes ikke i reseptregister");
    }

    /**
     * Henter resepter fra og med gitt dato til og med gitt dato for person med gitt ID og gitt ATC-kode
     * @param ID person sin ID
     * @param fra startdato
     * @param til sluttdato
     * @param ATC ATC-koden som søkes på
     * @return listen med resepter innen gitt dato og ATC-kode for person med gitt ID
     */
    public LinkedList<Resept> getLinkedListFraTilATC(String ID, Date fra, Date til, String ATC) {
        LinkedList<Resept> match = new LinkedList<>();
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> alleResepter = reseptregister.get(ID);
            for (Resept resept : alleResepter) {

                Date utskrift = resept.getUtskriftsdato();

                String utskriftstring = datoformat.format(utskrift);

                String fraString = datoformat.format(fra);
                String tilString = datoformat.format(til);

                if ((fra.before(utskrift) || fraString.equalsIgnoreCase(utskriftstring)) && (til.after(utskrift) || tilString.equalsIgnoreCase(utskriftstring)) && resept.getATC_kode().equalsIgnoreCase(ATC)) {
                    match.add(resept);
                }
            }
            return match;
        }
        return match;
    }

    /**
     * Henter resepter fra og med gitt dato til og med gitt dato for person med gitt ID og gitt preparatnavn
     * @param ID person sin ID
     * @param fra startdato
     * @param til sluttdato
     * @param navn preparatnavnet det søkes på
     * @return listen med resepter innen gitt dato og preparatnavn for person med gitt ID
     */
    public LinkedList<Resept> getLinkedListFraTilNavn(String ID, Date fra, Date til, String navn) {
        LinkedList<Resept> match = new LinkedList<>();
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> alleResepter = reseptregister.get(ID);
            for (Resept resept : alleResepter) {

                Date utskrift = resept.getUtskriftsdato();

                String utskriftstring = datoformat.format(utskrift);

                String fraString = datoformat.format(fra);
                String tilString = datoformat.format(til);

                if ((fra.before(utskrift) || fraString.equalsIgnoreCase(utskriftstring)) && (til.after(utskrift) || tilString.equalsIgnoreCase(utskriftstring)) && resept.getMedikament().equalsIgnoreCase(navn)) {
                    match.add(resept);
                }
            }
            return match;
        }
        return match;
    }

    /**
     * Henter resepter fra og med gitt dato til og med gitt dato for person med gitt ID, gitt preparatnavn
     * og gitt reseptgruppe
     * person med gitt ID, gitt reseptgruppe og gitt reseptnavn
     * @param ID person sin ID
     * @param fra startdato
     * @param til sluttdato
     * @param navn preparatet det søkes på
     * @param gruppe reseptgruppen det søkes på
     * @return liste med resepter innen gitt dato, gitt preparatnavn og gitt resepgruppe for person med gitt ID
     */
    public LinkedList<Resept> getLinkedListFraTilNavnReseptgruppe(String ID, Date fra, Date til, String navn, String gruppe) {
        LinkedList<Resept> match = new LinkedList<>();
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> alleResepter = reseptregister.get(ID);
            for (Resept resept : alleResepter) {

                Date utskrift = resept.getUtskriftsdato();

                String utskriftstring = datoformat.format(utskrift);

                String fraString = datoformat.format(fra);
                String tilString = datoformat.format(til);

                if ((fra.before(utskrift) || fraString.equalsIgnoreCase(utskriftstring)) && (til.after(utskrift) || tilString.equalsIgnoreCase(utskriftstring)) && resept.getMedikament().equalsIgnoreCase(navn) && resept.getReseptrgruppe().startsWith(gruppe)) {
                    match.add(resept);
                }
            }
            return match;
        }
        return match;
    }

    /**
     * Henter resepter fra og med gitt dato til og med gitt dato for person med gitt ID og gitt reseptgruppe
     * @param ID person sin ID
     * @param fra startdato
     * @param til sluttdato 
     * @param gruppe reseptgruppen det søkes på
     * @return liste med resepter innen gitt dato og gitt reseptgruppe for person med gitt ID
     */
    public LinkedList<Resept> getLinkedListFraTilReseptgruppe(String ID, Date fra, Date til, String gruppe) {
        LinkedList<Resept> match = new LinkedList<>();
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> alleResepter = reseptregister.get(ID);
            for (Resept resept : alleResepter) {

                Date utskrift = resept.getUtskriftsdato();

                String utskriftstring = datoformat.format(utskrift);

                String fraString = datoformat.format(fra);
                String tilString = datoformat.format(til);

                if ((fra.before(utskrift) || fraString.equalsIgnoreCase(utskriftstring)) && (til.after(utskrift) || tilString.equalsIgnoreCase(utskriftstring)) && resept.getReseptrgruppe().startsWith(gruppe)) {
                    match.add(resept);
                }
            }
            return match;
        }
        return match;
    }
}//class Reseptmap
