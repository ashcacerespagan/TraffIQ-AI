package traffic;

import java.util.ArrayList;
import java.util.List;

public class Intersection {
    private final List<TrafficLight> trafficLights = new ArrayList<>();
    private final List<Vehicle> queuedVehicles = new ArrayList<>();

    private final int positionX; // X-coordinate of the intersection center
    private final int positionY; // Y-coordinate of the intersection center

    public Intersection(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;

        // Create traffic lights for all directions
        trafficLights.add(new TrafficLight(TrafficLight.LightState.RED, 5)); // Northbound
        trafficLights.add(new TrafficLight(TrafficLight.LightState.GREEN, 5)); // Southbound
        trafficLights.add(new TrafficLight(TrafficLight.LightState.RED, 5)); // Eastbound
        trafficLights.add(new TrafficLight(TrafficLight.LightState.GREEN, 5)); // Westbound
    }

    public List<Vehicle> getQueuedVehicles() {
        return queuedVehicles;
    }

    public void manageTraffic(double simulationSpeed) {
        cycleTrafficLights(simulationSpeed);

        for (Vehicle vehicle : queuedVehicles) {
            TrafficLight light = vehicle.getDirection() == Vehicle.Direction.HORIZONTAL ? trafficLights.get(1) : trafficLights.get(0);
            if (light.isRed()) {
                vehicle.stop();
            } else if (light.isYellow()) {
                vehicle.updatePosition(0.5); // Slow down
            } else {
                vehicle.start();
            }
        }
    }

    private void cycleTrafficLights(double simulationSpeed) {
        for (TrafficLight light : trafficLights) {
            light.changeState(simulationSpeed);
        }
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public List<TrafficLight> getTrafficLights() {
        return trafficLights;
    }
}