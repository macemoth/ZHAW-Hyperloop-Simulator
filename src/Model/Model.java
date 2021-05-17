package Model;

import java.util.ArrayList;

public class Model {

    public Tube tube;
    public LoopControl loopControl;
    public ArrayList<Capsule> simulationObjects;
    private int numberOfCapsules;

    // model defines the general structure to be simulated. contains capsules
    // a tube and a loopControl unit.
    public Model(int tubeLength, int numberOfCapsules) {
        simulationObjects = new ArrayList<>(numberOfCapsules+2);

        tube = new Tube(tubeLength);
        loopControl = new LoopControl();

        Capsule capsule = new Capsule(loopControl, tube, 0, null);
        simulationObjects.add(capsule);
        loopControl.register(capsule);  // register capsule to loop control

        // create more capsules ? by number of capsules
        for (int i = 1; i < numberOfCapsules; i++) {
            capsule = new Capsule(loopControl, tube, i, capsule);
            simulationObjects.add(capsule);
            loopControl.register(capsule);
        }
    }

    // default generation of model.
    public static Model defaultInit() {
        Model model = new Model(50000, 4);
        return model;
    }

    // updating model also updates each element of model.
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
