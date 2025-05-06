package traffic;

public class Vehicle {
    public enum Direction { HORIZONTAL, VERTICAL }

    private final int id; // Unique identifier
    private double x, y; // Position
    private boolean moving; // Whether the vehicle is moving
    private final Direction direction; // Movement direction

    public Vehicle(int id, double x, double y, Direction direction) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.moving = false; // Default state is stopped
    }

    public void updatePosition(double deltaTime) {
        if (isMoving()) {
            switch (direction) {
                case HORIZONTAL -> x += deltaTime * 10; // Move horizontally
                case VERTICAL -> y += deltaTime * 10; // Move vertically
            }
        }
    }

    public void start() {
        this.moving = true;
    }

    public void stop() {
        this.moving = false;
    }

    public boolean isMoving() {
        return moving;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }
}