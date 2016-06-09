/**
 * Representerer en resept, inneholder datafeltene utkriftsdato, pasient, lege, til_pasient, av_lege
 * preparat, mengde og anvisning
 */
package Objekter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Sist endret: 12.05.14
 * @author Benjamin Aarstad Nygård
 */
public class Resept implements Serializable {

    private Date utskriftsdato;
    private Pasient pasient;
    private Lege lege;
    private String til_pasient;
    private String av_lege;
    private Preparat preparat;
    private String mengde;
    private String anvisning;

    /**
     * Konstruktør for Resept, initialiserer datafelter
     *
     * @param utskriftsdato - datoen resepten ble utskrevet
     * @param pasient - pasienten som mottar resepten
     * @param lege - legen som skrev ut resepten
     * @param preparat - preparatet resepten er skrevet på
     * @param mengde - mengden av angitt preparat
     * @param anvisning - anvisning fra legen om hvordan preparatet bør brukes
     */
    public Resept(Date utskriftsdato, Pasient pasient, Lege lege, Preparat preparat, String mengde, String anvisning) {
        this.utskriftsdato = utskriftsdato;
        this.pasient = pasient;
        this.lege = lege;
        this.preparat = preparat;
        this.mengde = mengde;
        this.anvisning = anvisning;

        til_pasient = pasient.getNavn() + ", " + pasient.getID();
        av_lege = lege.getNavn() + ", " + lege.getID();

    }

    /**
     * get-metode for preparat
     *
     * @return preparat - preparatet resepten er skrevet ut på
     */
    public Preparat getPreparat() {
        return preparat;
    }

    /**
     * get-metode for pasientinfo
     *
     * @return pasientinfo - informasjon om pasienten som mottar resepten
     */
    public String getPasientinfo() {
        return til_pasient;
    }

    /**
     * get-metode for legeinfo
     *
     * @return legeinfo - informasjon om legen som skrev ut resepten
     */
    public String getLegeinfo() {
        return av_lege;
    }

    /**
     * get-metode for preparatnavnet
     *
     * @return preparat.getPreparatnavn() - preparatnavnet til preparatet som er
     * skrevet ut resept på
     */
    public String getMedikament() {
        return preparat.getPreparatnavn();
    }

    /**
     * get-metode for ATC-kode
     *
     * @return preparat.getATC_kode() - henter ut ATC-koden til preparatet som
     * det er skrevet ut resept på
     */
    public String getATC_kode() {
        return preparat.getATC_kode();
    }

    /**
     * get-metode for reseptgruppe
     *
     * @return preparat.getReseptgruppe() - henter ut reseptgruppen til preparatet som det er skrevet ut resept på
     * 
     */
    private String getReseptgruppe() {
        return preparat.getReseptgruppe();
    }

    /**
     * get-metode for utskriftsdato
     *
     * @return utskriftsdato - henter ut datoen da resepten ble skrevet
     */
    public Date getUtskriftsdato() {
        return utskriftsdato;
    }

    /**
     * get-metode for formatert dato: dd MMMM YYYY HH:mm
     *
     * @return new SimpleDateFormat("dd MMMM YYYY, HH:mm").format(utskriftsdato)
     * - henter ut en stringrepresentant av datoen da resepten ble utskrevet
     */
    public String getDato() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM YYYY, HH:mm");
        return format.format(utskriftsdato);
    }

    /**
     * get-metode for reseptens mottaker (pasient)
     *
     * @return pasient - pasientobjektet som resepten er registrert på
     */
    public Pasient getPasient() {
        return pasient;
    }

    /**
     * get-metode for ansvarlig lege
     *
     * @return lege - legeobjektet som resepten er skrevet ut av
     */
    public Lege getLege() {
        return lege;
    }

    /**
     * get-metode for mengden av preparatet
     *
     * @return mengde - mengden av preparatet som er skrevet resept på
     */
    public String getMengde() {
        return mengde;
    }

    /**
     * get-metode for anvisning
     *
     * @return anvisning - anvisning fra lege om hvordan preparatet bør brukes
     */
    public String getAnvisning() {
        return anvisning;
    }

    /**
     * get-metode for kategori
     *
     * @return kategori - preparatets kategori
     */
    public String getKategori() {
        return preparat.getKategori();
    }

    /**
     * get-metode for reseptgruppe
     * @return reseptgruppe - preparatets reseptgruppe
     */
    public String getReseptrgruppe() {

        return preparat.getReseptgruppe();

    }

}//class Resept
