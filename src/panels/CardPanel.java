package panels;

import bingo.BingoCard;
import bingo.BingoMarkers;
import bingo.BingoParent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CardPanel extends JPanel {

    private final ParentPanel parentPanel;
    private final BingoParent bingoParent;
    private BufferedImage cardImage;
    private BingoCard bingoCard;
    private BingoMarkers bingoMarkers;
    private JMenuItem[] markerItems;
    private boolean toggleMarkers;

    public CardPanel(ParentPanel parentPanel, BingoParent bingoParent) {
        this.parentPanel = parentPanel;
        this.bingoParent = bingoParent;
        bingoCard = null;
        markerItems = new JMenuItem[2];
        toggleMarkers = false;

        try {
            cardImage = ImageIO.read(CardPanel.class.getResource("/images/card_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        setMenuBar();
        setPopupMenu();

        parentPanel.add(this, "card");
    }

    /**
     * Creates the menu bar on top of the View Card screen
     */
    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu cardMenu = new JMenu("Card");

        cardMenu.setMnemonic(KeyEvent.VK_C);

        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem infoItem = new JMenuItem("Info");
        JMenuItem toggleMarkersItem = new JMenuItem("Toggle markers");
        JMenuItem viewAnotherCardItem = new JMenuItem("View another card");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem exitItem = new JMenuItem("Exit");

        openItem.setMnemonic(KeyEvent.VK_O);
        infoItem.setMnemonic(KeyEvent.VK_I);
        toggleMarkersItem.setMnemonic(KeyEvent.VK_T);
        viewAnotherCardItem.setMnemonic(KeyEvent.VK_V);
        helpItem.setMnemonic(KeyEvent.VK_H);
        exitItem.setMnemonic(KeyEvent.VK_E);

        ImageIcon openIcon;
        ImageIcon infoIcon;
        ImageIcon checkTempIcon;
        final ImageIcon checkIcon;
        ImageIcon helpIcon;
        ImageIcon viewIcon;
        ImageIcon exitIcon;

        try {

            int width = 12;
            int height = 12;

            openIcon = new ImageIcon(ImageIO.read(CardPanel.class.getResource("/images/folder.png")));
            Image openImage = openIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            openIcon = new ImageIcon(openImage);

            infoIcon = new ImageIcon(ImageIO.read(CardPanel.class.getResource("/images/info.png")));
            Image infoImage = infoIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            infoIcon = new ImageIcon(infoImage);

            checkTempIcon = new ImageIcon(ImageIO.read(CardPanel.class.getResource("/images/checkmark.png")));
            Image checkImage = checkTempIcon.getImage().getScaledInstance(width, height+6, Image.SCALE_SMOOTH);
            checkIcon = new ImageIcon(checkImage);

            viewIcon = new ImageIcon(ImageIO.read(CardPanel.class.getResource("/images/view.png")));
            Image viewImage = viewIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            viewIcon = new ImageIcon(viewImage);

            helpIcon = new ImageIcon(ImageIO.read(CardPanel.class.getResource("/images/help.png")));
            Image helpImage = helpIcon.getImage().getScaledInstance(width, height+3, Image.SCALE_SMOOTH);
            helpIcon = new ImageIcon(helpImage);

            exitIcon = new ImageIcon(ImageIO.read(CardPanel.class.getResource("/images/exit.png")));
            Image exitImage = exitIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            exitIcon = new ImageIcon(exitImage);

        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        openItem.setIcon(openIcon);
        infoItem.setIcon(infoIcon);
        toggleMarkersItem.setIcon(toggleMarkers ? checkIcon : null);
        helpItem.setIcon(helpIcon);
        viewAnotherCardItem.setIcon(viewIcon);
        exitItem.setIcon(exitIcon);

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //                    getDesktop().open(new File(bingoCard.getFilePath()));
                try {
                    Runtime.getRuntime().exec("explorer.exe /select," + bingoCard.getFilePath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        infoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardInfoDialog();
            }
        });

        toggleMarkersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMarkers(checkIcon);
            }
        });

        viewAnotherCardItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(null, "Enter card ID number (max ID of " + bingoParent.getMaxBingoCards() + "): ", "View Card", JOptionPane.PLAIN_MESSAGE);

                try {
                    int id = Integer.parseInt(input);

                    if (!checkInput(id, bingoParent.getMaxBingoCards(), "card")) {
                        return;
                    }

                    changeCard(bingoParent.getCard(id));
                    repaint();
                    parentPanel.changePanel("card");

                } catch (NumberFormatException ex) {
                    String msg;

                    if (input == null) {
                        msg = "Input not found.";
                    } else if (input.matches("[0-9]+")) {
                        msg = "Input exceeded integer limit.";
                    } else {
                        msg = "Input must only include numbers.";
                    }

                    System.out.println("Exception error: " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
                    parentPanel.changePanel("card");
                }
            }
        });

        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelpPanel.showDialog(3);
            }
        });


        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.changePanel("menu");
            }
        });

        cardMenu.add(openItem);
        cardMenu.add(infoItem);
        markerItems[0] = toggleMarkersItem;
        cardMenu.add(toggleMarkersItem);

        cardMenu.addSeparator();

        cardMenu.add(viewAnotherCardItem);
        cardMenu.add(helpItem);

        cardMenu.addSeparator();

        cardMenu.add(exitItem);

        menuBar.add(cardMenu);
