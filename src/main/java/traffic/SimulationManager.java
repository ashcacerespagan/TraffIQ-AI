package traffic;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    private final List<Intersection> intersections = new ArrayList<>();
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final AIController aiController;
    private boolean running;
    private double simulationSpeed = 1.0;

    public SimulationManager() {
        this.aiController = new AIController(this);
    }

    public void initializeSimulation() {
        Intersection intersection = new Intersection(400, 300); // Intersection at screen center
        addIntersection(intersection);

        // Add vehicles from all directions
        for (int i = 0; i < 5; i++) {
            vehicles.add(new Vehicle(i, -50, 300, Vehicle.Direction.HORIZONTAL)); // Left to right
            vehicles.add(new Vehicle(i + 5, 800, 300, Vehicle.Direction.HORIZONTAL)); // Right to left
            vehicles.add(new Vehicle(i + 10, 400, -50, Vehicle.Direction.VERTICAL)); // Top to bottom
            vehicles.add(new Vehicle(i + 15, 400, 600, Vehicle.Direction.VERTICAL)); // Bottom to top
        }
    }

    public void startSimulation() {
        running = true;
    }

    public void stopSimulation() {
        running = false;
    }

    public void setSimulationSpeed(double speed) {
        this.simulationSpeed = speed;
    }

    public void updateSimulation(double deltaTime) {
        if (!running) return;

        // Update traffic lights and vehicle positions
        for (Intersection intersection : intersections) {
            intersection.manageTraffic(simulationSpeed);
        }

        aiController.optimizeTrafficFlow();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.isMoving()) {
                vehicle.updatePosition(deltaTime * simulationSpeed);

                // Reset vehicle position if it moves off-screen
                if (vehicle.getX() > 800 && vehicle.getDirection() == Vehicle.Direction.HORIZONTAL) {
                    vehicle.setX(-50);
                } else if (vehicle.getX() < -50 && vehicle.getDirection() == Vehicle.Direction.HORIZONTAL) {
                    vehicle.setX(800);
                } else if (vehicle.getY() > 600 && vehicle.getDirection() == Vehicle.Direction.VERTICAL) {
                    vehicle.setY(-50);
                } else if (vehicle.getY() < -50 && vehicle.getDirection() == Vehicle.Direction.VERTICAL) {
                    vehicle.setY(600);
                }
            }
        }
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    private void addIntersection(Intersection intersection) {
        intersections.add(intersection);
    }
}
