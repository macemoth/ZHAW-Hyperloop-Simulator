package Model;

import java.util.ArrayList;

public class Tube implements SimulationObject {
    private int MOTOR_A = 10; // acceleration is assumed 10 m/s^2
    private int BRAKE_A = -10; // normal brake deceleration is assumed -20 m/s^2
    private int SAIL_A = 0; // we assume no resistance at all
    private ArrayList<SimulationObject> capsules;
    public int[] accelerationProfile;
    public int length;

    public Tube(int length) {
        defaultInit();
        this.length = length;
    }

    public void defaultInit() {
        accelerationProfile = new int[100000];
        int firstMotor = 0;
        int secondMotor = 50000;
        int brake = 44000;
        // acceleration strip on the first 100m
        for(int i = firstMotor; i < firstMotor+100; i++) {
            accelerationProfile[i] = MOTOR_A;
        }

        for(int i = secondMotor; i < secondMotor+100; i++) {
            accelerationProfile[i] = MOTOR_A;
        }

        for(int i = brake; i < brake+5999; i++) {
            accelerationProfile[i] = BRAKE_A;
        }
    }

    @Override
    public void update(int dt) {

    }

    @Override
    public void receiveSignal(Signal signal) {

    }

    @Override
    public void register(SimulationObject simulationObject) {
        capsules.add(simulationObject);
    }


    public int getAcceleration(int pos) {
        return accelerationProfile[pos];
    }
}
