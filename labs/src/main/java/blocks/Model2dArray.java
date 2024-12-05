package blocks;

/**
 * Logical model for the Blocks Puzzle
 * This handles the game logic, such as the grid, the pieces, and the rules for
 * placing pieces and removing lines and subgrids.
 * <p>
 * Note this has no dependencies on the UI or the game view, and no
 * concept of pixel-space or screen coordinates.
 * <p>
 * The standard block puzzle is on a 9x9 grid, so all placeable shapes will have
 * cells in that range.
 */

import blocks.BlockShapes.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model2dArray extends State2dArray implements ModelInterface {
    List<Shape> regions = new RegionHelper().allRegions();
    int streak = 0;

    public Model2dArray() {
        grid = new boolean[width][height];
        // initially all cells are empty (false) - they would be by default anyway
        // but this makes it explicit
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = false;
            }
        }
    }

    public int getScore() {
        return score;
    }


    // interestingly, for canPlace we could also use sets to store the occupied cells
    // and then check if the shape's cells intersect with the occupied cells

    public boolean canPlace(Piece piece) {
        for (Cell cell : piece.cells()) {
            // Check if the cell is outside the grid or already occupied
            if (cell.x() < 0 || cell.x() >= width || cell.y() < 0 || cell.y() >= height || grid[cell.x()][cell.y()]) {
                return false; // Piece cannot be placed
            }
        }
        return true; // Piece can be placed
    }

    @Override
    public void place(Piece piece) {
        if (canPlace(piece)) {
            // Place the piece on the grid
            for (Cell cell : piece.cells()) {
                grid[cell.x()][cell.y()] = true; // Mark the cell as occupied
            }

            // Check which regions are complete
            Set<Cell> cellsToRemove = new HashSet<>();
            int completedRegionCount = 0; // Track how many regions are completed in this move

            for (Shape region : regions) {
                if (isComplete(region)) {
                    completedRegionCount++;
                    cellsToRemove.addAll(region); // Add all cells of the completed region to the set
                }
            }

            // Remove all cells in one go
            for (Cell cell : cellsToRemove) {
                grid[cell.x()][cell.y()] = false;
            }

            // Update the score
            if (completedRegionCount > 0) {
                if (completedRegionCount == 1) {
                    // Single region completion
                    score += 1 + streak;
                } else {
                    // Multiple regions completion
                    score += (int) (Math.pow(2, completedRegionCount) + streak);
                }
                streak++; // Increment streak for consecutive turns
            } else {
                streak = 0; // Reset streak if no regions were completed
            }
        }
    }


    @Override
    public void remove(Shape region) {
        for (Cell cell : region) {
            grid[cell.x()][cell.y()] = false; // Remove the cell from the grid
        }
    }

    public boolean isComplete(Shape region) {
        for (Cell cell : region) {
            if (!grid[cell.x()][cell.y()]) {
                return false; // If any cell is unoccupied, the region is not complete
            }
        }
        return true; // All cells are occupied, region is complete
    }

    private boolean wouldBeComplete(Shape region, List<Cell> toAdd) {
        for (Cell cell : region) {
            boolean isOccupied = grid[cell.x()][cell.y()]; // Check if cell is already occupied
            boolean willBeOccupied = toAdd.contains(cell); // Check if cell is in the new piece
            if (!isOccupied && !willBeOccupied) {
                return false; // Region wouldn't be complete
            }
        }
        return true; // All cells would be occupied
    }

    @Override
    public boolean isGameOver(List<Shape> palettePieces) {
        for (Shape shape : palettePieces) {
            if (canPlaceAnywhere(shape)) {
                return false; // Game is not over if at least one shape can be placed
            }
        }
        return true; // Game is over if no shape can be placed anywhere
    }



    public boolean canPlaceAnywhere(Shape shape) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Piece piece = new Piece(shape, new Cell(x, y));
                if (canPlace(piece)) {
                    return true; // If the shape can be placed at any location, return true
                }
            }
        }
        return false; // If no valid position found, return false
    }

    @Override
    public List<Shape> getPoppableRegions(Piece piece) {
        List<Shape> poppable = new ArrayList<>();
        for (Shape region : regions) {
            if (wouldBeComplete(region, piece.cells())) {
                poppable.add(region); // Add region to the poppable list if it would be completed
            }
        }
        return poppable;
    }

    @Override
    public Set<Cell> getOccupiedCells() {
        Set<Cell> occupiedCells = new HashSet<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j]) {
                    occupiedCells.add(new Cell(i, j)); // Add occupied cell to the set
                }
            }
        }
        return occupiedCells;
    }
}