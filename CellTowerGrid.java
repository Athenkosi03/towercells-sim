import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Custom JPanel for visualing the Cell Towers
 * Handles:
 * - Drawing the grid and the cell Towers as cir
 */
public class CellTowerGrid extends JPanel{
    Bounds boundaries = new Bounds();
    double[] rangesArr = boundaries.getTowerRanges();
    double minX = rangesArr[1];
    double maxX = rangesArr[0];
    double minY = rangesArr[3];
    double maxY = rangesArr[2];

    double screenWidth = 800;
    double screenHeight = 600;
    double padding = 2;

    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //Background square
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, screenWidth, screenHeight);
    }
}
