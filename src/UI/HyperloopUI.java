package UI;

import Simulation.Simulation;

import javax.swing.*;
import java.awt.*;

public class HyperloopUI extends JPanel {

    public final static int width = 900;
    public final static int height = 450;
    public final static int nPointsWidth = 20;
    public final static int nPointsHeight = 10;


    public HyperloopUI() {
        MediaTracker mt = new MediaTracker(this);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setPreferredSize(new Dimension(width, height));
    }

    public void startSimulation() {
        Simulation.start();
    }

    public void resetSimulation() {
        Simulation.reset();
    }

    public void paintComponent(Graphics g) {
        g.drawRect(0, 0, width, height);
//
//        for(int i = 0; i < width / nPointsWidth; i++) {
//
//        }
    }

}
