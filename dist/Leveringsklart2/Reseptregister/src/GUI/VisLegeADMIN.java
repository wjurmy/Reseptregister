/**
 * Klassen VisLegeADMIN gjørs som navnet sier, den viser en gitt lege i systemet
 */
package GUI;

import Objekter.Lege;
import Objekter.Pasient;
import Objekter.Preparat;
import Objekter.Resept;
import Registerklasser.Reseptmap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
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
public class VisLegeADMIN extends JPanel implements DocumentListener {
    
    private Color blåfarge = Color.decode("#007ec5");

    private JPanel overskriftpanel;
    private JSplitPane splitt;
    private JPanel reseptsøkpanel;
    private JPanel resepttabellpanel;

    private JPanel pasientsøkpanel;
    private JPanel pasienttabellpanel;

    private JTextField reseptsøkfelt;
    
    private VisLegeReseptmodellADMIN reseptmodell;
    private JTable resepttabell;
    private JScrollPane reseptscroll;
    private String[] reseptkolonner = {"UTSKREVET", "PREPARAT", "ATC-KODE", "KATEGORI", "RESEPTGRUPPE", "MENGDE", "PASIENT"};
    

    private JTextField pasientsøkfelt;
    
    private Pasientmodell pasientmodell;
    private JTable pasienttabell;
    private JScrollPane pasientscroll;
    private String[] pasientkolonner = {"FØDSELSNUMMER", "NAVN", "#GRUPPE A", "#GRUPPE B", "#GRUPPE C", "#TOTALT"};
    

    private JLabel overskriftslabel;

    private Lege lege;
    private Reseptmap resepter;

    private LinkedList<Resept> reseptliste;
    private LinkedList<Resept> resepttemp;

    private LinkedList<Pasient> pasientliste;
    private LinkedList<Pasient> pasienttemp;

    /**
     * Konstruktør for VisLegeADMIN, initialiserer datafelter
     * @param lege lege osm skal vises
     */
    public VisLegeADMIN(Lege lege) {
        super(new BorderLayout());
        this.lege = lege;
        resepter = Reseptregister.getReseptliste();
        initialiser();
        lagReseptpanel();
        lagPasientpanel();
        

        reseptliste = resepter.getReseptliste(lege.getID());
        pasientliste = lege.getPasientliste();

        splitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        splitt.add(resepttabellpanel);
        splitt.add(pasienttabellpanel);
        splitt.setResizeWeight(.5);

        overskriftslabel.setForeground(Color.WHITE);
        overskriftpanel.add(overskriftslabel);
        setRadhøyde(resepttabell);
        setRadhøyde(pasienttabell);
        add(overskriftpanel, BorderLayout.NORTH);
        add(splitt, BorderLayout.CENTER);

    }//VisLegeADMIN ferdig

