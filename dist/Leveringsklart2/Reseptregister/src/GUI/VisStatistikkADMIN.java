/**
 * Statistikkvindu som viser statistikk etter brukerens ønske
 */
package GUI;

import Objekter.Lege;
import Objekter.Pasient;
import Objekter.Preparat;
import Registerklasser.Preparatregister;
import Registerklasser.Register;
import Registerklasser.Reseptmap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import reseptregister.Reseptregister;

/**
 * sist endret: 12.05.14
 * @author Vegard Lokreim, Benjamin Aarstad Nygård, Walid Jurmy
 */
public class VisStatistikkADMIN extends JPanel {

    private Color blåfarge = Color.decode("#007ec5");
    private JPanel kombopanel;
    private JPanel datopanel;
    private JPanel valgpanel; // inneholder kombopanel og datopanel
    private JPanel hovedpanel;
    private JLabel overskrift;
    private JPanel overskriftpanel;
    private JPanel lege_pasientpanel;
    private JPanel valg2panel;
    private JTextField søk1; //brukes til å søke i lege/pasientlisten
    private JTextField søk2; //brues til å søke i atc og reseptlisten
    private JPanel tabellpanel;
    private JPanel knappanel;
    private JSplitPane statistikksplitt; //inneholder tabell og generator!
    private JSplitPane tabellsplitt; // brukes dersom to tabeller skal vises samtidig
    private JTable persontabell;
    private JTable stringtabell;
    private final String[] LEGEKOLONNER = {"ID", "Nanv", "Arbeidssted", "Faste Pasienter"};
    private final String[] PASIENTKOLONNER = {"ID", "NAVN", "FASTLEGE"};
    private final String[] ATCKOLONNER = {"ATC-KODE"};
    private final String[] PREPARATKOLONNER = {"PREPARATNAVN"};
    private final String[] RESEPTGRUPPEKOLONNER = {"RESEPTGRUPPE"};
    private final LinkedList<String> reseptgrupper = new LinkedList<>();
    //templister brukes til å hente ut informasjon fra tabell etter søk
    private LinkedList<Lege> legetemp;
    private LinkedList<Pasient> pasienttemp;
    private LinkedList<String> stringtemp;
    private AdminStatistikkLegemodell legemodell;
    private AdminStatistikkPasientmodell pasientmodell;
    private StringModell stringmodell;
    private JScrollPane personscroll;
    private JScrollPane stringscroll;
    private JScrollPane statistikkscroll;
    private JComboBox<String> valg1;
    private JComboBox<String> valg2;
    private final String[] valg1Strings = {"Alle leger", "Alle pasienter", "Lege", "Pasient"};
    private final String[] valg2Strings = {"Alle resepter", "ATC-KODE", "Preparat", "Reseptgruppe"};
    private final int ALLE_LEGER = 0;
    private final int ALLE_PASIENTER = 1;
    private final int LEGE = 2;
    private final int PASIENT = 3;
    private final int ALLE_RESEPTER = 0;
    private final int ATC_KODE = 1;
    private final int PREPARAT = 2;
    private final int RESEPTGRUPPE = 3;
    private JTextField fraDato;
    private JTextField tilDato;
    private String tekst;
    private String tekst1;
    private JButton visStatistikk;
    private JButton skrivUt;
    private Register register;
    private Reseptmap resepter;
    private SimpleDateFormat datoformat = new SimpleDateFormat("dd-MM-yyyy");
    private Statistikkgenerator generator;
    private Preparatregister preparatregister;
    private boolean legeIsAktiv;
    private boolean pasientIsAktiv;
    private boolean atcIsAktiv;
    private boolean preparatIsAktiv;
    private boolean reseptgruppeIsAktiv;

    /**
     * Konstruktør som initialiserer datafelter og lager GUI
     * @param register instans av klassen Register
     */
    public VisStatistikkADMIN(Register register) {
        super(new BorderLayout());
        this.register = register;
        preparatregister = Reseptregister.getPreparatregister();
        resepter = Reseptregister.getReseptliste();
        initialiser();
        lagValgpanel();

        hovedpanel.add(valgpanel, BorderLayout.PAGE_START);

        setBackground(blåfarge);

        overskriftpanel.add(overskrift);

        add(overskriftpanel, BorderLayout.PAGE_START);
        add(hovedpanel, BorderLayout.CENTER);

    }

    /**
     * initialiserer alt av paneler, kombobokser, søkfelter, tabeller, tabellmodeller, labeler, knapper osv.
     */
    public void initialiser() {

        hovedpanel = new JPanel(new BorderLayout());
        overskriftpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        overskriftpanel.setBackground(blåfarge);
        overskrift = new JLabel("Statistikk");
        overskrift.setFont(new Font("Tahoma", Font.BOLD, 28));
        overskrift.setForeground(Color.WHITE);

        reseptgrupper.add("Reseptgruppe A.");
        reseptgrupper.add("Reseptgruppe B.");
        reseptgrupper.add("Reseptgruppe C.");

        kombopanel = new JPanel(new GridLayout(1, 2));
        datopanel = new JPanel(new GridLayout(1, 2));
        valgpanel = new JPanel(new GridLayout(1, 2));

        tabellpanel = new JPanel(new BorderLayout());
        tabellpanel.setBackground(Color.WHITE);
        knappanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        knappanel.setBackground(blåfarge);

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        tabellsplitt = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        statistikkscroll = new JScrollPane();
        valg1 = new JComboBox<>(valg1Strings);
        valg2 = new JComboBox<>(valg2Strings);

        valg1.setFont(new Font("Serif", Font.BOLD, 24));
        valg2.setFont(new Font("Serif", Font.BOLD, 24));

        søk1 = new JTextField(10);
        søk2 = new JTextField(10);

        søk1.getDocument().addDocumentListener(new Tekstlytter());
        søk2.getDocument().addDocumentListener(new Tekstlytter());

        valg1.addActionListener(new Lytter());
        valg2.addActionListener(new Lytter());

        fraDato = new JTextField(10);
        tilDato = new JTextField(10);

        fraDato.setFont(new Font("Serif", Font.BOLD, 24));
        tilDato.setFont(new Font("Serif", Font.BOLD, 24));

        TextPrompt fraprompt = new TextPrompt("Startdato: dd-mm-åååå", fraDato);
        fraprompt.setHorizontalAlignment(JTextField.CENTER);
        fraDato.setHorizontalAlignment(JTextField.CENTER);
        fraDato.setBorder(null);

        fraprompt.changeAlpha(0.5f);
        fraprompt.setFont(new Font("Serif", Font.BOLD, 24));

        TextPrompt tilprompt = new TextPrompt("Sluttdato: dd-mm-åååå", tilDato);
        tilprompt.setHorizontalAlignment(JTextField.CENTER);
        tilDato.setHorizontalAlignment(JTextField.CENTER);
        tilDato.setBorder(null);

        tilprompt.changeAlpha(0.5f);
        tilprompt.setFont(new Font("Serif", Font.BOLD, 24));

        visStatistikk = lagBildeknapp("Bilder/vis_statistikk.png");
        skrivUt = lagBildeknapp("Bilder/print.png");

        skrivUt.addActionListener(new Lytter());
        visStatistikk.addActionListener(new Lytter());

        knappanel.add(visStatistikk);
        knappanel.add(skrivUt);

    }

    /**
     * Lager panelet som skal ligger øverst og inneholde dropdown-menyer og tekstfelt
     * hvor man kan velge kriterier 
     */
    public void lagValgpanel() {
        kombopanel.add(valg1);
        kombopanel.add(valg2);
        datopanel.add(fraDato);
        datopanel.add(tilDato);
        valgpanel.add(kombopanel);
        valgpanel.add(datopanel);

        kombopanel.setBackground(blåfarge);
        datopanel.setBackground(blåfarge);
        valgpanel.setBackground(blåfarge);

        hovedpanel.add(knappanel, BorderLayout.PAGE_END);

    }

