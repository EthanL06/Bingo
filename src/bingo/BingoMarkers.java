package bingo;

import java.awt.*;
import java.util.HashSet;

/**
 * Marks the bingo cards with a red circle
 */
public class BingoMarkers {

    private final HashSet<Coordinate> squares; // the coordinates of all the marked numbers

    public BingoMarkers(HashSet<Coordinate> squares) {
        this.squares = squares;
    }

    /**
     * Draws the markers to the card's corresponding marked numbers
     * @param g Reference to the JPanel's Graphics object
     */
    public void drawMarkers(Graphics g) {
        int width = 130;
        int height = 102;

        int verticalBorder = 9;
        int horizontalBorder = 10;

        g.setColor(new Color(255, 0, 0, 100));

        for (Coordinate coord: squares) {

            switch(coord.getRow()) {
                case 0:
                    height = 103;
                    break;
                case 1:
                    height = 104;
                    break;
                case 2:
                    height = 100;
                    break;
                case 3: case 4:
                    height = 101;
                    break;
            }

            switch(coord.getColumn()) {
                case 0: case 5:
                    width = 129;
                    break;
                case 1: case 3:
                    width = 130;
                    break;
                case 2:
                    width = 128;
                    break;
            }

            int x = 8 + ((width + horizontalBorder) * (coord.getColumn()));
            int y = 155 + ((height + verticalBorder) * (coord.getRow()));
            drawCenteredCircle(x+(width/2), y+(height/2), 85, g);
        }
    }

    private void drawCenteredCircle(int x, int y, int r, Graphics g) {
        x = x-(r/2);
        y = y-(r/2);
        g.fillOval(x,y,r,r);
    }
}