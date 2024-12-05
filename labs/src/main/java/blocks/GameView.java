package blocks;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

import blocks.BlockShapes.Piece;
import blocks.BlockShapes.Shape;
import blocks.BlockShapes.Cell;
import blocks.BlockShapes.ShapeSet;
import blocks.BlockShapes.Sprite;

// class should work in a basic way as provided if all the todos are implemented in the other classes
// though you need to provide or complete the implementations for the methods in todos below


public class GameView extends JComponent {
    ModelInterface model;
    Palette palette;
    int margin = 5;
    int shapeRegionHeight;
    int cellSize = 40;
    int paletteCellSize = 40;
    int shrinkSize = 25;
    Piece ghostShape = null;
    List<Shape> poppableRegions = null;


    public GameView(ModelInterface model, Palette palette) {
        this.model = model;
        this.palette = palette;
        this.shapeRegionHeight = cellSize * ModelInterface.height / 2;
    }

    private void paintShapePalette(Graphics g, int cellSize) {
        // Paint the palette background
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(margin, margin + ModelInterface.height * cellSize, ModelInterface.width * cellSize, shapeRegionHeight);

        // Loop through each sprite in the palette
        for (Sprite sprite : palette.getSprites()) {
            // Determine cell size: use `shrinkSize` for palette sprites
            int drawCellSize = sprite.state == BlockShapes.SpriteState.IN_PALETTE ? shrinkSize : cellSize;

            for (Cell cell : sprite.shape) {
                // Calculate coordinates
                int x = sprite.px + cell.x() * drawCellSize;
                int y = sprite.py + cell.y() * drawCellSize;

                // Draw the tile
                g.setColor(Color.BLUE);
                g.fillRect(x, y, drawCellSize, drawCellSize);

                // Draw the outline
                g.setColor(Color.BLACK);
                g.drawRect(x, y, drawCellSize, drawCellSize);
            }
        }
    }


    private void paintPoppableRegions(Graphics g, int cellSize) {
        if (poppableRegions == null) return;
        g.setColor(new Color(200, 255, 200, 128)); // Semi-transparent red
        for (Shape shape : poppableRegions) {
            for (Cell cell : shape) {
                int x = margin + cell.x() * cellSize;
                int y = margin + cell.y() * cellSize;
                g.fillRect(x, y, cellSize, cellSize);
            }
        }
    }

    private void paintGhostShape(Graphics g, int cellSize) {
        if (ghostShape == null) return;
        g.setColor(new Color(200, 200, 255, 128));
        for (Cell cell : ghostShape.cells()) {
            int x = margin + cell.x() * cellSize;
            int y = margin + cell.y() * cellSize;
            g.fillRect(x, y, cellSize, cellSize);
        }
    }

    private void paintGrid(Graphics g) {
        int x0 = margin;
        int y0 = margin;
        int width = ModelInterface.width * cellSize;
        int height = ModelInterface.height * cellSize;
        Set<Cell> occupiedCells = model.getOccupiedCells();
        g.setColor(Color.BLACK);
        g.drawRect(x0, y0, width, height);
        for (int x = 0; x < ModelInterface.width; x++) {
            for (int y = 0; y < ModelInterface.height; y++) {
                int cellX = x0 + x * cellSize;
                int cellY = y0 + y * cellSize;

                if (occupiedCells.contains(new Cell(x, y))) {
                    g.setColor(Color.CYAN); // Occupied cells in a different color
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fill3DRect(cellX, cellY, cellSize, cellSize, true);

                // Draw cell outlines
                g.setColor(Color.GRAY);
                g.drawRect(cellX, cellY, cellSize, cellSize);
            }
        }
    }

    private void paintMiniGrids(Graphics2D g) {
        // for now we're going to do this based on the cellSize multiple
        int s = ModelInterface.subSize;
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        for (int x = 0; x < ModelInterface.width; x += s) {
            for (int y = 0; y < ModelInterface.height; y += s) {
                g.drawRect(margin + x * cellSize, margin + y * cellSize, s * cellSize, s * cellSize);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintGrid(g);
        paintMiniGrids((Graphics2D) g); // cosmetic
        paintGhostShape(g, cellSize);
        paintPoppableRegions(g, cellSize);
        paintShapePalette(g, cellSize);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                ModelInterface.width * cellSize + 2 * margin,
                ModelInterface.height * cellSize + 2 * margin + shapeRegionHeight
        );
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clean Blocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ModelInterface model = new ModelSet();
        Shape shape = new ShapeSet().getShapes().get(0);
        Piece piece = new Piece(shape, new Cell(0, 0));
        Palette palette = new Palette();
        model.place(piece);
        frame.add(new GameView(model, palette));
        frame.pack();
        frame.setVisible(true);
    }
}