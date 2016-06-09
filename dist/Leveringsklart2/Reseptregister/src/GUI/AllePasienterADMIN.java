/**
 * Klassen AllePasienterADMIN viser en oversikt over alle registrerte pasienter
 * i systemt Klassen er et JPanel med et overskriftpanel, søkpanel, tabellpanel
 * og bunnpanel Brukeren kan også endre, fjerne eller vise informasjon om en
 * pasient
 */
package GUI;

import Objekter.Lege;
import Objekter.Pasient;
import Registerklasser.Register;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

/**
 *
 * Siste oppdatering: 12.05.14
 *
 * @author Vegard og Walid Jurmy
 *
 */
public class AllePasienterADMIN extends JPanel {

    private Color blåfarge = Color.decode("#007ec5");
    //<editor-fold desc="FELTER FOR PASIENTPANEL>
    private JTextField søkfelt;
    private JButton tilbake;
    private JLabel toppLabel;
    private JTable pasienttabell;
    private String[] kolonner = {"PNR", "NAVN", "#A", "#B", "#C", "#TOTALT", "FASTLEGE"};
    private AdminPasientmodell pasientmodell;
    private JButton visInfo;
    private JButton endre;
    private JButton fjern;
    private JPanel tilbakepanel;
    private JPanel topppanel;
    private JPanel søkpanel;
    private JPanel tabellpanel;
    private JPanel bunnpanel;
    private JScrollPane sp;
    private JPanel oversiktpanel;
    private JPanel vinduer;
    public static final String VIS_ALLE = "VIS ALLE PASIENTER";
    public static final String VIS_INFORMASJON = "VIS INFORMASJON";
    public static final String ENDRE = "ENDRE";
    //</editor-fold>
    private Register register;
    private MainFrameADMIN parent;
    private LinkedList<Pasient> temp;
    private JPopupMenu menu;
    private JMenuItem endrePasientItem;
    private JMenuItem visInformasjonItem;
    private final int finnesIkke = -1;

    /**
     * konstruktør som initialiserer datafelter og lager GUI Parameter 1:
     * register er registeret Parameter 2: parent er MainFrameADMIN som brukes
     * for få tilgang til parent sine metoder
     *
     * @param register en insatns av Register
     * @param parent JFrame som panelet ligger i. Brukes til å gjøre endriger i
     * parentvinduet
     */
    public AllePasienterADMIN(Register register, MainFrameADMIN parent) {
        super(new BorderLayout());

        this.register = register;
        this.parent = parent;

        initialiser();
        lagTopppanel();
        lagSøkpanel();
        lagTabellpanel();
        lagBunnpanel();
        setRadhøyde(pasienttabell);
        pasienttabell.setBorder(null);

        pasienttabell.addMouseListener(new Muslytter());

        lagOversiktpanel();
        setLayout(new BorderLayout());
        vinduer.add(oversiktpanel, VIS_ALLE);
        add(vinduer, BorderLayout.CENTER);

    }//AllePasienterADMIN ferdig

    /**
     * initialiserer alle datafeltene i klassen
     */
    private void initialiser() {
        søkfelt = new JTextField(20);
        søkfelt.setFont(new Font("Serif", Font.BOLD, 24));

        try {
            tilbake = lagBildeknapp("Bilder/tilbake.png");
        } catch (NullPointerException npe) {
            tilbake = new JButton("Tilbake");
        }

        søkfelt.setForeground(blåfarge);

        søkfelt.getDocument().addDocumentListener(new Tekstlytter());

        TextPrompt søk = new TextPrompt("Søk...", søkfelt);
        søk.setHorizontalAlignment(JTextField.CENTER);
        søkfelt.setHorizontalAlignment(JTextField.CENTER);
        søkfelt.setBorder(null);

        søk.changeAlpha(0.5f);
        søk.setFont(new Font("Serif", Font.BOLD, 24));

        pasienttabell = new JTable();
        pasientmodell = new AdminPasientmodell(kolonner, (LinkedList<Pasient>) register.getPasientliste());
        pasienttabell.setModel(pasientmodell);

        pasienttabell.addMouseListener(new Muslytter());

        menu = new JPopupMenu();

        try {
            endrePasientItem = new JMenuItem("Endre pasient", new ImageIcon(getClass().getResource("Bilder/EndreItem.png")));
            visInformasjonItem = new JMenuItem("Vis informasjon", new ImageIcon(getClass().getResource("Bilder/MerInfoItem.png")));
            endrePasientItem.addActionListener(new Knapplytter());
            visInformasjonItem.addActionListener(new Knapplytter());
        } catch (NullPointerException npe) {
            endrePasientItem = new JMenuItem("Endre pasient");
            visInformasjonItem = new JMenuItem("Vis informasjon");
            endrePasientItem.addActionListener(new Knapplytter());
            visInformasjonItem.addActionListener(new Knapplytter());
        }

        menu.add(endrePasientItem);
        menu.add(visInformasjonItem);

        pasienttabell.add(menu);
        setRadhøyde(pasienttabell);

        try {
            visInfo = lagBildeknapp("Bilder/MerInfoPasient.png");
            endre = lagBildeknapp("Bilder/Endre.png");
            fjern = lagBildeknapp("Bilder/FjernPasient.png");
        } catch (NullPointerException npe) {
            visInfo = new JButton("Vis info");
            endre = new JButton("Endre");
            fjern = new JButton("Fjern");
        }

        visInfo.setToolTipText("MER INFO");
        endre.setToolTipText("ENDRE INFORMASJON");
        fjern.setToolTipText("FJERN");

        visInfo.addActionListener(new Knapplytter());
        endre.addActionListener(new Knapplytter());
        fjern.addActionListener(new Knapplytter());

        topppanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topppanel.setBackground(blåfarge);

        tilbakepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tilbakepanel.add(tilbake);
        tilbakepanel.setBackground(blåfarge);

        søkpanel = new JPanel(new FlowLayout());
        søkpanel.setBackground(blåfarge);
        tabellpanel = new JPanel(new BorderLayout());
        bunnpanel = new JPanel(new GridLayout(1, 3));

        tilbake.addActionListener(new Knapplytter());

        oversiktpanel = new JPanel();
        oversiktpanel.setLayout(new BorderLayout());
        vinduer = new JPanel(new CardLayout());

    }//initialiser ferdig