    /**
     * setter radhøyde i tabellen som er tatt imot som parameter
     * @param tabell tabell som skal endres
     */
    private void setRadhøyde(JTable tabell) {
        try {

            for (int rad = 0; rad < tabell.getRowCount(); rad++) {
                int radHøyde = tabell.getRowHeight();

                for (int kolonne = 0; kolonne < tabell.getColumnCount(); kolonne++) {
                    Component comp = tabell.prepareRenderer(tabell.getCellRenderer(rad, kolonne), rad, kolonne);
                    radHøyde = 32;
                    

                }

//
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
    }//setRadhøyde ferdig

    /**
     * Initialiserer tekstfelter, knapper, osv
     */
    public void initialiser() {
        overskriftpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        overskriftpanel.setBackground(blåfarge);

        reseptsøkpanel = new JPanel();
        resepttabellpanel = new JPanel();
        pasientsøkpanel = new JPanel();
        pasienttabellpanel = new JPanel();

        reseptsøkfelt = new JTextField(10);
        
        TextPrompt søk1 = new TextPrompt("Søk...", reseptsøkfelt);
        søk1.setHorizontalAlignment(JTextField.CENTER);
        reseptsøkfelt.setHorizontalAlignment(JTextField.CENTER);
        

        søk1.changeAlpha(0.5f);
        søk1.setFont(new Font("Serif", Font.BOLD, 14));
        
       

        reseptsøkfelt.getDocument().addDocumentListener(this);

        reseptmodell = new VisLegeReseptmodellADMIN(reseptkolonner, resepter.getReseptliste(lege.getID()));

        resepttabell = new JTable(reseptmodell);

        reseptscroll = new JScrollPane(resepttabell);

        resepttabell.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (e.getSource() == resepttabell) {
                        System.out.println("Legetabell");
                    }
                    System.out.println("Dobbelklikk på resepttabell");

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
        });
        

        pasientsøkfelt = new JTextField(10);
        
        TextPrompt søk2 = new TextPrompt("Søk...", pasientsøkfelt);
        søk2.setHorizontalAlignment(JTextField.CENTER);
        pasientsøkfelt.setHorizontalAlignment(JTextField.CENTER);
        

        søk2.changeAlpha(0.5f);
        søk2.setFont(new Font("Serif", Font.BOLD, 14));

        pasientsøkfelt.getDocument().addDocumentListener(this);

        pasientmodell = new Pasientmodell(pasientkolonner, lege.getPasientliste());
        pasienttabell = new JTable(pasientmodell);
        

        pasientscroll = new JScrollPane(pasienttabell);

        overskriftslabel = new JLabel("Legenummer: " + lege.getID() + " - NAVN: " + lege.getNavn() + " - AREBIEDSSTED: " + lege.getArbeidssted());
        overskriftslabel.setFont(new Font("Serif", Font.BOLD, 24));
        
        
        
        TableRowSorter<Pasientmodell> pasientsorter = new TableRowSorter<>(pasientmodell);

        pasienttabell.setRowSorter(pasientsorter);
        pasientsorter.setSortsOnUpdates(true);
        
        TableRowSorter<VisLegeReseptmodellADMIN> reseptsorter = new TableRowSorter<>(reseptmodell);

        resepttabell.setRowSorter(reseptsorter);
        reseptsorter.setSortsOnUpdates(true);
        
        

    }//initialiser ferdig

    /**
     * Lager reseptpanelet
     */
    public void lagReseptpanel() {
        reseptsøkpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        reseptsøkpanel.add(reseptsøkfelt);
       

        reseptsøkpanel.setBackground(Color.WHITE);
        resepttabellpanel.setBackground(Color.WHITE);

        resepttabellpanel.setLayout(new BorderLayout());

        resepttabellpanel.add(reseptsøkpanel, BorderLayout.NORTH);
        resepttabellpanel.add(reseptscroll, BorderLayout.CENTER);

        lagBorder(resepttabellpanel, "RESEPTHISTORIKK");
    }//lagReseptpanel ferdig

    /**
     * Lager pasientpanelet
     */
    public void lagPasientpanel() {
        pasientsøkpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        pasientsøkpanel.add(pasientsøkfelt);
     

        pasientsøkpanel.setBackground(Color.WHITE);
        pasienttabellpanel.setBackground(Color.WHITE);

        pasienttabellpanel.setLayout(new BorderLayout());

        pasienttabellpanel.add(pasientsøkpanel, BorderLayout.NORTH);
        pasienttabellpanel.add(pasientscroll, BorderLayout.CENTER);

        lagBorder(pasienttabellpanel, "FASTE PASIENTER");

    }//lagPasientpanel ferdig

    /**
     * Lager grenser for panelet som er tatt imot som parameter med tittelen tittel
     * @param p panel som skal settes border på
     * @param tittel tittelent til borderen 
     */
    private void lagBorder(JPanel p, String tittel) {
        p.setBorder(BorderFactory.createTitledBorder(tittel));
    }//lagBorder ferdig

