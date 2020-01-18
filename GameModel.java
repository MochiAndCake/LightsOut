/*
  Faye Lin
  Ann Soong
*/

import java.util.Random;

public class GameModel {
  private int width, height; // Hold the width and height of the board
  private boolean[][] model; // Holds the CURRENT state of the game board
  private int steps; // Counts the number of steps the player takes
  private Solution shortestSol; // Holds the shortest solution to the current board. Occasionally needs ot be recalculated.

  public GameModel(int width, int height){ // Constructor, creates a game that's completely off

    if(width <= 0 || height <= 0){ // Throws exception for illegal width or height
      throw new NegativeArraySizeException("Width and/or height cannot be equal or less than zero.");
    }

    this.width = width; // columns
    this.height = height; // rows
    model = new boolean[height][width];
    steps = 0;
    shortestSol = null;
  }

  public int getHeight(){ // returns the height of the board
    return height;
  }

  public int getWidth(){ // returns the width of the board
    return width;
  }

  // returns true if the location at row (i) and column (j) in the current baord is ON
  public boolean isON(int i, int j){
    if(i < 0 || i >= height || j < 0 || j >= width){
      throw new IndexOutOfBoundsException("The row and column cannot be lesser than zero, or equal or greater to height and width respectively.");
    }
    return model[i][j];
  }

  public void reset(){ // Resets the model to all OFF
    model = new boolean[height][width]; // Creates new array of off states
    steps = 0; // steps get reset
    shortestSol = null; // old solution is discarded
  }

  public void set(int i, int j, boolean value){ // Sets the location row (j), column (i) of the model to "value"
    if(j < 0 || j >= height || i < 0 || i >= width){
      throw new IndexOutOfBoundsException("The row and column cannot be lesser than zero, or equal or greater to height and width respectively.");
    }
    model[j][i] = value;
  }

  public String toString(){
    // Returns a string representation of the model
    String strHold = "[";
    for(int i = 0; i < height; i++){
      strHold += "[";
      for(int j = 0; j < width; j++){
        if(j > 0){
          strHold += ",";
        }
        strHold += model[i][j];
      }
      if(i == height-1){
        strHold += "]]\n";
      }
      else{
        strHold += "],\n";
      }
    }
    return strHold;
  }

  public void click(int i, int j){ // Changes the state of the dot on the model and the state of its neighbors on the model
    if(i < 0 || i >= height || j < 0 || j >= width){
      throw new IndexOutOfBoundsException("The row and column cannot be lesser than zero, or equal or greater to height and width respectively.");
    }
    steps++; // The player has taken another step

    model[i][j] = (!model[i][j]); // If on, turn off. If off, turn on

    if(i > 0){ // above
      model[i-1][j] = (!model[i-1][j]);
    }

    if(i < height-1){ // below
      model[i+1][j] = (!model[i+1][j]);
    }

    if(j > 0){ // left
      model[i][j-1] = (!model[i][j-1]);
    }

    if(j < width-1){ //right
      model[i][j+1] = (!model[i][j+1]);
    }
  }

  public int getNumberOfSteps(){ // Returns the number of steps the player has taken so far
    return steps;
  }

  public boolean isFinished(){ // Checks if the model is completely turned on
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width ; j++){
        if(!model[i][j]){
          return false;
        }
      }
    }
    return true;
  }

  public void randomize(){ // Creates a randomized model that is solveable
    model = new boolean[height][width]; // new board is off
    steps = 0; // steps are reset
    int intBox, intIndex;
      // intBox - used to choose how many boxes to change
      // intIndex - used to choose which box to change
    boolean flag = false; // Used to see if the random board is solveable
    Random rnd = new Random();

    while(!flag){ // while the board is not solveable, repeat the process below
      intBox = rnd.nextInt(height*width) + 1; // choose how many boxes to change
      for(int k = 0; k < intBox; k++){
        intIndex = rnd.nextInt(height*width); // chose the box to change
        model[intIndex/width][intIndex%width] = !(model[intIndex/width][intIndex%width]);
        // switch the status of the box
      }

      if(LightsOut.solve(this).size() > 0){
        // if there is at least one solution, then the process does not need to repeat
        flag = true;
      }
    }
  }

  public void setSolution(){ // finds the shortest solution to the current model
    shortestSol = LightsOut.solveShortest(this);
  }

  // if the model had a solution (even if it is not updated) and row (i), column (j) is on in the solution
  public boolean solutionSelects(int i, int j){
    if(i < 0 || i >= height || j < 0 || j >= width){
      throw new IndexOutOfBoundsException("The row and column cannot be lesser than zero, or equal or greater to height and width respectively.");
    }
    return  shortestSol != null && shortestSol.get(i, j);
  }
}
