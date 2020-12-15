/** 
 * Name: Kenneth Lapid David, cs8bwans
 * Date: January 26th, 2017
 * 
 * 
 * This file contains the Board class which one can
 * use to create a new game board or load a previously
 * used game board. In addition, one can save their
 * current board, or issue different flips on
 * their boards under this class.
 * */

/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

/*
 * This class contains instructions for creating board objects, and 
 * different methods that can be invoked on board objects 
 * */
public class Board {
    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random;
    private int[][] grid;
    private int score;

    
    // Constructs a fresh board with random tiles
    // @param random: An object of the random class that will later be 
    //                 used to generate random int values
    // @param boardSize: Desired size of the game board
    public Board(Random random, int boardSize) {
      this.random = random;
      GRID_SIZE = boardSize;
      this.score = 0;
      this.grid = new int[boardSize][boardSize];
         
         for(int x = 0; x < this.NUM_START_TILES; x++){
           this.addRandomTile();
          }
    }

    
    // Construct a board based off of an input file
    // @param random: An object of the random class that will later be 
    //                 used to generate random int values
    // @param inputBoard: The file which contains the saved board that
    //                     will be loaded
    public Board(Random random, String inputBoard) throws IOException {
        this.random = random;
        int size = 0;
        int score = 0;
        
        Scanner input = new Scanner(new File(inputBoard));
        while(input.hasNext()){
        size = input.nextInt();
        score = input.nextInt();
        this.grid = new int[size][size];

          for(int x = 0; x < this.grid.length; x++){
            for(int y = 0; y < this.grid[x].length; y++){
              this.grid[x][y] = input.nextInt();
             }
            }
          }
 
        this.GRID_SIZE = size;
        this.score = score;
 
    }

    
    // Saves the current board to a file
    // @param outputBoard: The file to save the board to
    public void saveBoard(String outputBoard) throws IOException {
      // Create a file to save the board to
     File file = new File(outputBoard);
     // Create a printwriter object
     PrintWriter output = new PrintWriter(file);
     // Print out the grid size
     output.println(this.GRID_SIZE);
     // Print out the score
     output.print(this.score);
     
     //Loop through the 2D-array
     for(int x = 0; x < this.grid.length; x++){
      output.println();
      for(int y = 0; y < this.grid[x].length; y++){
        // Get the value at the [x][y] location of the board
       String values = Integer.toString(this.grid[x][y]) + " ";
       // Print out the values
       output.print(values);
      }
     }
     // New Line
     output.println();
     // Close the Print Writer
     output.close();
    }

    
    // Adds a random tile (of value 2 or 4) to a
    // random empty space on the board
    public void addRandomTile() {
     int count = 0;
     
     // Loop through the 2D-Array
     for(int x = 0; x < this.grid.length; x++){
      for(int y = 0; y < this.grid[x].length; y++){
        // if space is empty, increment the count
       if(this.grid[x][y] == 0) count++;
      }
     }
     
     // If there are no empty spaces, return
     if(count == 0) return;
     
     // Generate random int values 
     int location = this.random.nextInt(count);
     int value = this.random.nextInt(100);

     count = 0;
     
     // Loop through the 2D Array
     for(int x = 0; x < this.grid.length; x++){
      for(int y = 0; y < this.grid[x].length; y++){
        // If grid is empty increment the count
       if(this.grid[x][y] == 0) count++;
       // If count - 1 is equal to the randomly generated value
       // for location add a tile
       if(count - 1 == location && this.grid[x][y] == 0){
         // If randomly generated value is less than TWO_PROBABILITY
         // add a tile with a value of 2
        if(value < TWO_PROBABILITY){
         this.grid[x][y] = 2;
        }
        else{
          // If randomly generated value is greater than TWO_PROBABILITY
         // add a tile with a value of 4
         this.grid[x][y] = 4;
        }
       }
      }
     }

    }

    
    // Flip the board horizontally or vertically,
    // Rotate the board by 90 degrees clockwise or 90 degrees counter-clockwise.
    // @param change: int values from (1-5) associated with a given
    //                flip or rotation
    public void flip(int change) {
      if(change == 1){
        // Loop through all the rows of the 2D arrays but only
        // half of the columns
        for(int x = 0; x < this.grid.length; x++){
          for(int y = 0; y < this.grid[x].length/2; y++){
            // Save the value at this location
            int temp = this.grid[x][this.grid.length-y-1];
            // Set the end values to the beginning values
            this.grid[x][this.grid.length-y-1] = this.grid[x][y];
            // Set the beginning values to the end values
            this.grid[x][y] = temp;
            
          }
        }
      }
      
       if(change == 2){
         // Loop through all the columns of the 2D arrays but only
        // half of the rows
        for(int x = 0; x < this.grid.length/2; x++){
          for(int y = 0; y < this.grid[x].length; y++){
            // Save the value at this location
            int temp2 = this.grid[this.grid.length-x-1][y];
             // Set the end values to the beginning values
            this.grid[this.grid.length-x-1][y] = this.grid[x][y];
            // Set the beginning values to the end values
            this.grid[x][y] = temp2;
          }
        }
      }
       
        if(change == 3){
          // Make a new int[][]
        int[][] clockwise = new int[this.grid.length][this.grid.length];
        // Loop through both the 2D arrays
        for(int x = 0; x < this.grid.length; x++){
          for(int y = 0; y < this.grid[x].length; y++){
            clockwise[y][this.grid.length-x-1] = this.grid[x][y];
          }
        }
        
        // Copy the values from the created array to the
        // existing array
        for(int x = 0; x < this.grid.length; x++){
          for(int y = 0; y < this.grid[x].length; y++){
            this.grid[x][y] = clockwise[x][y];
          }
        }
      }
        
         if(change == 4){
           // Make a new int[][]
       int[][] counterClockwise = new int[this.grid.length][this.grid.length];
       // Loop through both the 2D arrays
        for(int x = 0; x < this.grid.length; x++){
          for(int y = 0; y < this.grid[x].length; y++){
            counterClockwise[this.grid.length-y-1][x] = this.grid[x][y];
          }
        }
        
        // Copy the values from the created array to the
        // existing array
        for(int x = 0; x < this.grid.length; x++){
          for(int y = 0; y < this.grid[x].length; y++){
            this.grid[x][y] = counterClockwise[x][y];
          }
        }
        }
         
         if(change == 5){
           // Make a new int[][]
       int[][] diagonal = new int[this.grid.length][this.grid.length];
       // Loop through both the 2D arrays
        for(int x = 0; x < this.grid.length; x++){
          for(int y = 0; y < this.grid[x].length; y++){
            diagonal[y][this.grid.length-x-1] = this.grid[x][y];
          }
        }
        
        // Copy the values from the created array to the
        // existing array
        for(int x = 0; x < this.grid.length; x++){
          for(int y = 0; y < this.grid[x].length; y++){
            this.grid[x][y] = diagonal[x][y];
          }
        }
        }

    }

