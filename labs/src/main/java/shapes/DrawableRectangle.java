package shapes;

import geometry.Vec2d;

import java.awt.*;

public class DrawableRectangle extends Rectangle implements Drawable {
    final private Color color;

    public DrawableRectangle(Vec2d p, double width, double height, Color color) {
        super(p, width, height);
        this.color = color;
    }

    public void draw(Graphics2D g) {
        Color oldColor = g.getColor();

        // Set the color for the rectangle
        g.setColor(color);

        // Calculate the top-left corner of the rectangle
        int x = (int) (getPosition().x() - width / 2);
        int y = (int) (getPosition().y() - height / 2);

        // Draw the filled rectangle
        g.fillRect(x, y, (int) width, (int) height);

        // Restore the previous color of the Graphics2D context
        g.setColor(oldColor);
    }
}