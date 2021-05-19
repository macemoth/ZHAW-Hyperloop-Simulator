package Model;

import javax.swing.*;
import javax.xml.stream.FactoryConfigurationError;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("Duplicates")
public class Surface extends JPanel implements ActionListener {

    public final int blink_DELAY = 500;
    public Timer blinktimer;
    private Boolean blink = true;

    // general Timer which defines time in simulation
    public Timer simulationTimer;
    private final int DELAY = 1;
    private int simulationTime = 0;

    // Settings of simulation
    private final int S_PER_STEP = 1; // how many seconds per step are executed in model.
    private static Model MODEL;
    public int HEIGHT_WINDOW;
    public int WIDTH_WINDOW;

    public Surface(int width, int height){

        // timer only needed for fashion
        blinktimer = new Timer(blink_DELAY, this);
        blinktimer.start();

        // main timer of simulation
        simulationTimer = new Timer(DELAY, this);
        simulationTimer.start();

        this.WIDTH_WINDOW = width;
        this.HEIGHT_WINDOW = height;
        MODEL = Model.defaultInit();
    }

    public void Experiment1(){

        Capsule capsule0 = MODEL.simulationObjects.get(0);
        Capsule capsule1 = MODEL.simulationObjects.get(1);
        Capsule capsule2 = MODEL.simulationObjects.get(2);
        Capsule capsule3 = MODEL.simulationObjects.get(3);
        //Capsule capsule4 = MODEL.simulationObjects.get(4);

        boolean accident = false;
        for( Capsule capsule : MODEL.simulationObjects){
            if(capsule.state == CapsuleState.EMERGENCY){
                accident = true;
            }
        }
        if(!accident) {

            if (simulationTime % 10 == 0 && ((capsule0.state == CapsuleState.ARRIVED) || (capsule0.state == CapsuleState.SAFE && capsule0.pos == 0))) {
                capsule0.pos = 0;
                capsule0.state = CapsuleState.TRAVELLING;
            }
            if (simulationTime % 50 == 0 && ((capsule1.state == CapsuleState.ARRIVED) || (capsule1.state == CapsuleState.SAFE && capsule1.pos == 0))) {
                capsule1.pos = 0;
                capsule1.state = CapsuleState.TRAVELLING;
            }
            if (simulationTime % 100 == 0 && ((capsule2.state == CapsuleState.ARRIVED) || (capsule2.state == CapsuleState.SAFE && capsule2.pos == 0))) {
                capsule2.pos = 0;
                capsule2.state = CapsuleState.TRAVELLING;
            }
            if (simulationTime % 150 == 0 && ((capsule3.state == CapsuleState.ARRIVED) || (capsule3.state == CapsuleState.SAFE && capsule3.pos == 0))) {
                capsule3.pos = 0;
                capsule3.state = CapsuleState.TRAVELLING;
            }
            /*
            if (simulationTime % 200 == 0 && ((capsule4.state == CapsuleState.ARRIVED) || (capsule4.state == CapsuleState.SAFE && capsule4.pos == 0))) {
                capsule4.pos = 0;
                capsule4.state = CapsuleState.TRAVELLING;
            }
            */
        }
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        drawTube(g2d);
        for(Capsule capsule : MODEL.simulationObjects){
            drawCapsules(g2d,  capsule);
        }
    }

    public void drawTube(Graphics2D g2d){
        g2d.setPaint(Color.black);
        g2d.drawLine(50,HEIGHT_WINDOW/2,WIDTH_WINDOW-50, HEIGHT_WINDOW/2);
    }

    public void drawCapsules(Graphics2D g2d, Capsule capsule){

        //drawing string information:
        g2d.setPaint(Color.black);
        g2d.drawLine(0,0,WIDTH_WINDOW,0);
        int x_string = 50;
        int y_string = 50;
        x_string += 200 * (capsule.id); // offset
        g2d.drawString("ID: " + capsule.id ,x_string,y_string);
        g2d.drawString("Pos: " + capsule.pos, x_string,y_string+20);
        g2d.drawString("v: " + capsule.v, x_string, y_string+40);
        g2d.drawString("STATE: " + capsule.state.name(), x_string, y_string+60);
        g2d.drawString("Signal:" + capsule.currentSignal.name(), x_string, y_string+80);
        // capsule position calculation on line:
        int lengthLine = WIDTH_WINDOW-100;
        double lineVsTube = (capsule.pos * 1.0) / (MODEL.tube.length *1.0);
        double posOnLine = (lineVsTube * lengthLine) + 50;
        int y = HEIGHT_WINDOW/2;

        switch (capsule.state){
            case EMERGENCY:
                g2d.setPaint(Color.red);
                break;
            case SAFE:
                g2d.setPaint(Color.green);

                if(capsule.pos == 0) {
                    y -= 50 * (capsule.id + 1);
                }
                break;
            case TRAVELLING:
                g2d.setPaint(Color.blue);
                break;
            case BRAKE:
                g2d.setPaint(Color.CYAN);
                break;
            case EMERGENCY_BRAKE:
                g2d.setPaint(Color.yellow);
                break;
            case ARRIVED:
                g2d.setPaint(Color.green);
                y += 50*(capsule.id+1);
                break;
        }

        g2d.drawString("ID:" + capsule.id, (int) posOnLine, y + 20);
        g2d.fillOval((int) posOnLine,y-5, 10,10);

    }

    // called when its time to paint. Sometimes needed to overwrite
    // the paint border or painchildren method.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == simulationTimer) {

            simulationTime += this.S_PER_STEP;
            MODEL.update(this.S_PER_STEP);
            Experiment1();
            repaint(); // recall the paint component method

            // logging Actions
            //System.out.println(simulationTime + "s  :   CAP0: " + MODEL.simulationObjects.get(0).state + " pos: " + MODEL.simulationObjects.get(0).pos + " v: " + MODEL.simulationObjects.get(0).v);

        }
    }
}