    /**
     * Viser alle pasienter i registeret etter at søket er nullstillt
     */
    public void visAlle() {

        tabellpanel.remove(sp);
        topppanel.remove(toppLabel);
        revalidate();

        LinkedList<Pasient> liste = (LinkedList<Pasient>) register.getPasientliste();

        temp = liste;

        pasienttabell = new JTable();

        pasienttabell.addMouseListener(new Muslytter());

        menu = new JPopupMenu();
        endrePasientItem = new JMenuItem("Endre pasient", new ImageIcon(getClass().getResource("Bilder/EndreItem.png")));
        visInformasjonItem = new JMenuItem("Vis informasjon", new ImageIcon(getClass().getResource("Bilder/MerInfoItem.png")));
        endrePasientItem.addActionListener(new Knapplytter());
        visInformasjonItem.addActionListener(new Knapplytter());

        menu.add(endrePasientItem);
        menu.add(visInformasjonItem);
        pasienttabell.add(menu);

        sp = new JScrollPane(pasienttabell);
        sp.setBorder(null);
        pasientmodell = new AdminPasientmodell(kolonner, liste);

        pasienttabell.setModel(pasientmodell);

        pasienttabell.addMouseListener(new Muslytter());

        pasienttabell.setFillsViewportHeight(true);
        pasienttabell.setBackground(Color.WHITE);

        sp.setBorder(null);
        tabellpanel.add(sp);

        toppLabel = new JLabel("Pasientoversikt - Antall pasienter: " + register.getPasientliste().size());
        toppLabel.setForeground(Color.WHITE);
        toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
        topppanel.add(toppLabel);
        setRadhøyde(pasienttabell);

        revalidate();
        repaint();

    }//visAlle ferdig

    /**
     * Metode for å søke med fritekst i pasientregisteret. Tabellen viser da en
     * oversikt over alle pasienter som matcher et gitt søk
     */
    public void frisøk() {

        String søk = søkfelt.getText();

        søkfelt.setBackground(Color.WHITE);

        if (søk.length() == 0) {
            visAlle();
            return;
        }

        tabellpanel.remove(sp);
        topppanel.remove(toppLabel);
        revalidate();

        LinkedList<Pasient> pasientliste = (LinkedList<Pasient>) register.getPasientliste();

        LinkedList<Pasient> søkliste = new LinkedList<>();
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

        temp = søkliste;

        if (søkliste.isEmpty()) {
            søkfelt.setBackground(Color.decode("#fd6d6d"));
        }

        pasientmodell = new AdminPasientmodell(kolonner, søkliste);

        pasienttabell = new JTable(pasientmodell);

        pasienttabell.addMouseListener(new Muslytter());

        menu = new JPopupMenu();
        endrePasientItem = new JMenuItem("Endre pasient", new ImageIcon(getClass().getResource("Bilder/EndreItem.png")));
        visInformasjonItem = new JMenuItem("Vis informasjon", new ImageIcon(getClass().getResource("Bilder/MerInfoItem.png")));
        endrePasientItem.addActionListener(new Knapplytter());
        visInformasjonItem.addActionListener(new Knapplytter());

        menu.add(endrePasientItem);
        menu.add(visInformasjonItem);

        pasienttabell.add(menu);

        sp = new JScrollPane(pasienttabell);
        sp.setBorder(null);
        tabellpanel.add(sp);

        String søkoversikt = søkfelt.getText();

        toppLabel = new JLabel("Pasientoversikt - Søk etter: " + søkoversikt + ", ga " + søkliste.size() + " treff");
        toppLabel.setForeground(Color.WHITE);
        toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
        topppanel.add(toppLabel);
        setRadhøyde(pasienttabell);
        revalidate();
        repaint();

    }//frisøk ferdig