    /**
     * Frisøk etter leger
     */
    public void frisøkLege() {

        if (!atcIsAktiv && !preparatIsAktiv && !reseptgruppeIsAktiv) {
            String søk = søk1.getText();

            if (søk.length() == 0) {
                visAlleLeger();
                return;
            }

            try {
                if (søk.substring(0, søk.length() - 1).matches("[0-9]+") && (søk.substring(søk.length() - 1).equalsIgnoreCase(".") || søk.substring(søk.length() - 1).equalsIgnoreCase(","))) {
                    søk = søk.substring(0, søk.length() - 1);

                    Lege lege = (Lege) register.getPerson(søk);
                    System.out.println(lege.getNavn());

                    tabellpanel.remove(personscroll);

                    tabellpanel.revalidate();

                    if (søk.length() == 0) {
                        visAlleLeger();
                        return;
                    }

                    LinkedList<Lege> søkliste = new LinkedList<>();
                    søkliste.add(lege);

                    if (søkliste.isEmpty()) {
                        søk1.setBackground(Color.decode("#fd6d6d"));
                    }

                    legemodell = new AdminStatistikkLegemodell(LEGEKOLONNER, søkliste);

                    persontabell = new JTable(legemodell);

                    persontabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    persontabell.setFillsViewportHeight(true);

                    personscroll = new JScrollPane(persontabell);

                    tabellpanel.add(personscroll, BorderLayout.CENTER);

                    tabellpanel.revalidate();
                    tabellpanel.repaint();

                    return;

                }
            } catch (IndexOutOfBoundsException exception) {
            }

            søk1.setBackground(Color.WHITE);

            tabellpanel.remove(personscroll);
            tabellpanel.revalidate();

            if (søk.length() == 0) {
                visAlleLeger();
                return;
            }

            LinkedList<Lege> legeliste = (LinkedList<Lege>) register.getLegeliste();

            LinkedList<Lege> søkliste = new LinkedList<>();
            for (Lege lege : legeliste) {

                String fornavn_etternavn = lege.getFornavn() + "" + lege.getEtternavn();
                String mellomnavn_etternavn = lege.getMellomnavn() + "" + lege.getEtternavn();

                if (lege.getArbeidssted() != null) {
                    if (lege.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getArbeidssted().toUpperCase().startsWith(søk.toUpperCase())
                            || String.valueOf(lege.getID()).startsWith(søk)
                            || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                            || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                        søkliste.add(lege);
                    }
                } else {
                    if (lege.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                            || String.valueOf(lege.getID()).startsWith(søk)
                            || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                            || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                        søkliste.add(lege);
                    }
                }
            }

            if (søkliste.isEmpty()) {
                søk1.setBackground(Color.decode("#fd6d6d"));
            }

            legetemp = søkliste;

            legemodell = new AdminStatistikkLegemodell(LEGEKOLONNER, søkliste);

            persontabell = new JTable(legemodell);

            persontabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            persontabell.setFillsViewportHeight(true);

            personscroll = new JScrollPane(persontabell);

            tabellpanel.add(personscroll, BorderLayout.CENTER);
            tabellpanel.revalidate();
            tabellpanel.repaint();

        } else {
            String søk = søk1.getText();

            if (søk.length() == 0) {
                //visAlle();

                return;

            }

            try {
                if (søk.substring(0, søk.length() - 1).matches("[0-9]+") && (søk.substring(søk.length() - 1).equalsIgnoreCase(".") || søk.substring(søk.length() - 1).equalsIgnoreCase(","))) {
                    søk = søk.substring(0, søk.length() - 1);

                    Lege lege = (Lege) register.getPerson(søk);
                    System.out.println(lege.getNavn());

                    lege_pasientpanel.remove(personscroll);
                    lege_pasientpanel.revalidate();

                    LinkedList<Lege> søkliste = new LinkedList<>();
                    søkliste.add(lege);

                    if (søkliste.isEmpty()) {
                        søk1.setBackground(Color.decode("#fd6d6d"));
                    }

                    legemodell = new AdminStatistikkLegemodell(LEGEKOLONNER, søkliste);

                    persontabell = new JTable(legemodell);

                    persontabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    persontabell.setFillsViewportHeight(true);

                    personscroll = new JScrollPane(persontabell);

                    tabellpanel.add(personscroll);

                    tabellpanel.revalidate();
                    tabellpanel.repaint();

                    return;

                }
            } catch (IndexOutOfBoundsException exception) {
            }

            søk1.setBackground(Color.WHITE);

            lege_pasientpanel.remove(personscroll);
            lege_pasientpanel.revalidate();

            LinkedList<Lege> legeliste = (LinkedList<Lege>) register.getLegeliste();

            LinkedList<Lege> søkliste = new LinkedList<>();
            for (Lege lege : legeliste) {

                String fornavn_etternavn = lege.getFornavn() + "" + lege.getEtternavn();
                String mellomnavn_etternavn = lege.getMellomnavn() + "" + lege.getEtternavn();

                if (lege.getArbeidssted() != null) {
                    if (lege.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getArbeidssted().toUpperCase().startsWith(søk.toUpperCase())
                            || String.valueOf(lege.getID()).startsWith(søk)
                            || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                            || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                        søkliste.add(lege);
                    }
                } else {
                    if (lege.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                            || lege.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                            || String.valueOf(lege.getID()).startsWith(søk)
                            || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                            || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                        søkliste.add(lege);
                    }
                }
            }

            if (søkliste.isEmpty()) {
                søk1.setBackground(Color.decode("#fd6d6d"));
            }

            legemodell = new AdminStatistikkLegemodell(LEGEKOLONNER, søkliste);

            persontabell = new JTable(legemodell);

            persontabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            persontabell.setFillsViewportHeight(true);

            personscroll = new JScrollPane(persontabell);

            lege_pasientpanel.add(personscroll, BorderLayout.CENTER);
            lege_pasientpanel.revalidate();
            lege_pasientpanel.repaint();

        }

    }// frisøkLege ferdig
    
    /**
     * Frisøk etter pasienter
     */
    public void frisøkPasient() {

        if (!atcIsAktiv && !preparatIsAktiv && !reseptgruppeIsAktiv) {
            String søk = søk1.getText();

            if (søk.length() == 0) {
                visAllePasienter();

                return;

            }

            søk1.setBackground(Color.WHITE);

            tabellpanel.remove(personscroll);
            tabellpanel.revalidate();

            LinkedList<Pasient> pasientliste = (LinkedList<Pasient>) register.getPasientliste();

            LinkedList<Pasient> søkliste = new LinkedList<>();
            for (Pasient pasient : pasientliste) {

                String fornavn_etternavn = pasient.getFornavn() + "" + pasient.getEtternavn();
                String mellomnavn_etternavn = pasient.getMellomnavn() + "" + pasient.getEtternavn();

                if (pasient.getFastlege() != null) {
                    if (pasient.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getFastlege().getNavn().toUpperCase().startsWith(søk.toUpperCase())
                            || String.valueOf(pasient.getID()).startsWith(søk)
                            || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                            || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                        søkliste.add(pasient);
                    }
                } else {
                    if (pasient.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                            || String.valueOf(pasient.getID()).startsWith(søk)
                            || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                            || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                        søkliste.add(pasient);
                    }
                }
            }

            if (søkliste.isEmpty()) {
                søk1.setBackground(Color.decode("#fd6d6d"));
            }

            pasientmodell = new AdminStatistikkPasientmodell(PASIENTKOLONNER, søkliste);

            persontabell = new JTable(pasientmodell);

            persontabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            persontabell.setFillsViewportHeight(true);

            personscroll = new JScrollPane(persontabell);

            tabellpanel.add(personscroll, BorderLayout.CENTER);
            tabellpanel.revalidate();
            tabellpanel.repaint();

        } else {
            String søk = søk1.getText();

            if (søk.length() == 0) {
                visAllePasienter();

                return;

            }

            søk1.setBackground(Color.WHITE);

            lege_pasientpanel.remove(personscroll);
            lege_pasientpanel.revalidate();

            LinkedList<Pasient> pasientliste = (LinkedList<Pasient>) register.getPasientliste();

            LinkedList<Pasient> søkliste = new LinkedList<>();
            for (Pasient pasient : pasientliste) {

                String fornavn_etternavn = pasient.getFornavn() + "" + pasient.getEtternavn();
                String mellomnavn_etternavn = pasient.getMellomnavn() + "" + pasient.getEtternavn();

                if (pasient.getFastlege() != null) {
                    if (pasient.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getFastlege().getNavn().toUpperCase().startsWith(søk.toUpperCase())
                            || String.valueOf(pasient.getID()).startsWith(søk)
                            || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                            || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                        søkliste.add(pasient);
                    }
                } else {
                    if (pasient.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                            || pasient.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                            || String.valueOf(pasient.getID()).startsWith(søk)
                            || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                            || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                        søkliste.add(pasient);
                    }
                }
            }

            if (søkliste.isEmpty()) {
                søk1.setBackground(Color.decode("#fd6d6d"));
            }

            pasientmodell = new AdminStatistikkPasientmodell(PASIENTKOLONNER, søkliste);

            persontabell = new JTable(pasientmodell);

            persontabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            persontabell.setFillsViewportHeight(true);

            personscroll = new JScrollPane(persontabell);

            lege_pasientpanel.add(personscroll, BorderLayout.CENTER);
            lege_pasientpanel.revalidate();
            lege_pasientpanel.repaint();

        }

    }// frisøkPasient ferdig

    /**
     * Frisøk etter ATC-koder
     */
    public void frisøkATC() {
        if (!legeIsAktiv && !pasientIsAktiv) {
            String søk = søk2.getText();

            søk2.setBackground(Color.WHITE);

            if (søk.length() == 0) {
                visAllATC();

                return;

            }

            tabellpanel.remove(stringscroll);
            tabellpanel.revalidate();

            LinkedList<String> atcliste = preparatregister.getATCListe();

            LinkedList<String> søkliste = new LinkedList<>();
            for (String atc : atcliste) {
                if (atc.toUpperCase().startsWith(søk.toUpperCase())) {
                    søkliste.add(atc);
                }
            }

            if (søkliste.isEmpty()) {
                søk2.setBackground(Color.decode("#fd6d6d"));
            }

            stringmodell = new StringModell(ATCKOLONNER, søkliste);

            stringtabell = new JTable(stringmodell);

            stringtabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            stringtabell.setFillsViewportHeight(true);

            stringscroll = new JScrollPane(stringtabell);

            tabellpanel.add(stringscroll, BorderLayout.CENTER);
            tabellpanel.revalidate();
            tabellpanel.repaint();

        } else {
            String søk = søk2.getText();

            søk2.setBackground(Color.WHITE);

            if (søk.length() == 0) {
                visAllATC();

                return;

            }

            valg2panel.remove(stringscroll);
            valg2panel.revalidate();

            LinkedList<String> atcliste = preparatregister.getATCListe();

            LinkedList<String> søkliste = new LinkedList<>();
            for (String atc : atcliste) {
                if (atc.toUpperCase().startsWith(søk.toUpperCase())) {
                    søkliste.add(atc);
                }
            }

            if (søkliste.isEmpty()) {
                søk2.setBackground(Color.decode("#fd6d6d"));
            }

            stringmodell = new StringModell(ATCKOLONNER, søkliste);

            stringtabell = new JTable(stringmodell);

            stringtabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            stringtabell.setFillsViewportHeight(true);

            stringscroll = new JScrollPane(stringtabell);

            valg2panel.add(stringscroll, BorderLayout.CENTER);
            valg2panel.revalidate();
            valg2panel.repaint();

        }
    }

