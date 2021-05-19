package Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Simulation extends JFrame {

    public int height = 800;
    public int width = 800;

    public Simulation() {
        initUI();
    }

    private void initUI() {

        // add experiment GUI
        var surface = new Surface(width, height);
        add(surface);

        // listener to stop timer when window closed.
        addWindowListener(new WindowAdapter() {
               @Override
            public void windowClosing(WindowEvent e){
                   Timer timer = surface.blinktimer;
                   timer.stop();
                   timer = surface.simulationTimer;
                   timer.stop();
               }
        });

        setTitle("Simulation");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Simulation ex = new Simulation();
            ex.setVisible(true);
        });
    }
}