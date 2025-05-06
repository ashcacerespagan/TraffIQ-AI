package traffic;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main extends JFrame {
    private final SimulationManager simulationManager;
    private final JPanel canvas;
    private Timer animationTimer;

    public Main() {
        setTitle("TraffIQ AI Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        simulationManager = new SimulationManager();
        simulationManager.initializeSimulation();

        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSimulation(g);
            }
        };
        canvas.setPreferredSize(new Dimension(600, 400));

        JLabel statusLabel = new JLabel("TraffIQ AI Simulation: Not Started");
        JPanel controls = createControls(statusLabel);

        add(statusLabel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createControls(JLabel statusLabel) {
        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton stopButton = new JButton("Stop");
        JSlider speedSlider = new JSlider(1, 20, 10); // Speed multiplier (1x to 2x)

        speedSlider.setMajorTickSpacing(5);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        startButton.addActionListener(e -> {
            statusLabel.setText("TraffIQ AI Simulation: Running");
            simulationManager.startSimulation();
            startAnimation();
        });

        pauseButton.addActionListener(e -> {
            statusLabel.setText("TraffIQ AI Simulation: Paused");
            simulationManager.stopSimulation();
            stopAnimation();
        });

        stopButton.addActionListener(e -> {
            statusLabel.setText("TraffIQ AI Simulation: Stopped");
            simulationManager.stopSimulation();
            stopAnimation();
        });

        speedSlider.addChangeListener(e -> {
            double speed = speedSlider.getValue() / 10.0;
            simulationManager.setSimulationSpeed(speed);
            statusLabel.setText("Speed: " + speed + "x");
        });

        JPanel controls = new JPanel();
        controls.add(startButton);
        controls.add(pauseButton);
        controls.add(stopButton);
        controls.add(new JLabel("Speed:"));
        controls.add(speedSlider);

        return controls;
    }

    private void startAnimation() {
        animationTimer = new Timer(16, e -> {
            simulationManager.updateSimulation(0.016);
            canvas.repaint();
        });
        animationTimer.start();
    }

    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    private void drawSimulation(Graphics g) {
        // Draw vehicles
        for (Vehicle vehicle : simulationManager.getVehicles()) {
            g.setColor(Color.BLUE);
            g.fillRect((int) vehicle.getX(), (int) vehicle.getY(), 10, 10);
        }

        // Draw traffic lights and intersections
        for (Intersection intersection : simulationManager.getIntersections()) {
            List<TrafficLight> lights = intersection.getTrafficLights();

            // Draw traffic lights for each direction
            drawLight(g, intersection.getPositionX(), 50, lights.get(0)); // Northbound
            drawLight(g, intersection.getPositionX(), 550, lights.get(1)); // Southbound
            drawLight(g, 50, intersection.getPositionY(), lights.get(2)); // Eastbound
            drawLight(g, 750, intersection.getPositionY(), lights.get(3)); // Westbound

            // Draw intersection rectangle
            g.setColor(Color.GRAY);
            g.fillRect(intersection.getPositionX() - 25, intersection.getPositionY() - 25, 50, 50);
        }
    }

    private void drawLight(Graphics g, int x, int y, TrafficLight light) {
        Color lightColor = switch (light.getState()) {
            case RED -> Color.RED;
            case YELLOW -> Color.YELLOW;
            default -> Color.GREEN;
        };
        g.setColor(lightColor);
        g.fillOval(x, y, 20, 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}