    /**
     * Frisøk etter preparat
     */
    public void frisøkPreparat() {
        if (!legeIsAktiv && !pasientIsAktiv) {
            String søk = søk2.getText();

            søk2.setBackground(Color.WHITE);

            if (søk.length() == 0) {
                visAllPreparat();

                return;

            }

            tabellpanel.remove(stringscroll);
            tabellpanel.revalidate();

            LinkedList<String> preparatliste = preparatregister.getPreparatnavnliste();

            LinkedList<String> søkliste = new LinkedList<>();
            for (String preparat : preparatliste) {
                if (preparat.toUpperCase().startsWith(søk.toUpperCase())) {
                    søkliste.add(preparat);
                }
            }

            if (søkliste.isEmpty()) {
                søk2.setBackground(Color.decode("#fd6d6d"));
            }

            stringmodell = new StringModell(PREPARATKOLONNER, søkliste);

            stringtabell = new JTable(stringmodell);

            stringtabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            stringtabell.setFillsViewportHeight(true);

            stringscroll = new JScrollPane(stringtabell);

            tabellpanel.add(stringscroll, BorderLayout.CENTER);
            tabellpanel.revalidate();
            tabellpanel.repaint();

        } else {
            String søk = søk2.getText();

            søk2.setBackground(Color.WHITE);

            if (søk.length() == 0) {
                visAllPreparat();

                return;

            }

            valg2panel.remove(stringscroll);
            valg2panel.revalidate();

            LinkedList<String> preparatliste = preparatregister.getPreparatnavnliste();

            LinkedList<String> søkliste = new LinkedList<>();
            for (String preparat : preparatliste) {
                if (preparat.toUpperCase().startsWith(søk.toUpperCase())) {
                    søkliste.add(preparat);
                }
            }

            if (søkliste.isEmpty()) {
                søk2.setBackground(Color.decode("#fd6d6d"));
            }

            stringmodell = new StringModell(PREPARATKOLONNER, søkliste);

            stringtabell = new JTable(stringmodell);

            stringtabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            stringtabell.setFillsViewportHeight(true);

            stringscroll = new JScrollPane(stringtabell);

            valg2panel.add(stringscroll, BorderLayout.CENTER);
            valg2panel.revalidate();
            valg2panel.repaint();

        }
    }

    /**
     * Viser alle leger etter at søket er nullstilt
     */
    public void visAlleLeger() {
        if (!atcIsAktiv && !preparatIsAktiv && !reseptgruppeIsAktiv) {

            tabellpanel.remove(personscroll);

            søk1.setBackground(Color.WHITE);
            tabellpanel.revalidate();

            LinkedList<Lege> liste = (LinkedList<Lege>) register.getLegeliste();

            legemodell = new AdminStatistikkLegemodell(LEGEKOLONNER, liste);
            persontabell = new JTable(legemodell);
            personscroll = new JScrollPane(persontabell);

            tabellpanel.add(persontabell);

            tabellpanel.revalidate();
            tabellpanel.repaint();
            System.out.println("YEI");
        } else {

            lege_pasientpanel.remove(personscroll);

            søk1.setBackground(Color.WHITE);
            lege_pasientpanel.revalidate();

            LinkedList<Lege> liste = (LinkedList<Lege>) register.getLegeliste();

            legemodell = new AdminStatistikkLegemodell(LEGEKOLONNER, liste);
            persontabell = new JTable(legemodell);
            personscroll = new JScrollPane(persontabell);

            lege_pasientpanel.add(persontabell);

            lege_pasientpanel.revalidate();
            lege_pasientpanel.repaint();
        }

    }//visAlle ferdig

    /**
     * Viser alle pasienter etter at søket er nullstilt
     */
    public void visAllePasienter() {
        if (!atcIsAktiv && !preparatIsAktiv && !reseptgruppeIsAktiv) {

            tabellpanel.remove(personscroll);

            søk1.setBackground(Color.WHITE);
            tabellpanel.revalidate();

            LinkedList<Pasient> liste = (LinkedList<Pasient>) register.getPasientliste();

            pasientmodell = new AdminStatistikkPasientmodell(PASIENTKOLONNER, liste);
            persontabell = new JTable(pasientmodell);
            personscroll = new JScrollPane(persontabell);

            tabellpanel.add(persontabell);

            tabellpanel.revalidate();
            tabellpanel.repaint();

        } else {

            lege_pasientpanel.remove(personscroll);

            søk1.setBackground(Color.WHITE);
            lege_pasientpanel.revalidate();

            LinkedList<Pasient> liste = (LinkedList<Pasient>) register.getPasientliste();

            pasientmodell = new AdminStatistikkPasientmodell(PASIENTKOLONNER, liste);
            persontabell = new JTable(pasientmodell);
            personscroll = new JScrollPane(persontabell);

            lege_pasientpanel.add(persontabell);

            lege_pasientpanel.revalidate();
            lege_pasientpanel.repaint();
        }
    }

    /**
     * Viser alle ATC-koder etter at søket er nullstilt
     */
    public void visAllATC() {
        if (!pasientIsAktiv && !legeIsAktiv) {

            tabellpanel.remove(stringscroll);

            søk2.setBackground(Color.WHITE);
            tabellpanel.revalidate();

            LinkedList<String> liste = preparatregister.getATCListe();

            stringmodell = new StringModell(ATCKOLONNER, liste);
            stringtabell = new JTable(stringmodell);
            stringscroll = new JScrollPane(stringtabell);

            tabellpanel.add(stringscroll);

            tabellpanel.revalidate();
            tabellpanel.repaint();

        } else {

            valg2panel.remove(stringscroll);

            søk2.setBackground(Color.WHITE);
            valg2panel.revalidate();

            LinkedList<String> liste = preparatregister.getATCListe();

            stringmodell = new StringModell(ATCKOLONNER, liste);
            stringtabell = new JTable(stringmodell);
            stringscroll = new JScrollPane(stringtabell);

            valg2panel.add(stringscroll);

            valg2panel.revalidate();
            valg2panel.repaint();
        }
    }

    /**
     * Viser alle preparater etter at søket er nullstilt
     */
    public void visAllPreparat() {
        if (!pasientIsAktiv && !legeIsAktiv) {

            tabellpanel.remove(stringscroll);

            søk2.setBackground(Color.WHITE);
            tabellpanel.revalidate();

            LinkedList<String> liste = preparatregister.getPreparatnavnliste();

            stringmodell = new StringModell(PREPARATKOLONNER, liste);
            stringtabell = new JTable(stringmodell);
            stringscroll = new JScrollPane(stringtabell);

            tabellpanel.add(stringscroll);

            tabellpanel.revalidate();
            tabellpanel.repaint();

        } else {

            valg2panel.remove(stringscroll);

            søk2.setBackground(Color.WHITE);
            valg2panel.revalidate();

            LinkedList<String> liste = preparatregister.getPreparatnavnliste();

            stringmodell = new StringModell(PREPARATKOLONNER, liste);
            stringtabell = new JTable(stringmodell);
            stringscroll = new JScrollPane(stringtabell);

            valg2panel.add(stringscroll);

            valg2panel.revalidate();
            valg2panel.repaint();
        }
    }

    //<editor-fold desc="METODER FOR ENDRING AV VINDU">
    /**
     * Endrer vindu til alle leger/pasienter og alle resepter
     */
    public void endreVinduAlleAlle() {
        overskrift.setText("Statistikk");
        hovedpanel.remove(statistikksplitt);
        hovedpanel.remove(statistikkscroll);

        legeIsAktiv = false;
        pasientIsAktiv = false;
        atcIsAktiv = false;
        preparatIsAktiv = false;
        reseptgruppeIsAktiv = false;

        hovedpanel.add(knappanel, BorderLayout.PAGE_END);
        hovedpanel.revalidate();
        hovedpanel.repaint();
    }

    /**
     * Endrer vindu til alle leger/pasienter og ATC kode
     */
    public void endreVinduAlleLegerPasienterATC() {
        overskrift.setText("Alle leger/pasieter - ATC");
        hovedpanel.remove(statistikksplitt);
        statistikkscroll.removeAll();
        hovedpanel.remove(knappanel);
        hovedpanel.revalidate();

        legeIsAktiv = false;
        pasientIsAktiv = false;
        atcIsAktiv = true;
        preparatIsAktiv = false;
        reseptgruppeIsAktiv = false;

        statistikkscroll.add(new Statistikkgenerator());

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());

        stringmodell = new StringModell(ATCKOLONNER, preparatregister.getATCListe());
        stringtabell = new JTable(stringmodell);
        stringscroll = new JScrollPane(stringtabell);

        tabellpanel.add(søk2, BorderLayout.PAGE_START);
        tabellpanel.add(stringscroll, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();
    }

    /**
     * Endrer vindu til alle leger/pasienter og preparat
     */
    public void endreVinduAlleLegerPasienterPreparat() {

        overskrift.setText("Alle leger/pasienter - Preparat");

        hovedpanel.remove(statistikksplitt);
        statistikkscroll.removeAll();
        hovedpanel.remove(knappanel);
        hovedpanel.revalidate();

        legeIsAktiv = false;
        pasientIsAktiv = false;
        atcIsAktiv = false;
        preparatIsAktiv = true;
        reseptgruppeIsAktiv = false;

        statistikkscroll.add(new Statistikkgenerator());

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());

