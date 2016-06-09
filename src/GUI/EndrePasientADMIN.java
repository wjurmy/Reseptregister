/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Objekter.Lege;
import Objekter.Pasient;
import Registerklasser.Register;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JButton;
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

/**
 *
 * @author Vegard
 */
public class EndrePasientADMIN extends JPanel {

    private Color blåfarge = Color.decode("#007ec5");

    private JTextField personnummerfelt;
    private JTextField fornavnfelt;
    private JTextField mellomnavnfelt;
    private JTextField etternavnfelt;
    private JTextField fastlegefelt;

    private JLabel personnummerlabel;
    private JLabel fornavnlabel;
    private JLabel mellomnavnlabel;
    private JLabel etternavnlabel;
    private JLabel fastlegelabel;
    private JButton endreFastlege;
    private JButton lagreEndring;
    private JButton avbryt;

    private JTextField søkfelt;

    private JTable legetabell;
    private JScrollPane tabellScroll;
    private AdminLegemodellForPasient legemodell;
    private String[] kolonner = {"PERSONNUMMER", "NAVN", "ARBEIDSSTED", "A", "B", "C"};

    private Register register;
    private Pasient pasient;
    private Lege fastlege;

    private JPanel feltpanel;
    private JPanel tabellpanel;
    private JPanel søkpanel;
    private JPanel bunnpanel;
    private JPanel panel;
    private AllePasienterADMIN parent;

    private MainFrameADMIN grandparent;

    private LinkedList<Lege> temp; //liste som alle legene som treffer et gitt søk legges inn i. Listen endres derfor hver gang brukeren søker

    private final int finnesIkke = -1;

