package graphics;

import javax.swing.*;

public class GreenComponent extends JComponent {
    public GreenComponent() {
        setPreferredSize(new java.awt.Dimension(400, 200));
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.setColor(java.awt.Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Green Component");
        frame.add(new GreenComponent());
        frame.pack();
        frame.setVisible(true);
    }
}