        stringmodell = new StringModell(PREPARATKOLONNER, preparatregister.getPreparatnavnliste());
        stringtabell = new JTable(stringmodell);
        stringscroll = new JScrollPane(stringtabell);

        tabellpanel.add(søk2, BorderLayout.PAGE_START);
        tabellpanel.add(stringscroll, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();
    }

    /**
     * Endrer vindu til alle leger/pasienter og reseptgruppe
     */
    public void endreVinduAlleLegerPasienterReseptgruppe() {
        overskrift.setText("Alle leger/pasienter - Reseptgruppe");
        hovedpanel.remove(statistikksplitt);
        statistikkscroll.removeAll();
        hovedpanel.remove(knappanel);
        hovedpanel.revalidate();

        legeIsAktiv = false;
        pasientIsAktiv = false;
        atcIsAktiv = false;
        preparatIsAktiv = false;
        reseptgruppeIsAktiv = true;

        statistikkscroll.add(new Statistikkgenerator());

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());

        stringmodell = new StringModell(RESEPTGRUPPEKOLONNER, reseptgrupper);
        stringtabell = new JTable(stringmodell);
        stringscroll = new JScrollPane(stringtabell);

        tabellpanel.add(stringscroll, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();
    }

    /**
     * Endrer vindu til å vise legeliste
     */
    public void endreVinduLegeAlleResepter() {
        overskrift.setText("Lege - Alle respter");
        hovedpanel.remove(statistikksplitt);
        hovedpanel.remove(knappanel);
        statistikkscroll.removeAll();
        hovedpanel.revalidate();

        legeIsAktiv = true;
        pasientIsAktiv = false;
        atcIsAktiv = false;
        preparatIsAktiv = false;
        reseptgruppeIsAktiv = false;

        statistikkscroll.add(new Statistikkgenerator());

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());
        legemodell = new AdminStatistikkLegemodell(LEGEKOLONNER, register.getLegeliste());
        persontabell = new JTable(legemodell);
        personscroll = new JScrollPane(persontabell);

        tabellpanel.add(søk1, BorderLayout.PAGE_START);
        tabellpanel.add(personscroll, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();

    }

    /**
     * Endrer vindu til å vise legeliste og ATCliste
     */
    public void endreVinduLegeATC() {

        overskrift.setText("Lege - ATC");

        hovedpanel.remove(statistikksplitt);
        statistikkscroll.removeAll();
        hovedpanel.remove(knappanel);

        hovedpanel.revalidate();

        legeIsAktiv = true;
        pasientIsAktiv = false;
        atcIsAktiv = true;
        preparatIsAktiv = false;
        reseptgruppeIsAktiv = false;

        statistikkscroll.add(new Statistikkgenerator());

        tabellsplitt = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());

        tabellpanel.setBackground(blåfarge);
        tabellpanel.setBorder(null);

        legemodell = new AdminStatistikkLegemodell(LEGEKOLONNER, register.getLegeliste());
        persontabell = new JTable(legemodell);
        personscroll = new JScrollPane(persontabell);

        stringmodell = new StringModell(ATCKOLONNER, preparatregister.getATCListe());
        stringtabell = new JTable(stringmodell);

        stringscroll = new JScrollPane(stringtabell);

        lege_pasientpanel = new JPanel(new BorderLayout());
        lege_pasientpanel.add(søk1, BorderLayout.PAGE_START);
        lege_pasientpanel.add(personscroll, BorderLayout.CENTER);

        valg2panel = new JPanel(new BorderLayout());
        valg2panel.add(søk2, BorderLayout.PAGE_START);
        valg2panel.add(stringscroll, BorderLayout.CENTER);

        tabellsplitt.add(lege_pasientpanel);
        tabellsplitt.add(valg2panel);

        tabellsplitt.setResizeWeight(.5);

        tabellpanel.add(tabellsplitt, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        lege_pasientpanel.setBackground(blåfarge);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();

    }

    /**
     * Endrer vindu til å vise legeliste og preparatliste
     */
    public void endreVinduLegePreparat() {
        overskrift.setText("Lege - Preparat");
        hovedpanel.remove(statistikksplitt);
        hovedpanel.remove(knappanel);
        try {
            statistikkscroll.removeAll();
        } catch (NullPointerException npe) {
            statistikkscroll = new JScrollPane();
        }

        hovedpanel.revalidate();

        legeIsAktiv = true;
        pasientIsAktiv = false;
        atcIsAktiv = false;
        preparatIsAktiv = true;
        reseptgruppeIsAktiv = false;

        statistikkscroll.add(new Statistikkgenerator());

        tabellsplitt = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());

        legemodell = new AdminStatistikkLegemodell(LEGEKOLONNER, register.getLegeliste());
        persontabell = new JTable(legemodell);
        personscroll = new JScrollPane(persontabell);

        stringmodell = new StringModell(PREPARATKOLONNER, preparatregister.getPreparatnavnliste());
        stringtabell = new JTable(stringmodell);

        stringscroll = new JScrollPane(stringtabell);

        lege_pasientpanel = new JPanel(new BorderLayout());
        lege_pasientpanel.add(søk1, BorderLayout.PAGE_START);
        lege_pasientpanel.add(personscroll, BorderLayout.CENTER);

        valg2panel = new JPanel(new BorderLayout());
        valg2panel.add(søk2, BorderLayout.PAGE_START);
        valg2panel.add(stringscroll, BorderLayout.CENTER);

        tabellsplitt.add(lege_pasientpanel);
        tabellsplitt.add(valg2panel);

        tabellsplitt.setResizeWeight(.5);

        tabellpanel.add(tabellsplitt, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();

    }

    /**
     * Endrer vindu til å vise legeliste og reseptgruppeliste
     */
    public void endreVinduLegeReseptgruppe() {
        overskrift.setText("Lege - Reseptgruppe");
        hovedpanel.remove(statistikksplitt);
        hovedpanel.remove(knappanel);
        try {
            statistikkscroll.removeAll();
        } catch (NullPointerException npe) {
            statistikkscroll = new JScrollPane();
        }

        hovedpanel.revalidate();

        legeIsAktiv = true;
        pasientIsAktiv = false;
        atcIsAktiv = false;
        preparatIsAktiv = false;
        reseptgruppeIsAktiv = true;

        statistikkscroll.add(new Statistikkgenerator());

        tabellsplitt = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());

        legemodell = new AdminStatistikkLegemodell(LEGEKOLONNER, register.getLegeliste());
        persontabell = new JTable(legemodell);
        personscroll = new JScrollPane(persontabell);

        stringmodell = new StringModell(RESEPTGRUPPEKOLONNER, reseptgrupper);
        stringtabell = new JTable(stringmodell);

        stringscroll = new JScrollPane(stringtabell);

        lege_pasientpanel = new JPanel(new BorderLayout());
        lege_pasientpanel.add(søk1, BorderLayout.PAGE_START);
        lege_pasientpanel.add(personscroll, BorderLayout.CENTER);

        tabellsplitt.add(lege_pasientpanel);
        tabellsplitt.add(stringscroll);

        tabellsplitt.setResizeWeight(.5);

        tabellpanel.add(tabellsplitt, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();

    }

    /**
     * Endrer vindu til å vise pasientliste
     */
    public void endreVinduPasientAlleResepter() {
        overskrift.setText("Pasient - Alle resepter");
        hovedpanel.remove(statistikksplitt);
        hovedpanel.remove(knappanel);
        statistikkscroll.removeAll();

        hovedpanel.revalidate();

        legeIsAktiv = false;
        pasientIsAktiv = true;
        atcIsAktiv = false;
        preparatIsAktiv = false;
        reseptgruppeIsAktiv = false;

        statistikkscroll.add(new Statistikkgenerator());

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());
        pasientmodell = new AdminStatistikkPasientmodell(PASIENTKOLONNER, register.getPasientliste());
        persontabell = new JTable(pasientmodell);
        personscroll = new JScrollPane(persontabell);

        tabellpanel.add(søk1, BorderLayout.PAGE_START);
        tabellpanel.add(personscroll, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();

    }

    /**
     * Endrer vindu til å vise pasientliste og gitt ATCliste
     */
    public void endreVinduPasientATC() {
        overskrift.setText("Pasient - ATC");
        hovedpanel.remove(statistikksplitt);
        hovedpanel.remove(knappanel);
        statistikkscroll.removeAll();

        hovedpanel.revalidate();

        legeIsAktiv = false;
        pasientIsAktiv = true;
        atcIsAktiv = true;
        preparatIsAktiv = false;
        reseptgruppeIsAktiv = false;

        statistikkscroll.add(new Statistikkgenerator());

        tabellsplitt = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());

        pasientmodell = new AdminStatistikkPasientmodell(PASIENTKOLONNER, register.getPasientliste());
        persontabell = new JTable(pasientmodell);
        personscroll = new JScrollPane(persontabell);

        stringmodell = new StringModell(ATCKOLONNER, preparatregister.getATCListe());
        stringtabell = new JTable(stringmodell);

        stringtabell.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println(stringmodell.getValueAt(stringtabell.getSelectedRow()));

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        stringscroll = new JScrollPane(stringtabell);

        lege_pasientpanel = new JPanel(new BorderLayout());
        lege_pasientpanel.add(søk1, BorderLayout.PAGE_START);
        lege_pasientpanel.add(personscroll, BorderLayout.CENTER);

        valg2panel = new JPanel(new BorderLayout());
        valg2panel.add(søk2, BorderLayout.PAGE_START);
        valg2panel.add(stringscroll, BorderLayout.CENTER);

        tabellsplitt.add(lege_pasientpanel);
        tabellsplitt.add(valg2panel);

