/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Registerklasser.Register;

import Registerklasser.Reseptmap;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import static java.awt.Frame.ICONIFIED;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import reseptregister.Reseptregister;

/**
 *
 * @author Vegard
 */
public class MainFrameADMIN extends JFrame {

    private Color blåfarge = Color.decode("#007ec5");

    private Register register;
    private Reseptmap reseptregister;

    private JTabbedPane faner;

    private VisStatistikkADMIN statistikkAdmin;
    private AlleLegerADMIN alleLegerAdmin;
    private AllePasienterADMIN allePasienterAdmin;
    private Statistikkgenerator generator;

    private JPanel p;
    //private JMenuBar menybar;
    private JButton lukk, minimer;
    private Font font = new Font("Arial", Font.BOLD, 24);
    private int pX, pY;

    private JPanel vinduer;
    private JPanel knapper;
    public static final String STATISTIKK = "STATISTIKK";
    public static final String LEGE = "LEGE";
    public static final String PASIENT = "PASIENT";

    private JButton legeknapp;
    private JButton pasientknapp;
    private JButton statistikkknapp;

    private JPanel menybar;

    private int teller = 0;
    private JFrame frame;

    private boolean statistikkIsAktiv = false;

    /**
     * konstruktør som initialiserer datafelter og lager GUI
     *
     * @param register
     * @param generator
     * @param boolean undecorated
     */
    public MainFrameADMIN(Register register, Statistikkgenerator generator) {
        super("ADMIN");

        this.register = register;


        this.generator = generator;

        gjørFlyttbar();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent winEv){
                Reseptregister.lagreRegister();
                Reseptregister.lagreReseptregister();
                System.exit(0);
            }
        });

        setBackground(blåfarge);

        knapper = new JPanel(new GridLayout(3, 1));

        knapper.setBackground(blåfarge);

        try {
            legeknapp = lagBildeknapp("Bilder/lege.png");
            pasientknapp = lagBildeknapp("Bilder/pasient.png");
            statistikkknapp = lagBildeknapp("Bilder/statistikk.png");
        } catch (NullPointerException npe) {
            legeknapp = new JButton("Lege");
            pasientknapp = new JButton("Pasient");
            statistikkknapp = new JButton("Statistikk");
        }

        alleLegerAdmin = new AlleLegerADMIN(register);
        allePasienterAdmin = new AllePasienterADMIN(register, this);

        vinduer = new JPanel(new CardLayout());

        vinduer.add(LEGE,
                new AlleLegerADMIN(register));
        vinduer.add(PASIENT,
                new AllePasienterADMIN(register, this));
        vinduer.add(STATISTIKK, new VisStatistikkADMIN(register));

        legeknapp.addActionListener(
                new Knapplytter());
        pasientknapp.addActionListener(
                new Knapplytter());
        statistikkknapp.addActionListener(
                new Knapplytter());

        setLocationRelativeTo(
                null);
        setVisible(
                true);
        setLayout(
                new BorderLayout());

        knapper.add(legeknapp);

        knapper.add(pasientknapp);

        knapper.add(statistikkknapp);

        add(knapper, BorderLayout.WEST);

        vinduer.setBackground(blåfarge);
        add(vinduer, BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(
                null);

        add(menybar, BorderLayout.NORTH);

    }//MainFrameADMIN ferdig

    /**
     * Lager systemikon
     */
    

    /**
     * Lager bildeknapp med parameteret icon som ikon
     *
     * @param icon
     * @return bildeknapp
     */
    private JButton lagBildeknapp(String icon) {
        JButton knapp = new JButton();
        knapp.setContentAreaFilled(false);
        knapp.setBorderPainted(false);
        knapp.setIcon(new ImageIcon(getClass().getResource(icon)));
        knapp.setRolloverIcon(new ImageIcon(getClass().getResource(icon)));

        return knapp;
    }//lagBildeknapp ferdig

    /**
     * Gjør paneler flyttbare
     */
    public void gjørFlyttbar() {

        setUndecorated(true);

        // Create panel
        // Create panel
        p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new GridLayout(1, 2, 10, 10));

        // Create buttons
        try {
            minimer = lagBildeknapp("Bilder/minimer.png", "Bilder/minimerRollover.png", "minimer");
            lukk = lagBildeknapp("Bilder/lukk.png", "Bilder/lukkRollover.png", "lukk");
        } catch (NullPointerException npe) {
            minimer = new JButton("_");
            lukk = new JButton("X");
        }

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

        //legger til knapper for å lukke, minimere og 
        menybar = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        menybar.setBackground(blåfarge);
        menybar.add(p);


        setLocationRelativeTo(null);

    }//gjørFlyttbar ferdig


    /**
     * Lager bildeknapp med parameteret icon som ikon, parameteret rolloverIcon
     * som ikon når man holder over ikonet, og parameteret tooltip som er en
     * infoboks som kommer opp når du holder på knappen.
     *
     * @param icon
     * @param rolloverIcon
     * @param tooltip
     * @return bildeknapp
     */
    private JButton lagBildeknapp(String icon, String rolloverIcon, String tooltip) {
        JButton knapp = new JButton();
        knapp.setContentAreaFilled(false);
        knapp.setBorderPainted(false);
        knapp.setToolTipText(tooltip);

        knapp.setRolloverEnabled(true);
        ImageIcon rollover = new ImageIcon(getClass().getResource(rolloverIcon));
        knapp.setRolloverIcon(new RolloverIcon(rollover));
        knapp.setIcon(new ImageIcon(getClass().getResource(icon)));

        return knapp;
    }//lagBildeknapp ferdig

    /**
     * Viser pasient
     */
    public void visPasient() {
        vinduer.add(PASIENT, allePasienterAdmin);
        visPanel(PASIENT);
    }//visPasient ferdig

    /**
     * viser lege
     */
    public void visLege() {
        vinduer.add(LEGE, alleLegerAdmin);
        visPanel(LEGE);

    }//visLege ferdig

    /**
     * Knappelytter som lytter på knapper, implementerer ActionListener
     */
    private class Knapplytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == lukk) {
                dispose();
                //tray.remove(trayIcon);
                login();
            } else if (e.getSource() == minimer) {
                setState(ICONIFIED);
            } else if (e.getSource() == legeknapp) {

                visLege();
            } else if (e.getSource() == pasientknapp) {
                visPasient();
            } else if (e.getSource() == statistikkknapp) {

                statistikkAdmin = new VisStatistikkADMIN(register);
                //statistikkAdmin.nullstill();
                vinduer.add(STATISTIKK, statistikkAdmin);
                visPanel(STATISTIKK);
            }

        }//actionPerformed ferdig
    }//Knapplytter ferdig

    /**
     * Viser panelet til parameteret vindu
     *
     * @param vindu
     */
    private void visPanel(String vindu) {

        if (statistikkIsAktiv && vindu.equalsIgnoreCase(STATISTIKK)) {
            return;
        }
        
        if(statistikkIsAktiv){
            int svar = JOptionPane.showConfirmDialog(null, "Ønsker du å gå bort fra statistikkvinduet\nDette vil føre til at statistikken forsvinner.","Endre?", JOptionPane.YES_NO_OPTION);
            
            if(svar != JOptionPane.YES_OPTION){
                return;
            }
        }

        if (vindu.equalsIgnoreCase(LEGE)) {
            statistikkIsAktiv = false;
            CardLayout cl = (CardLayout) vinduer.getLayout();
            cl.show(vinduer, vindu);
            
            
        }else if(vindu.equalsIgnoreCase(PASIENT)){
            statistikkIsAktiv = false;
            CardLayout cl = (CardLayout) vinduer.getLayout();
            cl.show(vinduer, vindu);
        }else if(vindu.equalsIgnoreCase(STATISTIKK)){
            statistikkIsAktiv = true;
            CardLayout cl = (CardLayout) vinduer.getLayout();
            cl.show(vinduer, vindu);
        }
       

    }//visPanel ferdig

    /**
     * Får fram loginboksen sentrert på skjermen
     */
    public void login() {
        Login login = new Login(register);
        login.setLocationRelativeTo(null);
    }//login ferdig

}//MainFrameADMIN ferdig
