package Model;

import Simulation.Simulation;

import java.util.logging.Logger;

public class Capsule implements SimulationObject {
    public int id;
    private SimulationObject loopControl;
    private Tube tube;
    private CapsuleState state;
    public int pos;
    private int v;
    private int a;
    private Capsule capsuleInFront;
    private int minDistance = 2000;
    private int minVdifference = 200;
    private int arrivalEps = 50; // consider the tube's last 50m as arrived
    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);



    public Capsule(SimulationObject loopControl, Tube tube, int capsuleID, Capsule capsuleInFront) {
        this.id = capsuleID;
        this.capsuleInFront = capsuleInFront;
        this.loopControl = loopControl;
        this.tube = tube;
        state = CapsuleState.SAFE;
        register(loopControl);
        register(tube);
        pos = 0;
        v = 0;
    }

    @Override
    public void update(int dt) {
        lookAhead();
        switch (state) {
            case TRAVELLING:
                calculateAcceleration();
                move(dt);
                break;
            case SAFE: // do nothing
                break;
            case EMERGENCY:
                engageEmergencyBrake();
                move(dt);
                break;
            case EMERGENCY_BRAKE:
                move(dt);
                if(v == 0) state = CapsuleState.SAFE;
                break;
        }
    }

    @Override
    public void receiveSignal(Signal signal) {
        switch(signal) {
            case PRESSURE_DROP:
                // do nothing
            case FIRE:
                state = CapsuleState.EMERGENCY;
            case EXCESSIVE_HEAT:
                state = CapsuleState.EMERGENCY;
            case UNKNOWN:
                state = CapsuleState.EMERGENCY;
            default: // do nothing
        }
    }

    @Override
    public void register(SimulationObject loopControl) {
    }

    public void move(int dt) {
        v += a * dt;
        int ds = v * dt;
        pos += ds;
    }

    public void lookAhead() {
        if(capsuleInFront != null) {
            if (capsuleInFront.pos < minDistance && (v > capsuleInFront.v - minVdifference)
                && capsuleInFront.state != CapsuleState.ARRIVED) {
                loopControl.receiveSignal(Signal.EMERGENCY_BREAK);
                state = CapsuleState.EMERGENCY;
                return;
            }
        }
        if(pos > tube.length - arrivalEps) {
            state = CapsuleState.ARRIVED;
            LOGGER.info(Simulation.timeInSimulation + ": Capsule " + id + " has arrived");
        }
    }

    public void engageEmergencyBrake() {
        state = CapsuleState.EMERGENCY_BRAKE;
        a = -20;
    }

    public void calculateAcceleration() {
        a = tube.getAcceleration(pos);
    }


    public void randomAccident() {
        // TODO: Random fire, pressure drop and other cruelties
    }
}