    /**
     * konstruktør som initialiserer datafelter og lager GUI
     *
     * @param pasient
     * @param register
     * @param parent
     * @param grandparent
     */
    public EndrePasientADMIN(Pasient pasient, Register register, AllePasienterADMIN parent, MainFrameADMIN grandparent) {

        super(new BorderLayout());
        this.register = register;
        this.pasient = pasient;
        this.parent = parent;
        this.grandparent = grandparent;

        lagOgOpprettGUI();
        setRadhøyde(legetabell);

        JLabel kjønn = new JLabel();
        boolean mann = true;

        JPanel overskriftpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel overskrift = new JLabel("Gjør endringer i pasientinformasjon for: " + pasient.getNavn());

        overskrift.setForeground(Color.WHITE);
        overskrift.setFont(new Font("Arial Unicode MS", Font.BOLD, 32));
        overskriftpanel.setBackground(blåfarge);
        overskriftpanel.add(overskrift);
        overskriftpanel.add(kjønn);

        panel.add(feltpanel, BorderLayout.NORTH);
        panel.add(tabellpanel, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
        add(bunnpanel, BorderLayout.SOUTH);
        add(overskriftpanel, BorderLayout.NORTH);
        setBackground(blåfarge);
        panel.setBackground(blåfarge);

    }//EndrePasientADMIN ferdig

    /**
     * Lager og oppretter GUI for endre pasient
     */
    public void lagOgOpprettGUI() {
        //panel = new JPanel(new GridLayout(2, 1));
        panel = new JPanel(new BorderLayout());

        personnummerfelt = new JTextField(10);
        fornavnfelt = new JTextField(10);
        mellomnavnfelt = new JTextField(10);
        etternavnfelt = new JTextField(10);
        fastlegefelt = new JTextField(10);

        personnummerfelt.setFont(new Font("Serif", Font.BOLD, 14));
        fornavnfelt.setFont(new Font("Serif", Font.BOLD, 14));
        mellomnavnfelt.setFont(new Font("Serif", Font.BOLD, 14));
        etternavnfelt.setFont(new Font("Serif", Font.BOLD, 14));
        fastlegefelt.setFont(new Font("Serif", Font.BOLD, 14));

        personnummerfelt.setEditable(false);
        fastlegefelt.setEditable(false);
        personnummerfelt.setText(String.valueOf(pasient.getID()));
        fornavnfelt.setText(pasient.getFornavn());
        mellomnavnfelt.setText(pasient.getMellomnavn());
        etternavnfelt.setText(pasient.getEtternavn());
        if (pasient.getFastlege() == null) {
            fastlegefelt.setText("HAR IKKE FASTLEGE");
        } else {
            fastlegefelt.setText(pasient.getFastlege().getNavn() + ", " + pasient.getFastlege().getID());
        }

        personnummerlabel = new JLabel("PERSONNUMMER: ");
        fornavnlabel = new JLabel("FORNAVN: ");
        mellomnavnlabel = new JLabel("MELLOMNAVN: ");
        etternavnlabel = new JLabel("ETTERNAVN: ");
        fastlegelabel = new JLabel("FASTLEGE: ");

        personnummerlabel.setFont(new Font("Serif", Font.BOLD, 14));
        fornavnlabel.setFont(new Font("Serif", Font.BOLD, 14));
        mellomnavnlabel.setFont(new Font("Serif", Font.BOLD, 14));
        etternavnlabel.setFont(new Font("Serif", Font.BOLD, 14));
        fastlegelabel.setFont(new Font("Serif", Font.BOLD, 14));

        søkfelt = new JTextField(50);
        
        
        TextPrompt søk = new TextPrompt("Søk etter en lege...", søkfelt);
        søk.setHorizontalAlignment(JTextField.CENTER);
        søkfelt.setHorizontalAlignment(JTextField.CENTER);
        

        søk.changeAlpha(0.5f);
        søk.setFont(new Font("Serif", Font.BOLD, 14));
        
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

        feltpanel = new JPanel(new GridLayout(5, 2));
        feltpanel.add(personnummerlabel);
        feltpanel.add(personnummerfelt);
        feltpanel.add(fornavnlabel);
        feltpanel.add(fornavnfelt);
        feltpanel.add(mellomnavnlabel);
        feltpanel.add(mellomnavnfelt);
        feltpanel.add(etternavnlabel);
        feltpanel.add(etternavnfelt);
        feltpanel.add(fastlegelabel);
        feltpanel.add(fastlegefelt);

        feltpanel.setBackground(Color.WHITE);

        søkpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        søkpanel.add(søkfelt);

        søkpanel.setBackground(Color.WHITE);

        tabellpanel = new JPanel();
        tabellpanel.setLayout(new BorderLayout());

        legemodell = new AdminLegemodellForPasient(kolonner, (LinkedList<Lege>) register.getLegeliste());

        legetabell = new JTable(legemodell);
        tabellpanel.add(søkpanel, BorderLayout.NORTH);
        tabellScroll = new JScrollPane(legetabell);
        tabellpanel.add(tabellScroll, BorderLayout.CENTER);

        legetabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        legetabell.setFillsViewportHeight(true);

        TableRowSorter<AdminLegemodellForPasient> sorter = new TableRowSorter<>(legemodell);

        legetabell.setRowSorter(sorter);
        sorter.setSortsOnUpdates(true);

        bunnpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        endreFastlege = new JButton("ENDRE FASTLEGE");
        lagreEndring = new JButton("LAGRE ENDRING");
        avbryt = new JButton("AVBRYT");

        endreFastlege.addActionListener(new Knapplytter());
        lagreEndring.addActionListener(new Knapplytter());
        avbryt.addActionListener(new Knapplytter());

        bunnpanel.add(endreFastlege);
        bunnpanel.add(lagreEndring);
        bunnpanel.add(avbryt);
        bunnpanel.setBackground(blåfarge);

    }//lagOgOpprettGUI ferdig

    /**
     * Setter høyde på tabellen som er tatt imot som parameter
     *
     * @param tabell
     */
    private void setRadhøyde(JTable tabell) {
        try {

            for (int rad = 0; rad < tabell.getRowCount(); rad++) {
                int radHøyde = tabell.getRowHeight();

                for (int kolonne = 0; kolonne < tabell.getColumnCount(); kolonne++) {
                    Component comp = tabell.prepareRenderer(tabell.getCellRenderer(rad, kolonne), rad, kolonne);
                    radHøyde = 24;
                    //Math.max(rowHeight, comp.getPreferredSize().height);

                }

                tabell.setRowHeight(rad, radHøyde);
                tabell.setFont(new Font("Tahoma", Font.PLAIN, 20));

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
     * Setter en lege som ny fastlege
     */
    public void setFastlege() {

        int index = legetabell.getSelectedRow();
        Lege tidligereFastlege = pasient.getFastlege();
        if (index != finnesIkke) {
            fastlege = legemodell.getValueAt(legetabell.getSelectedRow());

        } else {
            fastlege = pasient.getFastlege();
        }

        String spm;

        if (fastlegefelt.getText().equalsIgnoreCase("HAR IKKE FASTLEGE")) {
            if (fastlege == null) {
                spm = "";
            } else {
                spm = "Vil du endre fastlege til " + fastlege.getNavn() + " ?";
            }

        } else {
            if ((fastlege.getNavn() + ", " + fastlege.getID()).equalsIgnoreCase(fastlegefelt.getText())) {
                spm = null;
            } else {
                spm = "Vil du endre fastlege\nFRA: " + fastlegefelt.getText() + "\n"
                        + "TIL: " + fastlege.getNavn() + "?";
            }
        }

        int svar = finnesIkke;
        if (spm != null) {
            svar = JOptionPane.showConfirmDialog(null, spm, "VIL DU ENDRE FASTLEGE?", JOptionPane.YES_NO_OPTION);
        }

        if (svar == JOptionPane.YES_OPTION || svar == finnesIkke) {
            if (fastlege == null) {
                if (pasient.getFastlege() == null) {
                    fastlegefelt.setText("HAR IKKE FASTLEGE");
                } else {
                    fastlegefelt.setText(fastlege.getNavn() + ", " + fastlege.getID());
                }
            }

        }

    }//setFastlege ferdig

    /**
     * lagrer endring i endre pasient
     */
    public void lagreEndring() {
        fornavnfelt.setBackground(Color.WHITE);
        mellomnavnfelt.setBackground(Color.WHITE);
        etternavnfelt.setBackground(Color.WHITE);

        String fornavn = Hjelpemetoder.gyldigNavn(fornavnfelt.getText());
        String mellomnavn = Hjelpemetoder.gyldigNavn(mellomnavnfelt.getText());
        String etternavn = Hjelpemetoder.gyldigNavn(etternavnfelt.getText());

        boolean gyldig = true;
        if (fornavn == null) {
            gyldig = false;
            fornavnfelt.setBackground(Color.RED);
        }

        if (mellomnavn == null) {
            if (mellomnavnfelt.getText().equals("")) {
                mellomnavn = "";
            } else {
                gyldig = false;
                mellomnavnfelt.setBackground(Color.red);
            }
        }

        if (etternavn == null) {
            gyldig = false;
            etternavnfelt.setBackground(Color.RED);
        }

        if (!gyldig) {
            return;
        }

        String spm = "ER DU SIKKER PÅ AT DU VIL UTFØRE ENDRINGER?";
        if (pasient.getFornavn().equalsIgnoreCase(fornavn) && pasient.getMellomnavn().equalsIgnoreCase(mellomnavn) && pasient.getEtternavn().equalsIgnoreCase(etternavn)) {
            pasient.setFornavn(fornavn);
            pasient.setMellomnavn(mellomnavn);
            pasient.setEtternavn(etternavn);

        } else {
            int svar = JOptionPane.showConfirmDialog(null, spm, "VIL DU ENDRE PASIENTINFO?", JOptionPane.YES_NO_OPTION);
            if (svar == JOptionPane.YES_OPTION) {
                pasient.setFornavn(fornavn);
                pasient.setMellomnavn(mellomnavn);
                pasient.setEtternavn(etternavn);

            } else {
                return;
            }

        }

        pasient.setFastlege(fastlege);

        parent.visPanel(parent.VIS_ALLE);

    }//lagreEndring ferdig

    /**
     * Viser alle pasienter i systemet
     */
    public void visAlle() {

        tabellpanel.remove(tabellScroll);

        søkfelt.setBackground(Color.WHITE);
        revalidate();

        legetabell = new JTable();

        legemodell = new AdminLegemodellForPasient(kolonner, (LinkedList<Lege>) register.getLegeliste());
        legetabell.setModel(legemodell);
        tabellScroll = new JScrollPane(legetabell);

        //legetabell.addMouseListener(new AlleLegerADMIN.Muslytter());
        tabellpanel.add(tabellScroll, BorderLayout.CENTER);

        setRadhøyde(legetabell);
        revalidate();
        repaint();

    }//visAlle ferdig

    /**
     * Metode for å søke med fritekst i pasientregisteret
     */
    public void frisøk() {

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

                revalidate();

                temp = new LinkedList<>();
                temp.add(lege);

                if (temp.isEmpty()) {
                    søkfelt.setBackground(Color.decode("#fd6d6d"));
                }

                legemodell = new AdminLegemodellForPasient(kolonner, temp);

                legetabell = new JTable(legemodell);

                legetabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                legetabell.setFillsViewportHeight(true);

                tabellpanel.add(tabellScroll, BorderLayout.CENTER);

                revalidate();
                repaint();
                setRadhøyde(legetabell);
                return;

            }
        } catch (IndexOutOfBoundsException exception) {

        }

        søkfelt.setBackground(Color.WHITE);

        tabellpanel.remove(tabellScroll);

        revalidate();

        LinkedList<Lege> legeliste = (LinkedList<Lege>) register.getLegeliste();

        temp = new LinkedList<>();

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
                    temp.add(lege);
                }
            } else {
                if (lege.getFornavn().toUpperCase().startsWith(søk.toUpperCase())
                        || lege.getMellomnavn().toUpperCase().startsWith(søk.toUpperCase())
                        || lege.getEtternavn().toUpperCase().startsWith(søk.toUpperCase())
                        || lege.getNavn().toUpperCase().startsWith(søk.toUpperCase())
                        || String.valueOf(lege.getID()).startsWith(søk)
                        || fornavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())
                        || mellomnavn_etternavn.toUpperCase().startsWith(søk.toUpperCase())) {
                    temp.add(lege);
                }
            }

        }

        if (temp.isEmpty()) {
            søkfelt.setBackground(Color.decode("#fd6d6d"));
        }

        legemodell = new AdminLegemodellForPasient(kolonner, temp);

        legetabell = new JTable(legemodell);

        legetabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        legetabell.setFillsViewportHeight(true);

        tabellScroll = new JScrollPane(legetabell);
        tabellpanel.add(tabellScroll, BorderLayout.CENTER);

        setRadhøyde(legetabell);
        revalidate();
        repaint();

    }//frisøk ferdig

    /**
     * Knappelytter som lytter på knapper, implementerer ActionListener
     */
    private class Knapplytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == endreFastlege) {
                setFastlege();
            } else if (ae.getSource() == lagreEndring) {
                lagreEndring();
            } else if (ae.getSource() == avbryt) {
                parent.visPanel(parent.VIS_ALLE);
            }
        }//actionPerformed ferdig

    }//Knapplytter ferdig
}//EndrePasientADMIN ferdig
