import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JOptionPane;

/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 * @author Ann Soong
 * @author Faye Lin
 */
public class GameController implements ActionListener, ItemListener {

    private GameModel model;
    private GameView view;

    /**
     * Constructor used for initializing the controller. It creates the game's view
     * and the game's model instances
     *
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     */
    public GameController(int width, int height) {
        try{ // try & catch is used in case the game is not created from LightsOut
          model = new GameModel(width,height);
        } catch(NegativeArraySizeException e){
          model = new GameModel(LightsOut.DEFAULT_WIDTH, LightsOut.DEFAULT_HEIGTH);
        }
        view = new GameView(model, this);
    }

    /**
     * Callback used when the user clicks a button (reset,
     * random or quit)
     *
     * @param e
     *            the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof GridButton){ // if a grid button is clicked, the model changes
          GridButton gb = (GridButton) e.getSource();
          model.click(gb.getColumn(), gb.getRow()); // Changes the state of the place in the model
        }
        else{
          if(e.getActionCommand().equals("Reset")){
            model.reset();
          }
          else if(e.getActionCommand().equals("Random")){
            model.randomize();
          }
          else if(e.getActionCommand().equals("Quit")){
            System.exit(0); // Closes the whole program
          }
        }
        update(); // update redraws the board

        if(model.isFinished()){ // When the board is completely on, the game is finished and a JOptionPane appears asking the player to quit or play again
          Object[] option = {"Quit", "Play Again"};
          int ans = JOptionPane.showOptionDialog(null, "Congratulations, you won in " + model.getNumberOfSteps() + " steps!\nWould you like to play again?", "Won", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
          if(ans == 0){ // If the player chooses to quit, the game quits
            System.exit(0);
          }
          else{ // If the player chooses to play again, the game resets (even though the check box is still checked)
            model.reset();
          }
          update();
        }
    }

    /**
     * Callback used when the user select/unselects
     * a checkbox
     *
     * @param e
     *            the ItemEvent
     */
    public void itemStateChanged(ItemEvent e){
        update(); // Update the baord to show or hide the solution
    }

    private void update() { // This calls on the view's version of update which redraws the baord
  		view.update();
  	}
}
