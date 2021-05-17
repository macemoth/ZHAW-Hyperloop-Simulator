package Model;

public interface SimulationObject {

    public void update(int dt);
    public void receiveSignal(Signal signal, Capsule capsule);
    public void register(Capsule simulationObject);
}
