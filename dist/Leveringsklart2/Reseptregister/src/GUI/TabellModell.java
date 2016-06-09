/**
 * Tabellmodell for tabller. brukes til p hente ut data fra tabellene, vise tabeller og endre tabellr
 */
package GUI;

import Objekter.Lege;
import Objekter.Pasient;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;
import Objekter.Resept;
import Registerklasser.Reseptmap;
import javax.swing.JOptionPane;
import reseptregister.Reseptregister;

/**
 * Sist endret 12.05.14
 * Benjamin Aarstad Nygaard
 *
 */
public abstract class TabellModell<T> extends AbstractTableModel {

    private String[] kolonnenavn;
    private LinkedList<T> data;
    private Reseptmap resepter;

    /**
     * Konstruktør som initialiserer datafelter
     *
     * @param kolonnenavn navnet på kolonnens
     * @param data LinkedList med data som skal legges til i tabellen
     */
    public TabellModell(String[] kolonnenavn, LinkedList<T> data) {
        this.kolonnenavn = kolonnenavn;
        this.data = data;
        resepter = Reseptregister.getReseptliste();
    }//TabellModell ferdig

    /**
     * Henter kolonnenavn kol
     *
     * @param kol indeks til kolonnen som skal hentes
     * @return kolonnenavn[kol] - kolonnenavnet til kolonne[kol]
     */
    @Override
    public String getColumnName(int kol) {
        return kolonnenavn[kol];
    }//getColumnName ferdig

    /**
     * Teller opp antall rader
     *
     * @return data.size() - antall rader i tabellen
     */
    @Override
    public int getRowCount() {
        if(data == null){
            JOptionPane.showMessageDialog(null, "data er null, TabellModell");
        }
        return data.size();
    }//getRowCount

    /**
     * Henter verdien i rad med indeks rad
     *
     * @param rad
     * @return data.get(rad)
     */
    public T getRow(int rad) {
        return data.get(rad);
    }//getRow ferdig

    /**
     * Teller opp antall kolonner
     *
     * @return kolonnenavn.length
     */
    public int getColumnCount() {
        return kolonnenavn.length;
    }//getColumnCount ferdig

    /**
     * Returnerer LinkedList<T> data
     *
     * @return data
     */
    public LinkedList<T> getData() {
        return data;
    }//getData ferdig

    @Override
    public abstract Object getValueAt(int rad, int kol);

    /**
     * Setter inn et objekt i en rad i tabellen og oppdaterer modellen.
     *
     * @param obj Objektetsom skal settes inn.
     */
    public void addRow(T obj) {
        data.add(obj);
        this.fireTableDataChanged();
    }//addRow ferdig

    /**
     * Fjerner en rad fra tabellen og oppdaterer modellen.
     *
     * @param rad Indeksen til den raden som skal fjernes.
     */
    public void delRow(int rad) {
        data.remove(rad);
        this.fireTableDataChanged();
    }//delRow ferdig

    /**
     * Sletter all data og oppdaterer modellen.
     */
    public void delTabledata() {
        data.clear();
        this.fireTableDataChanged();
    }//delTabledata ferdig

    /**
     * Sletter all data, setter inn ny og oppdaterer modellen.
     *
     * @param d Den nye listen med data.
     */
    public void setTabledata(LinkedList<T> d) {
        data.clear();
        data.addAll(d);
        this.fireTableDataChanged();
    }//setTabledata ferdig

    public Reseptmap getResepter() {
        return resepter;
    }
}//TabellModell ferdig

class Reseptmodell extends TabellModell<Resept> {

    public static final int DATO = 0;
    public static final int MEDIKAMENT = 1;
    public static final int ATC_KODE = 2;
    public static final int TYPE = 3;
    public static final int RESEPTGRUPPE = 4;
    public static final int UTSKREVET_MENGDE = 5;
    public static final int ANSVARLIG_LEGE = 6;

    /**
     * Konstruktør for Reseptmodell, initialiserer datafelter
     *
     * @param kolonnenavn
     * @param data
     */
    public Reseptmodell(String[] kolonnenavn, LinkedList<Resept> data) {
        super(kolonnenavn, data);
    }//Reseptmodell ferdig

