/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Objekter.Lege;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Vegard
 */
public class EndreLegeADMIN extends JDialog {
    
    
    private Color blåfarge = Color.decode("#007ec5");

    private JTextField legenummerfelt;
    private JTextField fornavnfelt;
    private JTextField mellomnavnfelt;
    private JTextField etternavnfelt;
    private JTextField passordfelt;
    private JTextField arbeidsstedfelt;
    

    private JCheckBox bevA;
    private JCheckBox bevB;
    private JCheckBox bevC;

    private JLabel legenummerlabel;
    private JLabel fornavnlabel;
    private JLabel mellomnavnlabel;
    private JLabel etternavnlabel;
    private JLabel arbeidsstedlabel;
    private JLabel passordlabel;
    private JLabel autorisasjoner;
    
    private JButton lagreEndring;

    private Lege lege;

    private JPanel feltpanel;

    private JPanel bunnpanel;
    private JPanel panel;
    private AlleLegerADMIN parent;

    /**
     * konstruktør som initialiserer datafelter og lager GUI
     *
     * @param lege
     * @param parent
     */
    public EndreLegeADMIN(Lege lege, AlleLegerADMIN parent) {
        super((JFrame) SwingUtilities.getWindowAncestor(parent), Dialog.ModalityType.APPLICATION_MODAL);

        setLayout(new BorderLayout());
        this.parent = parent;
        this.lege = lege;

        lagOgOpprettGUI();

        add(feltpanel, BorderLayout.CENTER);
        add(bunnpanel, BorderLayout.SOUTH);
        setBackground(blåfarge);

        ///setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);

    }//EndreLegeADMIN ferdig

    /**
     * Lager og oppretter GUI for endre lege
     */
    public void lagOgOpprettGUI() {

        legenummerfelt = new JTextField(10);
        fornavnfelt = new JTextField(10);
        mellomnavnfelt = new JTextField(10);
        etternavnfelt = new JTextField(10);
        arbeidsstedfelt = new JTextField(10);
        passordfelt = new JTextField(10);

        bevA = new JCheckBox("A");
        bevB = new JCheckBox("B");
        bevC = new JCheckBox("C");
        
        JPanel  checkBoxPanel = new JPanel(new GridLayout(1,3));
        checkBoxPanel.add(bevA);
        checkBoxPanel.add(bevB);
        checkBoxPanel.add(bevC);
        
        checkBoxPanel.setBackground(Color.WHITE);

        legenummerfelt.setFont(new Font("Serif", Font.BOLD, 14));
        fornavnfelt.setFont(new Font("Serif", Font.BOLD, 14));
        mellomnavnfelt.setFont(new Font("Serif", Font.BOLD, 14));
        etternavnfelt.setFont(new Font("Serif", Font.BOLD, 14));
        arbeidsstedfelt.setFont(new Font("Serif", Font.BOLD, 14));
        passordfelt.setFont(new Font("Serif", Font.BOLD, 14));

        legenummerfelt.setEditable(false);

        legenummerfelt.setText(String.valueOf(lege.getID()));
        fornavnfelt.setText(lege.getFornavn());
        mellomnavnfelt.setText(lege.getMellomnavn());
        etternavnfelt.setText(lege.getEtternavn());
        arbeidsstedfelt.setText(lege.getArbeidssted());
        passordfelt.setText(lege.getPassord());
        bevA.setSelected(lege.isBevA());
        bevB.setSelected(lege.isBevB());
        bevC.setSelected(lege.isBevC());

        legenummerlabel = new JLabel("LEGENUMMER: ");
        fornavnlabel = new JLabel("FORNAVN: ");
        mellomnavnlabel = new JLabel("MELLOMNAVN: ");
        etternavnlabel = new JLabel("ETTERNAVN: ");
        arbeidsstedlabel = new JLabel("ARBEIDSSTED: ");
        passordlabel = new JLabel("PASSORD: ");
        autorisasjoner = new JLabel("Autorisasjoner: ");

        legenummerlabel.setFont(new Font("Serif", Font.BOLD, 14));
        fornavnlabel.setFont(new Font("Serif", Font.BOLD, 14));
        mellomnavnlabel.setFont(new Font("Serif", Font.BOLD, 14));
        etternavnlabel.setFont(new Font("Serif", Font.BOLD, 14));
        arbeidsstedlabel.setFont(new Font("Serif", Font.BOLD, 14));
        passordlabel.setFont(new Font("Serif", Font.BOLD, 14));
        autorisasjoner.setFont(new Font("Serif", Font.BOLD, 14));

        feltpanel = new JPanel(new GridLayout(7, 2));
        feltpanel.add(legenummerlabel);
        feltpanel.add(legenummerfelt);
        feltpanel.add(fornavnlabel);
        feltpanel.add(fornavnfelt);
        feltpanel.add(mellomnavnlabel);
        feltpanel.add(mellomnavnfelt);
        feltpanel.add(etternavnlabel);
        feltpanel.add(etternavnfelt);
        feltpanel.add(arbeidsstedlabel);
        feltpanel.add(arbeidsstedfelt);
        feltpanel.add(passordlabel);
        feltpanel.add(passordfelt);
        feltpanel.add(autorisasjoner);
        feltpanel.add(checkBoxPanel);
        

        bunnpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        lagreEndring = new JButton("LAGRE ENDRING");

        lagreEndring.addActionListener(new Knapplytter());

        feltpanel.setBackground(Color.WHITE);
        bunnpanel.setBackground(blåfarge);
        bunnpanel.add(lagreEndring);

    }//lagOgOpprettGUI ferdig

    /**
     * Lagrer endringer i lege
     */
    public void lagreEndring() {
        fornavnfelt.setBackground(Color.WHITE);
        mellomnavnfelt.setBackground(Color.WHITE);
        etternavnfelt.setBackground(Color.WHITE);
        String passord = passordfelt.getText();

        String fornavn = Hjelpemetoder.gyldigNavn(fornavnfelt.getText());
        String mellomnavn = Hjelpemetoder.gyldigNavn(mellomnavnfelt.getText());
        String etternavn = Hjelpemetoder.gyldigNavn(etternavnfelt.getText());
        String arbeidssted = arbeidsstedfelt.getText();

        boolean gyldig = true;
        if (fornavn == null) {
            gyldig = false;
            fornavnfelt.setBackground(Color.RED);
        }

        if (mellomnavn == null) {
            if(mellomnavnfelt.getText().equals("")){
                mellomnavn = "";
            }else{
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

        int svar = JOptionPane.showConfirmDialog(null, spm, "VIL DU ENDRE PASIENTINFO?", JOptionPane.YES_NO_OPTION);
        if (svar == JOptionPane.YES_OPTION) {
            lege.setFornavn(fornavn);
            lege.setMellomnavn(mellomnavn);
            lege.setEtternavn(etternavn);
            lege.setArbeidssted(arbeidssted);
            lege.setPassord(passord);

            lege.setBevA(bevA.isSelected());
            lege.setBevB(bevB.isSelected());
            lege.setBevC(bevC.isSelected());

            //parent.fjernTilbakepanel();
            parent.visPanel(parent.OVERSIKT);
            dispose();

        }
    }//lagreEndring ferdig

    /**
     * Knapplytter som lytter på knappene, implementerer ActionListener
     */
    private class Knapplytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == lagreEndring) {
                lagreEndring();
            }
        }//actionPerformed ferdig

    }//Knapplytter ferdig
}//EndreLegeADMIN ferdig
