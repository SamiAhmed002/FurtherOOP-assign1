package graphics;

import javax.swing.*;
import java.awt.*;

class ColorComponent extends JComponent {
    private final Color color;
    private final Dimension preferredSize;

    public ColorComponent(Color color, Dimension preferredSize) {
        this.color = color;
        this.preferredSize = preferredSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        return preferredSize != null ? preferredSize : new Dimension(100, 100); // Default size if null
    }
}

public class BorderLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BorderLayout Example");
        frame.setLayout(new BorderLayout());
        frame.add(new ColorComponent(Color.RED, new Dimension(400, 50)), BorderLayout.NORTH);
        frame.add(new ColorComponent(Color.BLUE, new Dimension(400, 50)), BorderLayout.SOUTH);
        frame.add(new ColorComponent(Color.GREEN, new Dimension(50, 100)), BorderLayout.EAST);
        frame.add(new ColorComponent(Color.YELLOW, new Dimension(50, 200)), BorderLayout.WEST);
        frame.add(new ColorComponent(Color.ORANGE, null), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