    /**
     * Henter data ved raden rad og kolonne kol
     *
     * @param rad
     * @param kol
     * @return resept.
     * utskriftsdato/medikament/ATCkode/kategori/reseptgruppe/mengde/lege
     */
    public Object getValueAt(int rad, int kol) {
        Resept resept = (Resept) super.getData().get(rad);

        switch (kol) {
            case DATO:
                return resept.getUtskriftsdato();
            case MEDIKAMENT:
                return resept.getMedikament();
            case ATC_KODE:
                return resept.getATC_kode();
            case TYPE:
                return resept.getKategori();
            case RESEPTGRUPPE:
                return resept.getReseptrgruppe();
            case UTSKREVET_MENGDE:
                return resept.getMengde();
            case ANSVARLIG_LEGE:
                return resept.getLege();
            default:
                return null;
        }

    }//getValueAt ferdig

    /**
     * Henter data ved raden rad
     *
     * @param rad
     * @return data
     */
    public Resept getValueAt(int rad) {
        return (Resept) super.getData().get(rad);

    }//getValueAt ferdig

}//Reseptmodell ferdig

class Pasientmodell extends TabellModell<Pasient> {

    public static final int PNR = 0;
    public static final int NAVN = 1;
    public static final int ANTALL_A = 2;
    public static final int ANTALL_B = 3;
    public static final int ANTALL_C = 4;
    public static final int TOTALT = 5;

    /**
     * Konstruktør for Pasientmodell, initialiserer datafelter
     *
     * @param kolonnenavn
     * @param data
     */
    public Pasientmodell(String[] kolonnenavn, LinkedList<Pasient> data) {
        super(kolonnenavn, data);
    }//Pasientmodell ferdig

    /**
     * Henter data ved raden rad og kolonne kol
     *
     * @param rad
     * @param kol
     * @return data
     */
    public Object getValueAt(int rad, int kol) {
        Pasient pasient = (Pasient) super.getData().get(rad);

        switch (kol) {
            case PNR:
                return pasient.getID();
            case NAVN:
                return pasient.getNavn();
            case ANTALL_A:
                return super.getResepter().antallA(pasient.getID());
            case ANTALL_B:
                return super.getResepter().antallB(pasient.getID());
            case ANTALL_C:
                return super.getResepter().antallC(pasient.getID());
            case TOTALT:
                return super.getResepter().getAntallTotalt(pasient.getID());
            default:
                return null;
        }

    }//getValueAt ferdig

    /**
     * Henter data ved raden rad
     *
     * @param rad
     * @return data
     */
    public Pasient getValueAt(int rad) {
        return (Pasient) super.getData().get(rad);

    }//getValueAt ferdig

}//Pasientmodell ferdig

class Legemodell extends TabellModell<Lege> {

    public static final int LEGENUMMER = 0;
    public static final int NAVN = 1;
    public static final int UTSK_A = 2;
    public static final int UTSK_B = 3;
    public static final int UTSK_C = 4;
    public static final int TOTALT = 5;
    public static final int BEVA = 6;
    public static final int BEVB = 7;
    public static final int BEVC = 8;
    public static final int ARBSTED = 9;
    public static final int STATUS = 10;

    /**
     * Konstruktør for Legemodell, initialiserer datafelter
     *
     * @param kolonnenavn
     * @param data
     */
    public Legemodell(String[] kolonnenavn, LinkedList<Lege> data) {
        super(kolonnenavn, data);
    }//Legemodell ferdig

