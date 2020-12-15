/** 
 * Name: Kenneth Lapid David, cs8bwans
 * Date: March 7, 2017
 * 
 * 
 * This file contains instructions for creating a playable
 * 2048 grid
 * */

//------------------------------------------------------------------//
// Gui2048.java                                                     //
//                                                                  //
// GUI Driver for 2048                                             //
//                                                                  //
// Author:  Ujjwal Gulecha                               //
// Date:    11/09/16                                                //
//------------------------------------------------------------------//


import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

/*
 * This class contains instructions for creating a 2048 board game
 * and have it be fully functional to a user
 * */
public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    private static final int TILE_WIDTH = 106;

    private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
    private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
                                                 //(128, 256, 512)
    private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
                                                  //(1024, 2048, Higher)

    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
    private static final Color COLOR_2 = Color.rgb(238, 228, 218);
    private static final Color COLOR_4 = Color.rgb(237, 224, 200);
    private static final Color COLOR_8 = Color.rgb(242, 177, 121);
    private static final Color COLOR_16 = Color.rgb(245, 149, 99);
    private static final Color COLOR_32 = Color.rgb(246, 124, 95);
    private static final Color COLOR_64 = Color.rgb(246, 94, 59);
    private static final Color COLOR_128 = Color.rgb(237, 207, 114);
    private static final Color COLOR_256 = Color.rgb(237, 204, 97);
    private static final Color COLOR_512 = Color.rgb(237, 200, 80);
    private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
    private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
    private static final Color COLOR_OTHER = Color.GREEN;
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
                        // For tiles >= 8

    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
                       // For tiles < 8

    private GridPane pane;
    
    private Tile[][] tile;
    
    private Text score;
    /** Add your own Instance Variables here */


     
    /*
   *  This method will create a 2048 game board
   *
   * @Param: primaryStage: the stage in which to set all the 
   *                       components
   * */
    @Override
    public void start(Stage primaryStage)
    {
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        // Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
        // Set the spacing between the Tiles
        pane.setHgap(15); 
        pane.setVgap(15);
        
        
        /** Add your Code for the GUI Here */
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Gui2048");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Text for the Game Title
        Text title = new Text();
        title.setText("2048");
        title.setFill(Color.rgb(119,110,101));
        title.setFont(Font.font("Impact", FontWeight.BOLD,100));
        
        // Box for the score
        Rectangle box = new Rectangle(156,80);
        box.setArcWidth(10);
        box.setArcHeight(10);
        box.setFill(COLOR_EMPTY);
        
        // Text for the score
        Text score = new Text();
        score.setText("SCORE " + "\n      " + Integer.toString(
                             this.getBoard().getScore()));
        score.setFill(COLOR_VALUE_LIGHT);
        score.setFont(Font.font("Impact", FontWeight.BOLD,25));
        this.score = score;
        
        // Add the score text, game title text, and 
        // rectangle for the score into the pane
        pane.add(title,0,0,2,1);
        pane.add(box,2,0,2,1);
        pane.add(score,2,0,2,1);
        
        // Aline the box for score and the score text
       
        GridPane.setHalignment(box,HPos.CENTER);
        GridPane.setHalignment(score,HPos.CENTER);
         
        // Define the instance variable tile[][] which will
        // hold all the tiles
        this.tile = new Tile[this.getBoard().GRID_SIZE] 
                                [this.getBoard().GRID_SIZE];
        
        // Create the tiles for the board
        for(int x = 0; x < board.GRID_SIZE; x++){
          for(int y = 0; y < board.GRID_SIZE; y++){
            // Create a tile
            Tile tile = new Tile();
            // Change the properties of the tile based on the position of
            // the grid
             tile = generateBoard(this.getBoard().getGrid(),tile,x,y);
             
             // Save this tile into the 2d tile array
             this.tile[x][y] = tile;
             
             
             // Add the Tiles to the Gridpane
             pane.add(tile.getTile(),y,x+1);
             pane.add(tile.getText(),y,x+1);
             // Center the text
             GridPane.setHalignment(tile.getText(),HPos.CENTER);
             
          }
        }
        
       // If a key is pressed, handle that event
       scene.setOnKeyPressed(new myKeyHandler());

    }


    /** Add your own Instance Methods Here */
    
    /*
   *  This method will get the board instance variable
   *
   * @Return: Board: the board that is to be played
   * */
    public Board getBoard(){
      return board;
    }
    
    /*
   *  This method will get the output file to save
   * the board to 
   *
   * @Return: String: the name of the file to save the board to
   * */
    public String getOutputBoard(){
      return this.outputBoard;
    }
    
    /*
   *  This method will get the size of the tiles
   *
   * @Return: int: the size of the tiles
   * */
    public static int getTileWidth(){
      return TILE_WIDTH;
    }
    
    /*
   *  This method will move the board up
   *
   * @Param: Board: the board that is to be moved
   * */
    public static void moveTileUp(Board board){
      boolean moveUp = false;
      // Attempt to move up
      moveUp = board.move(Direction.UP);
      // Check to see if a move up was successful
      if(moveUp == true){
        // Add a tile to the board
        board.addRandomTile();
        System.out.println("Moving Up");
      }
    }
    
     /*
   *  This method will move the board Down
   *
   * @Param: Board: the board that is to be moved
   * */
    public static void moveTileDown(Board board){
      boolean moveDown = false;
      // Attempt to move down
      moveDown = board.move(Direction.DOWN);
      // Check to see if a move down was successful
      if(moveDown == true){
        // Add a tile to the board
        board.addRandomTile();
        System.out.println("Moving Down");
      }
    }
    
     /*
   *  This method will move the board left
   *
   * @Param: Board: the board that is to be moved
   * */
    public static void moveTileLeft(Board board){
      boolean moveLeft = false;
      // Attempt to move left
      moveLeft = board.move(Direction.LEFT);
      // Check to see if a move left was successful
      if(moveLeft == true){
        // Add a random tile to the board
        board.addRandomTile();
        System.out.println("Moving Left");
      }
    }
    
     /*
   *  This method will move the board right
   *
   * @Param: Board: the board that is to be moved
   * */
    public static void moveTileRight(Board board){
      boolean moveRight = false;
      // Attempt to move right
      moveRight = board.move(Direction.RIGHT);
      // Check to see if a move right was successful
      if(moveRight == true){
        // Add a random tile to the board
        board.addRandomTile();
        System.out.println("Moving Right");
      }
    }
    
     /*
   *  This method will save the board
   *
   * @Param: Board: the board that is to be saved
   * @Param: String: the name of the file to save the board to
   * */
    public static void saveGame(Board board, String outputBoard){
      try {
        // Save the board to the passed file name
       board.saveBoard(outputBoard);
       System.out.println("Saving board to" + outputBoard);
      } 
      catch (IOException e) { 
       System.out.println("saveBoard threw an exception");
      }
    }
    
     /*
   *  This method will generate the tiles of the GUI game board
   *
   * @Param: board: the grid to get the tiles from
   * @Param: tile:
   * @param: x: Starting point of the grid at the rows
   * @param y: Starting point of the grid at the columns
   * 
   * @Return: Tile: The tile with the matching board properties
   * */
    public static Tile generateBoard(int[][] theBoard, Tile tile, int x, int y){
              // Check the value at the x,y position of the board grid
              if(theBoard[x][y] == 0){
                // Set the instance variables of the tile based on the
                // properties
                tile.getText().setText("");
                tile.getTile().setFill(COLOR_EMPTY);
              }
               // Check the value at the x,y position of the board grid
              if(theBoard[x][y] == 2){
                // Set the instance variables of the tile based on the
                // properties
                tile.getTile().setFill(COLOR_2);
                tile.getText().setText("2");
                tile.getText().setFill(COLOR_VALUE_DARK);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_LOW));
              }
               // Check the value at the x,y position of the board grid
              if(theBoard[x][y] == 4){
                // Set the instance variables of the tile based on the
                // properties
                tile.getTile().setFill(COLOR_4);
                tile.getText().setText("4");
                tile.getText().setFill(COLOR_VALUE_DARK);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_LOW));
              }
               if(theBoard[x][y] == 8){
                 // Set the instance variables of the tile based on the
                // properties
                tile.getTile().setFill(COLOR_8);
                tile.getText().setText("8");
                tile.getText().setFill(COLOR_VALUE_LIGHT);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_LOW));
              }
                // Check the value at the x,y position of the board grid
               if(theBoard[x][y] == 16){
                 // Set the instance variables of the tile based on the
                // properties
                tile.getTile().setFill(COLOR_16);
                tile.getText().setText("16");
                tile.getText().setFill(COLOR_VALUE_LIGHT);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_MID));
              }
                // Check the value at the x,y position of the board grid
               if(theBoard[x][y] == 32){
                 // Set the instance variables of the tile based on the
                // properties
                tile.getTile().setFill(COLOR_32);
                tile.getText().setText("32");
                tile.getText().setFill(COLOR_VALUE_LIGHT);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_MID));
              }
                // Check the value at the x,y position of the board grid
               if(theBoard[x][y] == 64){
                 // Set the instance variables of the tile based on the
                // properties
                tile.getTile().setFill(COLOR_64);
                tile.getText().setText("64");
                tile.getText().setFill(COLOR_VALUE_LIGHT);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_MID));
              }
                // Check the value at the x,y position of the board grid
               if(theBoard[x][y] == 128){
                 // Set the instance variables of the tile based on the
                // properties
                tile.getTile().setFill(COLOR_128);
                tile.getText().setText("128");
                tile.getText().setFill(COLOR_VALUE_LIGHT);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_MID));
              }
                // Check the value at the x,y position of the board grid
               if(theBoard[x][y] == 256){
                tile.getTile().setFill(COLOR_256);
                tile.getText().setText("256");
                tile.getText().setFill(COLOR_VALUE_LIGHT);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_MID));
              }
                // Check the value at the x,y position of the board grid
               if(theBoard[x][y] == 512){
                 // Set the instance variables of the tile based on the
                // properties
                tile.getTile().setFill(COLOR_512);
                tile.getText().setText("512");
                tile.getText().setFill(COLOR_VALUE_LIGHT);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_MID));
              }
                // Check the value at the x,y position of the board grid
               if(theBoard[x][y] == 1024){
                 // Set the instance variables of the tile based on the
                // properties
                tile.getTile().setFill(COLOR_1024);
                tile.getText().setText("1024");
                tile.getText().setFill(COLOR_VALUE_LIGHT);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_HIGH));
              }
                // Check the value at the x,y position of the board grid
               if(theBoard[x][y] == 2048){
                 // Set the instance variables of the tile based on the
                // properties
                tile.getTile().setFill(COLOR_2048);
                tile.getText().setText("2048");
                tile.getText().setFill(COLOR_VALUE_LIGHT);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_HIGH));
              }
                // Check the value at the x,y position of the board grid
               if(theBoard[x][y] > 2048){
                 // Set the instance variables of the tile based on the
                // properties
                 
                tile.getTile().setFill(COLOR_OTHER);
                tile.getText().setText(Integer.toString(theBoard[x][y]));
                tile.getText().setFill(COLOR_VALUE_LIGHT);
                tile.getText().setFont(Font.font("Impact", 
                          FontWeight.BOLD,TEXT_SIZE_HIGH));
              }
               
               return tile;
  
    }
    








    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(new Random(), inputBoard);
            else
                board = new Board(new Random(), boardSize);
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                               " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                           "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                           "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                           "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
    }
    
