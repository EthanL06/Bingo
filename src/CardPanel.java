import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CardPanel extends JPanel {

    private final ParentPanel parentPanel;
    private BufferedImage cardImage;
    private int[][] card;

    public CardPanel(ParentPanel parentPanel) {
        this.parentPanel = parentPanel;
        card = null;
        try {
            cardImage = ImageIO.read(CardPanel.class.getResource("/images/card.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        parentPanel.add(this, "card");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(cardImage, 0, 0, 1000, 1000, null);

        if (card != null) {
            fillCard(g);
        }
    }

    public void changeCard(int[][] card) {
        this.card = card;
    }

    private void fillCard(Graphics g) {

        // 182 164

        int width = 186;
        int height = 143;
        int x = 12;
        int y = 225;
        int verticalBorder = 12;
        int horizontalBorder = 11;

        g.setColor(Color.BLACK);

        Font font = g.getFont().deriveFont(Font.BOLD, 150);

        for (int col = 0; col < card[0].length; col++) {
            for (int[] row : card) {

                if (row[col] != 0) {
                    Rectangle rect = new Rectangle(x, y, width, height);
//                    g.drawRect(rect.x, rect.y, rect.width, rect.height);
                    drawCenteredString(g, Integer.toString(row[col]), rect, font);
                }

                y += height + verticalBorder;
            }

            x += width + horizontalBorder;
            y = 225;
        }
    }

    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String

        g.drawString(text, x, y);
    }
}
