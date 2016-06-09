/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Objekter.Lege;
import Objekter.Pasient;
import Objekter.Preparat;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;
import Objekter.Resept;
import Registerklasser.Reseptmap;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import reseptregister.Reseptregister;

/**
 *
 * @author Vegrad Lokreim
 */
public class VisPasient extends JPanel {

    private Color blåfarge = Color.decode("#007ec5");
    
    private JPanel topppanel;
    private JPanel søkpanel;
    private JPanel tabellpanel;
    private JPanel radiobuttonpanel;
    //private JPanel bunnpanel;

    private JTextField søkfelt;

    private JButton søk;

    private static JTable resepttabell;
    private static Reseptmodell reseptmodell;

    private static String[] kolonner = {"UTSKREVET", "MEDIKAMENT", "ATC-KODE", "KATEGORI", "RESEPTGRUPPE", "MENGDE", "ANSV.LEGE"};

    private JButton nyResept;

    private JScrollPane tabellScroll;

    private Pasient pasient;
    private ButtonGroup knapper;
    private Lege lege;

    private Reseptmap resepter;

    private JPanel oversiktpanel;
    private JPanel vinduer;

    public static final String NY_RESEPT = "NY RESEPT";
    public static final String OVERSIKT = "OVERSIKT";
    public static final String INFORMASJON = "VIS INFORMASJON";

    private MainFrame parent;
    private LinkedList<Resept> temp; // LinkedList. Alle resepter som matcher et gitt søk legges til i denne listen som senere vises på skjermen, listen endres hver gang brukeren søker på noe

    /**
     * Konstruktør for VisPasient, initialiserer datafelter og lager GUI
     *
     * @param pasient
     * @param lege
     * @param parent
     */
    public VisPasient(Pasient pasient, Lege lege, MainFrame parent) {
        setLayout(new BorderLayout());
        this.lege = lege;
        this.pasient = pasient;
        this.parent = parent;
        resepter = Reseptregister.getReseptliste();

        initialiser();
        konstruerTopp();
        konstruerSøk();
        konstruerTabell();
        //konstruerBunn();
        setRadhøyde(resepttabell);

        konstruerVindu();
        add(vinduer, BorderLayout.CENTER);

    }//VisPasient ferdig

    /**
     * Setter radhøyde for tabellen tabell
     *
     * @param tabell
     */
    private void setRadhøyde(JTable tabell) {
        try {
            tabell.setFont(new Font("Tahoma", Font.PLAIN, 20));
            TableRowSorter<Reseptmodell> sorter = new TableRowSorter<>(reseptmodell);

            resepttabell.setRowSorter(sorter);
            sorter.setSortsOnUpdates(true);

            for (int rad = 0; rad < tabell.getRowCount(); rad++) {
                int radHøyde = tabell.getRowHeight();

                for (int kolonne = 0; kolonne < tabell.getColumnCount(); kolonne++) {
                    Component comp = tabell.prepareRenderer(tabell.getCellRenderer(rad, kolonne), rad, kolonne);
                    radHøyde = 32;
                    //Math.max(rowHeight, comp.getPreferredSize().height);
                }

                tabell.addMouseListener(new Muslytter());

                tabell.setRowHeight(rad, radHøyde);
                tabell.setFillsViewportHeight(true);
                tabell.setShowVerticalLines(true);
                tabell.setGridColor(blåfarge);
                tabell.setBackground(Color.WHITE);
            }
        } catch (ClassCastException e) {
        }
    }//setRadhøyde ferdig

    /**
     * Initialiserer tekstfelter, knapper osv.
     */
    public void initialiser() {
        topppanel = new JPanel(new FlowLayout());
        søkpanel = new JPanel(new FlowLayout());
        tabellpanel = new JPanel(new BorderLayout());
        radiobuttonpanel = new JPanel(new FlowLayout());
        //bunnpanel = new JPanel(new FlowLayout());

        søkfelt = new JTextField(25);
        søkfelt.getDocument().addDocumentListener(new Tekstlytter());
        søkfelt.setFont(new Font("Serif", Font.BOLD, 24));

        søk = lagBildeknapp("Bilder/sok.png", "SØK");

        resepttabell = new JTable();

        resepttabell.setFont(new Font("Serif", Font.PLAIN, 16));

        oversiktpanel = new JPanel();
        oversiktpanel.setLayout(new BoxLayout(oversiktpanel, BoxLayout.Y_AXIS));
        vinduer = new JPanel(new CardLayout());
        vinduer.add(oversiktpanel, OVERSIKT);

        if (pasient == null) {
            JOptionPane.showMessageDialog(null, "INTERN FEIL! Fant ikke pasient: " + pasient.getID());
            return;
        }
        reseptmodell = new Reseptmodell(kolonner, resepter.getReseptliste(pasient.getID()));
        nyResept = lagBildeknapp("Bilder/nyResept.png", "SØK");

        nyResept.addActionListener(new Knapplytter());
        søk.addActionListener(new Knapplytter());

    }//initialiser ferdig

