package UI;

import javax.swing.*;
import java.awt.*;

public class HyperloopWindow extends JFrame {

    public HyperloopWindow(HyperloopUI ui) {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add("Center", ui);
        JButton start = new JButton("Start simulation");
        JButton reset = new JButton("Reset simulation");
        start.addActionListener((e) -> ui.startSimulation());
        reset.addActionListener((e) -> ui.resetSimulation());

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.add(start);
        c.add("South", p1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
