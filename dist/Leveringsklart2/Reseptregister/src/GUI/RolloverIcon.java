
/**
 * Klasse hentet fra nett
 */
package GUI;


import java.awt.*;

import javax.swing.*;

/**
 * 
 * @author http://alvinalexander.com/swing/java-button-jbutton-example-rollover
 */

public class RolloverIcon implements Icon {

    protected Icon icon;

    /**
     * Konstruktør som initialiserer datafelter
     * @param icon iconet som skal vises
     */
    public RolloverIcon(Icon icon) {
        this.icon = icon;
    }//RolloverIcon ferdig
    
    /**
     * Returnerer iconets høyde
     * @return icon.getIconHeight() - høyde
     */
    public int getIconHeight() {
        return icon.getIconHeight();
    }//getIconHeight ferdig

    /**
     * Returnerer iconets bredde
     * @return icon.getIconWidth() - bredde
     */
    public int getIconWidth() {
        return icon.getIconWidth();
    }//getIconWidth ferdig

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D graphics2d = (Graphics2D) g;
        Composite oldComposite = graphics2d.getComposite();
        //graphics2d.setComposite(RolloverComposite.DEFAULT);
        icon.paintIcon(c, g, x, y);
        graphics2d.setComposite(oldComposite);
    }//paintIcon ferdig
}//RolloverIcon ferdig
