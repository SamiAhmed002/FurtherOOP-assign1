package shapes;

import geometry.Vec2d;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    void testContainsPoint() {
        ArrayList<Vec2d> vertices = new ArrayList<>();
        vertices.add(new Vec2d(0, 0));
        vertices.add(new Vec2d(2, 0));
        vertices.add(new Vec2d(2, 2));
        vertices.add(new Vec2d(0, 2));
        Tile tile = new Tile(new Vec2d(1, 1), vertices, Color.BLUE);

        assertTrue(tile.contains(new Vec2d(1, 1)));
        assertFalse(tile.contains(new Vec2d(3, 3)));
    }

    @Test
    void testContainsTile() {
        ArrayList<Vec2d> verticesA = new ArrayList<>();
        verticesA.add(new Vec2d(0, 0));
        verticesA.add(new Vec2d(2, 0));
        verticesA.add(new Vec2d(2, 2));
        verticesA.add(new Vec2d(0, 2));
        Tile tileA = new Tile(new Vec2d(1, 1), verticesA, Color.BLUE);

        ArrayList<Vec2d> verticesB = new ArrayList<>();
        verticesB.add(new Vec2d(0.5, 0.5));
        verticesB.add(new Vec2d(1.5, 0.5));
        verticesB.add(new Vec2d(1.5, 1.5));
        verticesB.add(new Vec2d(0.5, 1.5));
        Tile tileB = new Tile(new Vec2d(1, 1), verticesB, Color.RED);

        assertTrue(tileA.contains(tileB));
        assertFalse(tileB.contains(tileA));
    }

    @Test
    void testIntersectsTile() {
        ArrayList<Vec2d> verticesA = new ArrayList<>();
        verticesA.add(new Vec2d(0, 0));
        verticesA.add(new Vec2d(2, 0));
        verticesA.add(new Vec2d(2, 2));
        verticesA.add(new Vec2d(0, 2));
        Tile tileA = new Tile(new Vec2d(1, 1), verticesA, Color.BLUE);

        ArrayList<Vec2d> verticesB = new ArrayList<>();
        verticesB.add(new Vec2d(1, 1));
        verticesB.add(new Vec2d(3, 1));
        verticesB.add(new Vec2d(3, 3));
        verticesB.add(new Vec2d(1, 3));
        Tile tileB = new Tile(new Vec2d(2, 2), verticesB, Color.RED);

        assertTrue(tileA.intersects(tileB));

        ArrayList<Vec2d> verticesC = new ArrayList<>();
        verticesC.add(new Vec2d(3, 3));
        verticesC.add(new Vec2d(5, 3));
        verticesC.add(new Vec2d(5, 5));
        verticesC.add(new Vec2d(3, 5));
        Tile tileC = new Tile(new Vec2d(4, 4), verticesC, Color.GREEN);

        assertFalse(tileA.intersects(tileC));
    }
}
