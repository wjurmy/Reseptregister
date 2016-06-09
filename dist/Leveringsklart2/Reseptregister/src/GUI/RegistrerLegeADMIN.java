/**
 * Panel for å registrere lege
 */
package GUI;

import Objekter.Lege;
import Registerklasser.Register;
import Registerklasser.Reseptmap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.SecureRandom;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import reseptregister.Reseptregister;

/**
 * siste endring: 12.05.14
 * @author Walid Jurmy
 */
public class RegistrerLegeADMIN extends JPanel {
    
    

    private JTextField legenummerfelt;
    private JTextField fornavnfelt;
    private JTextField mellomnavnfelt;
    private JTextField etternavnfelt;
    private JTextField arbeidsstedfelt;
    private JTextField passordfelt;

    private JButton registrer;
    private JButton avbryt;

    private Register register;
    private Reseptmap resepter;
    private JPanel personalia;

    private AlleLegerADMIN parent;

    private String passord;

    private SecureRandom random = new SecureRandom();

    private final int finnesIkke = -1;

    /**
     * Konstruktør som initialiserer datafelter og lager GUI
     *
     * @param parent instanse av klassen AlleLegerADMIN
     * @param register instanse av klasssen Register
     */
    public RegistrerLegeADMIN(AlleLegerADMIN parent, Register register) {
        super(new BorderLayout());
        this.parent = parent;
        this.register = register;
        resepter = Reseptregister.getReseptliste();
        passord = lagPassord();
        initialiser();
        
        lagPersoinalia();
        
        JLabel overskrift = new JLabel("REGISTRER NY LEGE");
        
        overskrift.setFont(new Font("SansSerif", Font.BOLD, 32));
        overskrift.setForeground(Color.WHITE);
        setBackground(Color.decode("#007ec5"));
        
        

        
        add(overskrift, BorderLayout.NORTH);
        add(personalia, BorderLayout.CENTER);
        
        

        JPanel knappanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        knappanel.setBackground(Color.decode("#007ec5"));
        knappanel.add(registrer);
        knappanel.add(avbryt);
        add(knappanel, BorderLayout.SOUTH);

    }//RegistrerLege ferdig

    /**
     * Lager personalia for lege
     */
    public void lagPersoinalia() {

        personalia = new JPanel(new GridLayout(6, 2));

        personalia.add(legenummerfelt);

        personalia.add(fornavnfelt);

        personalia.add(mellomnavnfelt);

        personalia.add(etternavnfelt);

        personalia.add(arbeidsstedfelt);

        personalia.add(passordfelt);
        
        personalia.setBackground(Color.decode("#007ec5"));
    }//lagPersoinalia ferdig

    /**
     * Initialiserer knapper, tekstfelter osv
     */
    public void initialiser() {
        legenummerfelt = new JTextField(10);

        legenummerfelt.setText(register.genererLegenummer());
        legenummerfelt.setEditable(false);

        fornavnfelt = new JTextField(10);
        mellomnavnfelt = new JTextField(10);
        etternavnfelt = new JTextField(10);
        arbeidsstedfelt = new JTextField(10);
        passordfelt = new JTextField(10);
        passordfelt.setEditable(false);
        passordfelt.setText(passord);

        legenummerfelt.setHorizontalAlignment(JTextField.CENTER);
        fornavnfelt.setHorizontalAlignment(JTextField.CENTER);
        mellomnavnfelt.setHorizontalAlignment(JTextField.CENTER);
        etternavnfelt.setHorizontalAlignment(JTextField.CENTER);
        arbeidsstedfelt.setHorizontalAlignment(JTextField.CENTER);
        passordfelt.setHorizontalAlignment(JTextField.CENTER);

        legenummerfelt.setFont(new Font("Serif", Font.BOLD, 14));
        fornavnfelt.setFont(new Font("Serif", Font.BOLD, 14));
        mellomnavnfelt.setFont(new Font("Serif", Font.BOLD, 14));
        etternavnfelt.setFont(new Font("Serif", Font.BOLD, 14));
        passordfelt.setFont(new Font("Serif", Font.BOLD, 14));
        arbeidsstedfelt.setFont(new Font("Serif", Font.BOLD, 14));

        TextPrompt fornavn = new TextPrompt("Fornavn...", fornavnfelt);
        fornavn.setHorizontalAlignment(JTextField.CENTER);
        fornavn.changeAlpha(0.5f);
        fornavn.setFont(new Font("Serif", Font.BOLD, 14));

        TextPrompt mellomnavn = new TextPrompt("Mellomnavn...", mellomnavnfelt);
        mellomnavn.setHorizontalAlignment(JTextField.CENTER);
        mellomnavn.changeAlpha(0.5f);
        mellomnavn.setFont(new Font("Serif", Font.BOLD, 14));

        TextPrompt etternavn = new TextPrompt("Etternavn...", etternavnfelt);
        etternavn.setHorizontalAlignment(JTextField.CENTER);
        etternavn.changeAlpha(0.5f);
        etternavn.setFont(new Font("Serif", Font.BOLD, 14));

        TextPrompt arbeidssted = new TextPrompt("Arbeidssted...", arbeidsstedfelt);
        arbeidssted.setHorizontalAlignment(JTextField.CENTER);
        arbeidssted.changeAlpha(0.5f);
        arbeidssted.setFont(new Font("Serif", Font.BOLD, 14));

        registrer = new JButton("Registrer");
        avbryt = new JButton("Avbryt");
        avbryt.addActionListener(new Knapplytter());
        registrer.addActionListener(new Knapplytter());
    }//initialiser ferdig

    /**
     * Registrerer lege med teksten som er skrevet i tekstfeltene
     */
    public void registrer() {

        fornavnfelt.setBackground(Color.WHITE);
        mellomnavnfelt.setBackground(Color.WHITE);
        etternavnfelt.setBackground(Color.WHITE);
        arbeidsstedfelt.setBackground(Color.WHITE);

        String legenummer = legenummerfelt.getText();
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

        Lege ny = new Lege(legenummer, fornavn, mellomnavn, etternavn, passord);
        if (arbeidssted.length() > 0) {

            ny.setArbeidssted(arbeidssted);

        }

        if (register.leggTil(ny)) {
            resepter.leggTilPerson(ny.getID());
            parent.ferdigRegistrert();
        } else {
            JOptionPane.showMessageDialog(null, "Finnes!");
            return;
        }

    }//registrer ferdig

    /**
     * Genererer passord
     *
     * @return passord - passord som er generert
     */
    public String lagPassord() {
        return new BigInteger(27, random).toString(32);
    }//lagPassord ferdig

    /**
     * Knappelytter som lytter på knapper, implementerer ActionListener
     */
    private class Knapplytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (ae.getSource() == registrer) {
                registrer();
            } else if (ae.getSource() == avbryt) {
                parent.ferdigRegistrert();
            }
        }//actionPerformed ferdig

    }//Knapplytter ferdig
}//RegistrerLege ferdig
