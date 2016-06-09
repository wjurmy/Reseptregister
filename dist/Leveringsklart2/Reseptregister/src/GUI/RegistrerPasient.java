/**
 * Dialogvindu som er modalt. Brukes til å fylle inn informasjon om ny pasient.
 */
package GUI;

import Objekter.Pasient;
import Registerklasser.Register;
import Registerklasser.Reseptmap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import reseptregister.Reseptregister;

/**
 * siste endring: 12.05.14
 * @author Benjamin Aarstad Nygård
 */
public class RegistrerPasient extends JDialog {
    
    private Color blåfarge = Color.decode("#007ec5");

    private JTextField personnummerfelt;
    private JTextField fornavnfelt;
    private JTextField mellomnavnfelt;
    private JTextField etternavnfelt;

    private JButton registrer;
    private JButton avbryt;

    private JPanel personalia;
    private String personnummer;
    private MainFrame parent;

    private Register register;
    private Reseptmap reseptliste;
    private JPanel overskrift;

    private final int finnesIkke = -1;

    /**
     * Kostruktør som sørger for å opprette alle datafeltene og lage GUI
     *
     * @param personnummer personnummer som er skrevet inn i søkfeltet til foreldreklassen
     * @param parent instanse av MainFrame
     * @param register instanse av Register
     */
    public RegistrerPasient(String personnummer, MainFrame parent, Register register) {

        super(parent, Dialog.ModalityType.APPLICATION_MODAL);
        this.register = register;
        reseptliste = Reseptregister.getReseptliste();
        this.parent = parent;
        this.personnummer = personnummer;
        setLayout(new BorderLayout());
        initialiser();

        personalia = new JPanel(new GridLayout(4, 4, 5, 5));

        JLabel pnr = new JLabel("Fødselsnummer: ");
        JLabel fn = new JLabel("Fornavn: ");
        JLabel mn = new JLabel("Mellomnavn: ");
        JLabel en = new JLabel("Etternavn: ");

        pnr.setFont(new Font("Tahoma", Font.BOLD, 14));
        fn.setFont(new Font("Tahoma", Font.BOLD, 14));
        mn.setFont(new Font("Tahoma", Font.BOLD, 14));
        en.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        pnr.setForeground(Color.WHITE);
        fn.setForeground(Color.WHITE);
        mn.setForeground(Color.WHITE);
        en.setForeground(Color.WHITE);

        personalia.add(pnr);
        personalia.add(personnummerfelt);
        personalia.add(fn);
        personalia.add(fornavnfelt);
        personalia.add(mn);
        personalia.add(mellomnavnfelt);
        personalia.add(en);
        personalia.add(etternavnfelt);

        personalia.setBackground(blåfarge);

        add(overskrift, BorderLayout.NORTH);
        add(personalia, BorderLayout.CENTER);

        JPanel knappanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        knappanel.add(registrer);
        knappanel.add(avbryt);
        add(knappanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setResizable(false);
        setVisible(true);

    }//RegistrerPasient ferdig

    /**
     * Initialiserer datafelter
     */
    public void initialiser() {
        personnummerfelt = new JTextField(10);
        personnummerfelt.setText(String.valueOf(personnummer));
        fornavnfelt = new JTextField(10);
        mellomnavnfelt = new JTextField(10);
        etternavnfelt = new JTextField(10);
        
        personnummerfelt.setEditable(false);

        overskrift = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel overskriftlabel = new JLabel("REGISTRER NY PASIENT");
        overskriftlabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        overskriftlabel.setForeground(Color.WHITE);
        overskrift.add(overskriftlabel);

        personnummerfelt.setFont(new Font("Tahoma", Font.BOLD, 14));
        fornavnfelt.setFont(new Font("Tahoma", Font.BOLD, 14));
        mellomnavnfelt.setFont(new Font("Tahoma", Font.BOLD, 14));
        etternavnfelt.setFont(new Font("Tahoma", Font.BOLD, 14));

        overskrift.setBackground(blåfarge);

        registrer = new JButton("Registrer");
        avbryt = new JButton("Avbryt");
        registrer.addActionListener(new Knapplytter());
        avbryt.addActionListener(new Knapplytter());
    }//initialiser ferdig

    /**
     * Registrerer pasient dersom pasienten ikke finnes fra før av. Pasienten legges også til i reseptregisteret
     */
    public void registrer() {

        personnummerfelt.setBackground(Color.WHITE);
        fornavnfelt.setBackground(Color.WHITE);
        mellomnavnfelt.setBackground(Color.WHITE);
        etternavnfelt.setBackground(Color.WHITE);

        String personnummer = personnummerfelt.getText();
        
        if(!personnummer.matches("[0-9]+") || personnummer.length() != 11){
            personnummerfelt.setBackground(Color.RED);
            return;
        }

        String fornavn = Hjelpemetoder.gyldigNavn(fornavnfelt.getText());
        String mellomnavn = Hjelpemetoder.gyldigNavn(mellomnavnfelt.getText());
        String etternavn = Hjelpemetoder.gyldigNavn(etternavnfelt.getText());

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

        Pasient ny = new Pasient(personnummer, fornavn, mellomnavn, etternavn);

        if (register.leggTil(ny)) {

            reseptliste.leggTilPerson(ny.getID());

            parent.hent(personnummer);
            dispose();

        } else {
            JOptionPane.showMessageDialog(null, "Personen finnes!");
            parent.hent(personnummer);
            dispose();
        }

    }//registrer ferdig

    /**
     * Knapplytter som implementerer ActionListner
     */
    private class Knapplytter implements ActionListener {

        /**
         * actionPerformed sjekker om det er blitt trykket på noen knapper
         *
         * @param ae
         */
        @Override
        public void actionPerformed(ActionEvent ae) {

            if (ae.getSource() == registrer) {
                registrer();
            } else if (ae.getSource() == avbryt) {

                dispose();
            }
        }//actionPerformed ferdig

    }//Knapplytter ferdig
}//RegistrerPasient ferdig
