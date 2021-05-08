package Model;

public interface SimulationObject {

    public void update(int dt);
    public void receiveSignal(Signal signal);
    public void register(SimulationObject simulationObject);
}
