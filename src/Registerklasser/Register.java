/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Benjamin Aarstad Nygård
 */
public class Register implements Serializable {

    private TreeMap<String, Pasient> pasientliste;
    private TreeMap<String, Lege> legeliste;
    private TreeMap<String, Pasient> fjernedePasienter;
    private TreeMap<String, Lege> fjernedeLeger;

    // Konstruktør som oppretter listene
    public Register() {
        pasientliste = new TreeMap<>();
        legeliste = new TreeMap<>();

        fjernedePasienter = new TreeMap<>();
        fjernedeLeger = new TreeMap<>();

    }

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

    public LinkedList<Lege> getLegeliste() {

        return new LinkedList<Lege>(legeliste.values());
    }

    public LinkedList<Pasient> getPasientliste() {
        return new LinkedList<Pasient>(pasientliste.values());
    }

    public LinkedList<Pasient> getFjernedePasienter() {
        return new LinkedList<Pasient>(fjernedePasienter.values());
    }

    public LinkedList<Lege> getFjernedeLeger() {
        return new LinkedList<Lege>(fjernedeLeger.values());
    }
    
    public LinkedList<Lege> getAlleLeger(){
        LinkedList<Lege> aktive = new LinkedList<>(legeliste.values());
        LinkedList<Lege> fjernede = new LinkedList<>(fjernedeLeger.values());
        LinkedList<Lege> alleLeger = new LinkedList<>();
        
        
        alleLeger.addAll(aktive);
        alleLeger.addAll(fjernede);
        
        return alleLeger;
    }

    public String genererLegenummer() {
        long i = 1;

        while (legeliste.containsKey(String.valueOf(i)) || fjernedeLeger.containsKey(String.valueOf(i))) {
            i++;
        }
        return String.valueOf(i);
    }

}