    /**
     * Søker etter pasient
     */
    public void pasientsøk() {
        String søk = pasientsøkfelt.getText();

        

        pasienttabellpanel.remove(pasientscroll);
        revalidate();

        Iterator it = pasientliste.iterator();
        String lowerSøk = søk.toLowerCase();

        pasienttemp = new LinkedList<Pasient>();

        while (it.hasNext()) {
            Pasient pasient = (Pasient) it.next();

            String fornavn = pasient.getFornavn().toLowerCase();
            String mellomnavn = pasient.getMellomnavn().toLowerCase();
            String etternavn = pasient.getEtternavn().toLowerCase();
            String navn = pasient.getNavn().toLowerCase();
            String fornavn_etternavn = fornavn + " " + etternavn;
            fornavn_etternavn = fornavn_etternavn.toLowerCase();
            String mellomnavn_etternavn = mellomnavn + " " + etternavn;
            mellomnavn_etternavn = mellomnavn_etternavn.toLowerCase();

            if (fornavn.startsWith(lowerSøk) || mellomnavn.startsWith(lowerSøk) || etternavn.startsWith(lowerSøk) || navn.startsWith(lowerSøk)
                    || fornavn_etternavn.startsWith(lowerSøk) || mellomnavn_etternavn.startsWith(lowerSøk)) {
                pasienttemp.add(pasient);
            }

        }

        pasientmodell = new Pasientmodell(pasientkolonner, pasienttemp);

        pasienttabell = new JTable(pasientmodell);

        pasientscroll = new JScrollPane(pasienttabell);
        
        TableRowSorter<Pasientmodell> pasientsorter = new TableRowSorter<>(pasientmodell);

        pasienttabell.setRowSorter(pasientsorter);
        pasientsorter.setSortsOnUpdates(true);
        
        

        pasienttabellpanel.add(pasientscroll, BorderLayout.CENTER);
        setRadhøyde(pasienttabell);
        revalidate();
        repaint();
    }//pasientsøk ferdig

    /**
     * søker etter resept(er)
     */
    public void reseptsøk() {
        String søk = reseptsøkfelt.getText();

       

        resepttabellpanel.remove(reseptscroll);
        revalidate();

        Iterator it = reseptliste.iterator();
        String lowerSøk = søk.toLowerCase();

        resepttemp = new LinkedList<Resept>();

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
                resepttemp.add(resept);
            }

        }

        reseptmodell = new VisLegeReseptmodellADMIN(reseptkolonner, resepttemp);

        resepttabell = new JTable(reseptmodell);

        reseptscroll = new JScrollPane(resepttabell);
        
        
        TableRowSorter<VisLegeReseptmodellADMIN> reseptsorter = new TableRowSorter<>(reseptmodell);

        resepttabell.setRowSorter(reseptsorter);
        reseptsorter.setSortsOnUpdates(true);

        resepttabellpanel.add(reseptscroll, BorderLayout.CENTER);
        setRadhøyde(resepttabell);
        revalidate();
        repaint();

    }//reseptsøk ferdig

    @Override
    public void insertUpdate(DocumentEvent e) {

        if (e.getDocument() == pasientsøkfelt.getDocument()) {
            pasientsøk();
        } else if (e.getDocument() == reseptsøkfelt.getDocument()) {
            reseptsøk();
        }
    }//insertUpdate ferdig

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (e.getDocument() == pasientsøkfelt.getDocument()) {
            pasientsøk();
        } else if (e.getDocument() == reseptsøkfelt.getDocument()) {
            reseptsøk();
        }
    }//removeUpdate ferdig

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (e.getDocument() == pasientsøkfelt.getDocument()) {
            pasientsøk();
        } else if (e.getDocument() == reseptsøkfelt.getDocument()) {
            reseptsøk();
        }
    }//changedUpdate ferdig

}//VisLegeADMIN ferdig
