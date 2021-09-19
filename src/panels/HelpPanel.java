package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpPanel {
	
	private static int page = 1;
	private static boolean exit = false;
	private static JButton backButton = new JButton("\uD83E\uDC68");
	private static JButton exitButton = new JButton("❌");
	private static JButton nextButton = new JButton("\uD83E\uDC6A");
	private static JDialog dialog = null;

    public static void showDialog() {
    	showDialog(1);
    }

    /**
     * Sets the ActionListeners of the dialog buttons and shows the selected page of the dialog
     */
    public static void showDialog(int pageNum) {
        page = pageNum;
        exit = false;
        setListeners();
        changePage(page);
    }

    /**
     * Changes the page of the dialog to the specified page
     * @param page The page number
     */
    private static void changePage(int page) {

        Object[] options = null;
        String msg = "";
        String title = "HELP: ";  // "Game Info (" + page + "/3)"

        switch (page) {
            case 1:
                options = new Object[]{exitButton, nextButton};
                msg = "<h1><span style=\"font-family: Verdana, Geneva, sans-serif; font-size: 20px; padding-top: 0; margin-top: 0;\">Getting Started</span></h1>\n" +
                        "<p><span style=\"font-family: Arial Rounded MT, sans-serif;\">Press <strong>START </strong>to start the bingo generator. Input the required parameters before generating:</span></p>\n" +
                        "<ol>\n" +
                        "    <ul style=\"margin-left: -30px; margin-bottom: -5px;\">" +
                        "        <li><span style=\"font-family: Arial Rounded MT, sans-serif; margin-left: -5px\"><strong>Game number &mdash;&nbsp;</strong>determines the sequence of randomly generated numbers</span></li>\n" +
                        "        <li><span style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>Number of cards</strong></span></li>\n" +
                        "        <li><span style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>Number of days</strong></span></li>\n" +
                        "        <li><span style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>Number of winners</strong></span></li>\n" +
                        "    </ul>\n" +
                        "</ol>\n" +
                        "<p><span style=\"font-family: Arial Rounded MT, sans-serif;\">Then, <strong>select the folder</strong> where the generated files will be placed in. You may create a new folder or select an already existing folder. After selecting the folder and pressing <strong>open</strong>, start generating the files by clicking <strong>OK</strong> on the next popup.<br><br>The generation process may take a while depending on the amount of bingo cards.</span></p>";
                title += "Getting Started";
                break;
            case 2:
                options = new Object[]{backButton, exitButton, nextButton};
                msg = "<h1><span style=\"font-family: Verdana, Geneva, sans-serif; padding-top: 0;\">Game Menu</span></h1>\n" +
                        "<p style=\"font-family: Arial Rounded MT, sans-serif;\">Once the files have been successfully created, you will be redirected to the <strong>Game Menu</strong> screen. The Game Menu has three buttons:</p>\n" +
                        "<ol>\n" +
                        "    <ul style=\"margin-left: -30px; padding-bottom: 0;\">\n" +
                        "        <li style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>Open Directory</strong>: Opens the folder where the generated files are located</li>\n" +
                        "        <li style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>View Card</strong>: View any generated card by inputting the card ID</li>\n" +
                        "        <li style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>Game Info</strong>: Displays the bingo game's parameters, the bingo ball sequence each day, and the winners each day.</li>\n" +
                        "    </ul>\n" +
                        "</ol>";
                title += "Game Menu";
                break;
            case 3:
                options = new Object[]{backButton, exitButton, nextButton};
                msg = "<h1><span style=\"font-family: Verdana, Geneva, sans-serif; padding-top: 0;\">View Card</span></h1>\n" +
                        "<p style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>View Card</strong> displays the generated bingo card of the specified ID. The View Card screen has a menu that can either be opened by <strong>right-clicking</strong> the card or <strong>selecting \"Card\"</strong> on the top menu bar. The menu has three main buttons:</p>\n" +
                        "<ol>\n" +
                        "    <ul style=\"margin-left: -30px; padding-bottom: 0;\">\n" +
                        "        <li style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>Open</strong>: Opens the PNG file of the card</li>\n" +
                        "        <li style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>Info</strong>: Displays the card's info including the card ID, if the card will win, the day the card wins, and the round the card wins.</li>\n" +
                        "        <li style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>Toggle Markers</strong>: Shows the marked spaces of the bingo card</li>\n" +
                        "    </ul>\n" +
                        "</ol>";
                title += "View Card";
                break;
            case 4:
                options = new Object[]{backButton, exitButton};
                msg = "<h1><span style=\"font-family: Verdana, Geneva, sans-serif; padding-top: 0;\">Generated Files</span></h1>\n" +
                        "<p style=\"font-family: Arial Rounded MT, sans-serif;\">The application will automatically generate files in your specified directory. The files can be viewed by navigating to the directory through the <strong>File Explorer</strong> or by clicking <strong>Open Directory</strong> in the Game Menu screen.</p>\n" +
                        "<ol>\n" +
                        "    <ul style=\"margin-left: -30px; padding-bottom: 0;\">\n" +
                        "        <li style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>schedule.txt</strong>: Contains information on the amount of bingo balls called each day and which bingo balls are called each day</li><br>\n" +
                        "        <li style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>winners.txt</strong>: Contains information about which card(s) won each day. The file lists the winning cards by their ID</li><br>\n" +
                        "        <li style=\"font-family: Arial Rounded MT, sans-serif;\"><strong>Cards</strong>: A subdirectory containing the PNG files of every card. Each PNG file contains up to 4 cards. To print every file, select all the files, right-click and press Print. The best printing size is letter paper (8.50\" x 11.00\")</li>\n" +
                        "    </ul>\n" +
                        "</ol>";
                title += "Generated Files";
        }

        title += " (" + page + "/4)";

        JEditorPane textArea = new JEditorPane("text/html", msg);;
        textArea.setEditable(false);
        textArea.setFont(textArea.getFont().deriveFont(16f));
        textArea.setCaretPosition(0);
        JScrollPane sp = new JScrollPane(textArea);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JOptionPane optionPane = new JOptionPane(sp);
        optionPane.setMessageType(JOptionPane.PLAIN_MESSAGE);
        optionPane.setPreferredSize(new Dimension(550, 306));
        optionPane.setOptions(options);


        dialog = optionPane.createDialog(null, title);

        if (!exit) {
            dialog.setModal(true);
            dialog.setVisible(true);
        }

    }

    /**
     * Sets the ActionListeners of the buttons of the dialog
     */
    private static void setListeners() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back clicked");
                dialog.dispose();
                page--;
                changePage(page);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit clicked");
                dialog.dispose();
                exit = true;
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!exit) {
                    dialog.dispose();
                    page++;
                    changePage(page);
                } else {
                    dialog.dispose();
                }

            }
        });
    }
}