package maze;

import acm.graphics.GCompound;
import acm.util.RandomGenerator;

/**
 * This class generates a random maze. The maze is represented by tiles that
 * potentially have walls blocking movement from one tile to another in a
 * particular direction. The maze is generated using the recursive divide
 * algorithm. The number of rows and columns of tiles is passed in as a
 * parameter to the constructor, allowing mazes of arbitrary size to be
 * randomly generated.
 * 
 * @author Thomas Ransom
 */
public class Maze extends GCompound {
    
    
    private Tile[][] floor;
    private int rows;
    private int cols;


    /**
     * Construct a rectangular maze with the specified number of rows and
     * columns. The paths through he maze are randomly generated. It is
     * not guaranteed that any two arbitrary tiles will have a valid path
     * connecting them.
     * 
     * @param rows the number of rows in the maze.
     * @param cols the number of columns in the maze.
     */
    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        floor = new Tile[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                floor[i][j] = new Tile(this, i, j);
                add(floor[i][j],
                        j * floor[i][j].getWidth(),
                        i * floor[i][j].getHeight());
            }
        }
        this.makeHorizontalWall(0, 0, cols - 1, Tile.NORTH);
        this.makeHorizontalWall(rows - 1, 0, cols - 1, Tile.SOUTH);
        this.makeVerticleWall(0, 0, rows - 1, Tile.WEST);
        this.makeVerticleWall(cols - 1, 0, rows - 1, Tile.EAST);
        divideMaze(0, 0, rows - 1, cols - 1);
    }

    /**
     * Returns the tile at position [row, col] of the maze.
     * 
     * @param row the row of the tile.
     * @param col the column of the tile.
     * @return the tile at [row, col] or null if the position is outside the maze.
     */
    public Tile getTile(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null;
        }
        return floor[row][col];
    }

    /**
     * Returns the number of rows in the maze.
     * 
     * @return the number of rows in the maze.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the maze.
     * @return the number of columns in the maze.
     */
    public int getCols() {
        return cols;
    }

    //
    // This method constructs the maze by recursively dividing an area
    // into four quadrants. On each division, the vertical and horizonal
    // wall defining the quadrants are drawn, and an arbitrary opening
    // is made in each of the quadrants. Then the method calls itself
    // recursively on each of the quadrants. The method terminates when
    // the width and height of the area to divide are equal to 0.
    //
    private void divideMaze(int r0, int c0, int r1, int c1) {
        if (r0 == r1 && c0 == c1) {
            return;
        }
        int row = Math.max(r0, RandomGenerator.getInstance().nextInt(r0, r1));
        int col = Math.max(c0, RandomGenerator.getInstance().nextInt(c0, c1));
        if (c1 - c0 >= 0) {
            makeHorizontalWall(row, c0, c1, Tile.SOUTH);
            if (row < rows - 1) {
                makeRandomHOpening(row, c0, col, Tile.SOUTH);
                makeRandomHOpening(row, col, c1, Tile.SOUTH);
            }
        }
        if (r1 - r0 >= 0) {
            makeVerticleWall(col, r0, r1, Tile.EAST);
            if (col < cols - 1) {
                makeRandomVOpening(col, r0, row, Tile.EAST);
                makeRandomVOpening(col, row, r1, Tile.EAST);
            }
        }
        if (row - r0 > 1) {
            if (col - c0 > 1) {
                divideMaze(r0, c0, row, col);
            }
            if (c1 - col > 1) {
                divideMaze(r0, col, row, c1);
            }
        }
        if (r1 - row > 1) {
            if (c1 - col > 1) {
                divideMaze(row, col, r1, c1);
            }
            if (col - c0 > 1) {
                divideMaze(row, c0, r1, col);
            }
        }
    }

    private void makeVerticleWall(int col, int startRow, int endRow, int direction) {
        for (int r = startRow; r <= endRow; ++r) {
            getTile(r, col).setWall(direction, true);
        }
    }

    private void makeHorizontalWall(int row, int startCol, int endCol, int direction) {
        for (int c = startCol; c <= endCol; ++c) {
            getTile(row, c).setWall(direction, true);
        }
    }

    private void makeRandomVOpening(int col, int startRow, int endRow, int direction) {
        if (endRow >= startRow) {
            getTile(RandomGenerator.getInstance().nextInt(startRow, endRow - 1), col).setWall(direction, false);
        }
    }

    private void makeRandomHOpening(int row, int startCol, int endCol, int direction) {
        if (endCol >= startCol) {
            getTile(row, RandomGenerator.getInstance().nextInt(startCol, endCol - 1)).setWall(direction, false);
        }
    }

}

