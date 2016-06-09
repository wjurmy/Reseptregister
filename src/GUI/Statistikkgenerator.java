package GUI;

import Objekter.Preparat;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/*
 * @author Vegard_Lokreim
 */
public class Statistikkgenerator extends JPanel {

    private int[] values;

    private String[] names;

    private String title;

    public Statistikkgenerator() {
        setBackground(Color.WHITE);
    }

    public Statistikkgenerator(String preparatnavn, int antall, String navn) {
        title = preparatnavn;
        names = new String[1];
        values = new int[1];
        names[0] = navn;
        values[0] = antall;
        setBackground(Color.WHITE);

    }

    public Statistikkgenerator(String[] n, int[] v) {
        names = n;
        values = v;
        title = "";
        setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        super.paintComponent(g);
        
        if (values == null || values.length == 0) {
            return;
        }
        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < values.length; i++) {
            if (minValue > values[i]) {
                minValue = values[i];
            }
            if (maxValue < values[i]) {
                maxValue = values[i];
            }
        }

        Dimension d = getSize();
        int clientWidth = d.width;
        int clientHeight = d.height;
        int barWidth = 250;
        
        setPreferredSize(new Dimension(barWidth*values.length,clientHeight-150));
        
        

        Font titleFont = new Font("SansSerif", Font.BOLD, 20);
        FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

        int titleWidth = titleFontMetrics.stringWidth(title);
        int y = titleFontMetrics.getAscent();
        int x = (clientWidth - titleWidth) / 2;
        g.setFont(titleFont);
        g.setColor(Color.decode("#007ec5"));
        //g.drawString(title, x, y);

        int top = titleFontMetrics.getHeight();
        int bottom = labelFontMetrics.getHeight();
        if (maxValue == minValue) {
            return;
        }
        double scale = (clientHeight - top - bottom) / (maxValue - minValue);
        y = clientHeight - labelFontMetrics.getDescent();
        g.setFont(labelFont);
        
        
        
       
        for (int i = 0; i < values.length; i++) {
            if (values[i] > 0) {
                
                int valueX = i * barWidth +10 ;
                int valueY = top;
                int height = (int) (values[i] * scale);
                
                if (values[i] >= 0) {
                    valueY += (int) ((maxValue - values[i]) * scale);
                } else {
                    valueY += (int) (maxValue * scale);
                    height = -height;
                }

                g.setColor(Color.decode("#FF6D0A"));
                g.fillRect(valueX, valueY, barWidth - 100, height);
                g.setColor(Color.decode("#007ec5"));
                
                g.setFont(new Font("Helvetica", Font.BOLD, 30));
                g.drawString(String.valueOf(values[i]), valueX+115, valueY - 2);
                g.setFont(new Font("Helvetica", Font.BOLD, 10));
                int labelWidth = labelFontMetrics.stringWidth(names[i]);
                x = i * barWidth + (barWidth - labelWidth) / 2;
                g.setColor(Color.decode("#007ec5"));
                
                g.drawString(names[i], x+10, y);
                
                
            }

        }
        

    }

}
