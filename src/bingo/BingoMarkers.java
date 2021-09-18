package bingo;

import java.awt.*;
import java.util.HashSet;

public class BingoMarkers {

    private final HashSet<Coordinate> squares;

    public BingoMarkers(HashSet<Coordinate> squares) {
        this.squares = squares;
    }

    public void drawMarkers(Graphics g) {
        int width = 186;
        int height = 143;

        int verticalBorder = 12;
        int horizontalBorder = 11;

        g.setColor(new Color(255, 0, 0, 100));

        for (Coordinate coord: squares) {

            int x = 12 + ((width + horizontalBorder) * (coord.getColumn()));
            int y = 225 + ((height + verticalBorder) * (coord.getRow()));
            drawCenteredCircle(x+(width/2), y+(height/2), 130, g);
        }
    }

    private void drawCenteredCircle(int x, int y, int r, Graphics g) {
        x = x-(r/2);
        y = y-(r/2);
        g.fillOval(x,y,r,r);
    }
}