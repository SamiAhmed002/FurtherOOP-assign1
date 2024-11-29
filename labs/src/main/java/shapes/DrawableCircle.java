package shapes;

import geometry.Vec2d;

import java.awt.*;

public class DrawableCircle extends Circle implements Drawable {
    private Color color;

    public DrawableCircle(Vec2d p, double radius, Color color) {
        super(p, radius);
        this.color = color;
    }

    public void draw(Graphics2D g) {
        Color col = g.getColor();

        g.setColor(color);

        int x = (int) (getPosition().x() - radius);
        int y = (int) (getPosition().y() - radius);
        int diameter = (int) (2 * radius);

        g.fillOval(x, y, diameter, diameter);
        g.setColor(col);
    }
}