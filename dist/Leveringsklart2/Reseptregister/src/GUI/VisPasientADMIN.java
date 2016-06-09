 /**
  * ViserPasientinformasjon in adminvinduet
 */
package GUI;

import Objekter.Lege;
import Objekter.Pasient;
import Objekter.Preparat;
import Objekter.Resept;
import Registerklasser.Reseptmap;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import reseptregister.Reseptregister;

/**
 * Sist endret 12.05.14
 * @author Vegard Lokreim
 */
public final class VisPasientADMIN extends JPanel {

    private Color blåfarge = Color.decode("#007ec5");

    private JPanel topppanel;
    private JPanel søkpanel;
    private JPanel tabellpanel;
    private JPanel radiobuttonpanel;


    private JTextField søkfelt;

    private String[] søkskriterier = {"ATC-KODE", "PREPARAT", "RESEPTGRUPPE", "KATEGORI"};
    private static final int ATC = 0;
    private static final int PREPARAT = 1;
    private static final int RESEPTGRUPPE = 2;
    private static final int KATEGORI = 3;

    private JComboBox kriterier;

    private static JTable resepttabell;
    private static Reseptmodell reseptmodell;

    private static String[] kolonner = {"UTSKREVET", "MEDIKAMENT", "ATC-KODE", "KATEGORI", "RESEPTGRUPPE", "MENGDE", "ANSV.LEGE"};

    private JScrollPane tabellScroll;

    private Pasient pasient;

    private Reseptmap resepter;

    private JPanel oversiktpanel;
    private JPanel vinduer;

    public static final String NY_RESEPT = "NY RESEPT";
    public static final String OVERSIKT = "OVERSIKT";
    private static final String INFORMASJON = "VIS INFO";



    private LinkedList<Resept> temp;

    /**
     * Konstruktør for VisPasientADMIN, initialiserer datafelter og lager GUI
     *
     * @param pasient pasient som skal vises
     */
    public VisPasientADMIN(Pasient pasient) {
        setLayout(new BorderLayout());

        this.pasient = pasient;
 
        resepter = Reseptregister.getReseptliste();

        initialiser();
        konstruerTopp();
        konstruerSøk();
        konstruerTabell();
        //konstruerBunn();

        konstruerVindu();

        add(vinduer, BorderLayout.CENTER);

    }//VisPasientADMIN ferdig

    /**
     * Initialiserer tekstfelter, knapper, osv.
     */
    public void initialiser() {
        topppanel = new JPanel(new FlowLayout());
        søkpanel = new JPanel(new FlowLayout());
        tabellpanel = new JPanel(new BorderLayout());
        radiobuttonpanel = new JPanel(new FlowLayout());
        //bunnpanel = new JPanel(new FlowLayout());

        søkfelt = new JTextField(25);
        søkfelt.setFont(new Font("Serif", Font.BOLD, 24));
        
      
        søkfelt.setForeground(blåfarge);

        TextPrompt søk = new TextPrompt("Søk...", søkfelt);
        søk.setHorizontalAlignment(JTextField.CENTER);
        søkfelt.setHorizontalAlignment(JTextField.CENTER);
        søkfelt.setBorder(null);

        søk.changeAlpha(0.5f);
        søk.setFont(new Font("Serif", Font.BOLD, 24));

        søkfelt.getDocument().addDocumentListener(new DocumentListener() {

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
        });

        kriterier = new JComboBox(søkskriterier);
        kriterier.setFont(new Font("Serif", Font.BOLD, 24));
        
        
        
        reseptmodell = new Reseptmodell(kolonner, resepter.getReseptliste(pasient.getID()));
        resepttabell = new JTable(reseptmodell);
        
        tabellScroll = new JScrollPane(resepttabell);

        resepttabell.setFont(new Font("Serif", Font.PLAIN, 16));
        resepttabell.addMouseListener(new Muslytter());

        oversiktpanel = new JPanel();
        oversiktpanel.setLayout(new BoxLayout(oversiktpanel, BoxLayout.Y_AXIS));
        vinduer = new JPanel(new CardLayout());
        vinduer.add(oversiktpanel, OVERSIKT);

        if (pasient == null) {
            JOptionPane.showMessageDialog(null, "INTERN FEIL! Fant ikke pasient: " + pasient.getID());
            return;
        }

    }//initialiser ferdig

