package Model;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

// capsule with id
public class Capsule implements SimulationObject {

    public int id;
    private SimulationObject loopControl;
    private Tube tube;
    public CapsuleState state;
    public int pos;
    public int v;
    private int a;
    private Capsule capsuleInFront;
    private int MIN_DISTANCE = 2000;  // min safe distance to next capsule
    private int COLLISION_DISTANCE = 5;  // min safe distance to next capsule
    private int minVdifference = 200;
    private int arrivalEps = 50; // consider the tube's last 50m as arrived.    what is EPS ?
    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Random random;

    // graphical elements
    private final static int capsuleHeight = 10;
    private final static int capsuleWidth = 30;
    private final static int startX = 5;
    private final static int startY = 50;

    private int MOTOR1_A = 10; // acceleration is assumed 10 m/s^2
    private int MOTOR2_A = 10; // acceleration is assumed 10 m/s^2
    private int BRAKE_A = -10; // normal brake deceleration is assumed -10 m/s^2
    private int EMERGENCY_BREAK = -20;
    private int V_MAX = 330;

    public Signal currentSignal = Signal.UNKNOWN;

    public Capsule(SimulationObject loopControl, Tube tube, int capsuleID, Capsule capsuleInFront) {
        this.id = capsuleID;
        this.capsuleInFront = capsuleInFront;
        this.loopControl = loopControl;
        this.tube = tube;
        random = new Random();
        state = CapsuleState.SAFE;
        //register(loopControl);
        //register(tube);
        pos = 0;
        v = 0;
    }

    @Override
    // updating the capsule by time change. It mostly makes sense to always
    // define that as 1 but whatever.
    public void update(int dt) {
        switch (state) {
            case TRAVELLING:
                calculateAcceleration();
                move(dt);
                checkBrakeDistance();
                lookAhead();
                createRandomAccident();
                break;
            case SAFE: // do nothing
                v = 0;
                a = 0;
                break;
            case EMERGENCY:
                //move(dt);
                v = 0;
                a = 0;
                loopControl.receiveSignal(Signal.EMERGENCY_BREAK,this);
                break;
            case EMERGENCY_BRAKE:
                if(v <= 0) {
                    v = 0;
                    a = 0;
                    state = CapsuleState.EMERGENCY;
                }
                loopControl.receiveSignal(Signal.EMERGENCY_BREAK,this);
                a = EMERGENCY_BREAK;
                move(dt);
                break;
            case  BRAKE:
                if(v <= 0){
                    v = 0;
                    a = 0;
                    if(tube.length-pos < 200) {
                        state = CapsuleState.ARRIVED;
                    }
                    else{
                        state = CapsuleState.SAFE;
                    }
                }
                a = BRAKE_A;
                move(dt);
                break;
        }
    }

    @Override
    // more like Sensor values of internal values in the tube detected.
    // Not really receiving signals.
    public void receiveSignal(Signal signal, Capsule sender) {
        /*
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
        */
        if(state == CapsuleState.TRAVELLING) {
            int distance = sender.pos - pos;
            if (distance < MIN_DISTANCE && distance > 0) {
                state = CapsuleState.EMERGENCY_BRAKE;
            }
            if (distance > 0) {
                state = CapsuleState.BRAKE;
            }
        }
    }

    @Override
    // what are you trying to register ?
    public void register(Capsule loopControl) {

    }

    // moving a capsule. dt seems to be time change.
    // calculating the movement of the capsule by the acceleration and time.
    // time to be set to 1 for most instances.
    // updating pos.
    public void move(int dt) {
        v += a * dt;
        int ds = v * dt;
        pos += ds;
    }

    // check front of capsule. if the distance is to small meaning under min distance and the speed of the capsule is
    // not as fast as the capsule then change state to emergency.
    public void lookAhead() {
        if(capsuleInFront != null) {
            if (Math.abs(capsuleInFront.pos - this.pos) < COLLISION_DISTANCE
                    && capsuleInFront.state != CapsuleState.ARRIVED
                    && state != CapsuleState.ARRIVED) {
                loopControl.receiveSignal(Signal.EMERGENCY_BREAK, this);
                LOGGER.log(Level.INFO, "ID: " + this.id + " collision occurred with " + capsuleInFront.id);
                state = CapsuleState.EMERGENCY;
                return;
            }
        }
    }

    // check if we should start braking for arrival
    public void checkBrakeDistance(){
        double brake_distance = -Math.pow(v,2.0) / ( 2.0*BRAKE_A);
        brake_distance += 170;
        if(tube.length-pos <= brake_distance){
            state = CapsuleState.BRAKE;
        }
    }

    // get acceleration by checking tube.
    public void calculateAcceleration() {
        //a = tube.getAcceleration(pos);
        a = MOTOR1_A;
        if(v >= V_MAX){
            a = 0;
        }
    }

    public void createRandomAccident(){
        double x = random.nextDouble();
        if(x < 0.001 && pos > 1000){
            randomAccident();
        }
    }

    public void randomAccident() {
        double x = random.nextDouble();
        if(x < 0.333){
            loopControl.receiveSignal(Signal.FIRE, this);
            LOGGER.log(Level.INFO, "ID: " + this.id + " " + Signal.FIRE.name());
            currentSignal = Signal.FIRE;
        }
        else if(x > 0.666){
            loopControl.receiveSignal(Signal.EXCESSIVE_HEAT, this);
            LOGGER.log(Level.INFO, "ID: " + this.id + " " + Signal.EXCESSIVE_HEAT.name());
            currentSignal = Signal.EXCESSIVE_HEAT;
        }
        else if(x > 0.333){
            loopControl.receiveSignal(Signal.PRESSURE_DROP, this);
            LOGGER.log(Level.INFO, "ID: " + this.id + " " + Signal.PRESSURE_DROP.name());
            currentSignal = Signal.PRESSURE_DROP;
        }
        state = CapsuleState.EMERGENCY_BRAKE;
        //loopControl.receiveSignal(Signal.FIRE);
    }
}
