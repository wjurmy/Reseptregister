/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Objekter.Preparat;
import Objekter.Lege;
import Objekter.Pasient;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import Objekter.Resept;
import Registerklasser.Preparatregister;
import Registerklasser.Reseptmap;

import java.awt.Color;
import java.awt.Dialog;
import javax.swing.JDialog;
import reseptregister.Reseptregister;

/**
 *
 * @author Vegrad Lokreim
 */
public class NyResept extends JDialog {
    
    private Color blåfarge = Color.decode("#007ec5");

    private JTextField kodefelt;
    private AutoFelt navnfelt;
    private JTextField produsentfelt;
    private JTextField kategorifelt;
    private JTextField reseptgruppefelt;
    private JTextField mengdefelt;
    private JTextArea anvisningArea;

    private JButton register;

    private Pasient pasient;
    private Lege lege;
    //private String pnr;
    private VisPasient parent;
    private MainFrame parent2;

    private JPanel feltpanel;
    private JPanel anvisningpanel;
    private JPanel felt_anvisningPanel;

    private JPanel input;

    private Preparatregister preparatliste;
    private Reseptmap reseptliste;

    /**
     * konstruktør for NyResept som initialiserer datafelter og lager GUI
     *
     * @param pasient
     * @param lege
     * @param parent
     * @param parent2
     */
    public NyResept(Pasient pasient, Lege lege, VisPasient parent, MainFrame parent2) {
        super(parent2, Dialog.ModalityType.APPLICATION_MODAL);
        //setUndecorated(true);
        preparatliste = Reseptregister.getPreparatregister();
        this.pasient = pasient;
        reseptliste = Reseptregister.getReseptliste();
        this.lege = lege;
        this.parent = parent;
        this.parent2 = parent2;

        initialiser();
        lagFeltpanel();
        lagAnvisningspanel();
        lagFeltAnvisningspanel();

        JPanel p = new JPanel(new GridLayout(1, 1));

        p.add(register);

        setLayout(new BorderLayout());

        add(felt_anvisningPanel, BorderLayout.CENTER);
        add(p, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent2);

        setVisible(true);

    }//NyResept ferdig

    /**
     * Initialiserer knapper, tekstfelt osv.
     */
    public void initialiser() {
        kodefelt = new JTextField(10);
        navnfelt = new AutoFelt();
        produsentfelt = new JTextField(10);
        kategorifelt = new JTextField(10);
        reseptgruppefelt = new JTextField(10);
        mengdefelt = new JTextField(10);
        anvisningArea = new JTextArea(10, 10);

        kodefelt.setFont(new Font("Serif", Font.BOLD, 24));
        navnfelt.setFont(new Font("Serif", Font.BOLD, 24));
        produsentfelt.setFont(new Font("Serif", Font.BOLD, 24));
        kategorifelt.setFont(new Font("Serif", Font.BOLD, 24));
        reseptgruppefelt.setFont(new Font("Serif", Font.BOLD, 24));
        mengdefelt.setFont(new Font("Serif", Font.BOLD, 24));
        anvisningArea.setFont(new Font("Serif", Font.BOLD, 24));

        produsentfelt.setEditable(false);

        reseptgruppefelt.setEditable(false);
        kategorifelt.setEditable(true);
        kodefelt.setEditable(false);

        register = new JButton("Registrer");

        register.addActionListener(new Knapplytter());
        //kodefelt.getDocument().addDocumentListener(new TekstLytter());

        feltpanel = new JPanel(new GridLayout(7, 1));
        input = new JPanel(new GridLayout(7, 1));
        feltpanel.setBackground(blåfarge);
        anvisningpanel = new JPanel(new BorderLayout());
        felt_anvisningPanel = new JPanel(new GridLayout(2, 1));

        navnfelt.addActionListener(new Knapplytter());

    }//initialiser ferdig

