package GUI;


import java.awt.*;

import javax.swing.*;

public class RolloverIcon implements Icon {

    protected Icon icon;

    /**
     * Konstruktør som initialiserer datafelter
     * @param icon 
     */
    public RolloverIcon(Icon icon) {
        this.icon = icon;
    }//RolloverIcon ferdig

    public int getIconHeight() {
        return icon.getIconHeight();
    }//getIconHeight ferdig

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