    /**
     * Setter radhøyde i tabellen tabell
     *
     * @param tabell tabell som skal endres
     */
    private void setRadhøyde(JTable tabell) {
        try {

            TableRowSorter<Reseptmodell> sorter = new TableRowSorter<>(reseptmodell);

            resepttabell.setRowSorter(sorter);
            sorter.setSortsOnUpdates(true);

            for (int rad = 0; rad < tabell.getRowCount(); rad++) {
                int radHøyde = tabell.getRowHeight();

                for (int kolonne = 0; kolonne < tabell.getColumnCount(); kolonne++) {
                    Component comp = tabell.prepareRenderer(tabell.getCellRenderer(rad, kolonne), rad, kolonne);
                    radHøyde = 24;

                    //Math.max(rowHeight, comp.getPreferredSize().height);
                }

                tabell.setRowHeight(rad, radHøyde);
                tabell.setFont(new Font("Tahoma", Font.PLAIN, 12));

                tabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                tabell.setFillsViewportHeight(true);
                tabell.setBackground(Color.WHITE);

                tabell.setShowVerticalLines(true);
                tabell.setShowHorizontalLines(false);

                tabell.setGridColor(blåfarge);

            }
        } catch (ClassCastException e) {
        }
    }//serRadhøyde ferdig

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
        søkpanel.setBackground(blåfarge);
    }//konstruerSøk ferdig

    /**
     * Lager tabell
     */
    public void konstruerTabell() {
        
        tabellpanel.add(tabellScroll);
        tabellpanel.setBackground(blåfarge);
        setRadhøyde(resepttabell);
    }//konstruerTabell ferdig

    /**
     * setter sammen vindu
     */
    public void konstruerVindu() {
        oversiktpanel.add(topppanel);

        oversiktpanel.add(søkpanel);
        oversiktpanel.add(tabellpanel);

    }//konstruerVindu ferdig

    /**
     * Metode for å kunne frisøke i tabeller
     */
    public void frisøk() {
        String søk = søkfelt.getText();

       
        tabellpanel.remove(tabellScroll);
        revalidate();

        if (søk.length() == 0) {
            visAlle();
            return;
        }

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

        tabellpanel.add(tabellScroll);
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
        resepttabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resepttabell.setFillsViewportHeight(true);

        TableRowSorter<Reseptmodell> sorter = new TableRowSorter<>(reseptmodell);

        resepttabell.setRowSorter(sorter);
        sorter.setSortsOnUpdates(true);
        tabellScroll = new JScrollPane(resepttabell);
        tabellpanel.add(tabellScroll);
        setRadhøyde(resepttabell);
        revalidate();
        repaint();
    }//visAlle ferdig

    

    /**
     * Viser informasjon om resept
     */
    public void visInformasjon() {
        Resept resept = reseptmodell.getValueAt(resepttabell.convertRowIndexToModel(resepttabell.getSelectedRow()));

        vinduer.add(new VisResept(resept), INFORMASJON);
        visPanel(INFORMASJON);
    }//visInformasjon ferdig

    /**
     * Viser panelet vindu
     *
     * @param vindu navnet til vinduet som skal vises
     */
    private void visPanel(String vindu) {
        CardLayout cl = (CardLayout) vinduer.getLayout();
        cl.show(vinduer, vindu);
    }//visPanel ferdig

    /**
     * Lager bildeknapp med parameteret icon som ikon
     *
     * @param icon filsti til ikonet som skal vises på knappen
     * @return bildeknapp - knapp med bilde på
     */
    private JButton lagBildeknapp(String icon) {
        JButton knapp = new JButton();
        knapp.setContentAreaFilled(false);
        knapp.setBorderPainted(false);
        knapp.setIcon(new ImageIcon(getClass().getResource(icon)));

        return knapp;
    }//lagBildeknapp ferdig

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
        }//mousePressed ferdig

        @Override
        public void mouseEntered(MouseEvent e) {
        }//mouseEntered ferdig

        @Override
        public void mouseExited(MouseEvent e) {
        }//mouseExited ferdig

    }//Muslytter ferdig
}//VisPasientADMIN ferdig