/*
 * This class contains instructions for handling certain
 * keyboard events for the 2048GUI
 * */    
private class myKeyHandler implements EventHandler<KeyEvent>
 {
  @Override
  public void handle(KeyEvent e)
  {
     /*
      * Handle different keyboard events based on
      * what key is pressed
      * */
     if(e.getCode() == KeyCode.UP){
        moveTileUp(Gui2048.this.getBoard());
      }
      else if(e.getCode() == KeyCode.DOWN){
        moveTileDown(Gui2048.this.getBoard());
      }
      else if(e.getCode() == KeyCode.LEFT){
        moveTileLeft(Gui2048.this.getBoard());
      }
      else if(e.getCode() == KeyCode.RIGHT){
        moveTileRight(Gui2048.this.getBoard());
      }
      else if(e.getCode() == KeyCode.S){
        saveGame(Gui2048.this.getBoard(), Gui2048.this.getOutputBoard());
      }
      
      // Update the tiles on the board
        for(int x = 0; x < Gui2048.this.getBoard().GRID_SIZE; x++){
         for(int y = 0; y < Gui2048.this.getBoard().GRID_SIZE; y++){
             generateBoard(Gui2048.this.getBoard().getGrid(), 
                           Gui2048.this.tile[x][y], x,y);   
          }
        }
        
        // Update the score
        Gui2048.this.score.setText("SCORE " + "\n      " + Integer.toString(
                             Gui2048.this.getBoard().getScore()));
        //Check to see if the game is over
        if(Gui2048.this.getBoard().isGameOver()){
          
          // Create "Game Over!" text
          Text text = new Text();
          text.setText("Game Over!");
          text.setFill(COLOR_VALUE_DARK);
          text.setFont(Font.font("Impact",FontWeight.BOLD,TEXT_SIZE_LOW));
          
          // Create transparent overlay
          StackPane transparent = new StackPane();
          StackPane.setAlignment(text, Pos.CENTER);
          transparent.getChildren().addAll(text);
          transparent.setStyle(
                   "-fx-background-color: rgb(238, 228, 218, 0.50);");
          transparent.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
         
          pane.add(transparent,0,0,Gui2048.this.getBoard().GRID_SIZE+1,
                   Gui2048.this.getBoard().GRID_SIZE+1);
        }
        
  }
 }
    
}

/*
 * This class defines a tile that corresponds
 * to a given location on the Board's 2d
 * array grid. Each tile object will contain
 * a rectangle, which will outline the tile
 * and a text which will show the value
 * of the tile
 * */
 class Tile{
          public Rectangle tile;
          public Text text;
          
           /*
            *  This method will get the tile's rectangle
            *
            * @Return: Rectangle: the rectangle
            * */
          public Rectangle getTile(){
            return this.tile;
          }
          
           /*
            *  This method will get the tile's text
            *
            * @Return: Text: the text associated with this tile
            * */
          public Text getText(){
            return this.text;
          }     
          
           /*
            * Constructor that creates a tile with set dimensions and
            * initializes the text
            * */
          public Tile(){
            this.tile = new Rectangle(Gui2048.getTileWidth(),
                                      Gui2048.getTileWidth());
            this.tile.setArcWidth(10);
            this.tile.setArcHeight(10);
            this.text = new Text();
          }
        }
 


