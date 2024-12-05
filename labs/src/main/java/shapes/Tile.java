package shapes;

import geometry.PolyGeometry;
import geometry.Vec2d;

import java.awt.*;
import java.util.ArrayList;

// an adapter class that extends DrawablePolygon
// and adds some useful methods for working with tiles
// by calling the methods in Geometry
public class Tile extends DrawablePolygon {

    public Tile(Vec2d p, ArrayList<Vec2d> vertices, Color color) {
        super(p, vertices, color);
    }

    // use this to help select a tile
    public boolean contains(Vec2d point) {
        return PolyGeometry.contains(getVertices(), point);
    }

    // use this to check if a tile is inside the box
    public boolean contains(Tile other) {
        return PolyGeometry.contains(getVertices(), other.getVertices());
    }

    // use this to check if a tile overlaps another tile
    public boolean intersects(Tile other) {
        return PolyGeometry.polygonsOverlap(getVertices(), other.getVertices());
    }
}