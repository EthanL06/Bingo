package panels;

import bingo.BingoParent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static java.awt.Desktop.getDesktop;

public class MenuPanel extends JPanel {

    private final ParentPanel parentPanel;
    private BingoParent bingoParent;
    private final CardPanel cardPanel;
    private final GameInfoPanel gameInfoPanel;
    private Color color;

    public MenuPanel(ParentPanel parentPanel) {
        this.parentPanel = parentPanel;

        setBackground(new Color(230, 69, 69));
        askPrompts();

        cardPanel = new CardPanel(parentPanel, bingoParent);
        gameInfoPanel = new GameInfoPanel(bingoParent.getSimulation());
    }

    /**
     * Creates and shows the dialogs where the user inputs the parameters needed for the bingo simulation:
     * Game number,
     * Number of bingo cards,
     * Number of days,
     * Number of winners,
     * File path to store generated files
     */
    private void askPrompts() {
        String input = "";
        int gameNumber;
        int numOfBingoCards;
        int numOfDays;
        int numOfWinners;

        try {
            input = getInput("Enter game number: ");
            gameNumber = Integer.parseInt(input);

            input = getInput("Enter number of bingo cards: ");
            numOfBingoCards = Integer.parseInt(input);

            if (!checkInput(numOfBingoCards, Integer.MAX_VALUE, "title")) {
                return;
            }

            input = getInput("Enter number of days to play (max of 5 days): ");
            numOfDays = Integer.parseInt(input);

            if (!checkInput(numOfDays, 5, "title")) {
                return;
            }

            input = getInput("Enter number of winners (max of " + numOfBingoCards + " winners): ");
            numOfWinners = Integer.parseInt(input);

            if (!checkInput(numOfWinners, numOfBingoCards, "title")) {
                return;
            }

            JLabel label = new JLabel("<html><center>Create and select folder where generated files will be stored.<br/>To create a new folder, right click in the folder selector and select \"New Folder.\"</html>");
            label.setHorizontalTextPosition(SwingConstants.CENTER);
            label.setHorizontalAlignment(SwingConstants.CENTER);

            JOptionPane.showMessageDialog(null, label, "Folder Selection", JOptionPane.PLAIN_MESSAGE);

            String filePath = getFileInput();

            if (filePath == null) {
                JOptionPane.showMessageDialog(null, "No folder selected or selected folder does not exist.", "ERROR", JOptionPane.ERROR_MESSAGE);
                parentPanel.changePanel("title");
            }  else {

                JOptionPane.showMessageDialog(null, "The application will generate a bingo simulation. This could take a while. Press OK to continue.", "Press OK to continue", JOptionPane.PLAIN_MESSAGE);

                bingoParent = new BingoParent(gameNumber, numOfBingoCards, numOfDays, numOfWinners, filePath, parentPanel);
                setGraphics(filePath);

                parentPanel.add(this, "menu");
            }
        } catch (NumberFormatException e) {

            String msg;

            if (input == null) {
                msg = "Input not found.";
            } else if (input.matches("[0-9]+")) {
                msg = "Input exceeded integer limit.";
            } else {
                msg = "Input must only include numbers.";
            }

            System.out.println("Exception error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
            parentPanel.changePanel("title");
        }
    }

    /**
     * Sets the graphics of the Game Menu screen
     * @param filePath The file path of where the generated files are located
     */
    private void setGraphics(String filePath) {
        setLayout(new BorderLayout());
        color = new Color(230, 69, 69);
        setBackground(color);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        JPanel imagePanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 50));

        Border centerBorder = BorderFactory.createEmptyBorder(0, 0, 300, 0);

        setImage(imagePanel);
        setButtons(filePath, buttonPanel);

