/** 
 * Name: Kenneth Lapid David, cs8bwans
 * Date: January 26th, 2017
 * 
 * 
 * This file contains the GameManager class which one
 * uses to actually play the created 2048 game
 * */

import java.util.*;
import java.io.*;

/*
 * This class manages the whole 2048 game. Use this class
 * to actually play the game
 * */
public class GameManager {
    // Instance variables
    private Board board; // The actual 2048 board
    private String outputFileName; // File to save the board to when exiting


    // GameManager Constructor
    // Generate new game
    // @param outputBoard: The file in which we will save the board
    // @param boardSize: The size of the game board
    // @param random: An object of the random class that will later be 
    //                 used to generate random int values
    public GameManager(String outputBoard, int boardSize, Random random) {
        System.out.println("Generating a New Board");
        this.outputFileName = new String(outputBoard);
        this.board = new Board(random,boardSize);

    }

    // GameManager Constructor
    // Load a saved game
    // @param inputBoard: The file which contains the saved board that
    //                     will be loaded
    // @param outputBoard: The file in which we will save the board
    // @param random: An object of the random class that will later be 
    //                 used to generate random int values
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
        System.out.println("Loading Board from " + inputBoard);
        this.outputFileName = new String(outputBoard);
        this.board = new Board(random,inputBoard);
    }

   
    // Main play loop
    // Takes in input from the user to specify moves to execute
    // valid moves are:
    //      k - Move up
    //      j - Move Down
    //      h - Move Left
    //      l - Move Right
    //      q - Quit and Save Board
    //
    //  If an invalid command is received then print the controls
    //  to remind the user of the valid moves.
    //
    //  Once the player decides to quit or the game is over,
    //  save the game board to a file based on the outputFileName
    //  string that was set in the constructor and then return
    //
    //  If the game is over print "Game Over!" to the terminal
    public void play() throws IOException {
      // Print the user controls
      this.printControls();
      
      // Print the current state of the game board
      System.out.println(this.board.toString());
      
      Scanner input = new Scanner(System.in);
      // Keep reading user input
      while(input.hasNext()){
      System.out.println("Enter a command");
      // Save the entered command
      char command = input.next().charAt(0);
      
      /* Check entered commands to see if they are
       * valid, if so perform that command then
       * print out the new state of the board. If
       * not, prompt the user to enter another 
       * command.
       * */
      if(Character.toLowerCase(command) == 'k'){
        boolean moveUp = false;
        moveUp = this.board.move(Direction.UP);
        if(moveUp == true) this.board.addRandomTile();
        System.out.println(this.board.toString());
      }
      else if(Character.toLowerCase(command) == 'j'){
        boolean moveDown = false;
        moveDown = this.board.move(Direction.DOWN);
        if(moveDown == true) this.board.addRandomTile();
        System.out.println(this.board.toString());
      }
      else if(Character.toLowerCase(command) == 'h'){
        boolean moveLeft = false;
        moveLeft = this.board.move(Direction.LEFT);
        if(moveLeft == true) this.board.addRandomTile();
        System.out.println(this.board.toString());
      }
      else if(Character.toLowerCase(command) == 'l'){
        boolean moveRight = false;
        moveRight = this.board.move(Direction.RIGHT);
        if(moveRight == true) this.board.addRandomTile();
        System.out.println(this.board.toString());
      }
      else if(Character.toLowerCase(command) == 'q'){
        this.board.saveBoard(this.outputFileName);
        return;
      }
      else{
        System.out.println("Not a valid command! Please enter" +
                             " another command.");
      }
      
      // Check if the game is over
      if(this.board.isGameOver() == true){
        System.out.println("Game Over!");
      }
      }
      
    }
      

    // Print the Controls for the Game
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    k - Move Up");
        System.out.println("    j - Move Down");
        System.out.println("    h - Move Left");
        System.out.println("    l - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}
