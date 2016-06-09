/**
 * Viser alle leger i systemet. gir muligheten til å endre leger, fjerne leger,
 * vise info om leger og registrere ny leger 
 */
package GUI;

/**
 * sist endret: 12.05.14
 * @author Vegard Lokreim
 */
import Objekter.Lege;
import Objekter.Pasient;
import Registerklasser.Register;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

/**

 * siste versjon: 12.05.14
 *
 * @author Walid Jurmy
 */
public class AlleLegerADMIN extends JPanel {

    private Color blåfarge = Color.decode("#007ec5");
    private JTextField søkfelt;
    private JCheckBox visAktive;
    private JCheckBox visFjernede;
    private JTable legetabell;
    private String[] kolonner = {"LEGENUMMER", "NAVN", "ARBEIDSSTED", "#A", "#B", "#C", "#TOTALT", "#FASTE PASIENTER", "BEV A", "BEV B", "BEV C", "STATUS"};
    private AdminLegemodell legemodell;
    private JButton visInfo;
    private JButton endre;
    private JButton nyLege;
    private JButton fjern;
    private JButton tilbake;
    private Register register;
    private JPanel topppanel;
    private JLabel toppLabel;
    private JPanel søkpanel;
    private JPanel tabellpanel;
    private JPanel bunnpanel;
    private JPanel oversiktpanel;
    private JPanel tilbakepanel;
    private JPanel vinduer;
    public static String OVERSIKT = "OVERSIKT";
    public static String REGISTRER_LEGE = "REGISTRER LEGE";
    public static String ENDRE = "ENDRE";
    public static String VIS_LEGE = "VIS LEGE";
    private JScrollPane tabellScroll;
    private JSplitPane splitt;
    private LinkedList<Lege> temp;
    private RegistrerLegeADMIN regLege;
    private boolean registrering_pågår;
    private boolean pågår;
    private JPopupMenu menu;
    private JMenuItem endreLegeItem;
    private JMenuItem visInformasjonItem;
    private final int finnesIkke = -1;

    /**
     * Kostruktør som sørger for at alle datafeltene blir initialisert og lager
     * GUI
     *
     * @param register tar imot en instans avvregisterklassen.
     */
    public AlleLegerADMIN(Register register) {
        super(new BorderLayout());
        this.register = register;
        initialiser();
        lagTopppanel();

        lagSøkpanel();
        lagTabellpanel();
        lagBunnpanel();

        legetabell.addMouseListener(new Muslytter());

        temp = (LinkedList<Lege>) register.getLegeliste();

        regLege = new RegistrerLegeADMIN(this, register);

        lagOversiktpanel();

        setRadhøyde(legetabell);

        vinduer.add(oversiktpanel, OVERSIKT);
        add(vinduer, BorderLayout.CENTER);

    }//Konstruktør ferdig

