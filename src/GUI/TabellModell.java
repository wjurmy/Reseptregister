package GUI;

import Objekter.Lege;
import Objekter.Pasient;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;
import Objekter.Resept;
import Registerklasser.Reseptmap;
import javax.swing.JOptionPane;
import reseptregister.Reseptregister;

/*
 * Forfatter: Vegard Lokreim
 * Oprettet: 08.nov.2013
 *
 */
public abstract class TabellModell<T> extends AbstractTableModel {

    private String[] kolonnenavn;
    private LinkedList<T> data;
    private Reseptmap resepter;

    /**
     * Konstruktør som initialiserer datafelter
     *
     * @param kolonnenavn
     * @param data
     */
    public TabellModell(String[] kolonnenavn, LinkedList<T> data) {
        this.kolonnenavn = kolonnenavn;
        this.data = data;
        resepter = Reseptregister.getReseptliste();
    }//TabellModell ferdig

    /**
     * Henter kolonnenavn kol
     *
     * @param kol
     * @return kolonnenavn[kol]
     */
    public String getColumnName(int kol) {
        return kolonnenavn[kol];
    }//getColumnName ferdig

    /**
     * Teller opp antall rader
     *
     * @return data.size()
     */
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