    //Complete this method ONLY if you want to attempt at getting the extra credit
    //Returns true if the file to be read is in the correct format, else return
    //false
    public static boolean isInputFileCorrectFormat(String inputFile) {
        //The try and catch block are used to handle any exceptions
        //Do not worry about the details, just write all your conditions inside the
        //try block
        try {
            //write your code to check for all conditions and return true if it satisfies
            //all conditions else return false
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /* Method that checks to see if a move can be made
     * towards the left direction
     * @param moveLeft: boolean value. If true, we can move left
     *                  if false, cant move left
     * */
    private boolean canMoveLeft(){
     boolean moveLeft = false;
     
     // Loop through the board
     for(int x = 0; x < this.grid.length; x++){
        for(int y = 0; y < this.grid[x].length-1; y++){
            // If there is an empty tile we can move left
            if(this.grid[x][y] == 0) moveLeft = true;
            // If there are neighboring tiles equal to each other
            // we can move left
            if(this.grid[x][y] == this.grid[x][y+1]) moveLeft = true;
           }
         }
     return moveLeft; 
    }
    
     /* Method that checks to see if a move can be made
     * towards the right direction
     * @param moveRight: boolean value. If true, we can move right
     *                  if false, cant move right
     * */
    private boolean canMoveRight(){
     boolean moveRight = false;
     
     // Loop through the entire board 
     for(int x = 0; x < this.grid.length; x++){
        for(int y = 0; y < this.grid[x].length-1; y++){
            // If there is an empty tile we can move right
            if(this.grid[x][y+1] == 0) moveRight = true;
            // If there are neighboring tiles equal to each other
            // then we can move right
            if(this.grid[x][y] == this.grid[x][y+1]) moveRight = true;
           }
         }
     return moveRight;
    }
    
     /* Method that checks to see if a move can be made
     * upward
     * @param moveUp: boolean value. If true, we can move upward
     *                  if false, cant move upwards
     * */
    private boolean canMoveUp(){
     boolean moveUp = false;
     
     // Loop through the entire board
     for(int x = 0; x < this.grid.length-1; x++){
        for(int y = 0; y < this.grid[x].length; y++){
            // If there is an empty tile we can move up
            if(this.grid[x][y] == 0) moveUp = true;
            // If there are neighboring tiles equal to each other
            // then we can move up
            if(this.grid[x][y] == this.grid[x+1][y]) moveUp = true;
           }
         }
     return moveUp;
    }
    /* Method that checks to see if a move can be made
     * downward
     * @param moveDown: boolean value. If true, we can move downward
     *                  if false, cant move downwards
     * */
    private boolean canMoveDown(){
     boolean moveDown = false;
     
     // Loop through the entire board
     for(int x = 0; x < this.grid.length-1; x++){
        for(int y = 0; y < this.grid[x].length; y++){
            // If there is an empty tile then we can move down
            if(this.grid[x+1][y] == 0) moveDown = true;
            // If there are neighboring tiles equal to each other
            // then we can move down 
            if(this.grid[x][y] == this.grid[x+1][y]) moveDown = true;
           }
         }
     return moveDown;
    }
    
    /* Method that checks to see if we can move in a certain
     * direction
     * @param direction: The direction you want to move
     * @return canMove: Boolean value. If true, then you can move
     *                  in the indicated direction, if false then
     *                  you cant move in the indicated direction
     * */
    public boolean canMove(Direction direction){
      boolean canMove = false;
      
      // Check to see if you can move Left
      if(direction.equals(Direction.LEFT)) canMove = this.canMoveLeft();
      // Check to see if you can move Right
      if(direction.equals(Direction.RIGHT)) canMove = this.canMoveRight();
      // Check to see if you can move Up
      if(direction.equals(Direction.UP)) canMove = this.canMoveUp();
      // Check to see if you can move Down
      if(direction.equals(Direction.DOWN)) canMove = this.canMoveDown();

      return canMove;

    }
    
    /* Method that will move the tiles on the board to the left
     * */
    private void moveLeft(){
      int loop = 0;
      while(loop < this.grid.length-1){
        // Loop through the game board
        for(int x = 0; x < this.grid.length; x++){
           for(int y = 0; y < this.grid[x].length-1; y++){
               // Find tiles that are empty
               if(this.grid[x][y] == 0){
               // Add the neighboring tiles together
               this.grid[x][y] += this.grid[x][y+1];
               // Let the right tile be empty
               this.grid[x][y+1] = 0;
               }
             }
          }
        loop++;
      }
      
      // Loop through the board a second time, looking for tiles
      // that can merge
      for(int x = 0; x < this.grid.length; x++){
         for(int y = 0; y < this.grid[x].length-1; y++){
             // Find neighboring tiles that are equal to each other
             if(this.grid[x][y] == this.grid[x][y+1]){
             // Add the two tiles together
             this.grid[x][y] += this.grid[x][y+1];
             // Let the right tile be empty
             this.grid[x][y+1] = 0;
             // Update the score
             this.score += this.grid[x][y];
             }
          }
       }

       // Loop through the board one last time 
       for(int x = 0; x < this.grid.length; x++){
           for(int y = 0; y < this.grid[x].length-1; y++){
              // Find tiles that are empty
              if(this.grid[x][y] == 0){
              // Add the neighboring tiles together
              this.grid[x][y] += this.grid[x][y+1];
              // Let the right tile be empty
              this.grid[x][y+1] = 0;
              }
            }
         }
          
   }
    
    /* Method that will move the tiles on the board to the right
     * */
    private void moveRight(){
      int loop = 0;
      while(loop < this.grid.length-1){
        // Loop through the game board
        for(int x = 0; x < this.grid.length; x++){
           for(int y = 0; y < this.grid[x].length-1; y++){
               // Find tiles that are empty
               if(this.grid[x][y+1] == 0){
               // Add the neighboring tiles together
               this.grid[x][y+1] += this.grid[x][y];
               // Let the left tile be empty
               this.grid[x][y] = 0;
               }
             }
          }
        loop++;
      }
      
      // Loop through the board a second time, looking for tiles
      // that can merge
      for(int x = 0; x < this.grid.length; x++){
         for(int y = this.grid.length-2; y >= 0; y--){
             // Find neighboring tiles that are equal to each other
             if(this.grid[x][y] == this.grid[x][y+1]){
             // Add the two tiles together
             this.grid[x][y+1] += this.grid[x][y];
             // Let the left tile be empty
             this.grid[x][y] = 0;
             // Update the score
             this.score += this.grid[x][y+1];
             }
          }
       }

       // Loop through the board one last time 
       for(int x = 0; x < this.grid.length; x++){
           for(int y = 0; y < this.grid[x].length-1; y++){
              // Find tiles that are empty
              if(this.grid[x][y+1] == 0){
              // Add the neighboring tiles together
              this.grid[x][y+1] += this.grid[x][y];
              // Let the left tile be empty
              this.grid[x][y] = 0;
              }
            }
         }
          
   }
    
    
    /* Method that will move the tiles on the board upward
     * */
    private void moveUp(){
      int loop = 0;
      while(loop < this.grid.length-1){
      // Loop through the board
      for(int y = 0; y < this.grid.length; y++){
          for(int x = 0; x < this.grid.length-1; x++){
              // Find the tiles that are empty
              if(this.grid[x][y] == 0){
                // Add the neighboring tiles together
                this.grid[x][y] += this.grid[x+1][y];
                // Let the lower tile be empty 
                this.grid[x+1][y] = 0;
              }
          }
      }
      loop++;
      }
      
      // Loop through the board a second time, looking for tiles
      // that can merge
       for(int y = 0; y < this.grid.length; y++){
          for(int x = 0; x < this.grid.length-1; x++){
             // Find neighboring tiles that are equal to each other
             if(this.grid[x][y] == this.grid[x+1][y]){
               // Add the two tiles together
               this.grid[x][y] += this.grid[x+1][y];
               // Let the lower tile be empty
               this.grid[x+1][y] = 0;
               // Update the score
               this.score += this.grid[x][y];
             }
          }
       }
       
       // Loop through the board one last time
       for(int y = 0; y < this.grid.length; y++){
          for(int x = 0; x < this.grid.length-1; x++){
              // Find the tiles that are empty
              if(this.grid[x][y] == 0){
                // Add the neighboring tiles together
                this.grid[x][y] += this.grid[x+1][y];
                // Let the lower tile be empty 
                this.grid[x+1][y] = 0;
              }
          }
      }
   }
    
    /* Method that will move the tiles on the board downward
     * */
    private void moveDown(){
      int loop = 0;
      while(loop < this.grid.length-1){
      // Loop through the board
      for(int y = 0; y < this.grid.length; y++){
          for(int x = 0; x < this.grid.length-1; x++){
              // Find the tiles that are empty
              if(this.grid[x+1][y] == 0){
                // Add the neighboring tiles together
                this.grid[x+1][y] += this.grid[x][y];
                // Let the upper tile be empty 
                this.grid[x][y] = 0;
              }
          }
      }
      loop++;
      }
      
      // Loop through the board a second time, looking for tiles
      // that can merge
       for(int y = 0; y < this.grid.length; y++){
          for(int x = 0; x < this.grid.length-1; x++){
             // Find neighboring tiles that are equal to each other
             if(this.grid[x][y] == this.grid[x+1][y]){
               // Add the two tiles together
               this.grid[x][y] += this.grid[x+1][y];
               // Let the lower tile be empty
               this.grid[x+1][y] = 0;
               // Update the score
               this.score += this.grid[x][y];
             }
          }
       }
       
      int loop2 = 0;
      while(loop2 < this.grid.length-1){
       // Loop through the board one last time
       for(int y = 0; y < this.grid.length; y++){
          for(int x = 0; x < this.grid.length-1; x++){
              // Find the tiles that are empty
              if(this.grid[x+1][y] == 0){
                // Add the neighboring tiles together
                this.grid[x+1][y] += this.grid[x][y];
                // Let the lower tile be empty 
                this.grid[x][y] = 0;
              }
          }
      }
       loop2++;
   }
    }
    
    
    // No need to change this for PSA3
    // Performs a move Operation
    //@return move: boolean value. If true, at least one tile had
    //              moved. False, no tiles moved.
    public boolean move(Direction direction) {
      boolean move = false;
      
      // Make a copy of the board
      int[][] copy  = new int[this.grid.length][this.grid.length];
      for(int x = 0; x < copy.length; x++){
        for(int y = 0; y < copy[x].length; y++){
          copy[x][y] = this.grid[x][y];
        }
      }
      
      // Check to see if direction is left
      if(direction.equals(Direction.LEFT)){
        // Check to see if a left move is valid
        if(this.canMove(direction) == true){ 
          // Move left
           this.moveLeft();
           move = true;
        }
      }
      
      // Check to see if the direction is right
      if(direction.equals(Direction.RIGHT)){
        // Check to see if a right move is valid
        if(this.canMove(direction) == true){
          // Move Right
           this.moveRight();
           move = true;
        }
      }
      
      // Check to see if the direction is up
      if(direction.equals(Direction.UP)){
        // Check to see if moving up is valid
        if(this.canMove(direction) == true){ 
          // Move up
           this.moveUp();
           move = true;
        }
      }
      
      // Check to see if the direction is down
      if(direction.equals(Direction.DOWN)){
        // Check to see if you can move downward
        if(this.canMove(direction) == true){
           // Move downward
           this.moveDown();
           move = true;
        }
      }
      
      // Check the copy array against the changed board
      boolean same = false;
      for(int x = 0; x < copy.length; x++){
        for(int y = 0; y < copy[x].length; y++){
          if(this.grid[x][y] != copy[x][y]) same = true;
        }
      }
      
      // If no tiles moved, then move will be false
      if(same == false) move = false;
      
      return move;
    }


    // Check to see if we have a game over
    // @return over: boolean value, if true game is over
    //              false, game is still running
    public boolean isGameOver() {
      boolean over = false;
      
      // Check to see if the board is full
      int empty = 0;
      // Loop through the board
      for(int x = 0; x < this.grid.length; x++){
        for(int y = 0; y < this.grid[x].length; y++){
          // If board has an empty space, increment variable empty
          if(this.grid[x][y] == 0) empty++;
        }
      }
      
      // Check to see if you have any valid moves left
      boolean moves = false;
      // Check tok see if you can move left
      if(this.canMove(Direction.LEFT) == true) moves = true;
      // Check tok see if you can move right
      if(this.canMove(Direction.RIGHT) == true) moves = true;
      // Check tok see if you can move up
      if(this.canMove(Direction.UP) == true) moves = true;
      // Check tok see if you can move down
      if(this.canMove(Direction.DOWN) == true) moves = true;
      
      // If board is full and no moves left, game is over
      if(empty == 0 && moves == false) over = true;
     
      return over;
    }

    // Return the reference to the 2048 Grid
    public int[][] getGrid() {
        return grid;
    }

    // Return the score
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}