    /**
     * Henter data ved raden rad og kolonne kol
     *
     * @param rad
     * @param kol
     * @return data
     */
    public Object getValueAt(int rad, int kol) {
        Lege lege = (Lege) super.getData().get(rad);

        switch (kol) {
            case LEGENUMMER:
                return lege.getID();
            case NAVN:
                return lege.getNavn();
            case UTSK_A:
                return super.getResepter().antallA(lege.getID());
            case UTSK_B:
                return super.getResepter().antallB(lege.getID());
            case UTSK_C:
                return super.getResepter().antallC(lege.getID());
            case TOTALT:
                return super.getResepter().getAntallTotalt(lege.getID());
            case BEVA:
                return lege.isBevA();
            case BEVB:
                return lege.isBevB();
            case BEVC:
                return lege.isBevC();
            case ARBSTED:
                return lege.getArbeidssted();
            case STATUS: 
            if(lege.isFinnes()){
                return "AKTIV";
            }else{
                return "INAKTIV";
            }
            default:
                return null;
        }

    }//getValueAt ferdig

    /**
     * Henter data ved raden rad
     *
     * @param rad
     * @return data
     */
    public Lege getValueAt(int rad) {
        return (Lege) super.getData().get(rad);
    }//getValueAt ferdig

}//Legemodell ferdig

class AdminPasientmodell extends TabellModell<Pasient> {

    public static final int PNR = 0;
    public static final int NAVN = 1;
    public static final int UTSK_A = 2;
    public static final int UTSK_B = 3;
    public static final int UTSK_C = 4;
    public static final int TOTALT = 5;
    public static final int FASTLEGE = 6;

    /**
     * Konstruktør for AdminPasientmodell, initialiserer datafelter
     *
     * @param kolonnenavn
     * @param data
     */
    public AdminPasientmodell(String[] kolonnenavn, LinkedList<Pasient> data) {
        super(kolonnenavn, data);
    }

    /**
     * Henter data ved raden rad og kolonne kol
     *
     * @param rad
     * @param kol
     * @return data
     */
    public Object getValueAt(int rad, int kol) {
        Pasient pasient = (Pasient) super.getData().get(rad);
        Lege lege = pasient.getFastlege();
        String navn = "";
        if (lege != null) {
            navn = lege.getNavn();
        }

        switch (kol) {
            case PNR:
                return pasient.getID();
            case NAVN:
                return pasient.getNavn();
            case UTSK_A:
                return super.getResepter().antallA(pasient.getID());
            case UTSK_B:
                return super.getResepter().antallB(pasient.getID());
            case UTSK_C:
                return super.getResepter().antallC(pasient.getID());
            case TOTALT:
                return super.getResepter().getAntallTotalt(pasient.getID());
            case FASTLEGE:
                return navn;
            default:
                return null;
        }

    }//getValueAt ferdig

    /**
     * Henter data ved raden rad
     *
     * @param rad
     * @return data
     */
    public Pasient getValueAt(int rad) {
        return (Pasient) super.getData().get(rad);
    }//getValueAt ferdig

}//AdminPasientmodell

class AdminLegemodell extends TabellModell<Lege> {

    public static final int LEGENUMMER = 0;
    public static final int NAVN = 1;
    public static final int ARBSTED = 2;
    public static final int UTSK_A = 3;
    public static final int UTSK_B = 4;
    public static final int UTSK_C = 5;
    public static final int TOTALT = 6;
    public static final int PASIENTER = 7;
    public static final int BEVA = 8;
    public static final int BEVB = 9;
    public static final int BEVC = 10;
    public static final int STATUS = 11;

    /**
     * Konstruktør for AdminLegemodell, initialiserer datafelter
     *
     * @param kolonnenavn
     * @param data
     */
    public AdminLegemodell(String[] kolonnenavn, LinkedList<Lege> data) {
        super(kolonnenavn, data);
    }//AdminLegemodell

    /**
     * Henter data ved raden rad og kolonne kol
     *
     * @param rad
     * @param kol
     * @return data
     */
    public Object getValueAt(int rad, int kol) {
        Lege lege = (Lege) super.getData().get(rad);

        switch (kol) {
            case LEGENUMMER:
                return lege.getID();
            case NAVN:
                return lege.getNavn();
            case ARBSTED:
                return lege.getArbeidssted();
            case UTSK_A:
                return super.getResepter().antallA(lege.getID());
            case UTSK_B:
                return super.getResepter().antallB(lege.getID());
            case UTSK_C:
                return super.getResepter().antallC(lege.getID());
            case TOTALT:
                return super.getResepter().getAntallTotalt(lege.getID());
            case PASIENTER:
                return lege.getPasientliste().size();
            case BEVA:
                return lege.isBevA();
            case BEVB:
                return lege.isBevB();
            case BEVC:
                return lege.isBevC();
            case STATUS:
                if (lege.isFinnes()) {
                    return "AKTIV";
                } else {
                    return "INAKTIV";
                }
            default:
                return null;
        }

    }//getValueAt ferdig

