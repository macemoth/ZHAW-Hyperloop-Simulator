package Model;

import java.util.ArrayList;

public class Model {

    public Tube tube;
    public LoopControl loopControl;
    private ArrayList<SimulationObject> simulationObjects;
    private int numberOfCapsules;

    public Model(int tubeLength, int numberOfCapsules) {
        simulationObjects = new ArrayList<>(numberOfCapsules+2);

        tube = new Tube(tubeLength);
        loopControl = new LoopControl();

        Capsule capsule = new Capsule(loopControl, tube, 0, null);
        simulationObjects.add(capsule);
        loopControl.register(capsule);

        for (int i = 1; i < numberOfCapsules; i++) {
            capsule = new Capsule(loopControl, tube, i, capsule);
            simulationObjects.add(capsule);
            loopControl.register(capsule);
        }
    }

    public static Model defaultInit() {
        Model model = new Model(50000, 1);
        return model;
    }

    public int update(int dt) {

        for (SimulationObject object : simulationObjects) {
            object.update(dt);
        }

        return 0;
    }

    public boolean hasNextStep() {
        return true;
    }
}