    /**
     * Viser panelet String vindu. Vindu forteller hvilket vindu som skal vises
     * metoden legger også til en menybar til tabellen som bruks til å nå de
     * forksjellige funkjsonene i programmet
     *
     * @param vindu navnet på vinduet som skal vises
     */
    public void visPanel(String vindu) {
        CardLayout cl = (CardLayout) vinduer.getLayout();
        menu = new JPopupMenu();
        endrePasientItem = new JMenuItem("Endre pasient", new ImageIcon(getClass().getResource("Bilder/EndreItem.png")));
        visInformasjonItem = new JMenuItem("Vis informasjon", new ImageIcon(getClass().getResource("Bilder/MerInfoItem.png")));
        endrePasientItem.addActionListener(new Knapplytter());
        visInformasjonItem.addActionListener(new Knapplytter());

        menu.add(endrePasientItem);
        menu.add(visInformasjonItem);

        pasienttabell.add(menu);
        setRadhøyde(pasienttabell);
        cl.show(vinduer, vindu);
    }//visPanel ferdig

    /**
     * lager panelet som skal ligge øverst i panelet
     */
    public void lagTopppanel() {
        toppLabel = new JLabel("Pasientoversikt - Antall pasienter: " + register.getPasientliste().size());
        toppLabel.setForeground(Color.WHITE);
        toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
        topppanel.add(toppLabel);
    }//lagTopppanel ferdig

    /**
     * lager søkepanelet som inneholder et JTextField som brukes til å søke i
     * tabellen
     */
    public void lagSøkpanel() {

        søkpanel.add(søkfelt);
        søkfelt.getDocument().addDocumentListener(new Tekstlytter());
        søkfelt.setForeground(blåfarge);

    }//lager søkpanelet ferdig

    /**
     * lager tabellpanelet som viser alle pasientene i registeret
     */
    public void lagTabellpanel() {
        sp = new JScrollPane(pasienttabell);
        sp.setBorder(null);
        tabellpanel.add(søkpanel, BorderLayout.NORTH);
        tabellpanel.add(sp, BorderLayout.CENTER);
    }//lagTabellpanel ferdig

    /**
     * lager bunnpanelet som innheolder fjern pasient, endre pasient og vis info
     * om pasient
     */
    public void lagBunnpanel() {
        bunnpanel.setBackground(blåfarge);
        bunnpanel.add(visInfo);
        bunnpanel.add(endre);
        bunnpanel.add(fjern);
    }//lagBunnpanel ferdig

    /**
     * lager oversiktspanelet som inneholder toppane, søkpanel, tabellpanel og
     * bunnpanel
     */
    public void lagOversiktpanel() {

        oversiktpanel.add(topppanel, BorderLayout.NORTH);
        oversiktpanel.add(tabellpanel, BorderLayout.CENTER);
        oversiktpanel.add(bunnpanel, BorderLayout.SOUTH);
    }//lagOversiktpanel ferdig

    /**
     * Lager bildeknapp
     *
     * @param icon filsti til bildet som skal legges til en knapp
     * @return JButton - knapp med ønsket bilde
     */
    private JButton lagBildeknapp(String icon) {
        JButton knapp = new JButton();
        knapp.setContentAreaFilled(false);
        knapp.setBorderPainted(false);
        knapp.setIcon(new ImageIcon(getClass().getResource(icon)));

        return knapp;
    }//lagBildeknapp ferdig

    /**
     * Viser panelet for informasjon om pasient på vinduet.
     */
    public void visInfo() {
        try {

            Pasient pasient = pasientmodell.getValueAt(pasienttabell.convertRowIndexToModel(pasienttabell.getSelectedRow()));
            add(tilbakepanel, BorderLayout.PAGE_END);
            vinduer.add(new VisPasientADMIN(pasient), VIS_INFORMASJON);
            visPanel(VIS_INFORMASJON);
        } catch (IndexOutOfBoundsException iobe) {
            JOptionPane.showMessageDialog(null, "For å vise info om pasient, velg en pasient fra tabellen!");
        }
    }//visInfo ferdig

