package UI;

import Model.Capsule;

import java.awt.*;

public class CapsuleElement {
    private final static int capsuleHeight = 10;
    private final static int capsuleWidth = 30;
    private final static int startX = 5;
    private final static int startY = 50;
    private Capsule capsule;

    public CapsuleElement(Capsule capsule) {
        this.capsule = capsule;
    }

    public void draw(Graphics g) {
//        g.drawRect(startX + capsule.pos, startY, capsuleWidth, capsuleHeight);
        g.drawOval(startX + capsule.pos, startY, 5, 5);
        g.drawString("Capsule " + capsule.id, startX + 5 + capsule.pos, startY);
    }
}
