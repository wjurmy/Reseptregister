/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Objekter.Lege;
import Registerklasser.Register;

import Registerklasser.Reseptmap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Frame.ICONIFIED;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import reseptregister.Reseptregister;
import static reseptregister.Reseptregister.lagreRegister;
import static reseptregister.Reseptregister.lagreReseptregister;

/**
 *
 * @author Vegrad Lokreim
 */
public class Login extends JFrame {
    
    private final Color blåfarge = Color.decode("#007ec5");

    private JPanel loginpanel;
    private JTextField personnummerfelt;
    private JPasswordField passordfelt;

    private JButton loggInn;

    private Register register;
    private Reseptmap reseptregister;

    private JPanel p;
    private JPanel menybar;
    private JButton lukk, minimer;
    private Font font = new Font("Arial", Font.BOLD, 24);
    private int pX, pY;

    private MenuItem infoItem;
    private MenuItem exitItem;

//    private SystemTray tray;
//    private final TrayIcon trayIcon = new TrayIcon(new ImageIcon(getClass().getResource("Bilder/RR.png")).getImage());
    private int teller = 0;

    /**
     * Oppretter login vinduet
     *
     * @param register
     */
    public Login(Register register) {
        super("Logg inn");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

            //menybar.setBa
        } catch (Exception e) {
        }
        gjørFlyttbar();
        this.register = register;
        this.reseptregister = reseptregister;
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent winEv){
                Reseptregister.lagreRegister();
                Reseptregister.lagreReseptregister();
                System.exit(0);
            }
        });

        setLayout(new BorderLayout());

        //setIconImage(new ImageIcon(getClass().getResource("Bilder/R.png")).getImage());
        //lagTrayIcon();
        loginpanel = new JPanel(new GridLayout(4, 2));
        personnummerfelt = new JTextField(15);
        passordfelt = new JPasswordField(15);

        try {
            loggInn = lagBildeknapp("Bilder/login.png");
        } catch (NullPointerException npe) {
            loggInn = new JButton("Logg inn");
        }

        personnummerfelt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sjekk();
                }
            }//keyTyped ferdig

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sjekk();
                }
            }//keyPressed ferdig

            @Override
            public void keyReleased(KeyEvent e) {
                
            }//keyReleased ferdig
        });
        
        passordfelt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sjekk();
                }
            }//keyTyped ferdig

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sjekk();
                }
            }//keyPressed ferdig

            @Override
            public void keyReleased(KeyEvent e) {
                
            }//keyReleased ferdig
        });
        
        loggInn.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sjekk();
                }
            }//keyTyped ferdig

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sjekk();
                }
            }//keyPressed ferdig

            @Override
            public void keyReleased(KeyEvent e) {
                
            }//keyReleased ferdig
        });

        JLabel brukernavn = new JLabel("Brukernavn: ");
        JLabel passord = new JLabel("Passord: ");
        brukernavn.setForeground(Color.WHITE);
        passord.setForeground(Color.WHITE);
        Font font = new Font("Helvetica", Font.PLAIN, 14);
        brukernavn.setFont(font);
        passord.setFont(font);

        loginpanel.add(brukernavn);
        loginpanel.add(personnummerfelt);
        loginpanel.add(passord);
        loginpanel.add(passordfelt);
        loginpanel.add(new JLabel());
        loginpanel.add(loggInn);

        loggInn.addActionListener(new Knapplytter());
        loginpanel.setBackground(blåfarge);
        
        JPanel panel = new JPanel(new GridLayout(3,3));
        
        JLabel overskrift = new JLabel("Prescription Libary 14");
        overskrift.setFont(new Font("Tahoma", Font.BOLD, 24));
        overskrift.setForeground(Color.WHITE);
        
        
        panel.add(new JLabel(""));
        panel.add(overskrift);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(loginpanel);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        
        panel.setBackground(blåfarge);
        
        
        add(panel, BorderLayout.CENTER);
       
        setVisible(true);
        add(menybar, BorderLayout.PAGE_START);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        

    }//Login ferdig

    /**
     * Gjør panel flyttbare ved å legge muslytter til vinduet
     */
    public void gjørFlyttbar() {

        setUndecorated(true);

        // Create panel
        p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new GridLayout(1, 2, 5, 5));

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
        menybar = new JPanel(new BorderLayout());

        menybar.setBackground(blåfarge);
        menybar.add(p, BorderLayout.EAST);
        JLabel tekst = new JLabel("Logg inn");
        tekst.setForeground(Color.red);
        menybar.add(tekst, BorderLayout.CENTER);

        setLocationRelativeTo(null);

    }//gjørFlyttbar ferdig
    public void sjekk() {

        String passord = passordfelt.getText();

        String brukernavn = personnummerfelt.getText();
        if (brukernavn.equalsIgnoreCase("ADMIN") && passord.equals("admin")) {

            new MainFrameADMIN(register, new Statistikkgenerator());
            //tray.remove(trayIcon);
            dispose();

            return;
        }

        String personnummer = personnummerfelt.getText();

        Lege lege = (Lege) register.getPerson(personnummer);

        if (lege != null && lege.getID().equalsIgnoreCase(brukernavn) && lege.getPassord().equals(passord)) {

            dispose();

            //tray.remove(trayIcon);
            MainFrame frame = new MainFrame(lege, register);

            frame.setVisible(true);

            return;

        }
        JOptionPane.showMessageDialog(this, "Feil brukernavn/passsord");

    }//sjekk ferdig

    /**
     * Lager bildeknapp med parameteret icon som ikon
     *
     * @param icon
     * @return knappen som har blitt laget
     */
    private JButton lagBildeknapp(String icon) {
        JButton knapp = new JButton();
        knapp.setContentAreaFilled(false);
        knapp.setBorderPainted(false);
        knapp.setIcon(new ImageIcon(getClass().getResource(icon)));

        return knapp;
    }//lagBildeknapp ferdig

    /**
     * Lager bildeknapp med parameteret icon som ikon, rolloverIcon parameteret
     * som ikon når man holder over knappen, tooltip parameteret som en
     * beskrivende tekst når man peker på knappen med musen.
     *
     * @param icon
     * @param rolloverIcon
     * @param tooltip
     * @return
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
     * Knappelytter som lytter på knappene, implementerer ActionListener
     */
    private class Knapplytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loggInn) {
                sjekk();
            } else if (e.getSource() == lukk || e.getSource() == exitItem) {
                lagreRegister();
                lagreReseptregister();
                System.exit(0);

            }else if(e.getSource() == minimer){
                setState(ICONIFIED);
            }

        }//actionPerformed ferdig

    }//Knapplytter ferdig

}//Login ferdig