    /**
     * Registrerer ny resept
     */
    public void registrer() {
        
        

        if (pasient != null) {
            
            if(mengdefelt.getText().equalsIgnoreCase("") || kategorifelt.getText().equalsIgnoreCase("") || anvisningArea.getText().equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null, "Fyll inn alle feleter!");
                return;
            }

            Preparat prep = preparatliste.getPreparaNAVN((String) navnfelt.getSelectedItem());

            if (prep != null) {

                if (!preparatliste.finnes(prep.getATC_kode(), (String) navnfelt.getSelectedItem(), produsentfelt.getText())) {
                    JOptionPane.showMessageDialog(null, "Du har oppgitt uriktige opplysninger!");
                    return;
                } else {
                    prep = preparatliste.finnes2(prep.getATC_kode(), (String) navnfelt.getSelectedItem(), produsentfelt.getText());
                }

                String mengde = mengdefelt.getText();
                String anvisning = anvisningArea.getText();

                prep.setKategori(kategorifelt.getText());

                
                Resept resept = new Resept(new Date(), pasient, lege, prep, mengde, anvisning);

                String reseptgruppe = resept.getReseptrgruppe();
                
                
                
                if (reseptgruppe.startsWith("Reseptgruppe A.")) {
                    if (lege.isBevA()) {
                        reseptliste.addResept(pasient.getID(), resept);
                        reseptliste.addResept(lege.getID(), resept);
                        kategorifelt.setEditable(false);
                        parent.visPanel(VisPasient.OVERSIKT);
                        parent2.endreTilbakeknappverdiTIL_HENT();
                        dispose();
                        parent.oppdater();
                        return;

                    }
                    dispose();
                    JOptionPane.showMessageDialog(null, "Manglende bevilgning A, resepten ble ikke registrert");

                    return;
                } else if (reseptgruppe.startsWith("Reseptgruppe B.")) {
                    if (lege.isBevB()) {
                        reseptliste.addResept(pasient.getID(), resept);
                        reseptliste.addResept(lege.getID(), resept);
                        kategorifelt.setEditable(false);
                        parent.visPanel(VisPasient.OVERSIKT);
                        parent2.endreTilbakeknappverdiTIL_HENT();

                        dispose();
                        parent.oppdater();
                        return;

                    }
                    dispose();
                    JOptionPane.showMessageDialog(null, "Manglende bevilgning B, resepten ble ikke registrert");
                    return;

                } else if (reseptgruppe.startsWith("Reseptgruppe C.")) {

                    if (lege.isBevC()) {
                        reseptliste.addResept(pasient.getID(), resept);
                        reseptliste.addResept(lege.getID(), resept);
                        kategorifelt.setEditable(false);
                        parent.visPanel(VisPasient.OVERSIKT);
                        parent2.endreTilbakeknappverdiTIL_HENT();

                        dispose();
                        parent.oppdater();
                        return;

                    }
                    dispose();
                    JOptionPane.showMessageDialog(null, "Manglende bevilgning C, resepten ble ikke registrert");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Fyll ut alle felter!");
                return;
            }

        }

    }//register ferdig

    /**
     * Sjekker i tekstfeltet om teksten som er skrevet inn er et gyldig
     * preparat. dersom det er gyldig preparat vil resten av feltene autofylles
     */
    public void sjekk() {

        String kode = (String) navnfelt.getSelectedItem();

        Preparat prep = preparatliste.getPreparaNAVN(kode);

        if (prep == null) {
            //navnfelt.setText("");
            produsentfelt.setText("");
            kategorifelt.setText("");
            reseptgruppefelt.setText("");
            kodefelt.setText("");
            kategorifelt.setEditable(false);
            return;
        }

        produsentfelt.setText(prep.getProdusent());
        if (prep.getKategori().startsWith("IKKE SPE")) {
            kategorifelt.setText("");
            kategorifelt.setEditable(true);

        } else {
            kategorifelt.setEditable(false);
            kategorifelt.setText(prep.getKategori());

        }
        String reseptgruppe = prep.getReseptgruppe();
        
        if(reseptgruppe.startsWith("Reseptgruppe A.")){
            reseptgruppefelt.setText("A");
        }else if(reseptgruppe.startsWith("Reseptgruppe B.")){
            reseptgruppefelt.setText("B");
        }else if(reseptgruppe.startsWith("Reseptgruppe C.")){
            reseptgruppefelt.setText("C");
        }
        
        
        kodefelt.setText(prep.getATC_kode());
        parent.visAlle();
    }//sjekk ferdig

