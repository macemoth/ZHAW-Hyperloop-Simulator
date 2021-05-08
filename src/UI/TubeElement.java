package UI;

import Model.Capsule;

import java.awt.*;

public class TubeElement {

    private final static int capsuleHeight = 10;
    private final static int capsuleWidth = 30;
    private final static int startX = 5;
    private final static int startY = 50;
    private Capsule capsule;

    public void draw(Graphics g) {
        g.drawString("Capsule " + capsule.id, startX + 5 + capsule.pos, startY);
    }
}
