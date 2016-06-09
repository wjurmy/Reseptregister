/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Component;
import java.text.RuleBasedCollator;
import java.util.Scanner;
import javax.swing.JOptionPane;
import Objekter.Preparat;
import Registerklasser.Preparatregister;

/**
 *
 * @author Vegard
 */
public class Hjelpemetoder {

    public static final String VALID_OK = "OK";
    private static Preparatregister register = new Preparatregister();

    /**
     * Konverterer navn, fjerner unødvendig mellomrom, setter stor forbokstav
     * osv.
     *
     * @param navn
     * @return ferdig konvertert navn
     */
    public static String navnKonverter(String navn) {
        Scanner ordleser = new Scanner(navn);

        String konvertert = "";

        while (ordleser.hasNext()) {
            String nesteord = ordleser.next().toLowerCase();

            if (konvertert.length() > 0) {
                konvertert += " ";
            }

            konvertert += nesteord.substring(0, 1).toUpperCase();

            if (nesteord.length() > 1) {
                konvertert += nesteord.substring(1);
            }

            // konverterer liten til stor bokstav etter eventuelle bindestreker
            int bindestrekpos = konvertert.indexOf("-");

            while (bindestrekpos != -1) {
                String sub1 = konvertert.substring(0, bindestrekpos + 1);
                String sub2 = konvertert.substring(bindestrekpos + 1);

                konvertert = sub1 + sub2.substring(0, 1).toUpperCase();

                if (sub2.length() > 1) {
                    konvertert += sub2.substring(1);
                }

                bindestrekpos = konvertert.indexOf("-", bindestrekpos + 1);
            }
        }
        ordleser.close();

        return konvertert;
    }//navnKonverter ferdig

    /**
     * Henter preparat navn utifra parameterne
     *
     * @param register
     * @param atc_kode
     * @return Om den ble funnet
     */
    public String getPreparatnavn(Preparatregister register, String atc_kode) {
        return "FUNNET";
    }//getPreparatnavn ferdig

    /**
     * Henter preparat utifra parameteret (kode)
     *
     * @param kode
     * @return preparatet som har kode lik parameter kode
     */
    public static Preparat getPreparat(String kode) {
        return register.hent_preparat(kode);
    }//getPreparat ferdig

    /**
     * Metode for å sjekke om navn kun inneholder bokstaver eller "-" eller
     * mellomrom Dersom navnet starter med - eller mellomrom så returneres false
     *
     * @param navn navn som skal sjekkes
     * @return true/false - returnerer false dersom navnet inneholde tegns som
     * ikke er bokstaver eller "-" eller " "
     */
    public static String gyldigNavn(String navn) {
        char[] bokstaver = navn.toCharArray();

        if (navn.equals("")) {
            return null;
        }

        if (navn.startsWith(" ") || navn.startsWith("-")) {
            return null;
        }

        for (char c : bokstaver) {
            if (!Character.isLetter(c) && c != ' ' && c != '-') {
                return null;
            }
        }

        return navnKonverter(navn);
    }
} // Hjelpemetoder ferdig
