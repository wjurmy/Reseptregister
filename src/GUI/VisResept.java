package GUI;

import Objekter.Resept;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * @author Vegard_Lokreim
 */
public class VisResept extends JPanel {
    
    private Color blåfarge = Color.decode("#007ec5");

    private Resept resept;
    private JTextArea informasjon;
    private JPanel knapper;
    private String informasjonstekst;
    private JButton skrivUt;

    /**
     * Konstruktør for VisResept, initialiserer datafelter og lager GUI
     * @param resept 
     */
    public VisResept(Resept resept) {

        super(new BorderLayout());
        this.resept = resept;
        skrivUt = lagBildeknapp("Bilder/print.png");

        JLabel label = new JLabel("Reseptinformasjon");
        label.setFont(new Font("SansSerif", Font.BOLD, 32));
        label.setForeground(Color.WHITE);

        skrivUt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                printToPrinter();
            }//actionPerformed ferdig
        });
        informasjon = new JTextArea();

        lagInformasjon();

        setBackground(blåfarge);
        
        JPanel overskrift = new JPanel(new FlowLayout(FlowLayout.CENTER));
        overskrift.add(label);
        overskrift.setBackground(blåfarge);
        add(overskrift, BorderLayout.NORTH);
        add(new JScrollPane(informasjon), BorderLayout.CENTER);
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(skrivUt);
        panel.setBackground(blåfarge);
        add(panel, BorderLayout.SOUTH);
    }//VisResept ferdig

    /**
     * Lager bildeknapp med parameteret icon som ikon
     * @param icon
     * @return bildeknapp
     */
    private JButton lagBildeknapp(String icon) {
        JButton knapp = new JButton();
        knapp.setContentAreaFilled(false);
        knapp.setBorderPainted(false);
        knapp.setIcon(new ImageIcon(getClass().getResource(icon)));

        return knapp;
    }//lagBildeknapp ferdig

    /**
     * Setter sammen informasjonen til resept
     */
    public void lagInformasjon() {
        String preparat = resept.getMedikament();
        String ATC = resept.getATC_kode();
        String kategori = resept.getKategori();
        String reseptgruppe = resept.getReseptrgruppe();
        String mengde = resept.getMengde();
        String ansvarlig_lege = resept.getLege().getNavn() + ", " + resept.getLege().getID();
        String pasient = resept.getPasient().getNavn() + ", " + resept.getPasient().getID();

        String anvisning = resept.getAnvisning();
        String dato = resept.getDato();

        informasjonstekst = 
                "IKKE Å BETRAKTES SOM EN RESEPT!\n\n"
                +"Preparat: " + preparat + "\n"
                + "ATC-KODE: " + ATC + "\n"
                + "Kategori: " + kategori + "\n"
                + reseptgruppe + "\n"
                + "Utskrevet mengde: " + mengde + "\n"
                + "Utskrevet av: " + ansvarlig_lege + "\n"
                + "Til pasient: " + pasient + "\n\n"
                + "Ansvisning: \n" + anvisning + "\n\n"
                + "Utskriftsdato: " + dato;
        informasjon.setText(informasjonstekst);
        informasjon.setEditable(false);
        informasjon.setFont(new Font("SansSerif", Font.BOLD, 18));

    }//lagInformasjon ferdig

    /**
     * Sender data om hva som skal printes ut til printeren
     */
    private void printToPrinter() {
        String printData = informasjonstekst;
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new OutputPrinter(printData));
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                // Print job did not complete.
            }
        }
    }//printToPrinter ferdig

}//VisResept ferdig