    /**
     * Lager toppanel
     */
    public void konstruerTopp() {

        if (pasient == null) {
            JOptionPane.showMessageDialog(null, "INTERN FEIL! Fant ikke pasient: " + pasient.getID());
        }
        String fastlege = "";
        if (pasient.getFastlege() == null) {
            fastlege = "INGEN";
        } else {
            fastlege = pasient.getFastlege().getNavn();
        }
        String overskrift = "PASIENT: " + pasient.getNavn() + ". PERSONNUMMER: " + pasient.getID() + ". FASTLEGE: " + fastlege;
        JLabel header = new JLabel(overskrift);
        header.setFont(new Font("Serif", Font.BOLD, 24));
        header.setForeground(Color.WHITE);
        topppanel.add(header);
        topppanel.setBackground(blåfarge);

    }//konstruerTopp ferdig

    /**
     * Lager søkpanel
     */
    public void konstruerSøk() {

        søkpanel.add(søkfelt);

        søkpanel.add(nyResept);
        søkpanel.setBackground(blåfarge);
    }//konstruerSøk ferdig

    /**
     * Lager tabell
     */
    public void konstruerTabell() {
        resepttabell.setModel(reseptmodell);
        resepttabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resepttabell.setFillsViewportHeight(true);

        TableRowSorter<Reseptmodell> sorter = new TableRowSorter<>(reseptmodell);

        resepttabell.setRowSorter(sorter);
        sorter.setSortsOnUpdates(true);
        tabellScroll = new JScrollPane(resepttabell);
        tabellpanel.add(tabellScroll);
        tabellpanel.setBackground(blåfarge);
    }//konstruerTabell ferdig

    /**
     * Setter sammen paneler til et vindu
     */
    public void konstruerVindu() {
        oversiktpanel.add(topppanel);

        oversiktpanel.add(søkpanel);
        oversiktpanel.add(tabellpanel);

        //add(bunnpanel);
    }//konstruerVindu ferdig

    /**
     * Lager ny resept
     */
    public void nyResept() {

        new NyResept(pasient, lege, this, parent);

        setRadhøyde(resepttabell);

    }//nyResept ferdig

    /**
     * Oppdaterer resepttabell
     */
    public void oppdater() {
        reseptmodell.fireTableDataChanged();
        visAlle();
    }//oppdater ferdig

    /**
     * Metode for å kunne frisøke i tabeller
     */
    public void frisøk() {
        String søk = søkfelt.getText();


        tabellpanel.remove(tabellScroll);
        revalidate();

        Iterator it = resepter.getReseptliste(pasient.getID()).iterator();
        String lowerSøk = søk.toLowerCase();

        temp = new LinkedList<Resept>();

        while (it.hasNext()) {
            Resept resept = (Resept) it.next();
            Preparat preparat = resept.getPreparat();
            Pasient pasient = resept.getPasient();

            String preparatnavn = preparat.getPreparatnavn().toLowerCase();
            String atc = preparat.getATC_kode().toLowerCase();
            String kategori = preparat.getKategori().toLowerCase();
            String gruppe = preparat.getReseptgruppe().toLowerCase();
            String produsent = preparat.getProdusent().toLowerCase();
            String fornavn = pasient.getFornavn().toLowerCase();
            String mellomnavn = pasient.getMellomnavn().toLowerCase();
            String etternavn = pasient.getEtternavn().toLowerCase();
            String navn = pasient.getNavn().toLowerCase();
            String fornavn_etternavn = fornavn + " " + etternavn;
            fornavn_etternavn = fornavn_etternavn.toLowerCase();
            String mellomnavn_etternavn = mellomnavn + " " + etternavn;
            mellomnavn_etternavn = mellomnavn_etternavn.toLowerCase();

            if (preparatnavn.startsWith(lowerSøk) || atc.startsWith(lowerSøk) || kategori.startsWith(lowerSøk) || gruppe.startsWith(lowerSøk)
                    || produsent.startsWith(lowerSøk) || fornavn.startsWith(lowerSøk) || mellomnavn.startsWith(lowerSøk) || etternavn.startsWith(lowerSøk) || navn.startsWith(lowerSøk)
                    || fornavn_etternavn.startsWith(lowerSøk) || mellomnavn_etternavn.startsWith(lowerSøk)) {
                temp.add(resept);
            }

        }

        reseptmodell = new Reseptmodell(kolonner, temp);

        resepttabell = new JTable(reseptmodell);

        tabellScroll = new JScrollPane(resepttabell);

        tabellpanel.add(tabellScroll, BorderLayout.CENTER);
        setRadhøyde(resepttabell);
        revalidate();
        repaint();

    }//frisøk ferdig

