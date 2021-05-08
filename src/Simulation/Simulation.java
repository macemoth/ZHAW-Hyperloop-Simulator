package Simulation;

import Model.Model;
import UI.HyperloopUI;
import UI.HyperloopWindow;

public class Simulation {

    private static Model model;
    private static int simulationState;
    public static int timeInSimulation;
    public static int naptime = 25;

    public static void main(String[] args) {
        reset();
        HyperloopUI ui = new HyperloopUI();
        HyperloopWindow window = new HyperloopWindow(ui);
        window.pack();
        window.setVisible(true);

        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    nap(naptime);
                    window.repaint();
                }
            }
        }).start();
    }

    private static void nap(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            ;
        }
    }

    public static void start() {
        timeInSimulation = 0;
        int simulationEndTime = 100000;
        int dt = 1;

        for(int t = 0; t < simulationEndTime; t += dt) {
            model.update(dt);
            timeInSimulation += dt;
        }
    }

    public static void reset() {
        model = Model.defaultInit();
    }
}