    /**
     * Initialiserer datafelter
     */
    private void initialiser() {

        vinduer = new JPanel(new CardLayout());

        splitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        søkfelt = new JTextField(20);
        søkfelt.setFont(new Font("Serif", Font.BOLD, 24));
        søkfelt.setForeground(blåfarge);

        søkfelt.getDocument().addDocumentListener(new Tekstlytter());

        TextPrompt søk = new TextPrompt("Søk...", søkfelt);
        søk.setHorizontalAlignment(JTextField.CENTER);
        søkfelt.setHorizontalAlignment(JTextField.CENTER);
        søkfelt.setBorder(null);

        søk.changeAlpha(0.5f);
        søk.setFont(new Font("Serif", Font.BOLD, 24));

        LinkedList<Lege> liste = (LinkedList<Lege>) register.getLegeliste();

        legetabell = new JTable();

        menu = new JPopupMenu();
        try {
            endreLegeItem = new JMenuItem("Endre lege", new ImageIcon(getClass().getResource("Bilder/EndreItem.png")));
            visInformasjonItem = new JMenuItem("Vis informasjon", new ImageIcon(getClass().getResource("Bilder/MerInfoItem.png")));
            endreLegeItem.addActionListener(new Knapplytter());
            visInformasjonItem.addActionListener(new Knapplytter());
        } catch (NullPointerException npe) {
            endreLegeItem = new JMenuItem("Endre Lege");
            visInformasjonItem = new JMenuItem("Informasjon");
            endreLegeItem.addActionListener(new Knapplytter());
            visInformasjonItem.addActionListener(new Knapplytter());
        }

        menu.add(endreLegeItem);

        menu.add(visInformasjonItem);

        legetabell.add(menu);

        tabellScroll = new JScrollPane(legetabell);

        this.setBackground(Color.red);

        legemodell = new AdminLegemodell(kolonner, liste);

        legetabell.setModel(legemodell);

        legetabell.addMouseListener(new Muslytter());

        try {
            visInfo = lagBildeknapp("Bilder/MerInfoLege.png");
            nyLege = lagBildeknapp("Bilder/RegistrerPerson.png");
            endre = lagBildeknapp("Bilder/Endre.png");
            fjern = lagBildeknapp("Bilder/FjernLege.png");
            tilbake = lagBildeknapp("Bilder/tilbake.png");
        } catch (NullPointerException npe) {
            visInfo = new JButton("Mer info");
            nyLege = new JButton("Registrer ny");
            endre = new JButton("Endre");
            fjern = new JButton("Fjern");
            tilbake = new JButton("Tilbake");
        }

        visInfo.setToolTipText("MER INFO");
        nyLege.setToolTipText("NY LEGE");
        endre.setToolTipText("ENDRE INFORMASJON");
        fjern.setToolTipText("FJERN");

        nyLege.addActionListener(new Knapplytter());
        visInfo.addActionListener(new Knapplytter());
        endre.addActionListener(new Knapplytter());
        tilbake.addActionListener(new Knapplytter());
        fjern.addActionListener(new Knapplytter());

        topppanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topppanel.setBackground(blåfarge);

        søkpanel = new JPanel(new FlowLayout());
        søkpanel.setBackground(blåfarge);
        tabellpanel = new JPanel(new BorderLayout());
        tabellpanel.setBackground(blåfarge);

        bunnpanel = new JPanel(new GridLayout(1, 4));

        tilbakepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tilbakepanel.setBackground(blåfarge);

        oversiktpanel = new JPanel();
        oversiktpanel.setBackground(Color.WHITE);
        oversiktpanel.setLayout(new BoxLayout(oversiktpanel, BoxLayout.Y_AXIS));

        tilbakepanel.add(tilbake);

    }//initialiser ferdig