        tabellsplitt.setResizeWeight(.5);

        tabellpanel.add(tabellsplitt, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();

    }

    /**
     * Endrer vindu til å vise pasientliste og preparatliste
     */
    public void endreVinduPasientPreparat() {
        overskrift.setText("Pasient - Preparat");
        hovedpanel.remove(statistikksplitt);
        hovedpanel.remove(knappanel);
        try {
            statistikkscroll.removeAll();
        } catch (NullPointerException npe) {
            statistikkscroll = new JScrollPane();
        }

        hovedpanel.revalidate();

        legeIsAktiv = false;
        pasientIsAktiv = true;
        atcIsAktiv = false;
        preparatIsAktiv = true;
        reseptgruppeIsAktiv = false;

        statistikkscroll.add(new Statistikkgenerator());

        tabellsplitt = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());

        pasientmodell = new AdminStatistikkPasientmodell(PASIENTKOLONNER, register.getPasientliste());
        persontabell = new JTable(pasientmodell);
        personscroll = new JScrollPane(persontabell);

        stringmodell = new StringModell(PREPARATKOLONNER, preparatregister.getPreparatnavnliste());
        stringtabell = new JTable(stringmodell);

        stringscroll = new JScrollPane(stringtabell);

        lege_pasientpanel = new JPanel(new BorderLayout());
        lege_pasientpanel.add(søk1, BorderLayout.PAGE_START);
        lege_pasientpanel.add(personscroll, BorderLayout.CENTER);

        valg2panel = new JPanel(new BorderLayout());
        valg2panel.add(søk2, BorderLayout.PAGE_START);
        valg2panel.add(stringscroll, BorderLayout.CENTER);

        tabellsplitt.add(lege_pasientpanel);
        tabellsplitt.add(valg2panel);

        tabellsplitt.setResizeWeight(.5);

        tabellpanel.add(tabellsplitt, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();

    }

    /**
     * Endrer vindu til å vise pasientliste og reseptgruppeliste
     */
    public void endreVinduPasientReseptgruppe() {
        overskrift.setText("Pasient - Reseptrgruppe");
        hovedpanel.remove(statistikksplitt);
        hovedpanel.remove(knappanel);

        try {
            statistikkscroll.removeAll();
        } catch (NullPointerException npe) {
            statistikkscroll = new JScrollPane();
        }

        hovedpanel.revalidate();

        legeIsAktiv = false;
        pasientIsAktiv = true;
        atcIsAktiv = false;
        preparatIsAktiv = false;
        reseptgruppeIsAktiv = true;

        statistikkscroll.add(new Statistikkgenerator());

        tabellsplitt = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        statistikksplitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        tabellpanel = new JPanel(new BorderLayout());

        pasientmodell = new AdminStatistikkPasientmodell(PASIENTKOLONNER, register.getPasientliste());
        persontabell = new JTable(pasientmodell);
        personscroll = new JScrollPane(persontabell);

        stringmodell = new StringModell(RESEPTGRUPPEKOLONNER, reseptgrupper);
        stringtabell = new JTable(stringmodell);

        stringscroll = new JScrollPane(stringtabell);

        lege_pasientpanel = new JPanel(new BorderLayout());
        lege_pasientpanel.add(søk1, BorderLayout.PAGE_START);
        lege_pasientpanel.add(personscroll, BorderLayout.CENTER);

        tabellsplitt.add(lege_pasientpanel);
        tabellsplitt.add(stringscroll);

        tabellsplitt.setResizeWeight(.5);

        tabellpanel.add(tabellsplitt, BorderLayout.CENTER);
        tabellpanel.add(knappanel, BorderLayout.PAGE_END);

        statistikksplitt.add(tabellpanel);
        statistikksplitt.add(statistikkscroll);

        hovedpanel.add(statistikksplitt, BorderLayout.CENTER);

        hovedpanel.revalidate();
        hovedpanel.repaint();

    }
    //</editor-fold>

    //<editor-fold desc="METODER FOR Å VISE STATISTIKK">
    