        centerPanel.setBorder(centerBorder);
        centerPanel.setPreferredSize(new Dimension(150, 150));
        centerPanel.setBackground(color);
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.NORTH);
    }

    /**
     * Sets the title image of the Game Menu panel
     * @param imagePanel Reference to the JPanel where the image will be in
     */
    private void setImage(JPanel imagePanel) {

        ImageIcon menuImageIcon = null;
        Border border = BorderFactory.createEmptyBorder(100, 0, 100, 0);

        try {
            menuImageIcon = new ImageIcon(ImageIO.read(MenuPanel.class.getResource("/images/menu.png")));
            Image tempImage = menuImageIcon.getImage().getScaledInstance(700, (int)(menuImageIcon.getIconHeight()/1.25), Image.SCALE_SMOOTH);
            menuImageIcon = new ImageIcon(tempImage);
        } catch (Exception e) {
            System.out.println(e);
        }

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(menuImageIcon);

        imagePanel.add(imageLabel);
        imagePanel.setBorder(border);
        imagePanel.setSize(new Dimension(menuImageIcon.getIconWidth(), menuImageIcon.getIconWidth()));
        imagePanel.setBackground(color);
    }

    /**
     * Sets the three buttons in the Game Menu panel: Open Directory, View Card, and Game Info
     * @param filePath The file path of where the generated files are stored
     * @param buttonPanel Reference to the JPanel where the buttons will be in
     */
    private void setButtons(String filePath, JPanel buttonPanel) {

        JButton openDirectory = createButton("OPEN DIRECTORY");
        JButton viewCard = createButton("VIEW CARD");
        JButton gameInfo = createButton("GAME INFO");

        buttonPanel.setPreferredSize(new Dimension(200, 300));
        buttonPanel.setBackground(color);

        buttonPanel.add(openDirectory);
        buttonPanel.add(viewCard);
        buttonPanel.add(gameInfo);

        openDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getDesktop().open(new File(filePath));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Directory cannot be found.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(null, "Enter card ID number (max ID of " + bingoParent.getMaxBingoCards() + "): ", "View Card", JOptionPane.PLAIN_MESSAGE);

                try {
                    int id = Integer.parseInt(input);

                    if (!checkInput(id, bingoParent.getMaxBingoCards(), "menu")) {
                        return;
                    }

                    cardPanel.changeCard(bingoParent.getCard(id));
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
                    parentPanel.changePanel("menu");
                }
            }
        });

        gameInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameInfoPanel.showDialog();
            }
        });
    }

    /**
     * Creates a Game Menu button
     * @param text Button text
     * @return The created JButton
     */
    private JButton createButton(String text) {
        JButton b = new JButton(text);
        Border border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(125, 31, 31), 2), BorderFactory.createEmptyBorder(40, 30, 40, 30));
        b.setBackground(new Color(214, 40, 40));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(border);
        b.setAlignmentX(JButton.CENTER_ALIGNMENT);
        b.setAlignmentY(JButton.CENTER_ALIGNMENT);
        b.setFont(new Font("Gill Sans MT", Font.BOLD, 30));

        return b;
    }

    /**
     * Creates the dialog that takes in the user's input
     * @param message The message of the dialog
     * @return The user's input
     */
    private String getInput(String message) {
        return JOptionPane.showInputDialog(null, message, "Parameter Selection", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Handles user input errors
     * @param input The user input
     * @param max The max number the user can input
     * @param changePanel The panel that the program changes to if an error occurs
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
     * Gets the user selected directory where the generated files will be stored
     * @return The file directory string
     */
    private String getFileInput() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File
                (System.getProperty("user.home") + System.getProperty("file.separator")+ "Downloads"));
        chooser.setDialogTitle("Select folder directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile());

            File file = chooser.getSelectedFile();

            if (!file.exists()) {
                System.out.println("Folder does not exist");
                return null;
            } else {
                System.out.println("Folder does exist");
            }

            return chooser.getSelectedFile().toString();
        }
        else {
            System.out.println("No Selection ");
            return null;
        }
    }
}