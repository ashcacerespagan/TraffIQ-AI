package traffic;

public class TrafficLight {
    public enum LightState { RED, GREEN, YELLOW }

    private LightState state; // RED, GREEN, YELLOW
    private long lastChangeTime; // Timestamp of the last state change
    private int duration; // Duration of the current state in seconds

    public TrafficLight(LightState initialState, int initialDuration) {
        if (initialDuration <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }
        this.state = initialState;
        this.duration = initialDuration;
        this.lastChangeTime = System.currentTimeMillis();
    }

    public void setDuration(int newDuration) {
        if (newDuration > 0) { // Ensure valid duration
            this.duration = newDuration;
        } else {
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }
    }

    public void changeState(double simulationSpeed) {
        long durationMillis = (long) (duration * 1000L / simulationSpeed); // Adjust for simulation speed
        if (System.currentTimeMillis() - lastChangeTime >= durationMillis) {
            switch (state) {
                case RED -> state = LightState.GREEN;
                case GREEN -> state = LightState.YELLOW;
                case YELLOW -> state = LightState.RED;
            }
            lastChangeTime = System.currentTimeMillis();
        }
    }

    public LightState getState() {
        return state;
    }

    public boolean isRed() {
        return state == LightState.RED;
    }

    public boolean isYellow() {
        return state == LightState.YELLOW;
    }

    public boolean isGreen() {
        return state == LightState.GREEN;
    }

    public long getLastChangeTime() {
        return lastChangeTime;
    }

    public int getDuration() {
        return duration;
    }
}