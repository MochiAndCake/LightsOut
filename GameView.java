import javax.swing.*;

import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.ComponentOrientation;

import java.awt.GridLayout;

import java.awt.Color;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>GridButton</b> (the actual game) and
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 * @author Ann Soong
 * @author Faye Lin
 */
public class GameView extends JFrame {

    private GameModel model;
    private JButton btnReset, btnRandom, btnQuit;
    private JCheckBox chkSolution;
    private JLabel lblSteps;

    private JPanel panBoard, panMenu, panTop;
    private GridBagConstraints gbC; // Used for formatting with GridBagLayout

    private static int width, height;
    private GridButton[][] board;

    /**
     * Constructor used for initializing the Frame
     *
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */
    public GameView(GameModel gameModel, GameController gameController) {
        super("Lights Out -- the ITI 1121 version");
        super.getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if(gameModel == null || gameController == null){
          throw new NullPointerException("parameters can't be null");
        }
        model = gameModel;

        this.getContentPane().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.getContentPane().setLayout(new GridBagLayout());
        gbC = new GridBagConstraints();

        panTop = new JPanel();
        panTop.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        panTop.setLayout(new GridBagLayout());
        panTop.setBackground(Color.WHITE);

        /*** Board Creation ***************************************************************************************/

        this.height = model.getHeight();
        this.width = model.getWidth();

        panBoard = new JPanel();
        panBoard.setBackground(Color.WHITE);
        panBoard.setLayout(new GridLayout(height, width));
        board = new GridButton[height][width];

        for(int i = 0; i < height; i++){
          for(int j = 0; j < width; j++){
            board[i][j] = new GridButton(i,j);
            panBoard.add(board[i][j]);
            board[i][j].addActionListener(gameController);
          }
        }
        gbC.gridwidth = 2;
        gbC.gridx = 0;
        gbC.gridy = 0;
        panTop.add(panBoard, gbC);

        /*** Menu Creation ****************************************************************************************/

        panMenu = new JPanel();
        panMenu.setBackground(Color.WHITE);
        panMenu.setLayout(new BoxLayout(panMenu, BoxLayout.Y_AXIS));

        btnReset = new JButton("Reset");
        btnReset.addActionListener(gameController);

        btnRandom = new JButton("Random");
        btnRandom.addActionListener(gameController);

        chkSolution = new JCheckBox("Solution");
        chkSolution.addItemListener(gameController);
        chkSolution.setBackground(Color.WHITE);

        btnQuit = new JButton("Quit");
        btnQuit.addActionListener(gameController);

        btnReset.setAlignmentX(Component.CENTER_ALIGNMENT);
        panMenu.add(btnReset);

        panMenu.add(Box.createRigidArea(new Dimension(0, 10)));

        btnRandom.setAlignmentX(Component.CENTER_ALIGNMENT);
        panMenu.add(btnRandom);

        panMenu.add(Box.createRigidArea(new Dimension(0, 10)));

        chkSolution.setAlignmentX(Component.CENTER_ALIGNMENT);
        panMenu.add(chkSolution);

        panMenu.add(Box.createRigidArea(new Dimension(0, 10)));

        btnQuit.setAlignmentX(Component.CENTER_ALIGNMENT);
        panMenu.add(btnQuit);

        gbC.insets = new Insets(0,20,0,0);
        gbC.gridx = 2;
        gbC.gridy = 0;
        panTop.add(panMenu, gbC);

        /*** Label and adding it all to the frame *****************************************************************/

        gbC.gridx = 0;
        gbC.gridy = 0;
        gbC.insets = new Insets(10,10,0,0);
        this.getContentPane().add(panTop, gbC); // Add the top panel (containing the baord and the menu) to the frame

        lblSteps = new JLabel("Number of step: " + model.getNumberOfSteps());
        lblSteps.setOpaque(true);
        lblSteps.setBackground(Color.WHITE);

        gbC.anchor = GridBagConstraints.PAGE_END; // bottom
        gbC.insets = new Insets(10,0,0,0); // padding
        gbC.gridx = 0;
        gbC.gridy = 1;
        this.getContentPane().add(lblSteps, gbC); // Add the label to the frame, underneath the top panel

        pack();
        setResizable(false);
        setVisible(true);
    }

    /**
     * updates the status of the board's GridButton instances based
     * on the current game model, then redraws the view
     */
    public void update(){
        lblSteps.setText("Number of step: " + model.getNumberOfSteps());

        if(solutionShown()){ // if the solution checkbox is checked
          model.setSolution(); // update the shortest solution to the model
          for(int i = 0; i < height; i++){ // loops through to change each icon
            for(int j = 0; j < width; j++){
              board[i][j].setState(model.isON(i,j), model.solutionSelects(i,j));
            }
          }
        }
        else{
          for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
              if(model.isON(i,j)){ // If the light is on, we pass true & false to get the light orange icon
                board[i][j].setState(model.isON(i,j), !model.isON(i,j));
              }
              else{ // If the light is off, we pass false & false to get the light grey icon
                board[i][j].setState(model.isON(i,j), model.isON(i,j));
              }
            }
          }
        }
    }

    /**
     * returns true if the ``solution'' checkbox
     * is checked
     *
     * @return the status of the ``solution'' checkbox
     */
    public boolean solutionShown(){
        return chkSolution.isSelected();
    }
}