    /**
     * Viser statistikk for alle leger og alle resepter
     * @throws ParseException - dersom datoformat ikke stemmer
     */
    public void alleLegerAlleResepter() throws ParseException {

        hovedpanel.remove(statistikkscroll);
        hovedpanel.remove(statistikksplitt);
        hovedpanel.revalidate();

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra = datoformat.parse(fraString);
        Date til = datoformat.parse(tilString);

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                JOptionPane.showMessageDialog(null, "Fra må være før til");
                return;
            }
        }

        LinkedList<Lege> alleLeger = (LinkedList<Lege>) register.getLegeliste();
        LinkedList<Lege> liste = new LinkedList<>();
        for (Lege lege : alleLeger) {

            if (resepter.getLinkedListFraTil(lege.getID(), fra, til).size() != 0) {

                liste.add(lege);
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_legemap = new TreeMap<>(bvc);

        Iterator listeiterator = liste.iterator();

        for (int i = 0; listeiterator.hasNext(); i++) {

            Lege lege = (Lege) listeiterator.next();
            String navn = lege.getNavn() + ", " + lege.getID();
            int antall = resepter.getLinkedListFraTil(lege.getID(), fra, til).size();

            map.put(navn, antall);

        }

        sortert_legemap.putAll(map);

        Object[] values = sortert_legemap.values().toArray();
        Set keySet = sortert_legemap.keySet();
        Object[] names = keySet.toArray();

        int[] verdier = new int[values.length];
        String[] navn = new String[names.length];

        for (int i = 0; i < verdier.length; i++) {
            verdier[i] = (int) values[i];
            navn[i] = (String) names[i];
        }

        generator = new Statistikkgenerator(navn, verdier);

        søk(generator);

        overskrift.setText("Alle leger - Alle resepter. Antall leger: " + navn.length);

        hovedpanel.revalidate();
        hovedpanel.repaint();

    }
    
    /**
     * Viser statistikk for alle leger og ATC
     */
    public void alleLegerATC() {

        //<editor-fold desc="Oppretter og sjekker dato">
        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }
        //</editor-fold>

        String ATC;

        try {
            ATC = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            JOptionPane.showMessageDialog(null, "Velg en ATC-KODE ut fra ATC-tabellen!");
            return;
        }

        Preparat preparat = preparatregister.hent_preparat(ATC);

        if (preparat == null) {
            JOptionPane.showMessageDialog(null, "SKRIV INN GYLDIG ATC-KODE!");
            return;
        }

        LinkedList<Lege> liste = (LinkedList<Lege>) register.getLegeliste();

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_legemap = new TreeMap<>(bvc);
        for (Lege lege : liste) {
            if (resepter.getLinkedListFraTilATC(lege.getID(), fra, til, ATC).size() > 0) {
                int antall = resepter.getLinkedListFraTilATC(lege.getID(), fra, til, ATC).size();
                String navn = lege.getNavn() + ", " + lege.getID();
                map.put(navn, antall);
            }
        }
        sortert_legemap.putAll(map);

        Object[] names = sortert_legemap.keySet().toArray();
        Object[] values = sortert_legemap.values().toArray();

        String[] legenavn = new String[sortert_legemap.size()];
        int[] antall = new int[sortert_legemap.size()];

        for (int i = 0; i < sortert_legemap.size(); i++) {
            antall[i] = (int) values[i];
            legenavn[i] = (String) names[i];

        }

        generator = new Statistikkgenerator(legenavn, antall);

        overskrift.setText("Alle leger - ATC: " + ATC + ". Antall leger: " + antall.length);

        søk(generator);

    }
    
    /**
     * Viser statistikk for alle leger og gitt preparat
     */
    public void alleLegerPreparat() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        String NAVN;
        try {
            NAVN = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg et preparatnavn fra preparat-tabellen!");
            return;
        }
        Preparat preparat = preparatregister.getPreparaNAVN(NAVN);

        if (preparat == null) {
            visMelding("Det skjedde en intern feil, kontakt systemadministrator!");
            return;
        }

        LinkedList<Lege> liste = (LinkedList<Lege>) register.getLegeliste();

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);

        TreeMap<String, Integer> sortert_legemap = new TreeMap<>(bvc);
        for (Lege lege : liste) {
            if (resepter.getLinkedListFraTilNavn(lege.getID(), fra, til, NAVN).size() > 0) {
                int antall = resepter.getLinkedListFraTilNavn(lege.getID(), fra, til, NAVN).size();
                String navn = lege.getNavn() + ", " + lege.getID();
                map.put(navn, antall);
            }
        }
        sortert_legemap.putAll(map);

        Object[] names = sortert_legemap.keySet().toArray();
        Object[] values = sortert_legemap.values().toArray();

        String[] legenavn = new String[sortert_legemap.size()];
        int[] antall = new int[sortert_legemap.size()];

        for (int i = 0; i < sortert_legemap.size(); i++) {
            antall[i] = (int) values[i];
            legenavn[i] = (String) names[i];

        }

        generator = new Statistikkgenerator(legenavn, antall);

        overskrift.setText("Alle leger - Preparat: " + NAVN + ". Antall leger: " + antall.length);

        søk(generator);

    }
   
    /**
     * Viser statistikk for alle leger og reseptgruppe
     */
    public void alleLegerReseptgruppe() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        String gruppe;
        try {
            gruppe = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg en reseptgruppe fra reseptgruppe-tabellen");
            return;
        }

        LinkedList<Lege> alleLeger = (LinkedList<Lege>) register.getLegeliste();

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);

        TreeMap<String, Integer> sortert_legemap = new TreeMap<>(bvc);
        for (Lege lege : alleLeger) {
            if (resepter.getLinkedListFraTilReseptgruppe(lege.getID(), fra, til, gruppe).size() > 0) {
                int antall = resepter.getLinkedListFraTilReseptgruppe(lege.getID(), fra, til, gruppe).size();
                String navn = lege.getNavn() + ", " + lege.getID();
                map.put(navn, antall);
            }
        }
        sortert_legemap.putAll(map);

        Object[] names = sortert_legemap.keySet().toArray();
        Object[] values = sortert_legemap.values().toArray();

        String[] legenavn = new String[sortert_legemap.size()];
        int[] antall = new int[sortert_legemap.size()];

        for (int i = 0; i < sortert_legemap.size(); i++) {
            antall[i] = (int) values[i];
            legenavn[i] = (String) names[i];

        }

        generator = new Statistikkgenerator(legenavn, antall);

        overskrift.setText("Alle leger - " + gruppe + "Antall leger: " + antall.length);

        søk(generator);

    }
    
    /**
     * Viser statistikk over alle pasienter og alle resepter
     */
    public void allePasienterAlleResepter() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        LinkedList<Pasient> allePasienter = (LinkedList<Pasient>) register.getPasientliste();
        LinkedList<Pasient> liste = new LinkedList<>();
        for (Pasient pasient : allePasienter) {

            if (resepter.getLinkedListFraTil(pasient.getID(), fra, til).size() > 0) {

                liste.add(pasient);
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_legemap = new TreeMap<>(bvc);

        Iterator listeiterator = liste.iterator();

        for (int i = 0; listeiterator.hasNext(); i++) {

            Pasient pasient = (Pasient) listeiterator.next();
            String navn = pasient.getNavn() + ", " + pasient.getID();
            int antall = resepter.getLinkedListFraTil(pasient.getID(), fra, til).size();

            map.put(navn, antall);

        }

        sortert_legemap.putAll(map);

        Object[] values = sortert_legemap.values().toArray();
        Set keySet = sortert_legemap.keySet();
        Object[] names = keySet.toArray();

        int[] verdier = new int[values.length];
        String[] navn = new String[names.length];

        for (int i = 0; i < verdier.length; i++) {
            verdier[i] = (int) values[i];
            navn[i] = (String) names[i];
        }

        generator = new Statistikkgenerator(navn, verdier);

        overskrift.setText("Alle pasienter - Alle resepter. Antall pasienter: " + verdier.length);

        søk(generator);
    }

    /**
     * Viser statistikk over alle pasienter og gitt ATC-kode
     */
    public void allePasienterATC() {
        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        String ATC;
        try {
            ATC = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg en ATC-KODE ut fra ATC-tabellen!");
            return;
        }

        Preparat preparat = preparatregister.hent_preparat(ATC);

        if (preparat == null) {
            visMelding("Intern feil! Kontakt systemadministrator");
            return;
        }
        LinkedList<Pasient> allePasienter = (LinkedList<Pasient>) register.getPasientliste();
        LinkedList<Pasient> liste = new LinkedList<>();
        for (Pasient pasient : allePasienter) {

            if (resepter.getLinkedListFraTilATC(pasient.getID(), fra, til, ATC).size() > 0) {

                liste.add(pasient);
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_legemap = new TreeMap<>(bvc);

        Iterator listeiterator = liste.iterator();

        for (int i = 0; listeiterator.hasNext(); i++) {

            Pasient pasient = (Pasient) listeiterator.next();
            String navn = pasient.getNavn() + ", " + pasient.getID();
            int antall = resepter.getLinkedListFraTilATC(pasient.getID(), fra, til, ATC).size();

            map.put(navn, antall);

        }

        sortert_legemap.putAll(map);

        Object[] values = sortert_legemap.values().toArray();
        Set keySet = sortert_legemap.keySet();
        Object[] names = keySet.toArray();

        int[] verdier = new int[values.length];
        String[] navn = new String[names.length];

        for (int i = 0; i < verdier.length; i++) {
            verdier[i] = (int) values[i];
            navn[i] = (String) names[i];
        }
        generator = new Statistikkgenerator(navn, verdier);

        overskrift.setText("Alle pasienter - ATC: " + ATC + ". Antall pasienter: " + verdier.length);

        søk(generator);
    }

    /**
     * Viser statistikk over alle pasienter og gitt preparat
     */
    public void allePasienterPreparat() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        String NAVN;
        try {
            NAVN = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg et preparat fra preparat-tabellen!");
            return;
        }
        Preparat preparat = preparatregister.getPreparaNAVN(NAVN);

        if (preparat == null) {
            visMelding("Intern feil! Kontakt systemadministrator");
            return;
        }
        LinkedList<Pasient> allePasienter = (LinkedList<Pasient>) register.getPasientliste();
        LinkedList<Pasient> liste = new LinkedList<>();
        for (Pasient pasient : allePasienter) {

            if (resepter.getLinkedListFraTilNavn(pasient.getID(), fra, til, NAVN).size() > 0) {

                liste.add(pasient);
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_legemap = new TreeMap<>(bvc);

        Iterator listeiterator = liste.iterator();

        for (int i = 0; listeiterator.hasNext(); i++) {

            Pasient pasient = (Pasient) listeiterator.next();
            String navn = pasient.getNavn() + ", " + pasient.getID();
            int antall = resepter.getLinkedListFraTilNavn(pasient.getID(), fra, til, NAVN).size();

            map.put(navn, antall);

        }

        sortert_legemap.putAll(map);

        Object[] values = sortert_legemap.values().toArray();
        Set keySet = sortert_legemap.keySet();
        Object[] names = keySet.toArray();

        int[] verdier = new int[values.length];
        String[] navn = new String[names.length];

        for (int i = 0; i < verdier.length; i++) {
            verdier[i] = (int) values[i];
            navn[i] = (String) names[i];
        }

        generator = new Statistikkgenerator(navn, verdier);

        overskrift.setText("Alle passienter - " + NAVN + ". Antall pasienter: " + verdier.length);

        søk(generator);

    }

    /**
     * Viser statistikk over alle pasienter og reseptgruppe
     */
    public void allePasienterReseptgruppe() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        String gruppe;

        try {
            gruppe = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg en reseptgruppe fra reseptgruppe-tabellen");
            return;
        }

        LinkedList<Pasient> allePasienter = (LinkedList<Pasient>) register.getPasientliste();
        LinkedList<Pasient> liste = new LinkedList<>();
        for (Pasient pasient : allePasienter) {

            if (resepter.getLinkedListFraTilReseptgruppe(pasient.getID(), fra, til, gruppe).size() > 0) {

                liste.add(pasient);
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_legemap = new TreeMap<>(bvc);

        Iterator listeiterator = liste.iterator();

        for (int i = 0; listeiterator.hasNext(); i++) {

            Pasient pasient = (Pasient) listeiterator.next();
            String navn = pasient.getNavn() + ", " + pasient.getID();
            int antall = resepter.getLinkedListFraTilReseptgruppe(pasient.getID(), fra, til, gruppe).size();

            map.put(navn, antall);

        }

        sortert_legemap.putAll(map);

        Object[] values = sortert_legemap.values().toArray();
        Set keySet = sortert_legemap.keySet();
        Object[] names = keySet.toArray();

        int[] verdier = new int[values.length];
        String[] navn = new String[names.length];

        for (int i = 0; i < verdier.length; i++) {
            verdier[i] = (int) values[i];
            navn[i] = (String) names[i];
        }

        generator = new Statistikkgenerator(navn, verdier);

        overskrift.setText("Alle pasienter - " + gruppe + " Antall pasienter: " + verdier.length);
        søk(generator);

    }

    /**
     * Viser statistikk over gitt lege og alle resepter
     */
    public void legeAlleResepter() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        Lege lege;
        try {
            lege = legemodell.getValueAt(persontabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg en lege fra legetabellen!");
            return;
        }

        if (lege == null) {
            visMelding("Intern feil! Kontakt systemadministrator!");
            return;
        }

        LinkedList<String> alleResepter = resepter.getReseptlisteUink(lege.getID());

        Iterator it = alleResepter.iterator();

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_map = new TreeMap<>(bvc);

        while (it.hasNext()) {
            String preparatnavn = (String) it.next();
            int antall = resepter.getLinkedListFraTilNavn(lege.getID(), fra, til, preparatnavn).size();
            if (antall > 0) {

                map.put(preparatnavn, antall);
            }
        }

        sortert_map.putAll(map);

        Object[] names = sortert_map.keySet().toArray();
        Object[] values = sortert_map.values().toArray();
        String[] navn = new String[sortert_map.size()];
        int[] verdier = new int[sortert_map.size()];

        for (int i = 0; i < sortert_map.size(); i++) {
            navn[i] = (String) names[i];
            verdier[i] = (int) values[i];
        }

        generator = new Statistikkgenerator(navn, verdier);
        overskrift.setText(lege.getNavn() + " - Alle resepter. Antall preparater: " + verdier.length);
        søk(generator);

    }

    /**
     * Viser statistikk over gitt lege og gitt ATC-kode
     */
    public void legeATC() {
        //<editor-fold desc="Sjekker og oppretter dato">
        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        //</editor-fold>
        String ATC = "";
        Lege lege;
        try {
            lege = legemodell.getValueAt(persontabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg lege fra legetabellen!");
            return;
        }
        try {
            ATC = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg ATC-KODE fra ATC-tabellen!");
        }


        Preparat p = preparatregister.hent_preparat(ATC);



        if (p == null) {
            visMelding("Intern feil! Kontakt systemadministrator");
            return;
        } else if (lege == null) {
            visMelding("Intern feil! Kontakt systemadministrator");
            return;
        }

        LinkedList<String> alleResepter = resepter.getReseptlisteUink(lege.getID());
        LinkedList<String> liste = new LinkedList<>();
        for (String preparat : alleResepter) {

            if (preparatregister.getPreparaNAVN(preparat).getATC_kode().equalsIgnoreCase(ATC)) {
                if (resepter.getLinkedListFraTilNavn(lege.getID(), fra, til, preparat).size() > 0) {
                    liste.add(preparat);
                }
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_map = new TreeMap<>(bvc);
        for (String navn : liste) {
            int antall = resepter.getLinkedListFraTilNavn(lege.getID(), fra, til, navn).size();
            map.put(navn, antall);
        }
        sortert_map.putAll(map);

        Object[] names = sortert_map.keySet().toArray();
        Object[] values = sortert_map.values().toArray();
        String[] navn = new String[sortert_map.size()];
        int[] verdier = new int[sortert_map.size()];

        for (int i = 0; i < sortert_map.size(); i++) {
            navn[i] = (String) names[i];
            verdier[i] = (int) values[i];
        }

        generator = new Statistikkgenerator(navn, verdier);

        overskrift.setText(lege.getNavn() + " - ATC: " + ATC + ". Antall preparater: " + verdier.length);
        //søk(generator, "Lege: "+ lege.getNavn() + ", "+ lege.getID(), p.getATC_kode());
        søk(generator);

    }

    /**
     * Viser statistikk over gitt lege og gitt preparat
     */
    public void legePreparat() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        Lege lege;
        String preparatnavn;

        try {
            lege = legemodell.getValueAt(persontabell.getSelectedRow());
            preparatnavn = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg lege fra lege og preparatnavn fra tabellene!");
            return;
        }

        Preparat preparat = preparatregister.getPreparaNAVN(preparatnavn);

        if (lege == null || preparat == null) {
            visMelding("Intern feil! Kontakt systemadministrator!");
            return;
        }
        int antall = resepter.getLinkedListFraTilNavn(lege.getID(), fra, til, preparatnavn).size();
        generator = new Statistikkgenerator(preparatnavn, antall, lege.getNavn());

        overskrift.setText(lege.getNavn() + " - Preparat: " + preparatnavn);

        søk(generator);


    }

    /**
     * Viser statistikk over gitt lege og gitt reseptgruppe
     */
    public void legeReseptgruppe() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        Lege lege;
        String gruppe;

        try {
            lege = legemodell.getValueAt(persontabell.getSelectedRow());
            gruppe = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg lege og reseptgruppe fra tabellene!");
            return;
        }

        if (lege == null) {
            visMelding("Intern feil! Kontakt systemadministartor!");
            return;
        }

        LinkedList<String> reseptliste = resepter.getReseptlisteUink(lege.getID());
        LinkedList<String> liste = new LinkedList<>();
        for (String preparatnavn : reseptliste) {

            if (resepter.getLinkedListFraTilNavnReseptgruppe(lege.getID(), fra, til, preparatnavn, gruppe).size() > 0) {
                liste.add(preparatnavn);
            }
        }
        Iterator it = liste.iterator();

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_map = new TreeMap<>(bvc);

        while (it.hasNext()) {
            String preparatnavn = (String) it.next();
            int antall = resepter.getLinkedListFraTilNavn(lege.getID(), fra, til, preparatnavn).size();
            map.put(preparatnavn, antall);

        }

        sortert_map.putAll(map);

        Object[] names = sortert_map.keySet().toArray();
        Object[] values = sortert_map.values().toArray();
        String[] navn = new String[sortert_map.size()];
        int[] verdier = new int[sortert_map.size()];

        for (int i = 0; i < sortert_map.size(); i++) {
            navn[i] = (String) names[i];
            verdier[i] = (int) values[i];
        }

        generator = new Statistikkgenerator(navn, verdier);
        overskrift.setText(lege.getNavn() + " - " + gruppe + ". Antall preparater: " + verdier.length);
        søk(generator);

    }

    /**
     * Viser statistikk over gitt pasient og alle resepter
     */
    public void pasientAlleResepter() {
        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        Pasient pasient;
        try {
            pasient = pasientmodell.getValueAt(persontabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg en pasient fra pasienttabellen!");
            return;
        }

        if (pasient == null) {
            visMelding("Intern feil! Kontakt systemadministrator!");
            return;
        }
        LinkedList<String> alleResepter = resepter.getReseptlisteUink(pasient.getID());

        Iterator it = alleResepter.iterator();

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_map = new TreeMap<>(bvc);

        while (it.hasNext()) {
            String preparatnavn = (String) it.next();
            int antall = resepter.getLinkedListFraTilNavn(pasient.getID(), fra, til, preparatnavn).size();
            if (antall > 0) {

                map.put(preparatnavn, antall);
            }
        }

        sortert_map.putAll(map);

        Object[] names = sortert_map.keySet().toArray();
        Object[] values = sortert_map.values().toArray();
        String[] navn = new String[sortert_map.size()];
        int[] verdier = new int[sortert_map.size()];

        for (int i = 0; i < sortert_map.size(); i++) {
            navn[i] = (String) names[i];
            verdier[i] = (int) values[i];
        }

        generator = new Statistikkgenerator(navn, verdier);

        overskrift.setText(pasient.getNavn() + " - Alle resepter. Antall preparater: " + verdier.length);

        søk(generator);

    }

    /**
     * Viser statistikk over gitt pasient og gitt ATC-kode
     */
    public void pasientATC() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        Pasient pasient;
        String atc;

        try {
            pasient = pasientmodell.getValueAt(persontabell.getSelectedRow());
            atc = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg både pasient og ATC-KODE fra tabellene!");
            return;
        }

        Preparat p = preparatregister.hent_preparat(atc);

        if (p == null || pasient == null) {
            visMelding("Intern feil! Kontakt systemadministrator!");
            return;
        }

        LinkedList<String> alleResepter = resepter.getReseptlisteUink(pasient.getID());
        LinkedList<String> liste = new LinkedList<>();
        for (String preparat : alleResepter) {

            if (preparatregister.getPreparaNAVN(preparat).getATC_kode().equalsIgnoreCase(atc)) {
                if (resepter.getLinkedListFraTilNavn(pasient.getID(), fra, til, preparat).size() > 0) {
                    liste.add(preparat);
                }
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_map = new TreeMap<>(bvc);
        for (String navn : liste) {
            int antall = resepter.getLinkedListFraTilNavn(pasient.getID(), fra, til, navn).size();
            map.put(navn, antall);
        }
        sortert_map.putAll(map);

        Object[] names = sortert_map.keySet().toArray();
        Object[] values = sortert_map.values().toArray();
        String[] navn = new String[sortert_map.size()];
        int[] verdier = new int[sortert_map.size()];

        for (int i = 0; i < sortert_map.size(); i++) {
            navn[i] = (String) names[i];
            verdier[i] = (int) values[i];
        }

        generator = new Statistikkgenerator(navn, verdier);
        overskrift.setText(pasient.getNavn() + " - ATC: " + atc + ". Antall preparater: " + verdier.length);
        søk(generator);


    }

    /**
     * Viser statistikk over gitt pasient og gitt preparat
     */
    public void pasientPreparat() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-MM-YYYY");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        Pasient pasient;
        String preparatnavn;

        try {
            pasient = pasientmodell.getValueAt(persontabell.getSelectedRow());
            preparatnavn = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg både pasient og preparat fra tabellene!");
            return;
        }

        Preparat preparat = preparatregister.getPreparaNAVN(preparatnavn);

        if (pasient == null || preparat == null) {
            visMelding("Intern feil! Kontakt systemadministartor!");
            return;
        }

        int antall = resepter.getLinkedListFraTilNavn(pasient.getID(), fra, til, preparatnavn).size();
        generator = new Statistikkgenerator(preparatnavn, antall, pasient.getNavn());
        overskrift.setText(pasient.getNavn() + " - Preparat: " + preparatnavn + ". Antall preparater: " + antall);
        søk(generator);
       
    }

    /**
     * Viser statistikk over gitt pasient og gitt reseptgruppe
     */
    public void pasientReseptgruppe() {

        String fraString = fraDato.getText();
        String tilString = tilDato.getText();

        Date fra;
        Date til;

        try {
            fra = datoformat.parse(fraString);

        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-mm-åååå");
            return;
        }
        try {
            til = datoformat.parse(tilString);
        } catch (ParseException ex) {
            visMelding("Skriv inn gyldig startdato\nFormat: dd-m-åååå");
            return;
        }

        if (fra == null || til == null) {
            visMelding("Skriv inn gyldig dato!");
            return;
        }

        if (!fra.before(til)) {
            if (fra.equals(til)) {
            } else {
                visMelding("Startdato kan ikke være før sluttdato!");
                return;
            }
        }

        Pasient pasient;
        String gruppe;

        try {
            pasient = pasientmodell.getValueAt(persontabell.getSelectedRow());
            gruppe = stringmodell.getValueAt(stringtabell.getSelectedRow());
        } catch (IndexOutOfBoundsException iobe) {
            visMelding("Velg både pasient og reseptgruppe fra tabellene!");
            return;
        }

        if (pasient == null || gruppe == null) {
            visMelding("Intern feil! Kontakt systemadministrator");
            return;
        }

        LinkedList<String> reseptliste = resepter.getReseptlisteUink(pasient.getID());
        LinkedList<String> liste = new LinkedList<>();
        for (String preparatnavn : reseptliste) {

            if (resepter.getLinkedListFraTilNavnReseptgruppe(pasient.getID(), fra, til, preparatnavn, gruppe).size() > 0) {
                liste.add(preparatnavn);
            }
        }
        Iterator it = liste.iterator();

        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sortert_map = new TreeMap<>(bvc);

        while (it.hasNext()) {
            String preparatnavn = (String) it.next();
            int antall = resepter.getLinkedListFraTilNavn(pasient.getID(), fra, til, preparatnavn).size();
            map.put(preparatnavn, antall);

        }

        sortert_map.putAll(map);

        Object[] names = sortert_map.keySet().toArray();
        Object[] values = sortert_map.values().toArray();
        String[] navn = new String[sortert_map.size()];
        int[] verdier = new int[sortert_map.size()];

        for (int i = 0; i < sortert_map.size(); i++) {
            navn[i] = (String) names[i];
            verdier[i] = (int) values[i];
        }

        generator = new Statistikkgenerator(navn, verdier);
        overskrift.setText(pasient.getNavn() + " - " + gruppe + " Antall preparater: " + verdier.length);
        søk(generator);
       

    }

    //</editor-fold>
    //</editor-fold>
    /**
     * søk viser panel med statistikk på skjermen
     * @param generator Jpanel med statistikk
     */
    public void søk(Statistikkgenerator generator) {
        if (!legeIsAktiv && !pasientIsAktiv && !atcIsAktiv && !preparatIsAktiv && !reseptgruppeIsAktiv) {
            hovedpanel.remove(statistikkscroll);
            hovedpanel.revalidate();
            statistikkscroll = new JScrollPane(generator);
            hovedpanel.add(statistikkscroll, BorderLayout.CENTER);
            hovedpanel.revalidate();
            hovedpanel.repaint();

        } else {
            statistikksplitt.remove(statistikkscroll);
            statistikksplitt.revalidate();
            statistikkscroll = new JScrollPane(generator);
            statistikksplitt.setRightComponent(statistikkscroll);
            statistikksplitt.revalidate();
            statistikksplitt.repaint();
        }

    }

    /**
     * Viser en JOptionPane-dialog som gir brukeren instrukser om hva som har blitt gjort galt.
     * @param melding meldingen som skal vises
     */
    private void visMelding(String melding) {
        JOptionPane.showMessageDialog(null, melding);
    }

    /**
     * Lager en knapp med bilde.
     * @param icon filsti til hvor bildet ligger lagret
     * @return JButton -Knapp med bilde
     */
    private JButton lagBildeknapp(String icon) {
        JButton knapp = new JButton();
        knapp.setContentAreaFilled(false);
        knapp.setBorderPainted(false);
        knapp.setIcon(new ImageIcon(getClass().getResource(icon)));

        return knapp;
    }//lagBildeknapp ferdig

    /**
     * Knappelytter som lytter til når knappene blir trykket på, implementerer ActionListener
     */
    private class Lytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == visStatistikk) {
                try {
                    if (valg1.getSelectedIndex() == ALLE_LEGER && valg2.getSelectedIndex() == ALLE_RESEPTER) {
                        alleLegerAlleResepter();
                    } else if (valg1.getSelectedIndex() == ALLE_LEGER && valg2.getSelectedIndex() == ATC_KODE) {
                        alleLegerATC();
                    } else if (valg1.getSelectedIndex() == ALLE_LEGER && valg2.getSelectedIndex() == PREPARAT) {
                        alleLegerPreparat();
                    } else if (valg1.getSelectedIndex() == ALLE_LEGER && valg2.getSelectedIndex() == RESEPTGRUPPE) {
                        alleLegerReseptgruppe();
                    } else if (valg1.getSelectedIndex() == ALLE_PASIENTER && valg2.getSelectedIndex() == ALLE_RESEPTER) {
                        allePasienterAlleResepter();
                    } else if (valg1.getSelectedIndex() == ALLE_PASIENTER && valg2.getSelectedIndex() == ATC_KODE) {
                        allePasienterATC();
                    } else if (valg1.getSelectedIndex() == ALLE_PASIENTER && valg2.getSelectedIndex() == PREPARAT) {
                        allePasienterPreparat();
                    } else if (valg1.getSelectedIndex() == ALLE_PASIENTER && valg2.getSelectedIndex() == RESEPTGRUPPE) {
                        allePasienterReseptgruppe();
                    } else if (valg1.getSelectedIndex() == LEGE && valg2.getSelectedIndex() == ALLE_RESEPTER) {
                        legeAlleResepter();
                    } else if (valg1.getSelectedIndex() == LEGE && valg2.getSelectedIndex() == ATC_KODE) {
                        legeATC();
                    } else if (valg1.getSelectedIndex() == LEGE && valg2.getSelectedIndex() == PREPARAT) {
                        legePreparat();
                    } else if (valg1.getSelectedIndex() == LEGE && valg2.getSelectedIndex() == RESEPTGRUPPE) {
                        legeReseptgruppe();
                    } else if (valg1.getSelectedIndex() == PASIENT && valg2.getSelectedIndex() == ALLE_RESEPTER) {
                        pasientAlleResepter();
                    } else if (valg1.getSelectedIndex() == PASIENT && valg2.getSelectedIndex() == ATC_KODE) {
                        pasientATC();
                    } else if (valg1.getSelectedIndex() == PASIENT && valg2.getSelectedIndex() == PREPARAT) {
                        pasientPreparat();
                    } else if (valg1.getSelectedIndex() == PASIENT && valg2.getSelectedIndex() == RESEPTGRUPPE) {
                        pasientReseptgruppe();
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Skriv gyldig datoformat: dd-mm-åååå!");
                }
            } else if (e.getSource() == valg1 || e.getSource() == valg2) {
                if ((valg1.getSelectedIndex() == ALLE_LEGER || valg1.getSelectedIndex() == ALLE_PASIENTER) && valg2.getSelectedIndex() == ALLE_RESEPTER) {
                    endreVinduAlleAlle();
                } else if ((valg1.getSelectedIndex() == ALLE_LEGER || valg1.getSelectedIndex() == ALLE_PASIENTER) && valg2.getSelectedIndex() == ATC_KODE) {
                    endreVinduAlleLegerPasienterATC();
                } else if ((valg1.getSelectedIndex() == ALLE_LEGER || valg1.getSelectedIndex() == ALLE_PASIENTER) && valg2.getSelectedIndex() == PREPARAT) {
                    endreVinduAlleLegerPasienterPreparat();
                } else if ((valg1.getSelectedIndex() == ALLE_LEGER || valg1.getSelectedIndex() == ALLE_PASIENTER) && valg2.getSelectedIndex() == RESEPTGRUPPE) {
                    endreVinduAlleLegerPasienterReseptgruppe();
                } else if (valg1.getSelectedIndex() == LEGE && valg2.getSelectedIndex() == ALLE_RESEPTER) {
                    endreVinduLegeAlleResepter();
                } else if (valg1.getSelectedIndex() == LEGE && valg2.getSelectedIndex() == ATC_KODE) {
                    endreVinduLegeATC();
                } else if (valg1.getSelectedIndex() == LEGE && valg2.getSelectedIndex() == PREPARAT) {
                    endreVinduLegePreparat();
                } else if (valg1.getSelectedIndex() == LEGE && valg2.getSelectedIndex() == RESEPTGRUPPE) {
                    endreVinduLegeReseptgruppe();
                } else if (valg1.getSelectedIndex() == PASIENT && valg2.getSelectedIndex() == ALLE_RESEPTER) {
                    endreVinduPasientAlleResepter();
                } else if (valg1.getSelectedIndex() == PASIENT && valg2.getSelectedIndex() == ATC_KODE) {
                    endreVinduPasientATC();
                } else if (valg1.getSelectedIndex() == PASIENT && valg2.getSelectedIndex() == PREPARAT) {
                    endreVinduPasientPreparat();
                } else if (valg1.getSelectedIndex() == PASIENT && valg2.getSelectedIndex() == RESEPTGRUPPE) {
                    endreVinduPasientReseptgruppe();
                }
            }

        }
    }

    /**
     * Tekstlytter som lytter til tekst, implementerer DocumentListener
     */
    private class Tekstlytter implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (e.getDocument() == søk1.getDocument()) {
                if (legeIsAktiv) {
                    frisøkLege();

                } else if (pasientIsAktiv) {
                    //pasientsøk;
                    frisøkPasient();
                }
            } else if (e.getDocument() == søk2.getDocument()) {
                if (atcIsAktiv) {
                    frisøkATC();
                } else if (preparatIsAktiv) {
                    frisøkPreparat();
                }
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (e.getDocument() == søk1.getDocument()) {
                if (legeIsAktiv) {
                    frisøkLege();

                } else if (pasientIsAktiv) {
                    //pasientsøk;
                    frisøkPasient();
                }
            } else if (e.getDocument() == søk2.getDocument()) {
                if (atcIsAktiv) {
                    frisøkATC();
                } else if (preparatIsAktiv) {
                    frisøkPreparat();
                }
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (e.getDocument() == søk1.getDocument()) {
                if (legeIsAktiv) {
                    frisøkLege();

                } else if (pasientIsAktiv) {
                    //pasientsøk;
                    frisøkPasient();
                }
            } else if (e.getDocument() == søk2.getDocument()) {
                if (atcIsAktiv) {
                    frisøkATC();
                } else if (preparatIsAktiv) {
                    frisøkPreparat();
                }
            }
        }
    }
}

/**
 * Sammenligner verdier, brukes for å sortere verdier
 * 
 */
class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;

    /**
     * Sorterer maps
     * @param base map som skal sorteres
     */
    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

  
    @Override
    public int compare(String a, String b) {
        if (base.get(a) <= base.get(b)) {
            return 1;
        } else {
            return -1;
        } // returning 0 would merge keys
    }
}
