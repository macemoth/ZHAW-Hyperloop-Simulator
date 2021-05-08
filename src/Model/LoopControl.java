package Model;

import Simulation.Simulation;

import java.util.ArrayList;
import java.util.logging.Logger;

public class LoopControl implements SimulationObject {

    private int hyperloopState;
    private ArrayList<SimulationObject> capsules;
    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public LoopControl() {
        capsules = new ArrayList<SimulationObject>();
    }

    @Override
    public void update(int dt) {

    }

    @Override
    public void receiveSignal(Signal signal) {
        LOGGER.info(Simulation.timeInSimulation + ": " + signal.toString());
        switch (signal) {
            case FIRE:
                notifyAllCapsules(Signal.EMERGENCY_BREAK);
                break;
            case EMERGENCY_BREAK:
                notifyAllCapsules(Signal.EMERGENCY_BREAK);
                break;
            default:
                // do nothing
                break;
        }
    }

    public void register(SimulationObject capsule) {
        capsules.add(capsule);
    }

    public void notifyAllCapsules(Signal signal) {
        for (SimulationObject capsule : capsules) {
            capsule.receiveSignal(signal);
        }
    }



}
