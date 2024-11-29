package shapes;


import geometry.Vec2d;

import java.util.ArrayList;

public class Polygon extends MovableShape {
    ArrayList<Vec2d> vertices;

    public Polygon(Vec2d p, ArrayList<Vec2d> vertices) {
        super(p);
        this.vertices = vertices;
    }

    public double area() {
        double sum1 = 0;
        double sum2 = 0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Vec2d current = vertices.get(i);
            Vec2d next = vertices.get((i + 1) % n);
            sum1 += current.x() * next.y();
            sum2 += current.y() * next.x();
        }
        return Math.abs(sum1 - sum2) / 2.0;
    }


    public double perimeter() {
        double sum = 0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Vec2d current = vertices.get(i);
            Vec2d next = vertices.get((i + 1) % n);
            sum += current.distance(next);
        }
        return sum;
    }
}