    /**
     * Metde som viser panelet for å endre pasientinformasjon på skjermen
     */
    public void endre() {
        try {
            Pasient pasient = pasientmodell.getValueAt(pasienttabell.convertRowIndexToModel(pasienttabell.getSelectedRow()));
            vinduer.add(new EndrePasientADMIN(pasient, register, this, parent), ENDRE);
            visPanel(ENDRE);
        } catch (IndexOutOfBoundsException iobe) {
            JOptionPane.showMessageDialog(null, "For å endre pasientinfo, må du først velge en pasient!");
        }

    }//endre ferdig

    /**
     * Fjerner pasient fra registeret
     */
    public void fjern() {

        try {
            Pasient pasient = pasientmodell.getValueAt(pasienttabell.convertRowIndexToModel(pasienttabell.getSelectedRow()));
            String spm = "ØNSKER DU VIRKELIG Å FJERNE: " + pasient.getID() + " - " + pasient.getNavn() + " ?";
            int svar = JOptionPane.showConfirmDialog(null, spm, "BEKREFT!", JOptionPane.YES_NO_OPTION);
            if (svar == JOptionPane.YES_OPTION) {
                if (pasient.getFastlege() != null) {
                    Lege fastlege = pasient.getFastlege();
                    fastlege.fjernPasient(pasient);

                    register.fjern(pasient);

                } else {
                    register.fjern(pasient);

                }
                pasientmodell.fireTableDataChanged();
                setRadhøyde(pasienttabell);
            }

        } catch (IndexOutOfBoundsException ioobe) {
            JOptionPane.showMessageDialog(null, "VELG PASIENT!");
            return;
        }

    }//fjern ferdig

    /**
     * Tekstlytter som lytter på teksten, implementerer DocumentListener
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
     * Knappelytter som lytter på knapper, implementerer ActionListener
     */
    private class Knapplytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == tilbake) {

                visPanel(VIS_ALLE);
                remove(tilbakepanel);
                revalidate();
                repaint();
            } else if (ae.getSource() == visInfo || ae.getSource() == visInformasjonItem) {
                visInfo();
            } else if (ae.getSource() == endre || ae.getSource() == endrePasientItem) {
                endre();
            } else if (ae.getSource() == fjern) {
                fjern();
            }
        }//actionPerformed ferdig
    }//Knapplytter ferdig

    /**
     * Setter radhøyde for mottatt tabell. Endrer også skrift og skriftstørrelse
     * for tabellen
     *
     * @param tabell tabellen som skal gjøres endringer på
     */
    private void setRadhøyde(JTable tabell) {
        try {

            TableRowSorter<AdminPasientmodell> sorter = new TableRowSorter<>(pasientmodell);

            tabell.setRowSorter(sorter);
            sorter.setSortsOnUpdates(true);

            tabell.setFont(new Font("Tahoma", Font.PLAIN, 14));

            for (int rad = 0; rad < tabell.getRowCount(); rad++) {
                int radHøyde = tabell.getRowHeight();

                for (int kolonne = 0; kolonne < tabell.getColumnCount(); kolonne++) {
                    Component comp = tabell.prepareRenderer(tabell.getCellRenderer(rad, kolonne), rad, kolonne);
                    radHøyde = 20;
                    //Math.max(rowHeight, comp.getPreferredSize().height);

                }

                tabell.setRowHeight(rad, radHøyde);

                tabell.setBackground(Color.WHITE);
                tabell.setFillsViewportHeight(true);
                tabell.setShowVerticalLines(true);
                tabell.setGridColor(blåfarge);

            }
        } catch (ClassCastException e) {
        }
    }//setRadhøyde ferdig

    /**
     * Muslytter som lytter på musen, implementerer MouseListener
     */
    private class Muslytter implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent me) {
            if (me.getClickCount() == 2) {
                doPop(me);
            }

        }//mouseClicked ferdig

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()) {
                doPop(e);

            }
        }//mousePressed ferdig

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                doPop(e);
            }
        }//mouseReleased ferdig

        @Override
        public void mouseEntered(MouseEvent me) {
        }//mouseEntered ferdig

        @Override
        public void mouseExited(MouseEvent me) {
        }//mouseExited ferdig

        /**
         * metode som viser popupmenyen dersom man dobbeltklikker eller
         * høyreklikker
         *
         * @param e
         */
        private void doPop(MouseEvent e) {
            try {
                int index = pasienttabell.convertRowIndexToModel(pasienttabell.getSelectedRow());
                if (index != finnesIkke) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }catch(IndexOutOfBoundsException iobe){
                System.out.println("velg en pasient");
            }

        }//doPop ferdig
    }//Muslytter ferdig
}//AllePasienterADMIN ferdig
