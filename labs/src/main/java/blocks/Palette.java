package blocks;

import java.util.ArrayList;
import java.util.List;

import blocks.BlockShapes.Shape;
import blocks.BlockShapes.ShapeSet;
import blocks.BlockShapes.SpriteState;
import blocks.BlockShapes.Sprite;
import blocks.BlockShapes.PixelLoc;

public class Palette {
    ArrayList<Shape> shapes = new ArrayList<>();
    List<Sprite> sprites;
    int nShapes = 3;

    public Palette() {
        shapes.addAll(new ShapeSet().getShapes());
        sprites = new ArrayList<>();
        replenish();
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public ArrayList<Shape> getShapesToPlace() {
        // Return a list of shapes for sprites currently in the palette
        ArrayList<Shape> shapesToPlace = new ArrayList<>();
        for (Sprite sprite : sprites) {
            if (sprite.state == SpriteState.IN_PALETTE) {
                shapesToPlace.add(sprite.shape);
            }
        }
        return shapesToPlace;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    // if we have a sprite that contains the point (px, py), return it
    // and the size of the cells - the sprite location is already in pixel coordinates
    public Sprite getSprite(PixelLoc mousePoint, int cellSize) {
        for (Sprite sprite : sprites) {
            if (sprite.contains(mousePoint, cellSize)) {
                return sprite;
            }
        }
        return null;
    }

    private int nReadyPieces() {
        int count = 0;
        for (Sprite sprite : sprites) {
            if (sprite.state == SpriteState.IN_PALETTE || sprite.state == SpriteState.IN_PLAY) {
                count++;
            }
        }
        System.out.println("nReadyPieces: " + count);
        return count;
    }

    public void doLayout(int x0, int y0, int cellSize) {
        // Arrange layout of sprites on the palette

        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);

            int row = i / nShapes;
            int col = i % nShapes;

            int spriteX = x0 + col * (cellSize * 3);
            int spriteY = y0 + row * (cellSize * 3);

            sprite.px = spriteX;
            sprite.py = spriteY;

            sprite.initialPx = spriteX;
            sprite.initialPy = spriteY;

            System.out.println("Sprite created at " + spriteX + ", " + spriteY);
        }
    }

    public void replenish() {
        // Only replenish when no pieces are ready
        if (nReadyPieces() > 0) {
            return;
        }

        sprites.clear();

        for (int i = 0; i < nShapes; i++) {
            int randomIndex = (int) (Math.random() * shapes.size());
            Shape randomShape = shapes.get(randomIndex);

            Sprite sprite = new Sprite(randomShape, 0, 0);
            sprite.state = SpriteState.IN_PALETTE;
            sprites.add(sprite);
        }

        doLayout(5, 365, 40);

        System.out.println("Replenished: " + sprites);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
        if (nReadyPieces() == 0) {
            replenish();
        }
    }

    public static void main(String[] args) {
        Palette palette = new Palette();
        System.out.println(palette.shapes);
        System.out.println(palette.sprites);
        palette.doLayout(0, 0, 20);
        System.out.println(palette.sprites);
    }
}