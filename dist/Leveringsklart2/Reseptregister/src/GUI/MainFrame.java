/**
 * MainFrame innholder alle panelene for Legedelen av programmet.
 * Her ligger en liste over legens faste pasiente.
 * Legen kan søke ut pasienter og vise resepthistorikken til pasienter. Legen kan også skrive ut nye resetper
 * 
 */
package GUI;

import Objekter.Lege;
import Objekter.Pasient;
import Registerklasser.Register;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import static java.awt.Frame.ICONIFIED;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import reseptregister.Reseptregister;

/**
 *Siste endret: 12.05.14
 * @author Vegrad Lokreim og Walid Jurmy
 */
public final class MainFrame extends JFrame {

    private Color blåfarge = Color.decode("#007ec5");

    private VisPasient visPasientinfo;
    
    private JPanel bunnpanel;
    private JPanel senterpanel;
    private JButton tilbake;
    private JButton tilbake1;

    private JTextField personnummerFelt;
    private JButton hent;

    private JPanel vinduer;

    private JPanel hentPanel;
    private JPanel overskriftpanel;
    private JPanel søkpanel;

    private static JPanel tabellpanel;

    private static final String HENT_PASIENT = "HENT PASIENT";
    private static final String VIS_PASIENT = "VIS PASIENT";
    private static final String REGISTRER_PASIENT = "REGISTRER PASIENT";
    private static Lege lege;

    private static JTable faste_pasienter;
    private JScrollPane tabellScroll;
    private static Pasientmodell pasientmodell;
    private static String[] kolonner = {"P.NR", "NAVN", "ANTALL A", "ANTALL B", "ANTALL C", "TOTALT"};

    private Register register;
    private Pasient pasient;

    private JPanel p;
    private JPanel menybar;
    private JButton lukk, minimer;
    private Font font = new Font("Arial", Font.BOLD, 24);
    private int pX, pY;

    private MenuItem infoItem;
    private MenuItem exitItem;

    private LinkedList<Pasient> temp;

    /**
     * oppretter MainFrame for legen som er gitt i parameteret, med tilhørende
     * register
     *
     * @param lege lege som er logget inn
     * @param register en instans av Register
     */
    public MainFrame(Lege lege, Register register) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