    /**
     * Henter data ved raden rad
     *
     * @param rad
     * @return data
     */
    public Lege getValueAt(int rad) {
        return (Lege) super.getData().get(rad);
    }//getValueAt ferdig

}//AdminLegemodell ferdig

class AdminLegemodellForPasient extends TabellModell<Lege> {

    public static final int LEGENUMMER = 0;
    public static final int NAVN = 1;
    public static final int ARBSTED = 2;

    public static final int BEVA = 3;
    public static final int BEVB = 4;
    public static final int BEVC = 5;

    /**
     * Konstruktør for AdminLegemodellForPasient, initialiserer datafelter
     *
     * @param kolonnenavn
     * @param data
     */
    public AdminLegemodellForPasient(String[] kolonnenavn, LinkedList<Lege> data) {
        super(kolonnenavn, data);
    }//AdminLegemodellForPasient ferdig

    /**
     * Henter data ved raden rad og kolonne kol
     *
     * @param rad
     * @param kol
     * @return data
     */
    public Object getValueAt(int rad, int kol) {
        Lege lege = (Lege) super.getData().get(rad);

        switch (kol) {
            case LEGENUMMER:
                return lege.getID();
            case NAVN:
                return lege.getNavn();
            case ARBSTED:
                return lege.getArbeidssted();
            case BEVA:
                return lege.isBevA();
            case BEVB:
                return lege.isBevB();
            case BEVC:
                return lege.isBevC();
            default:
                return null;
        }

    }//getValueAt ferdig

    /**
     * Henter data ved raden rad
     *
     * @param rad
     * @return data
     */
    public Lege getValueAt(int rad) {
        return (Lege) super.getData().get(rad);
    }//getValueAt ferdig

}//AdminLegemodellForPasient ferdig

class VisLegeReseptmodellADMIN extends TabellModell<Resept> {

    public static final int DATO = 0;
    public static final int MEDIKAMENT = 1;
    public static final int ATC_KODE = 2;
    public static final int TYPE = 3;
    public static final int RESEPTGRUPPE = 4;
    public static final int UTSKREVET_MENGDE = 5;
    public static final int PASIENT = 6;

    /**
     * Konstruktør for VisLegeReseptmodellADMIN, initialiserer datafelter
     *
     * @param kolonnenavn
     * @param data
     */
    public VisLegeReseptmodellADMIN(String[] kolonnenavn, LinkedList<Resept> data) {
        super(kolonnenavn, data);
    }//VisLegeReseptmodellADMIN ferdig

    /**
     * Henter data ved raden rad og kolonne kol
     *
     * @param rad
     * @param kol
     * @return data
     */
    public Object getValueAt(int rad, int kol) {
        Resept resept = (Resept) super.getData().get(rad);

        switch (kol) {
            case DATO:
                return resept.getUtskriftsdato();
            case MEDIKAMENT:
                return resept.getMedikament();
            case ATC_KODE:
                return resept.getATC_kode();
            case TYPE:
                return resept.getKategori();
            case RESEPTGRUPPE:
                return resept.getReseptrgruppe();
            case UTSKREVET_MENGDE:
                return resept.getMengde();
            case PASIENT:
                return resept.getPasient().getNavn() + " " + resept.getPasient().getID();
            default:
                return null;
        }

    }//getValueAt ferdig

    /**
     * Henter data ved raden rad
     *
     * @param rad
     * @return data
     */
    public Resept getValueAt(int rad) {
        return (Resept) super.getData().get(rad);

    }//getValueAt ferdig

}//VisLegeReseptmodellADMIN ferdig