//        menuBar.add(helpMenu);

        add(menuBar, BorderLayout.NORTH);
    }

    /**
     * Creates the popup menu when the user right-clicks the View Card screen
     */
    private void setPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem infoItem = new JMenuItem("Info");
        JMenuItem toggleMarkersItem = new JMenuItem("Toggle markers");

        openItem.setMnemonic(KeyEvent.VK_O);
        infoItem.setMnemonic(KeyEvent.VK_I);
        toggleMarkersItem.setMnemonic(KeyEvent.VK_T);

        ImageIcon openIcon;
        ImageIcon infoIcon;
        ImageIcon checkTempIcon;
        final ImageIcon checkIcon;

        try {
            int width = 12;
            int height = 12;

            openIcon = new ImageIcon(ImageIO.read(CardPanel.class.getResource("/images/folder.png")));
            Image openImage = openIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            openIcon = new ImageIcon(openImage);

            infoIcon = new ImageIcon(ImageIO.read(CardPanel.class.getResource("/images/info.png")));
            Image infoImage = infoIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            infoIcon = new ImageIcon(infoImage);

            checkTempIcon = new ImageIcon(ImageIO.read(CardPanel.class.getResource("/images/checkmark.png")));
            Image checkImage = checkTempIcon.getImage().getScaledInstance(width, height+6, Image.SCALE_SMOOTH);
            checkIcon = new ImageIcon(checkImage);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        openItem.setIcon(openIcon);
        infoItem.setIcon(infoIcon);
        toggleMarkersItem.setIcon(toggleMarkers ? checkIcon : null);

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("explorer.exe /select," + bingoCard.getFilePath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        infoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardInfoDialog();
            }
        });

        toggleMarkersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMarkers(checkIcon);
//                toggleMarkers = !toggleMarkers;
//
//                setToggleIcon(checkIcon);
            }
        });

        popupMenu.add(openItem);
        popupMenu.add(infoItem);
        markerItems[1] = toggleMarkersItem;
        popupMenu.add(toggleMarkersItem);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    int x = e.getX();
                    int y = e.getY();

                    popupMenu.show(CardPanel.this, x, y);
                }
            }
        });
    }

    /**
     * Creates the dialog that displays the card info
     */
    private void cardInfoDialog() {
        String cardID = String.format("%06d", bingoCard.getID());
        String isWinner = bingoCard.isWinner() ? "Yes" : "No";

        String msg =    "Card ID:\n#" + cardID + "\n\n" +
                        "Card Win:\n" + isWinner;

        if (isWinner.equals("Yes")) {
            String winDay = bingoCard.getWinDay();
            String roundWin = Integer.toString(bingoCard.getRoundWin());

            msg +=  " (" + bingoCard.getWinType() + ")\n\n" +
                    "Day Win:\n" + winDay + "\n\n" +
                    "Round Win:\nRound " + roundWin;
        }

        JOptionPane.showMessageDialog(null, msg, "Card Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void changeCard(BingoCard card) {
        this.bingoCard = card;
    }

    /**
     * Handles user-input errors
     * @param input The number the user inputted
     * @param max The max number the user can input
     * @param changePanel The panel that the user switches to if an error occurs
     * @return True if the input is valid, else False
     */
    private boolean checkInput(int input, int max, String changePanel) {
        if (input <= 0 || input > max) {
            JOptionPane.showMessageDialog(null, "Invalid Input.", "ERROR", JOptionPane.ERROR_MESSAGE);
            parentPanel.changePanel(changePanel);
            return false;
        }

        return true;
    }

    /**
     * Handles the toggle markers icon. If the markers are shown on screen, the icon is shown, else the icon is hidden
     * @param icon The toggle markers icon
     */
    private void toggleMarkers(ImageIcon icon) {
        toggleMarkers = !toggleMarkers;

        markerItems[0].setIcon(toggleMarkers ? icon : null);
        markerItems[1].setIcon(toggleMarkers ? icon : null);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(cardImage, 0, 0, 700, 700, null);

        if (bingoCard != null) {
            fillCard(g);
            drawID(g, bingoCard.getID());

            if (toggleMarkers) {
                bingoMarkers = new BingoMarkers(bingoCard.getChosenSquares());
                bingoMarkers.drawMarkers(g);
            }
        }
    }

    /**
     * Draws the card's ID on the top left
     * @param g Reference to panel's Graphics object
     * @param id The card's ID
     */
    private void drawID(Graphics g, int id) {
        g.setFont(new Font("TimesRoman", Font.BOLD, 25));
        String formattedID = "#" + String.format("%06d", id);

        final int x = 43;
        final int y = 145;

        g.drawString("ID: " + formattedID, x, y);
    }

    /**
     * Fills in the numbers of the selected card
     * @param g Reference to the panel's Graphics object
     */

    private void fillCard(Graphics g) {

        // 182 164

        int width = 130;
        int height = 102;
        int x = 8;
        int y = 155;
        int verticalBorder = 9;
        int horizontalBorder = 10;

        g.setColor(Color.BLACK);

        Font font = g.getFont().deriveFont(Font.BOLD, 100);

        int[][] card = bingoCard.getCard();

        for (int col = 0; col < card[0].length; col++) {
            for (int row = 0; row < card.length; row++) {
                switch(row) {
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
                        height = 98;
                        break;
                }

                if (card[row][col] != 0) {
                    Rectangle rect = new Rectangle(x, y, width, height);
//                    g.drawRect(rect.x, rect.y, rect.width, rect.height);
                    drawCenteredString(g, Integer.toString(card[row][col]), rect, font);
                }

                switch(col) {
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

                y += height + verticalBorder;
            }

            x += width + horizontalBorder;
            y = 155;
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
}