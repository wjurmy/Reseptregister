/**
 * Klasse som arver JComboBox. komboboksen brukes til å  velge preparat som skal skrives ut
 * Dette fungerer som en slags autocomplete funksjon som skal gjøre det lettere for legen å skrive
 * ut resepter på et ønsket preparat
 */
package GUI;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 *  Siste oppdatering: 12.05.14
 * @author Vegard Lokreim
 */
public class AutoFelt extends JComboBox {

    private LinkedList<String> liste;
    static final long serialVersionUID = 4321421L;

    /**
     * Oppretter en liste med alle preparater som er tilgjengelig i norge
     * og setter JComboBoxen editable
     * Kontruktøren legger også til en KyeListener til boksen og sørger for å liste opp en liste av 
     * Alle preparater som matcher stringen som brukeren skriver inn i søkfeltet.
     * 
     * En focusListener blir også lagt til for å gjøre feltet focusable(false) dersom brukeren klikker enter
     * og focusable(true) dersom focus er lost
     */
    public AutoFelt() {

        super();

        Liste l = new Liste();
        liste = l.getListe();

        setEditable(true);

        Component c = getEditor().getEditorComponent();

        if (c instanceof JTextComponent) {

            final JTextComponent tc = (JTextComponent) c;

            tc.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    update();
                    requestFocus(true);

                }//keyTyped ferdig

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        setPopupVisible(false);
                        setFocusable(false);

                        String navn = (String) getSelectedItem();

                    }
                }//keyPressed ferdig

                @Override
                public void keyReleased(KeyEvent e) {
                }//keyReleased ferdig

                public void update() {

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            List<String> founds = (List<String>) søk(tc.getText());

                            Set<String> foundSet = new HashSet<String>();

                            for (String s : founds) {

                                foundSet.add(s.toLowerCase());

                            }

                            Collections.sort(founds);//sort alphabetically

                            setEditable(false);

                            removeAllItems();

                            //if founds contains the søk text, then only add once.
                            if (!foundSet.contains(tc.getText().toLowerCase())) {

                                addItem(tc.getText());

                            }

                            for (String s : founds) {

                                addItem(s);

                            }

                            setEditable(true);

                            setPopupVisible(true);

                        }//run ferdig
                    });

                    //When the text component changes, focus is gained 
                    //and the menu disappears. To account for this, whenever the focus
                    //is gained by the JTextComponent and it has søkable values, we show the popup.
                    tc.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent arg0) {

                            if (tc.getText().length() > 0) {

                                setPopupVisible(true);

                            }

                        }

                        @Override
                        public void focusLost(FocusEvent arg0) {
                            setFocusable(true);
                        }//focusLost ferdig
                    }); // focusGained ferdig

                }//update ferdig
            });
        }
    }//Autofelt ferdig

    /**
     * Returnerer en liste med String verdier, verdiene er preparatnavn som
     * matcher med søket etter "value" Parameter: value inneholder deler av
     * eller hele preparatnavnet som søkes etter.
     *
     * @param value string som er skr
     * @return founds - ArrayList med alle verdiene som matcher value
     */
    
    public Collection<String> søk(String value) {

        List<String> founds = new ArrayList<>();
        for (String temp : liste) {
            String upperCase = temp.toUpperCase();

            if (upperCase.startsWith(value.toUpperCase())) {

                founds.add(temp);
            }
        }

        return founds;

    }//søk ferdig
}//Autofelt ferdig
