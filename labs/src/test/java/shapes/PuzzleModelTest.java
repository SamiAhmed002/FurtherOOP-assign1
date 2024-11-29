package shapes;

import geometry.Vec2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PuzzleModelTest {

    private PuzzleModel puzzleModel;
    private Tile box;
    private Tile tile1;
    private Tile tile2;
    private Tile tile3;

    @BeforeEach
    public void setUp() {
        // Define a box (container) and some tiles
        ArrayList<Vec2d> boxVertices = new ArrayList<>();
        boxVertices.add(new Vec2d(0, 0));
        boxVertices.add(new Vec2d(10, 0));
        boxVertices.add(new Vec2d(10, 10));
        boxVertices.add(new Vec2d(0, 10));
        box = new Tile(new Vec2d(0, 0), boxVertices, Color.BLACK);

        ArrayList<Vec2d> tile1Vertices = new ArrayList<>();
        tile1Vertices.add(new Vec2d(1, 1));
        tile1Vertices.add(new Vec2d(3, 1));
        tile1Vertices.add(new Vec2d(3, 3));
        tile1Vertices.add(new Vec2d(1, 3));
        tile1 = new Tile(new Vec2d(0, 0), tile1Vertices, Color.RED);

        ArrayList<Vec2d> tile2Vertices = new ArrayList<>();
        tile2Vertices.add(new Vec2d(2, 2));
        tile2Vertices.add(new Vec2d(4, 2));
        tile2Vertices.add(new Vec2d(4, 4));
        tile2Vertices.add(new Vec2d(2, 4));
        tile2 = new Tile(new Vec2d(0, 0), tile2Vertices, Color.BLUE);

        ArrayList<Vec2d> tile3Vertices = new ArrayList<>();
        tile3Vertices.add(new Vec2d(5, 5));
        tile3Vertices.add(new Vec2d(6, 5));
        tile3Vertices.add(new Vec2d(6, 6));
        tile3Vertices.add(new Vec2d(5, 6));
        tile3 = new Tile(new Vec2d(0, 0), tile3Vertices, Color.GREEN);

        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);

        puzzleModel = new PuzzleModel(box, tiles);
    }

    @Test
    public void testIsSolved_initialState() {
        // Initially, tiles overlap and are not all contained
        assertFalse(puzzleModel.isSolved());
    }

    @Test
    public void testCountOverlaps() {
        // tile1 and tile2 overlap
        assertEquals(2, puzzleModel.countOverlaps());
    }

    @Test
    public void testCountContains() {
        assertEquals(3, puzzleModel.countContains());
    }

    @Test
    public void testGetTileAt() {
        // Test point within tile1
        Vec2d pointInTile1 = new Vec2d(2, 2);
        assertEquals(tile2, puzzleModel.getTileAt(pointInTile1));

        // Test point outside all tiles
        Vec2d pointOutside = new Vec2d(15, 15);
        assertNull(puzzleModel.getTileAt(pointOutside));
    }

    @Test
    public void testCheckOverlaps() {
        assertTrue(puzzleModel.checkOverlaps(tile1));
        assertTrue(puzzleModel.checkOverlaps(tile2));
        assertFalse(puzzleModel.checkOverlaps(tile3));
    }
}
