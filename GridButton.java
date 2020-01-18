import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
*
* This class <b>GridButton</b> represents a cell/button in a game of Lights Out
*
* @author Ann Soong
* @author Faye Lin
*/

public class GridButton extends JButton {

    private int column, row;
    private static final int NUM_COLOURS = 4;
    private static final ImageIcon[] icons = new ImageIcon[NUM_COLOURS];

    /**
     * Constructor used for initializing a GridButton at a specific
     * Board location.
     *
     * @param column
     *            the column of this Cell
     * @param row
     *            the row of this Cell
     */
    public GridButton(int column, int row) {

        this.row = row;
        this.column = column;
        setBackground(Color.WHITE);
        setIcon(new ImageIcon(GridButton.class.getResource("Icons/Light-1.png")));
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        setBorderPainted(false);
    }

   /**
    * sets the icon of the button to reflect the
    * state specified by the parameters
    * @param isOn true if that location is ON
    * @param isClicked true if that location is
    * tapped in the model's current solution
    */
    public void setState(boolean isOn, boolean isClicked) {

        int type = 1;
        if(isClicked && isOn){ // If normal off and solution on
          type = 2; // vibrant orange
        }
        else if(!isClicked && !isOn){
          type = 1; // Light grey
        }
        else if(isOn){
          type = 0; // orange
        }
        else if(!isOn){
          type = 3; // black
        }

        if(icons[type] == null){
          icons[type] = new ImageIcon(GridButton.class.getResource("Icons/Light-" + Integer.toString(type) + ".png"));
        }
        setIcon(icons[type]);
    }

    /**
     * Getter method for the attribute row.
     *
     * @return the value of the attribute row
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter method for the attribute column.
     *
     * @return the value of the attribute column
     */
    public int getColumn() {
        return column;
    }
}