            //menybar.setBa
        } catch (Exception e) {
        }
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent winEv){
                Reseptregister.lagreRegister();
                Reseptregister.lagreReseptregister();
                System.exit(0);
            }
        });

        gjørFlyttbar();
        
        this.register = register;

        this.lege = lege;
        setLayout(new BorderLayout());
        initialiser();
        lagTabell();

        konstruerHentPanel();

        vinduer = new JPanel(new CardLayout());

        bunnpanel.add(tilbake);
        bunnpanel.setBackground(blåfarge);

        vinduer.add(hentPanel, HENT_PASIENT);
        setVisible(true);

        senterpanel.add(vinduer, BorderLayout.CENTER);

        add(senterpanel, BorderLayout.CENTER);
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {

                        dispose();
                        login();

                    }//windowClosing ferdig
                });
        setRadhøyde(faste_pasienter);
        add(menybar, BorderLayout.NORTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }//Mainframe ferdig

    
    /**
     * Initialiserer alle datafeltene og legger til tekstlytter til personnummerfelt
     */
    public void initialiser() {
        personnummerFelt = new JTextField(10);

        personnummerFelt.getDocument().addDocumentListener(new Tekstlytter());

        hent = lagBildeknapp("Bilder/sok.png", "SØK");

        try {
            tilbake = lagBildeknapp("Bilder/tilbake.png", "");
        } catch (NullPointerException npe) {
            tilbake = new JButton("TILBAKE");
        }

        senterpanel = new JPanel(new BorderLayout());

        tilbake1 = lagBildeknapp("Bilder/tilbake.png", "TILBAKE TIL PASIENT"); // dersom brukeren klikker tilbake når han er i registrering av ny resept
        personnummerFelt.setFont(new Font("Serif", Font.BOLD, 24));
        hent.addActionListener(new Knapplytter());
        personnummerFelt.addActionListener(new Knapplytter());
        tilbake.addActionListener(new Knapplytter());
        tilbake1.addActionListener(new Knapplytter());
        bunnpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        overskriftpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        søkpanel = new JPanel(new FlowLayout());

        tabellpanel = new JPanel(new BorderLayout());

    }//initialiser ferdig

    /**
     * lager systemikon
     */

    /**
     * Legger til panel i toppen av vinduet og legger til knapper for å lukke /minimere.
     * gjør også JFrame undecorated
     */
    public void gjørFlyttbar() {

        setUndecorated(true);

        // Create panel
        p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new GridLayout(1, 2, 5, 5));

        // Create buttons
        minimer = lagBildeknapp("Bilder/minimer.png", "Bilder/minimerRollover.png", "minimer");
        lukk = lagBildeknapp("Bilder/lukk.png", "Bilder/lukkRollover.png", "lukk");

        minimer.addActionListener(new Knapplytter());
        lukk.addActionListener(new Knapplytter());

        // fjerner FocusPaint fra knappene
        minimer.setFocusPainted(false);
        lukk.setFocusPainted(false);

        minimer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lukk.setCursor(new Cursor(Cursor.HAND_CURSOR));

        p.add(minimer);

        p.add(lukk);
        minimer.setBorder(null);
        lukk.setBorder(null);

        p.revalidate();
        p.repaint();

        //legger til knapper for å lukke, minimere og 
        menybar = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        menybar.setBackground(blåfarge);
        menybar.add(p);
       
        setLocationRelativeTo(null);

    }//gjørFlyttbar ferdig



    /**
     * Setter radhøyde på tabellen som er gitt som parameter
     *
     * @param tabell tabell som skal endres
     */
    private void setRadhøyde(JTable tabell) {
        try {
            faste_pasienter.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            faste_pasienter.setFillsViewportHeight(true);

            TableRowSorter<Pasientmodell> sorter = new TableRowSorter<>(pasientmodell);

            faste_pasienter.setRowSorter(sorter);
            sorter.setSortsOnUpdates(true);
            tabell.setFont(new Font("Serif", Font.PLAIN, 20));

            for (int rad = 0; rad < tabell.getRowCount(); rad++) {
                int radHøyde = tabell.getRowHeight();

                for (int kolonne = 0; kolonne < tabell.getColumnCount(); kolonne++) {
                    Component comp = tabell.prepareRenderer(tabell.getCellRenderer(rad, kolonne), rad, kolonne);
                    radHøyde = 24;
                    //Math.max(rowHeight, comp.getPreferredSize().height);
                }

                tabell.setRowHeight(rad, radHøyde);
            }
        } catch (ClassCastException e) {
        }
    }//setRadhøyde ferdig

    /**
     * Åpner login-vinduet på nytt
     */
    public void login() {
        Login login = new Login(register);
        login.setLocationRelativeTo(null);
    }//login ferdig

    /**
     * Lager tabell
     */
    public void lagTabell() {
        faste_pasienter = new JTable();
        tabellScroll = new JScrollPane(faste_pasienter);
        faste_pasienter.addMouseListener(new Muslytter());

        LinkedList<Pasient> faste_pasienter_liste = new LinkedList<>();

        LinkedList<Pasient> pasientliste = register.getPasientliste();
        for (Pasient pasient : pasientliste) {

            if (pasient.getFastlege() == lege) {
                faste_pasienter_liste.add(pasient);
            }
        }

        pasientmodell = new Pasientmodell(kolonner, faste_pasienter_liste);
        faste_pasienter.setModel(pasientmodell);

    }//lagTabell ferdig

    /**
     * Lager hentpanelet
     */
    public void konstruerHentPanel() {
        JLabel overskriftLabel = new JLabel(lege.getNavn() + " - " + lege.getArbeidssted());

        overskriftLabel.setFont(new Font("Serif", Font.BOLD, 32));
        overskriftLabel.setForeground(Color.WHITE);
        overskriftpanel.add(overskriftLabel);

        søkpanel.add(personnummerFelt);
        søkpanel.add(hent);

        tabellpanel.add(tabellScroll, BorderLayout.CENTER);

        hentPanel = new JPanel();
        hentPanel.setLayout(new BoxLayout(hentPanel, BoxLayout.Y_AXIS));
        hentPanel.setBackground(blåfarge);

        overskriftpanel.setBackground(blåfarge);
        søkpanel.setBackground(blåfarge);
        tabellpanel.setBackground(blåfarge);

        hentPanel.add(overskriftpanel);
        hentPanel.add(søkpanel);

        hentPanel.add(tabellpanel);

    }//konstruerHentPanel ferdig

    /**
     * Viser informajsjon om pasient basert på persnr som skrives inn i
     * tekstfelt, hvis pasienten ikke er registrert tidligere må man registrere
     * pasienten
     */
    public void hent() {
        String personnummer = personnummerFelt.getText();
        if (personnummer.length() != 11 || !personnummer.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "Skriv inn gyldig fødsels og personnummer eller velg en pasient fra listen!");
            return;
        }

        if (personnummer.length() != 11) {
            try {
                pasient = pasientmodell.getValueAt(faste_pasienter.convertRowIndexToModel(faste_pasienter.getSelectedRow()));

                hent(pasient);
                return;
            } catch (IndexOutOfBoundsException boundsException) {
                JOptionPane.showMessageDialog(null, "Skriv inn gyldig fødsels og personnummer eller velg en pasient fra listen!");
                return;
            }
        }

        pasient = (Pasient) register.getPerson(personnummer);

        if (pasient != null) {
            vinduer.add(new VisPasient(pasient, lege, this), VIS_PASIENT);
            visPanel(VIS_PASIENT);
            leggTilTilbakeknapp();
            nullstill();

        } else {

            RegistrerPasient registrerPasient = new RegistrerPasient(personnummer, this, register);

            //vinduer.add(registrerPasient, REGISTRER_PASIENT);
            //visPanel(REGISTRER_PASIENT);
        }

    }//hent ferdig

    /**
     * Viser informasjon pasient basert på mottatt personnummer Parameter:
     * Personnumer til pasient som skal hentes ut
     *
     * @param personnummer personnummer til pasienten som skal hentes
     */
    public void hent(String personnummer) {

        if (personnummer.length() != 11 || !personnummer.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "Skriv inn gyldig fødsels og personnumemr!");
            return;
        }

        pasient = (Pasient) register.getPerson(personnummer);
        if (pasient != null) {
            vinduer.add(new VisPasient(pasient, lege, this), VIS_PASIENT);
            visPanel(VIS_PASIENT);
            leggTilTilbakeknapp();
            nullstill();
        } else {
            JOptionPane.showMessageDialog(null, "INTERN FEIL, FANT IKKE NY PASIENT ETTER REGISTRERING");
        }

    }//hent ferdig

    /**
     * Viser informasjon om pasient basert på mottatt pasient Parameter: Pasient
     * pasienten som skal vises informasjon for
     *
     * @param p pasienten som skal vises
     */
    public void hent(Pasient p) {
        pasient = p;
        vinduer.add(new VisPasient(p, lege, this), VIS_PASIENT);
        visPanel(VIS_PASIENT);
        leggTilTilbakeknapp();
        nullstill();

    }//hent ferdig

    /**
     * Legger til en tilbakeknapp som tar deg tilbake til hovedvinduet Metoden
     * fjerner også tilbakeknappen som tar deg tilbake til pasientoversikten
     */
    public void leggTilTilbakeknapp() {
        bunnpanel.remove(tilbake1);
        bunnpanel.add(tilbake);
        senterpanel.add(bunnpanel, BorderLayout.PAGE_START);
        revalidate();
        repaint();
    }//leggTilTilbakeknapp ferdig

    /**
     * Fjerner tilbake knappen som tar deg tilbake til hovedvinduet
     */
    public void fjernTilbakeknapp() {
        bunnpanel.remove(tilbake1);
        bunnpanel.add(tilbake);
        senterpanel.remove(bunnpanel);
        senterpanel.revalidate();
        senterpanel.repaint();
        revalidate();
        repaint();
    }//fjernTilbakeknapp ferdig

    /**
     * Fjerner tilbakeknappen som tar deg tilbake til pasientoversikten
     */
    public void fjernTilbakeknapp2() {
        bunnpanel.remove(tilbake);
        bunnpanel.add(tilbake1);
        senterpanel.remove(bunnpanel);
        senterpanel.revalidate();
        senterpanel.repaint();
        revalidate();
        repaint();
    }//fjernTilbakeknapp2 ferdig

    /**
     * Legger til tilbakeknapp som tar deg tilbake til pasientoversikten Metoden
     * fjerner også tilbakeknappen som tar deg tilbake til hovedvinduet
     */
    public void leggTilTilbakeaknapp2() {
        bunnpanel.remove(tilbake);
        bunnpanel.add(tilbake1);
        senterpanel.add(bunnpanel, BorderLayout.PAGE_START);
        revalidate();
        repaint();
    }//leggTilTilbakeaknapp2 ferdig

    /**
     * nullstiller personnummerfeltet
     */
    public void nullstill() {
        personnummerFelt.setText("");
    }//nullstill ferdig

    /**
     * nullstiller vinduet, viser panelet hvor man henter pasient og fjerner
     * bunnpanelet
     */
    public void tilbake() {
        nullstill();

        visPanel(HENT_PASIENT);
        fjernTilbakeknapp();
        fjernTilbakeknapp2();

        revalidate();
        repaint();

    }//tilbake ferdig

    /**
     * Sender deg tilbake fra reseptsiden til pasient p(fra parameter) sin side
     * med oversikt over resepter
     *
     * @param p pasienten som skal vises
     */
    public void tilbakeTilPasient(Pasient pasient) {
        endreTilbakeknappverdiTIL_HENT();

        vinduer.add(new VisPasient(pasient, lege, this), VIS_PASIENT);
        visPanel(VIS_PASIENT);
    }//tilbakeTilPasient ferdig

    /**
     * Endrer tilbakeknappens verdi fra hent til pasient
     */
    public void endreTilbakeknappverdiTIL_PASIENT() {

        bunnpanel.remove(tilbake);
        bunnpanel.add(tilbake1);
    }//endreTilbakeknappverdiTIL_PASIENT ferdig

    /**
     * Endrer tilbakeknappens verdi fra pasient til hent
     */
    public void endreTilbakeknappverdiTIL_HENT() {

        bunnpanel.remove(tilbake1);
        bunnpanel.add(tilbake);

    }//endreTilbakeknappveriTIL_HENT ferdig

    /**
     * Viser panelet vindu som er sendt inn som parameter
     *
     * @param vindu
     */
    private void visPanel(String vindu) {
        CardLayout cl = (CardLayout) vinduer.getLayout();
        remove(menybar);
        revalidate();
        add(menybar, BorderLayout.PAGE_START);
        revalidate();
        repaint();
        cl.show(vinduer, vindu);
    }//visPanel ferdig

    /**
     * Lager en bildeknapp med parameteret icon som ikon og med parameteret
     * rollover når man holder over ikonet
     *
     * @param icon path til iconet som skal legges til på knappen
     * @param rollover rollover tekst for knappen
     * @return knappen - med bilde og rolloverfunksjon dersom bildet ikke ble funnet, returneres en knapp med
     * filsti som tekst sånn at systemadministrator vet hva so,m har gått galt
     * 
     */
    private JButton lagBildeknapp(String icon, String rollover) {
        try {
            JButton knapp = new JButton();
            knapp.setContentAreaFilled(false);
            knapp.setBorderPainted(false);
            knapp.setIcon(new ImageIcon(getClass().getResource(icon)));
            knapp.setRolloverIcon(new ImageIcon(getClass().getResource(icon)));

            return knapp;
        } catch (NullPointerException npe) {
            JButton knapp = new JButton(icon);

            return knapp;
        }
    }//lagBildeknapp ferdig

    /**
     * Lager en bildeknapp med parameteret icon som ikon og med parameteret
     * rollover når man holder over ikonet og med parameteret tooltip som er en
     * informasjonsboks som kommer opp når man holder over knappen.
     *
     * @param icon filsti til bildet
     * @param rolloverIcon icon som skal vises dersom musen er over knappen
     * @param tooltip tekst som skal vises dersom musa holdes over knappen
     * @return bildeknappen - knapp med bilde på, dersom bildet ikke finnes, returneres en knapp med 
     * bildets filstin på, sånn at sytsemadministrator vet hva som har gått galt
     */
    private JButton lagBildeknapp(String icon, String rolloverIcon, String tooltip) {
        try {
            JButton knapp = new JButton();
            knapp.setContentAreaFilled(false);
            knapp.setBorderPainted(false);
            knapp.setToolTipText(tooltip);

            knapp.setRolloverEnabled(true);
            ImageIcon rollover = new ImageIcon(getClass().getResource(rolloverIcon));
            knapp.setRolloverIcon(new RolloverIcon(rollover));
            knapp.setIcon(new ImageIcon(getClass().getResource(icon)));

            return knapp;
        } catch (NullPointerException npe) {
            JButton knapp = new JButton(icon);

            return knapp;
        }
    }//lagBildeknapp ferdig

    /**
     * Viser alle pasienter etter at søket er nullstilt eller brukeren søkte på noe som ikke fiines
     */
    public void visAlle() {

        tabellpanel.remove(tabellScroll);

        revalidate();

        LinkedList<Pasient> liste = lege.getPasientliste();

        temp = liste;

        faste_pasienter = new JTable();
        pasientmodell = new Pasientmodell(kolonner, temp);
        faste_pasienter.setModel(pasientmodell);

        faste_pasienter.addMouseListener(new Muslytter());

        tabellScroll = new JScrollPane(faste_pasienter);

        faste_pasienter.setFillsViewportHeight(true);
        faste_pasienter.setBackground(Color.WHITE);

        tabellpanel.add(tabellScroll, BorderLayout.CENTER);

        setRadhøyde(faste_pasienter);

        revalidate();
        repaint();

    }//visAlle ferdig

    /**
     * Metode for å søke med fritekst i søkefeltet
     */
    public void frisøk() {

        String søk = personnummerFelt.getText();

        personnummerFelt.setBackground(Color.WHITE);

        if (søk.length() == 0) {
            visAlle();
            return;
        }

        tabellpanel.remove(tabellScroll);

        revalidate();

        LinkedList<Pasient> pasientliste = lege.getPasientliste();

        temp = new LinkedList<>();
        for (Pasient pasient : pasientliste) {

            String fornavn_etternavn = pasient.getFornavn() + " " + pasient.getEtternavn();
            String mellomnavn_etternavn = pasient.getMellomnavn() + " " + pasient.getEtternavn();

            if (pasient.getFastlege() != null) {
                if (pasient.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                        || pasient.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                        || pasient.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                        || pasient.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                        || pasient.getFastlege().getNavn().toUpperCase().startsWith(søk.toUpperCase())
                        || String.valueOf(pasient.getID()).startsWith(søk)
                        || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                        || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                    temp.add(pasient);
                }
            } else {
                if (pasient.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                        || pasient.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                        || pasient.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                        || pasient.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                        || String.valueOf(pasient.getID()).startsWith(søk)
                        || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                        || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                    temp.add(pasient);
                }
            }
        }

        if (temp.isEmpty()) {
            visAlle();
            return;
        }

        pasientmodell = new Pasientmodell(kolonner, temp);

        faste_pasienter = new JTable(pasientmodell);

        faste_pasienter.addMouseListener(new Muslytter());

        tabellScroll = new JScrollPane(faste_pasienter);

        tabellpanel.add(tabellScroll, BorderLayout.CENTER);

        setRadhøyde(faste_pasienter);
        revalidate();
        repaint();

    }//frisøk ferdig

    /**
     * Knapperlytter som lytter på knappene, implementerer ActionListener
     */
    private class Knapplytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == hent || e.getSource() == personnummerFelt) {
                hent();
            } else if (e.getSource() == tilbake) {

                tilbake();

            } else if (e.getSource() == tilbake1) {
                tilbakeTilPasient(pasient);

            } else if (e.getSource() == lukk || e.getSource() == exitItem) {
                dispose();
                //tray.remove(trayIcon);
                login();
            } else if (e.getSource() == minimer) {
                setState(ICONIFIED);
            }

        }//actionPerformed ferdig

    }//Knapplytter ferdig

    /**
     * Muslytter som lytter på musen, implementerer MouseListener
     */
    private class Muslytter implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                Pasient p = pasientmodell.getValueAt(faste_pasienter.convertRowIndexToModel(faste_pasienter.getSelectedRow()));
                hent(p);
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
        }//mouseEntered ferdig

        @Override
        public void mouseExited(MouseEvent e) {
        }//mouseExited ferdig

    }//Muslytter ferdig

    /**
     * Tekstlyter som lytter på teksten, implementerer DocumentListener
     *
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
}//Mainframe ferdig