    /**
     * Viser alle i resepttabellen
     */
    public void visAlle() {
        tabellpanel.remove(tabellScroll);
        reseptmodell = new Reseptmodell(kolonner, resepter.getReseptliste(pasient.getID()));
        resepttabell.setModel(reseptmodell);

        tabellScroll = new JScrollPane(resepttabell);
        tabellpanel.add(tabellScroll);
        setRadhøyde(resepttabell);
        revalidate();
        repaint();
    }//visAlle ferdig

    /**
     * Viser vinduet vindu
     *
     * @param vindu
     */
    public void visPanel(String vindu) {
        CardLayout cl = (CardLayout) vinduer.getLayout();
        cl.show(vinduer, vindu);
    }//visPanel ferdig

    /**
     * Lager bildeknapp med parameteret icon som ikon og parameteret rollover
     * som ikon når man holder over ikonet.
     *
     * @param icon
     * @param rollover
     * @return bildeknapp
     */
    private JButton lagBildeknapp(String icon, String rollover) {
        try {
            JButton knapp = new JButton();
            knapp.setContentAreaFilled(false);
            knapp.setBorderPainted(false);
            knapp.setIcon(new ImageIcon(getClass().getResource(icon)));
            knapp.setRolloverIcon(new ImageIcon(getClass().getResource(icon)));

            return knapp;
        }catch(NullPointerException npe){
            return new JButton(icon);
        }
    }//lagBildeknapp ferdig

    /**
     * Viser informasjon om resept
     */
    public void visInformasjon() {
        Resept resept = reseptmodell.getValueAt(resepttabell.convertRowIndexToModel(resepttabell.getSelectedRow()));

        parent.fjernTilbakeknapp();
        parent.leggTilTilbakeaknapp2();

        vinduer.add(new VisResept(resept), INFORMASJON);
        visPanel(INFORMASJON);
    }//visInformasjon ferdig

    /**
     * Tekstlytter som lytter til teksten, implementerer DocumentListener
     */
    private class Tekstlytter implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            frisøk();
        }//insertUpdate ferdig

        @Override
        public void removeUpdate(DocumentEvent e) {
            frisøk();
        }//removeUpdate ferdig

        @Override
        public void changedUpdate(DocumentEvent e) {
            frisøk();
        }//changedUpdate ferdig

    }//Tekstlytter ferdig

    /**
     * Muslytter som lytter på musen, implementerer MouseListener
     */
    private class Muslytter implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                visInformasjon();
            }
        }//mouseClicked ferdig

        @Override
        public void mousePressed(MouseEvent e) {
        }//mousePressed ferdig

        @Override
        public void mouseReleased(MouseEvent e) {
        }//mouseReleased ferdig

        @Override
        public void mouseEntered(MouseEvent e) {
        }//mouseEnteredf erdig

        @Override
        public void mouseExited(MouseEvent e) {
        }//mouseExited ferdig

    }//Muslytter ferdig

    /**
     * Knapplytter som lytter på knappene, implementerer ActionListener
     */
    private class Knapplytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == nyResept) {
                nyResept();
            } else if (e.getSource() == søk) {
                frisøk();
            }

        }//actionPerformed ferdig
    }//Knapplytter ferdig
}//VisPasient ferdig
