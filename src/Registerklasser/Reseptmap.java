/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Vegardlokreim
 */
public class Reseptmap implements Serializable {

    private TreeMap<String, LinkedList<Resept>> reseptregister;

    private SimpleDateFormat datoformat = new SimpleDateFormat("dd-MM-yyyy");

    public Reseptmap() {
        reseptregister = new TreeMap<>();
    }

    public void leggTilPerson(String ID) {
        if (!ID.matches("[0-9]+")) {
            throw new IllegalArgumentException("Ugyldig ID");
        }

        if (!reseptregister.containsKey(ID)) {
            reseptregister.put(ID, new LinkedList<Resept>());
            System.out.println(ID + " ble lagt til!!");

        }

    }

    public LinkedList<Resept> getReseptliste(String ID) {

        if (reseptregister.containsKey(ID)) {
            return reseptregister.get(ID);
        }
        throw new NoSuchElementException("ID finnes ikke i reseptregister");
    }

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

    public int antallA(String ID) {

        int teller = 0;
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> reseptliste = reseptregister.get(ID);
            Iterator it = reseptliste.iterator();

            while (it.hasNext()) {
                Resept resept = (Resept) it.next();
                if (resept.getReseptrgruppe().startsWith("Reseptgruppe A.")) {
                    teller++;
                }
            }
            return teller;
        }
        return teller;
    }

    public int antallB(String ID) {

        int teller = 0;
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> reseptliste = reseptregister.get(ID);
            Iterator it = reseptliste.iterator();

            while (it.hasNext()) {
                Resept resept = (Resept) it.next();
                if (resept.getReseptrgruppe().startsWith("Reseptgruppe B.")) {
                    teller++;
                }
            }
            return teller;
        }
        return teller;
    }

    public int antallC(String ID) {

        int teller = 0;
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> reseptliste = reseptregister.get(ID);
            Iterator it = reseptliste.iterator();

            while (it.hasNext()) {
                Resept resept = (Resept) it.next();
                if (resept.getReseptrgruppe().startsWith("Reseptgruppe C.")) {
                    teller++;
                }
            }
            return teller;
        }
        return teller;
    }

    public int getAntallTotalt(String ID) {
        if (reseptregister.containsKey(ID)) {
            return reseptregister.get(ID).size();
        }
        return 0;
    }

    /**
     * Ser om det allerede er resept på gitt medikament
     *
     * @param medikament
     * @param liste
     * @return true om det allerede er en resept på gitt medikament, false
     * ellers.
     */
    public boolean reseptFinnes(String medikament, LinkedList liste) { //sjekker om resept finnes for angitt medikament
        if (medikament == null || liste == null) {
            throw new IllegalArgumentException("Ugyldig medikament eller liste");
        }
        Iterator it = liste.iterator(); //oppretter en iterator

        while (it.hasNext()) { //så lenge it.hasNext()
            String navn = (String) it.next(); //navn hentes ut fra lista

            if (navn.equalsIgnoreCase(medikament)) { //sjekker om navn er det samme som medikament
                return true; //returnerer true dersom navn  er det samme som motatt medikament
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
            Iterator it = reseptregister.get(ID).iterator(); //oppretter en iterator for å iterere over reseptliste
            while (it.hasNext()) {//så lenge som at it.hasNext()

                Resept resept = (Resept) it.next(); //resept settes lik it.next

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
     * Henter resepter fra og med gitt dato til og med gitt dato
     *
     * @param ID
     * @param fra
     * @param til
     * @return listen med resepter innenfor gitt tidsperiode
     */
    public LinkedList<Resept> getLinkedListFraTil(String ID, Date fra, Date til) {
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> alleResepter = reseptregister.get(ID);
            LinkedList<Resept> fraTil = new LinkedList<Resept>();

            Iterator it = alleResepter.iterator();

            while (it.hasNext()) {
                Resept resept = (Resept) it.next();

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

    public LinkedList<Resept> getLinkedListFraTilATC(String ID, Date fra, Date til, String ATC) {
        LinkedList<Resept> match = new LinkedList<>();
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> alleResepter = reseptregister.get(ID);

            Iterator it = alleResepter.iterator();

            while (it.hasNext()) {
                Resept resept = (Resept) it.next();

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

    public LinkedList<Resept> getLinkedListFraTilNavn(String ID, Date fra, Date til, String navn) {
        LinkedList<Resept> match = new LinkedList<>();
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> alleResepter = reseptregister.get(ID);

            Iterator it = alleResepter.iterator();

            while (it.hasNext()) {
                Resept resept = (Resept) it.next();

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

    public LinkedList<Resept> getLinkedListFraTilNavnReseptgruppe(String ID, Date fra, Date til, String navn, String gruppe) {
        LinkedList<Resept> match = new LinkedList<>();
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> alleResepter = reseptregister.get(ID);

            Iterator it = alleResepter.iterator();

            while (it.hasNext()) {
                Resept resept = (Resept) it.next();

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

    public LinkedList<Resept> getLinkedListFraTilReseptgruppe(String ID, Date fra, Date til, String gruppe) {
        LinkedList<Resept> match = new LinkedList<>();
        if (reseptregister.containsKey(ID)) {
            LinkedList<Resept> alleResepter = reseptregister.get(ID);

            Iterator it = alleResepter.iterator();

            while (it.hasNext()) {
                Resept resept = (Resept) it.next();

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

}
