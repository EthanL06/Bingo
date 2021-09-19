package bingo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class that generates bingo card PNG files
 */
public class BingoCardFileGenerator implements Runnable {

    private final String filePath;  // file path of where the generated PNG files will be stored
    private final BingoCard[] cards; // array of all BingoCard objects
    private BufferedImage cardImage;
    private final int cardWidth;
    private final int cardHeight;
    private int currentCard;

    public BingoCardFileGenerator(String filePath, BingoCard[] cards) {
        this.filePath = filePath + "\\Cards";
        this.cards = cards;
        cardWidth = 1265;
        cardHeight = 1640;
        currentCard = 0;

        try {
            cardImage = ImageIO.read(BingoCardFileGenerator.class.getResource("/images/card.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates folder where PNG files will be stored and creates a PNG file for every 4th card
     */
    @Override
    public void run() {
        createFolder();

        for (int i = 0; i < cards.length / 4; i++) {
            printCard(currentCard+1);
        }

        if (cards.length % 4 > 0) {
            printCard(currentCard+1, cards.length%4);
        }
    }

    private void printCard(int startingID) {
        printCard(startingID, 4);
    }

    /**
     *
     * @param startingID the starting ID for the file; used in drawing the IDs onto the PNG file and in the title of the file
     * @param amount the number of bingo cards that will be drawn to the file
     */
    private void printCard(int startingID, int amount) {
        BufferedImage image = new BufferedImage(2550, 3330, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        g.setColor(Color.BLACK);

        g.drawImage(cardImage, 0, 0, cardWidth, cardHeight, null);

        // top-right
        if (amount >= 2) {
            g.drawImage(cardImage, cardWidth+20, 0, cardWidth, cardHeight, null);
        }

        // bottom-left
        if (amount >= 3) {
            g.drawImage(cardImage, 0, cardHeight+20, cardWidth, cardHeight, null);
        }

        // bottom-right
        if (amount == 4) {
            g.drawImage(cardImage, cardWidth+20, cardHeight+20, cardWidth, cardHeight, null);
        }

        String startID = Integer.toString(startingID);
        String endID = Integer.toString((startingID+amount)-1);

        fillCards(g, amount);

        String fileName = "cards_" + startID + "-" + endID;
        setCardsFilePath(Integer.parseInt(startID), Integer.parseInt(endID), fileName);
        createFile(fileName, image);
    }

    /**
     * Fills in the numbers of all the bingo cards
     * @param g Reference to Graphics object of the BufferedImage
     * @param amount The number of bingo cards on the file
     */
    private void fillCards(Graphics g, int amount) {
        int width = 234;
        int height = 236;

        Font font = new Font("SANS_SERIF", Font.BOLD, 200);

        for (int quadrant = 0; quadrant < amount; quadrant++) {

            int x = 0;
            int y = 0;
            int baseHeight;

            BingoCard bingoCard = cards[currentCard];
            int[][] card = bingoCard.getCard();
            int id = bingoCard.getID();

            drawID(g, id, quadrant);

            switch (quadrant) {
                case 0: // top left
                    x = 15;
                    y = 368;
                    break;
                case 1: // top right
                    x = cardWidth + 35;
                    y = 368;
                    break;
                case 2: // bottom left
                    x = 15;
                    y = cardHeight + 388;
                    break;
                case 3: // bottom right
                    x = cardWidth + 35;
                    y = cardHeight + 388;
                    break;
            }

            baseHeight = y;

            for (int col = 0; col < card[0].length; col++) {

                // accounts for each bingo box having a different width
                switch (col) {
                    case 0:
                        width = 234;
                        break;
                    case 1: case 3:
                        width = 238;
                        break;
                    case 2:
                        width = 233;
                        break;
                    case 4:
                        width = 235;
                        break;
                }

                for (int row = 0; row < card.length; row++) {

                    // accounts for each bingo box having a different height
                    switch (row) {
                        case 0:
                            height = 236;
                            break;
                        case 1:
                            height = 244;
                            break;
                        case 2:
                            height = 237;
                            break;
                        case 3:
                            height = 228;
                            break;
                        case 4:
                            height = 227;
                            break;
                    }

                    Rectangle rect = new Rectangle(x, y, width, height);

                    if (card[row][col] != 0) {
                        drawCenteredString(g, Integer.toString(card[row][col]), rect, font);
                    }

                    y += rect.height + 20; // accounts for border and goes to next row
                }

                y = baseHeight;
                x += width + 14; // accounts for border and goes to next column
            }

            currentCard++;
        }
    }

    /**
     * Draws a centered string within a rectangle
     * @param g Reference to BufferedImage's Graphics object
     * @param text The text drawn onto the file
     * @param rect The rectangle where the text will be centered in
     * @param font The font of the text
     */
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

    /**
     * Draws the ID of a bingo card
     * @param g Reference to the BufferedImage's Graphics object
     * @param id The ID of the bingo card
     * @param quadrant Indicates which card to draw the ID on; 1: top-left, 2: top-right, 3: bottom-left, 4: bottom-right
     */
    private void drawID(Graphics g, int id, int quadrant) {
        g.setFont(new Font("TimesRoman", Font.BOLD, 35));
        String formattedID = "#" + String.format("%06d", id);

        final int x = 75;
        final int y = 50;

        final int xWithBorder = cardWidth + x + 20;
        final int yWithBorder = cardHeight + y + 20;
        switch (quadrant) {
            case 0: // top left
                g.drawString(formattedID, x, y);
                break;
            case 1: // top right
                g.drawString(formattedID, xWithBorder, y);
                break;
            case 2: // bottom left
                g.drawString(formattedID, x, yWithBorder);
                break;
            case 3: // bottom right
                g.drawString(formattedID, xWithBorder, yWithBorder);
                break;
        }
    }

    /**
     * Creates the Cards subdirectory of the user selected folder
     */
    private void createFolder() {
        File folder = new File(filePath);

        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Bingo Cards directory successfully created in " + folder.getAbsolutePath());
            } else {
                System.out.println("Failed to create Bingo Cards directory.");
            }
        } else {
            System.out.println("Bingo Cards directory already exists.");
        }
    }

    /**
     * Creates the PNG file of the bingo cards
     * @param fileName The name of the generated file
     * @param image The image that will be used to generate the PNG
     */
    private void createFile(String fileName, BufferedImage image) {
        try {
            String path = filePath + "/" + fileName + ".png";
            ImageIO.write(image, "png", new File(path));
//            System.out.println(fileName + " created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the file path to open the card. Sets the file path of bingo cards between ID start and end.
     * @param start The starting ID of the file
     * @param end The ending ID of the file
     * @param fileName The file name of the cards
     */
    private void setCardsFilePath(int start, int end, String fileName) {
        for (int i = start-1; i <= end-1; i++) {
            cards[i].setFilePath(filePath + "\\" + fileName + ".png");
//            System.out.println("Card " + (i+1) + ": " + cards[i].getFilePath());
        }
    }


}