    /**
     * Setter radhøyde og endrer skrift for tabell
     *
     * @param tabell tabellen som skal endres
     */
    private void setRadhøyde(JTable tabell) {
        try {
            TableRowSorter<AdminLegemodell> sorter = new TableRowSorter<>(legemodell);

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

                tabell.setRowHeight(rad, radHøyde);

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
     * Nullstiller søket, og viser alle registrerte leger i systemet
     *
     */
    public void visAlle() {

        if (visAktive.isSelected() && !visFjernede.isSelected()) {
            tabellpanel.remove(tabellScroll);
            tabellpanel.remove(splitt);
            topppanel.remove(toppLabel);
            søkfelt.setBackground(Color.WHITE);
            revalidate();

            LinkedList<Lege> liste = (LinkedList<Lege>) register.getLegeliste();

            temp = liste;

            legetabell = new JTable();
            tabellScroll = new JScrollPane(legetabell);

            legemodell = new AdminLegemodell(kolonner, liste);

            legetabell.setModel(legemodell);

            legetabell.addMouseListener(new Muslytter());

            tabellpanel.add(tabellScroll);

            toppLabel = new JLabel("Legeovesikt - Antall leger: " + register.getLegeliste().size());
            toppLabel.setForeground(Color.WHITE);
            toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
            topppanel.add(toppLabel);
            setRadhøyde(legetabell);
            revalidate();
            repaint();
        } else if (visAktive.isSelected() && visFjernede.isSelected()) {
            tabellpanel.remove(tabellScroll);
            tabellpanel.remove(splitt);
            topppanel.remove(toppLabel);
            søkfelt.setBackground(Color.WHITE);
            revalidate();

            LinkedList<Lege> liste = (LinkedList<Lege>) register.getAlleLeger();

            temp = liste;

            legetabell = new JTable();
            tabellScroll = new JScrollPane(legetabell);

            legemodell = new AdminLegemodell(kolonner, liste);

            legetabell.setModel(legemodell);

            legetabell.addMouseListener(new Muslytter());

            tabellpanel.add(tabellScroll);

            toppLabel = new JLabel("Legeovesikt - Antall leger: " + register.getAlleLeger().size());
            toppLabel.setForeground(Color.WHITE);
            toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
            topppanel.add(toppLabel);
            setRadhøyde(legetabell);
            revalidate();
            repaint();
        } else if (!visAktive.isSelected() && visFjernede.isSelected()) {
            tabellpanel.remove(tabellScroll);
            tabellpanel.remove(splitt);
            topppanel.remove(toppLabel);
            søkfelt.setBackground(Color.WHITE);
            revalidate();

            LinkedList<Lege> liste = (LinkedList<Lege>) register.getFjernedeLeger();

            temp = liste;

            legetabell = new JTable();
            tabellScroll = new JScrollPane(legetabell);

            legemodell = new AdminLegemodell(kolonner, liste);

            legetabell.setModel(legemodell);

            legetabell.addMouseListener(new Muslytter());

            tabellpanel.add(tabellScroll);

            toppLabel = new JLabel("Legeovesikt - Antall leger: " + register.getFjernedeLeger().size());
            toppLabel.setForeground(Color.WHITE);
            toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
            topppanel.add(toppLabel);
            setRadhøyde(legetabell);
            revalidate();
            repaint();
        } else {
            tabellpanel.remove(tabellScroll);
            tabellpanel.remove(splitt);
            topppanel.remove(toppLabel);
            søkfelt.setBackground(Color.WHITE);
            revalidate();

            LinkedList<Lege> liste = new LinkedList<>();

            temp = liste;

            legetabell = new JTable();
            tabellScroll = new JScrollPane(legetabell);

            legemodell = new AdminLegemodell(kolonner, liste);

            legetabell.setModel(legemodell);

            legetabell.addMouseListener(new Muslytter());

            tabellpanel.add(tabellScroll);

            toppLabel = new JLabel("Legeovesikt");
            toppLabel.setForeground(Color.WHITE);
            toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
            topppanel.add(toppLabel);
            setRadhøyde(legetabell);
            revalidate();
            repaint();
        }

    }//visAlle ferdig

    /**
     * Metode for å søke med fritekst i legeregisteret Programmet vil vise alle
     * leger som matcher med gitt søk
     */
    public void frisøk() {

        if (visAktive.isSelected() && !visFjernede.isSelected()) {
            String søk = søkfelt.getText();

            if (søk.length() == 0) {
                visAlle();

                return;

            }

            try {
                if (søk.substring(0, søk.length() - 1).matches("[0-9]+") && (søk.substring(søk.length() - 1).equalsIgnoreCase(".") || søk.substring(søk.length() - 1).equalsIgnoreCase(","))) {
                    søk = søk.substring(0, søk.length() - 1);

                    Lege lege = (Lege) register.getPerson(søk);
                    System.out.println(lege.getNavn());

                    tabellpanel.remove(tabellScroll);
                    topppanel.remove(toppLabel);
                    revalidate();

                    LinkedList<Lege> søkliste = new LinkedList<>();
                    søkliste.add(lege);

                    temp = søkliste;

                    if (søkliste.isEmpty()) {
                        søkfelt.setBackground(Color.decode("#fd6d6d"));
                    }

                    legemodell = new AdminLegemodell(kolonner, søkliste);

                    legetabell = new JTable(legemodell);

                    legetabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    legetabell.setFillsViewportHeight(true);

                    tabellScroll = new JScrollPane(legetabell);

                    tabellpanel.add(tabellScroll);

                    toppLabel = new JLabel("Legeovesikt - Antall leger funnet: " + søkliste.size());
                    toppLabel.setForeground(Color.WHITE);
                    toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
                    topppanel.add(toppLabel);

                    revalidate();
                    repaint();
                    setRadhøyde(legetabell);
                    return;

                }
            } catch (IndexOutOfBoundsException exception) {
            }

            søkfelt.setBackground(Color.WHITE);

            tabellpanel.remove(tabellScroll);
            topppanel.remove(toppLabel);
            revalidate();

            LinkedList<Lege> legeliste = (LinkedList<Lege>) register.getLegeliste();

            LinkedList<Lege> søkliste = new LinkedList<>();
            for (Lege lege : legeliste) {

                String fornavn_etternavn = lege.getFornavn() + " " + lege.getEtternavn();
                String mellomnavn_etternavn = lege.getMellomnavn() + " " + lege.getEtternavn();

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

            temp = søkliste;

            if (søkliste.isEmpty()) {
                søkfelt.setBackground(Color.decode("#fd6d6d"));
            }

            legemodell = new AdminLegemodell(kolonner, søkliste);

            legetabell = new JTable(legemodell);

            legetabell.addMouseListener(new Muslytter());

            legetabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            legetabell.setFillsViewportHeight(true);

            tabellScroll = new JScrollPane(legetabell);

            tabellpanel.add(tabellScroll);

            toppLabel = new JLabel("Legeovesikt - Antall leger funnet: " + søkliste.size());
            toppLabel.setForeground(Color.WHITE);
            toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
            topppanel.add(toppLabel);
            setRadhøyde(legetabell);
            revalidate();
            repaint();
        } else if (visAktive.isSelected() && visFjernede.isSelected()) {
            String søk = søkfelt.getText();

            if (søk.length() == 0) {
                visAlle();

                return;

            }

            try {
                if (søk.substring(0, søk.length() - 1).matches("[0-9]+") && (søk.substring(søk.length() - 1).equalsIgnoreCase(".") || søk.substring(søk.length() - 1).equalsIgnoreCase(","))) {
                    søk = søk.substring(0, søk.length() - 1);

                    Lege lege = (Lege) register.getPerson(søk);

                    tabellpanel.remove(tabellScroll);
                    topppanel.remove(toppLabel);
                    revalidate();

                    LinkedList<Lege> søkliste = new LinkedList<>();
                    søkliste.add(lege);

                    temp = søkliste;

                    if (søkliste.isEmpty()) {
                        søkfelt.setBackground(Color.decode("#fd6d6d"));
                    }

                    legemodell = new AdminLegemodell(kolonner, søkliste);

                    legetabell = new JTable(legemodell);

                    legetabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    legetabell.setFillsViewportHeight(true);

                    tabellScroll = new JScrollPane(legetabell);

                    tabellpanel.add(tabellScroll);

                    toppLabel = new JLabel("Legeovesikt - Antall leger funnet: " + søkliste.size());
                    toppLabel.setForeground(Color.WHITE);
                    toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
                    topppanel.add(toppLabel);

                    revalidate();
                    repaint();
                    setRadhøyde(legetabell);
                    return;

                }
            } catch (IndexOutOfBoundsException exception) {
            }

            søkfelt.setBackground(Color.WHITE);

            tabellpanel.remove(tabellScroll);
            topppanel.remove(toppLabel);
            revalidate();

            LinkedList<Lege> legeliste = (LinkedList<Lege>) register.getAlleLeger();

            LinkedList<Lege> søkliste = new LinkedList<>();

            Iterator it = legeliste.iterator();

            while (it.hasNext()) {
                Lege lege = (Lege) it.next();

                String fornavn_etternavn = lege.getFornavn() + " " + lege.getEtternavn();
                String mellomnavn_etternavn = lege.getMellomnavn() + " " + lege.getEtternavn();

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

            temp = søkliste;

            if (søkliste.isEmpty()) {
                søkfelt.setBackground(Color.decode("#fd6d6d"));
            }

            legemodell = new AdminLegemodell(kolonner, søkliste);

            legetabell = new JTable(legemodell);

            legetabell.addMouseListener(new Muslytter());

            legetabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            legetabell.setFillsViewportHeight(true);

            tabellScroll = new JScrollPane(legetabell);

            tabellpanel.add(tabellScroll);

            toppLabel = new JLabel("Legeovesikt - Antall leger funnet: " + søkliste.size());
            toppLabel.setForeground(Color.WHITE);
            toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
            topppanel.add(toppLabel);
            setRadhøyde(legetabell);
            revalidate();
            repaint();
        } else if (!visAktive.isSelected() && visFjernede.isSelected()) {
            String søk = søkfelt.getText();

            if (søk.length() == 0) {
                visAlle();

                return;

            }

            try {
                if (søk.substring(0, søk.length() - 1).matches("[0-9]+") && (søk.substring(søk.length() - 1).equalsIgnoreCase(".") || søk.substring(søk.length() - 1).equalsIgnoreCase(","))) {
                    søk = søk.substring(0, søk.length() - 1);

                    Lege lege = (Lege) register.getPerson(søk);

                    tabellpanel.remove(tabellScroll);
                    topppanel.remove(toppLabel);
                    revalidate();

                    LinkedList<Lege> søkliste = new LinkedList<>();
                    søkliste.add(lege);

                    temp = søkliste;

                    if (søkliste.isEmpty()) {
                        søkfelt.setBackground(Color.decode("#fd6d6d"));
                    }

                    legemodell = new AdminLegemodell(kolonner, søkliste);

                    legetabell = new JTable(legemodell);

                    legetabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    legetabell.setFillsViewportHeight(true);

                    tabellScroll = new JScrollPane(legetabell);

                    tabellpanel.add(tabellScroll);

                    toppLabel = new JLabel("Legeovesikt - Antall leger funnet: " + søkliste.size());
                    toppLabel.setForeground(Color.WHITE);
                    toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
                    topppanel.add(toppLabel);

                    revalidate();
                    repaint();
                    setRadhøyde(legetabell);
                    return;

                }
            } catch (IndexOutOfBoundsException exception) {
            }

            søkfelt.setBackground(Color.WHITE);

            tabellpanel.remove(tabellScroll);
            topppanel.remove(toppLabel);
            revalidate();

            LinkedList<Lege> legeliste = (LinkedList<Lege>) register.getFjernedeLeger();

            LinkedList<Lege> søkliste = new LinkedList<>();

            Iterator it = legeliste.iterator();

            while (it.hasNext()) {
                Lege lege = (Lege) it.next();

                String fornavn_etternavn = lege.getFornavn() + " " + lege.getEtternavn();
                String mellomnavn_etternavn = lege.getMellomnavn() + " " + lege.getEtternavn();

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

            temp = søkliste;

            if (søkliste.isEmpty()) {
                søkfelt.setBackground(Color.decode("#fd6d6d"));
            }

            legemodell = new AdminLegemodell(kolonner, søkliste);

            legetabell = new JTable(legemodell);

            legetabell.addMouseListener(new Muslytter());

            legetabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            legetabell.setFillsViewportHeight(true);

            tabellScroll = new JScrollPane(legetabell);

            tabellpanel.add(tabellScroll);

            toppLabel = new JLabel("Legeovesikt - Antall leger funnet: " + søkliste.size());
            toppLabel.setForeground(Color.WHITE);
            toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
            topppanel.add(toppLabel);
            setRadhøyde(legetabell);
            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Velg Vis aktive, Vis Fjernede eller begge!");
        }

    }// frisøk ferdig

    /**
     * Lager panelet som skal ligge i toppen av vinduet.
     */
    public void lagTopppanel() {
        toppLabel = new JLabel("Legeovesikt - Antall leger: " + register.getLegeliste().size());
        toppLabel.setForeground(Color.WHITE);
        toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
        topppanel.add(toppLabel);
    }// lagTopppanel ferdig

    /**
     * Lager panelet hvor søkefeltet skal ligge.
     */
    public void lagSøkpanel() {
        visAktive = new JCheckBox("Vis aktive leger");
        visAktive.setSelected(true);
        visFjernede = new JCheckBox("Vis fjernede leger");
        visFjernede.setSelected(false);

        visAktive.setFont(new Font("Serif", Font.BOLD, 24));
        visAktive.setForeground(Color.WHITE);

        visFjernede.setFont(new Font("Serif", Font.BOLD, 24));
        visFjernede.setForeground(Color.WHITE);

        visAktive.addActionListener(new Knapplytter());
        visFjernede.addActionListener(new Knapplytter());

        søkpanel.add(søkfelt);
        søkpanel.add(visAktive);
        søkpanel.add(visFjernede);
    }//lagSøkpanel ferdig

    /**
     * Lager panelet hvor tabellen skal være
     */
    public void lagTabellpanel() {
        tabellpanel.add(tabellScroll, BorderLayout.CENTER);
    }//lagTabellpanel ferdig

    /**
     * Lager panelet som skal være i bunnen av vinduet.
     */
    public void lagBunnpanel() {
        bunnpanel.setBackground(blåfarge);
        bunnpanel.add(nyLege);
        bunnpanel.add(visInfo);
        bunnpanel.add(endre);
        bunnpanel.add(fjern);
    }//lagBunnpanel ferdig

    /**
     * Lager hele panelet som skal legges til vinduet Inneholder toppanel,
     * søkpanel, tabellpanel og bunnpanel
     */
    public void lagOversiktpanel() {

        oversiktpanel.add(topppanel);

        oversiktpanel.add(søkpanel);
        oversiktpanel.add(tabellpanel);
        oversiktpanel.add(bunnpanel);

    }//lagOversiktpanel ferdig

    /**
     * Legger til registreringspanel for ny lege på vinduet
     */
    public void nyLege() {
        if (!registrering_pågår) {

            tabellpanel.remove(tabellScroll);

            splitt.remove(tabellScroll);
            splitt.remove(regLege);

            revalidate();
            repaint();

            LinkedList<Lege> legeliste = (LinkedList<Lege>) register.getLegeliste();

            legemodell = new AdminLegemodell(kolonner, legeliste);

            legetabell = new JTable(legemodell);

            legetabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            legetabell.setFillsViewportHeight(true);

            legetabell.addMouseListener(new Muslytter());

            tabellScroll = new JScrollPane(legetabell);

            regLege = new RegistrerLegeADMIN(this, register);

            splitt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitt.setEnabled(false);

            splitt.add(regLege);
            splitt.add(tabellScroll);

            tabellpanel.add(splitt);
            setRadhøyde(legetabell);
            revalidate();
            repaint();

            registrering_pågår = true;

        }

    }//nyLege ferdig

    /**
     * Viser panel med identifikator vindu
     *
     * @param vindu navn på vindu som skal vises
     */
    public void visPanel(String vindu) {
        CardLayout cl = (CardLayout) vinduer.getLayout();

        menu = new JPopupMenu();
        endreLegeItem = new JMenuItem("Endre lege", new ImageIcon(getClass().getResource("Bilder/EndreItem.png")));
        visInformasjonItem = new JMenuItem("Vis informasjon", new ImageIcon(getClass().getResource("Bilder/MerInfoItem.png")));
        endreLegeItem.addActionListener(new Knapplytter());
        visInformasjonItem.addActionListener(new Knapplytter());

        menu.add(endreLegeItem);

        menu.add(visInformasjonItem);

        legetabell.add(menu);
        setRadhøyde(legetabell);
        cl.show(vinduer, vindu);
    }//visPanel ferdig

    /**
     * Tar bort panelet for registrering av lege
     */
    public void ferdigRegistrert() {

        tabellpanel.remove(splitt);
        tabellpanel.remove(tabellScroll);
        tabellpanel.remove(splitt);
        topppanel.remove(toppLabel);
        revalidate();

        LinkedList<Lege> liste = (LinkedList<Lege>) register.getLegeliste();

        temp = liste;

        legetabell = new JTable();
        tabellScroll = new JScrollPane(legetabell);

        legemodell = new AdminLegemodell(kolonner, liste);

        legetabell.setModel(legemodell);

        legetabell.addMouseListener(new Muslytter());

        tabellScroll = new JScrollPane(legetabell);

        tabellpanel.add(tabellScroll);

        toppLabel = new JLabel("Legeovesikt - Antall leger: " + register.getLegeliste().size());
        toppLabel.setForeground(Color.WHITE);
        toppLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 28));
        topppanel.add(toppLabel);
        setRadhøyde(legetabell);
        revalidate();
        repaint();
        registrering_pågår = false;

    }//ferdigRegistrert ferdig

    /**
     * Lager knapp med bilde
     *
     * @param icon filsti til bildet som skal legge til på knappen
     * @return knapp -Jbutton med bilde på
     */
    private JButton lagBildeknapp(String icon) {
        JButton knapp = new JButton();
        knapp.setContentAreaFilled(false);
        knapp.setBorderPainted(false);
        knapp.setIcon(new ImageIcon(getClass().getResource(icon)));

        return knapp;
    }//lagBildeknapp ferdig

    /**
     * bytter panelet til informasjonspanel om legen.
     */
    public void visInfo() {

        try {
            Lege lege = legemodell.getValueAt(legetabell.convertRowIndexToModel(legetabell.getSelectedRow()));

            vinduer.add(new VisLegeADMIN(lege), VIS_LEGE);

            leggTilTilbakepanel();
            visPanel(VIS_LEGE);
        } catch (IndexOutOfBoundsException iobe) {
            JOptionPane.showMessageDialog(null, "For å vise informasjon om en lege, må du velge en lege fra tabellen!");
        }

    }//visInfo ferdig

    /**
     * fjerner tilbakepanelet
     */
    public void fjernTilbakepanel() {
        remove(tilbakepanel);

    }//fjernTilbakepanel ferdig

    /**
     * legger til tilbakepanelet med tilbakeknapp
     */
    public void leggTilTilbakepanel() {
        add(tilbakepanel, BorderLayout.SOUTH);
    }//leggTilTilbakepanel ferdig

    /**
     * Viser en dialogboks hvor man kan informasjonen til en lege. kaller på
     * EndreLegeADMIN klassen
     */
    public void endre() {
        try {
            Lege lege = null;

            lege = legemodell.getValueAt(legetabell.convertRowIndexToModel(legetabell.getSelectedRow()));

            new EndreLegeADMIN(lege, this);
            return;

        } catch (IndexOutOfBoundsException iobe) {
            JOptionPane.showMessageDialog(null, "For å endre informasjon må du velge en lege fra tabelln først!");
        }

    }//endre ferdig

    /**
     * Fjerner en valgt lege, dersom det er valgt en lege i tabellen. Hvis det
     * ikke valgt en lege gjøres det en unntakshåndtering for
     * IndexOutOfBoundsException og det vises en dialogmelding som forteller brukeren 
     * hva som skal til for å fjerne en lege
     */
    public void fjern() {

        try {
            int index = legetabell.convertRowIndexToModel(legetabell.getSelectedRow());
            Lege lege = null;
            if (index != finnesIkke) {
                lege = legemodell.getValueAt(index);
                if (lege == null) {
                    throw new IndexOutOfBoundsException("Velg en lege som skal fjernes!");
                }
            }

            int faste_pasiente = lege.getPasientliste().size();

            String spm = "Ønsker du vikelig å fjerne " + lege.getNavn() + ", ID:" + lege.getID() + " fra registeret?";

            if (faste_pasiente > 0) {
                spm += "\nDette fører til at " + faste_pasiente + " pasient";
                if (faste_pasiente > 1) {
                    spm += "er står uten fastlege";
                } else {
                    spm += " står uten fastlege";
                }
            }

            int svar = JOptionPane.showConfirmDialog(null, spm, "Fjern lege", JOptionPane.YES_NO_OPTION);

            if (svar == JOptionPane.YES_OPTION) {
                LinkedList<Pasient> pasientliste = new LinkedList<>();

                Iterator it = lege.getPasientliste().iterator();

                while (it.hasNext()) {
                    pasientliste.add((Pasient) it.next());
                }
                for (int i = 0; i < pasientliste.size(); i++) {
                    Pasient pasient = pasientliste.get(i);
                    pasient.fjernFastlege();
                    register.fjern(lege);
                    frisøk();
                    legemodell.fireTableDataChanged();
                    setRadhøyde(legetabell);
                }
            }
        } catch (IndexOutOfBoundsException iobe) {
            JOptionPane.showMessageDialog(null, "For å fjerne en lege, må du velge en lege fra tabellen!");

        }

    }//fjern ferdig

    /**
     * Tekstlytter som lytter på tekstfelt, implementerer DocumentListener
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
    }//tekstlytter ferdig

    /**
     * Knapplytter som lytter på knappene, implementerer ActionListener
     *
     */
    private class Knapplytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == nyLege) {
                nyLege();
            } else if (e.getSource() == visInfo || e.getSource() == visInformasjonItem) {
                if (!registrering_pågår) {
                    visInfo();
                } else {
                    JOptionPane.showMessageDialog(null, "Fullfør registreringen av lege eller trykk avbryt!");
                }
            } else if (e.getSource() == endre || e.getSource() == endreLegeItem) {
                if (!registrering_pågår) {
                    endre();
                } else {
                    JOptionPane.showMessageDialog(null, "Fullfør registreringen av lege eller trykk avbryt!");
                }
            } else if (e.getSource() == tilbake) {
                remove(tilbakepanel);
                visPanel(OVERSIKT);

            } else if (e.getSource() == fjern) {
                if (!registrering_pågår) {
                    fjern();
                } else {
                    JOptionPane.showMessageDialog(null, "Fullfør registreringen av lege eller trykk avbryt!");
                }

            } else if (e.getSource() == visAktive) {
                if (!registrering_pågår) {
                    visAlle();
                } else {
                    JOptionPane.showMessageDialog(null, "Fullfør registreringen av lege eller trykk avbryt!");
                }
            } else if (e.getSource() == visFjernede) {
                if (!registrering_pågår) {
                    visAlle();
                } else {
                    JOptionPane.showMessageDialog(null, "Fullfør registreringen av lege eller trykk avbryt!");
                }
            }
        }// actionPerformed ferdig
    }//knapplytter ferdig

    /**
     * Muslytter som lytter på musklikk, implementerer MouseListener
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
         * metode som viser popupmenyen dersom en dobbel eller høyreklikker
         *
         * @param e
         */
        private void doPop(MouseEvent e) {

            if (legetabell.getSelectedRow() != finnesIkke) {
                menu.show(e.getComponent(), e.getX(), e.getY());
            }

        }//doPop ferdig
    }//Muslytter ferdig
}//class AlleLegerADMIN ferdig
