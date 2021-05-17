package Model;

import java.util.ArrayList;
import java.util.logging.Logger;

public class LoopControl implements SimulationObject {

    private int hyperloopState;
    private ArrayList<Capsule> capsules;
    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public LoopControl() {
        capsules = new ArrayList<Capsule>();
    }

    @Override
    public void update(int dt) {

    }

    @Override
    public void receiveSignal(Signal signal, Capsule sender) {
        /*LOGGER.info(Simulation.timeInSimulation + ": " + signal.toString());*/
        /*switch (signal) {
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
        */
        // notifiying all capsules.
        for (Capsule capsule : capsules) {
            if(capsule != sender){
                capsule.receiveSignal(signal, sender);
            }
        }
    }

    public void register(Capsule capsule) {
        capsules.add(capsule);
    }


}
