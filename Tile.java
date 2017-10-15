package maze;

import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GRect;
import java.awt.Color;

/**
 *
 * @author Thomas Ransom
 */
public class Tile extends GCompound {

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public static final int TILEWIDTH = 20;
    public static final int TILEHEIGHT = 20;
    public boolean isFilled = false;

    public Tile(Maze maze, int row, int col) {
        this(maze, row, col, false, false, false, false);
    }

    public Tile(Maze maze, int row, int col, boolean nw, boolean ew, boolean sw, boolean ww) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        wall = new boolean[4];
        wall[NORTH] = nw;
        wall[EAST] = ew;
        wall[SOUTH] = sw;
        wall[WEST] = ww;
        rect = new GRect(1,1,TILEWIDTH-1,TILEHEIGHT-1);
        rect.setColor(Color.WHITE);
        add(rect);
        northWall = new GLine(0, 0, TILEWIDTH, 0);
        add(northWall);
        eastWall = new GLine(TILEWIDTH, 0, TILEWIDTH, TILEHEIGHT);
        add(eastWall);
        southWall = new GLine(0, TILEHEIGHT, TILEWIDTH, TILEHEIGHT);
        add(southWall);
        westWall = new GLine(0, 0, 0, TILEHEIGHT);
        add(westWall);
        drawTile();
    }

    public boolean hasWall(int direction) {
        return wall[direction];
    }
    
    public void fillColor(Color color){
        rect.setColor(color);
        rect.setFillColor(color);
        rect.setFilled(true);
    }
    
    /*
    Boolean method used to set a marker on a tile which indicates 
    that it has been visited by the path.
    */
    public boolean isFilled(Tile tile){
        return isFilled;
    }

    public void setWall(int direction, boolean status) {
        if (status) {
            if (!hasWall(direction)) {
                wall[direction] = true;
                if (hasNeighbor(direction)) {
                    getNeighbor(direction).setWall(getOppDir(direction), status);
                }
                drawTile();
            }
        } else {
            if (hasWall(direction)) {
                wall[direction] = false;
                if (hasNeighbor(direction)) {
                    getNeighbor(direction).setWall(getOppDir(direction), status);
                }
                drawTile();
            }
        }
    }

    public Tile getNeighbor(int direction) {
        switch (direction) {
            case NORTH:
                return maze.getTile(row - 1, col);
            case EAST:
                return maze.getTile(row, col + 1);
            case SOUTH:
                return maze.getTile(row + 1, col);
            case WEST:
                return maze.getTile(row, col - 1);
            default:
                return null;
        }
    }

    public boolean hasNeighbor(int direction) {
        return getNeighbor(direction) != null;
    }

    private int getOppDir(int direction) {
        return (direction + 2) % 4;
    }

    private void drawTile() {
        northWall.setVisible(false);
        eastWall.setVisible(false);
        southWall.setVisible(false);
        westWall.setVisible(false);
        if (hasWall(NORTH)) {
            northWall.setVisible(true);
        }
        if (hasWall(EAST)) {
            eastWall.setVisible(true);
        }
        if (hasWall(SOUTH)) {
            southWall.setVisible(true);
        }
        if (hasWall(WEST)) {
            westWall.setVisible(true);
        }
        this.repaint();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.row;
        hash = 67 * hash + this.col;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tile other = (Tile) obj;
        if (this.row != other.row) {
            return false;
        }
        if (this.col != other.col) {
            return false;
        }
        return true;
    }
    
    

    private boolean[] wall;
    private Maze maze;
    private int row;
    private int col;
    private GRect rect;
    private GLine northWall;
    private GLine southWall;
    private GLine eastWall;
    private GLine westWall;

}