    /**
     * fjerner tekst i reseptvinduet
     */
    public void fjern() {
        //navnfelt.setText("");
        kodefelt.setText("");
        produsentfelt.setText("");
        kategorifelt.setText("");
        reseptgruppefelt.setText("");
    }//fjern ferdig

    /**
     * Lager feltpanel
     */
    public void lagFeltpanel() {
        
        JLabel preparatlabel = new JLabel("Preparat: ");
        JLabel ATClabel = new JLabel("ATC-KODE: ");
        JLabel produsentlabel = new JLabel("Produsent: ");
        JLabel kategorilabel = new JLabel("Kategori: ");
        JLabel reseptgruppelabel = new JLabel("Reseptgruppe: ");
        JLabel mengdelabel = new JLabel("Mengde mg/ml");
        JLabel anvisningslabel = new JLabel("Anvisning: ");
        
        preparatlabel.setFont(new Font("Serif", Font.BOLD, 24));
        ATClabel.setFont(new Font("Serif", Font.BOLD, 24));
        produsentlabel.setFont(new Font("Serif", Font.BOLD, 24));
        kategorilabel.setFont(new Font("Serif", Font.BOLD, 24));
        reseptgruppelabel.setFont(new Font("Serif", Font.BOLD, 24));
        mengdelabel.setFont(new Font("Serif", Font.BOLD, 24));
        anvisningslabel.setFont(new Font("Serif", Font.BOLD, 24));
        
        preparatlabel.setForeground(Color.WHITE);
        ATClabel.setForeground(Color.WHITE);
        produsentlabel.setForeground(Color.WHITE);
        kategorilabel.setForeground(Color.WHITE);
        reseptgruppelabel.setForeground(Color.WHITE);
        mengdelabel.setForeground(Color.WHITE);
        anvisningslabel.setForeground(Color.WHITE);
        
        

        input.add(preparatlabel);
        feltpanel.add(navnfelt);
        input.add(ATClabel);
        feltpanel.add(kodefelt);

        input.add(produsentlabel);
        feltpanel.add(produsentfelt);
        input.add(kategorilabel);
        feltpanel.add(kategorifelt);
        input.add(reseptgruppelabel);
        feltpanel.add(reseptgruppefelt);
        input.add(mengdelabel);
        feltpanel.add(mengdefelt);
        input.add(anvisningslabel);
    }//lagFeltpanel ferdig

    /**
     * lager anvisningspanel
     */
    public void lagAnvisningspanel() {
        anvisningpanel.add(new JScrollPane(anvisningArea), BorderLayout.CENTER);
    }//lagAnvisningspanel ferdig

    /**
     * Lager feltanvisningspanel
     */
    public void lagFeltAnvisningspanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        input.setBackground(blåfarge);
        panel.add(input);
        panel.add(feltpanel);

        felt_anvisningPanel.add(panel);
        felt_anvisningPanel.add(anvisningpanel);
    }//lagFeltAnvisningpanel ferdig

    /**
     * Knappelytter som lytter på knappene, implementerer ActionListener
     */
    private class Knapplytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == register) {

                registrer();

            }
            if (ae.getSource() == navnfelt) {
                sjekk();
            }

        }//actionPerformed ferdig

    }//Knapplytter ferdig

    /**
     * Tekstlytter som lytter på tekst, implementerer DocumentListener
     */
    private class TekstLytter implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent de) {
            sjekk();
        }//insertUpdate ferdig

        @Override
        public void removeUpdate(DocumentEvent de) {
            fjern();
        }//removeUpdate ferdig

        @Override
        public void changedUpdate(DocumentEvent de) {
            sjekk();

        }//changedUpdate ferdig

    }//Tekstlytter ferdig

}//NyResept ferdig
