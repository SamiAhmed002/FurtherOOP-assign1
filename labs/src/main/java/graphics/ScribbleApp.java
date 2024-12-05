package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class DrawComponent extends JComponent {
    private final ArrayList<Point> points = new ArrayList<>();  // Store points for the current line
    private final ArrayList<ArrayList<Point>> allPoints = new ArrayList<>();  // Store all drawn lines

    int pointSpan = 10;  // You can adjust this value to control the span between points

    class MouseDrawListener implements MouseListener, MouseMotionListener {
        @Override
        public void mousePressed(MouseEvent e) {
            // Start a new line by clearing points and adding the initial point
            points.clear();
            points.add(e.getPoint());
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // When the mouse is released, store the current points as a completed line
            allPoints.add(new ArrayList<>(points));  // Save the current points as a new line
            points.clear();  // Clear points for the next line
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            points.add(e.getPoint());  // Add the new point while dragging
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {}
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    public DrawComponent() {
        MouseDrawListener mouseListener = new MouseDrawListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        setBackground(Color.WHITE);
    }

    public void reset() {
        allPoints.clear();
        points.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));  // Adjusted thickness for a bold effect

        // Draw each line separately from allPoints
        for (ArrayList<Point> linePoints : allPoints) {
            for (int i = pointSpan; i < linePoints.size(); i++) {
                Point currentPoint = linePoints.get(i);
                Point previousPoint = linePoints.get(i - pointSpan);  // Get the point at the specified span
                g2d.drawLine(currentPoint.x, currentPoint.y, previousPoint.x, previousPoint.y);
            }
        }

        // Draw the current line being drawn (before the mouse is released)
        for (int i = pointSpan; i < points.size(); i++) {
            Point currentPoint = points.get(i);
            Point previousPoint = points.get(i - pointSpan);
            g2d.drawLine(currentPoint.x, currentPoint.y, previousPoint.x, previousPoint.y);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 300);
    }
}




class ScribblePanel extends JPanel {
    private final DrawComponent drawComponent = new DrawComponent();

    public ScribblePanel() {
        setLayout(new BorderLayout());


        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> drawComponent.reset());

        add(resetButton, BorderLayout.SOUTH);

        add(drawComponent, BorderLayout.CENTER);
    }
}

public class ScribbleApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Scribble App - Mouse Event Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ScribblePanel());
        frame.pack();
        frame.setVisible(true);
    }
}