class Lege_resept_statistikk extends TabellModell<Lege> {

    public static final int NAVN = 0;
    public static final int LEGENUMMER = 1;

    /**
     * Konstruktør for Lege_resept_statistikk, initialiserer datafelter
     *
     * @param kolonner
     * @param data
     */
    public Lege_resept_statistikk(String[] kolonner, LinkedList<Lege> data) {
        super(kolonner, data);
    }//Lege_resept_statistikk ferdig

    @Override
    public Object getValueAt(int rad, int kol) {
        Lege lege = (Lege) super.getData().get(rad);
        switch (kol) {
            case NAVN:
                return lege.getNavn();
            case LEGENUMMER:
                return lege.getID();
            default:
                return null;
        }
    }//getValueAt ferdig
}//Lege_resept_statistikk ferdig

class AdminStatistikkPasientmodell extends TabellModell<Pasient> {

    public static final int PNR = 0;
    public static final int NAVN = 1;
    public static final int FASTLEGE = 2;

    /**
     * Konstruktør for AdminStatistikkPasientmodell, initialiserer datafelter
     *
     * @param kolonnenavn
     * @param data
     */
    public AdminStatistikkPasientmodell(String[] kolonnenavn, LinkedList<Pasient> data) {
        super(kolonnenavn, data);
    }//AdminStatistikkPasientmodell ferdig

    /**
     * Henter data ved raden rad og kolonne kol
     *
     * @param rad
     * @param kol
     * @return data
     */
    public Object getValueAt(int rad, int kol) {
        Pasient pasient = (Pasient) super.getData().get(rad);
        Lege lege = pasient.getFastlege();
        String navn;
        if (lege != null) {
            navn = lege.getNavn();
        } else {
            navn = "HAR IKKE!";
        }

        switch (kol) {
            case PNR:
                return pasient.getID();
            case NAVN:
                return pasient.getNavn();
            case FASTLEGE:
                return navn;
            default:
                return null;
        }

    }//getValueAt ferdig

    /**
     * Henter data ved raden rad
     *
     * @param rad
     * @return data
     */
    public Pasient getValueAt(int rad) {
        return (Pasient) super.getData().get(rad);
    }//getValueAt ferdig

}//AdminStatistikkPasientmodell ferdig

class AdminStatistikkLegemodell extends TabellModell<Lege> {

    public static final int LEGENUMMER = 0;
    public static final int NAVN = 1;
    public static final int ARBSTED = 2;
    public static final int PASIENTER = 3;

    /**
     * Konstruktør for AdminStatistikkLegemodell, initialiserer datafelter
     *
     * @param kolonnenavn
     * @param data
     */
    public AdminStatistikkLegemodell(String[] kolonnenavn, LinkedList<Lege> data) {
        super(kolonnenavn, data);
    }//AdminStatistikkLegemodell ferdig

    /**
     * Henter data ved raden rad og kolonne kol
     *
     * @param rad
     * @param kol
     * @return data
     */
    public Object getValueAt(int rad, int kol) {
        Lege lege = (Lege) super.getData().get(rad);

        switch (kol) {
            case LEGENUMMER:
                return lege.getID();
            case NAVN:
                return lege.getNavn();
            case ARBSTED:
                return lege.getArbeidssted();
            case PASIENTER:
                return lege.getPasientliste().size();
            default:
                return null;
        }

    }//getValueAt ferdig

    /**
     * Henter data ved raden rad
     *
     * @param rad
     * @return data
     */
    public Lege getValueAt(int rad) {
        return (Lege) super.getData().get(rad);
    }//getValueAt ferdig

}//AdminStatistikkLegemodell ferdig

class StringModell extends TabellModell<String> {

    public StringModell(String[] kolonnenavn, LinkedList<String> data) {
        super(kolonnenavn, data);
    }

    @Override
    public String getValueAt(int rad, int kol) {
        return super.getData().get(rad);
    }

    public String getValueAt(int rad) {
        return super.getData().get(rad);
    }

}
