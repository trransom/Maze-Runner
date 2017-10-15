package maze;

import acm.program.GraphicsProgram;
import java.awt.Color;
import svu.csc213.Dialog;

/**
 * Uses a recursive method to traverse from 
 * one tile in a maze (the "start") to another
 * tile in the maze (the "end").
 * @author Thomas Ransom
 */
public class MazeRunner extends GraphicsProgram {

    Tile start;
    Tile end;
    Tile back;
    Tile current;
    Tile next;
    boolean ispath;
    boolean test;

    /*
    Initializes the maze, sets up the
    beginning and ending tiles, and
    displays the appropriate message for whether
    a path was found or not.
    */
    @Override
    public void run() {
        Maze maze = new Maze(22, 35);
        add(maze);
        int start1 = 0;
        int start2 = 0;
        int end1 = 21;
        int end2 = 34;
        start = maze.getTile(start1, start2);
        end = maze.getTile(end1, end2);
        start.fillColor(Color.green);
        end.fillColor(Color.green);
        if (findPath(start)){
            Dialog.showMessage(this, "Path found!");
        } else Dialog.showMessage(this, "No solution");
    }
    
    
    /*
    Recursive method which takes a tile, checks the
    tiles surrounding it, and then changes the colors of
    the tiles appropriately. The current tile turns gray
    and the next tile turns yellow if there is an unflagged
    tile within the tile's reach, and the tile turns red if
    there are no possible paths.
    */
    public boolean findPath(Tile current) {
        if(current == end){
            return true;
        } 
        for (int i=0; i<=3; ++i){
            if (current.hasWall(i) == false && !(current.getNeighbor(i).isFilled)){
                next = current.getNeighbor(i);
                next.fillColor(Color.yellow);
                next.isFilled = true;
                current.fillColor(Color.gray);
                current.isFilled = true;
                start.fillColor(Color.green);
                pause(20);
                if (findPath(next)){
                    return true;
                } 
            }
        } current.fillColor(Color.red);
          current.isFilled = true;
          return false;
    }
    
    /*
    Initializes the program.
    */
    public static void main(String[] args) {
        new MazeRunner().start();
    }

}


