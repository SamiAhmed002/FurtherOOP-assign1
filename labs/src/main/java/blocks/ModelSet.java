package blocks;

import blocks.BlockShapes.Piece;
import blocks.BlockShapes.Shape;
import blocks.BlockShapes.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelSet extends StateSet implements ModelInterface {

    Set<Cell> locations = new HashSet<>();
    List<Shape> regions = new RegionHelper().allRegions();

    // we need a constructor to initialise the regions
    public ModelSet() {
        super();
        initialiseLocations();
    }
    // method implementations below ...

    public int getScore() {
        return score;
    }

    private void initialiseLocations() {
        // having all grid locations in a set is in line with the set based approach
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                locations.add(new Cell(i, j));
            }
        }
    }

    @Override
    public boolean canPlace(Piece piece) {
        // can be placed if the cells are not occupied i.e. not in the occupiedCells set
        // though each one must be within the bounds of the grid
        // use a stream to check if all the cells are not occupied

        return piece.cells().stream()
                .allMatch(cell -> locations.contains(cell) && !occupiedCells.contains(cell));
    }

    @Override
    public void place(Piece piece) {
        occupiedCells.addAll(piece.cells());

        List<Shape> poppableRegions = getPoppableRegions(piece);
        poppableRegions.forEach(this::remove);

        score += poppableRegions.size();
    }

    @Override
    public void remove(Shape region) {
        region.forEach(occupiedCells::remove);
        // remove the cells from the occupiedCells set
    }

    @Override
    public boolean isComplete(Shape region) {
        // use a stream to check if all the cells in the region are occupied
        return region.stream().allMatch(occupiedCells::contains);
    }

    @Override
    public boolean isGameOver(List<Shape> palettePieces) {
        return palettePieces.stream().noneMatch(this::canPlaceAnywhere);
    }

    public boolean canPlaceAnywhere(Shape shape) {
        for (Cell loc : locations) {
            Piece piece = new Piece(shape, loc);

            // Check if the piece can be placed (it should not overlap any other cells)
            if (canPlace(piece)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Shape> getPoppableRegions(Piece piece) {
        List<Shape> poppableRegions = new ArrayList<>();

        Set<Cell> hypotheticalOccupied = new HashSet<>(occupiedCells);
        hypotheticalOccupied.addAll(piece.cells());

        for (Shape region : regions) {
            if (hypotheticalOccupied.containsAll(region)) {
                poppableRegions.add(region);
            }
        }
        return poppableRegions;
    }


    @Override
    public Set<Cell> getOccupiedCells() {
        return new HashSet<>(occupiedCells);
    }
}