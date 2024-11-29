package shapes;

import geometry.PolyGeometry;
import geometry.Vec2d;
import java.awt.*;
import java.util.ArrayList;

public class Tile extends DrawablePolygon {

    public Tile(Vec2d p, ArrayList<Vec2d> vertices, Color color) {
        super(p, vertices, color);
    }

    public boolean contains(Vec2d point) {
        // select tile
        return PolyGeometry.contains(vertices, point);
    }

    public boolean contains(Tile other) {
        // is in box
        return PolyGeometry.contains(vertices, other.vertices);
    }

    public boolean intersects(Tile other) {
        // intersects other tile
        return PolyGeometry.polygonsOverlap(vertices, other.vertices);
    }
}