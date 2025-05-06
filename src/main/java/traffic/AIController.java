package traffic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AIController {
    private final SimulationManager simulationManager;
    private static final Logger logger = LoggerFactory.getLogger(AIController.class);

    public AIController(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
    }

    public void optimizeTrafficFlow() {
        for (Intersection intersection : simulationManager.getIntersections()) {
            int totalVehicles = intersection.getQueuedVehicles().size();

            for (TrafficLight light : intersection.getTrafficLights()) {
                if (light.isGreen() && totalVehicles > 10) {
                    light.setDuration(15); // Extend green light for high traffic
                    logger.info("Extended green light duration for heavy traffic at intersection ({}, {}).",
                            intersection.getPositionX(), intersection.getPositionY());
                } else if (light.isYellow() && totalVehicles < 5) {
                    light.setDuration(2); // Reduce yellow light for low traffic
                    logger.info("Reduced yellow light duration for light traffic at intersection ({}, {}).",
                            intersection.getPositionX(), intersection.getPositionY());
                }

                // Log vehicle IDs waiting at the intersection for debugging
                for (Vehicle vehicle : intersection.getQueuedVehicles()) {
                    logger.debug("Vehicle ID {} is queued at intersection ({}, {}).",
                            vehicle.getId(), intersection.getPositionX(), intersection.getPositionY());
                }
            }
        }
    }
}