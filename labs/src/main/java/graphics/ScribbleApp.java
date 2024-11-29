package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class DrawComponent extends JComponent {
    final ArrayList<Line> lines = new ArrayList<>();
    private int lastX, lastY;

    static class Line {
        int x1, y1, x2, y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    class MouseDrawListener implements MouseListener, MouseMotionListener {
        @Override
        public void mousePressed(MouseEvent e) {
            lastX = e.getX();
            lastY = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            lines.add(new Line(lastX, lastY, x, y));
            lastX = x;
            lastY = y;

            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
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
        setOpaque(true);
    }

    public void reset() {
        lines.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        for (Line line : lines) {
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
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
