package Registerklasser;

import reseptregister.Reseptregister;
import Objekter.Preparat;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;


/* 
 * @author Benjamin Aarstad Nygård
 * 
 */
public class Preparatregister implements Serializable {

    private LinkedList<Preparat> liste;
    private TreeMap<String, String> atcliste;
    private TreeMap<String, String> preparatnavnliste;

    public Preparatregister() {
        liste = Reseptregister.getListe();
        atcliste = new TreeMap<>();
        preparatnavnliste = new TreeMap<>();
        lagATCListe();
        lagPreparatnavnliste();
    }

    public void lagATCListe() {
        Iterator it = liste.iterator();

        while (it.hasNext()) {
            Preparat preparat = (Preparat) it.next();
            String atc = preparat.getATC_kode();
            if (!atcliste.containsKey(atc)) {
                atcliste.put(atc, atc);
            }
        }
    }

    public void lagPreparatnavnliste() {
        Iterator it = liste.iterator();
        while (it.hasNext()) {
            Preparat preparat = (Preparat) it.next();
            String preparatnavn = preparat.getPreparatnavn();

            if (!preparatnavnliste.containsKey(preparatnavn)) {
                preparatnavnliste.put(preparatnavn, preparatnavn);
            }
        }
    }

    public LinkedList<String> getATCListe() {
        return new LinkedList<String>(atcliste.values());
    }

    public LinkedList<String> getPreparatnavnliste() {
        return new LinkedList<String>(preparatnavnliste.values());
    }

    public Preparatregister(LinkedList<Preparat> liste) {
        this.liste = liste;
    }

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

    public int getSize() {
        return liste.size();
    }

}
