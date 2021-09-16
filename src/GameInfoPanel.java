import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInfoPanel extends JPanel {

    private final BingoSimulation simulation;
    private final int gameNumber;
    private final int numOfCards;
    private final int numOfWinners;
    private final int days;
    private final String schedule;
    private final String winners;
    private int page;
    private JButton backButton;
    private JButton exitButton;
    private JButton nextButton;
    private JDialog dialog;
    private boolean exit;


    public GameInfoPanel(BingoSimulation simulation) {
        this.simulation = simulation;

        gameNumber = simulation.getGameNumber();
        numOfCards = simulation.getNumOfCards();
        numOfWinners = simulation.getMaxWinners();
        days = simulation.getDays();

        String temp = simulation.getScheduleString();
        String winnerTemp = simulation.getWinnerSchedule();

        temp = temp.substring(temp.indexOf("DAY 1", temp.indexOf("DAY 1") + 1));

        temp = temp.replaceAll("﹉", "");
        temp = temp.replaceAll("┊", "");
        temp = temp.replaceAll("┅", "");
        temp = temp.replaceAll("﹍", "");
        temp = temp.replaceAll("\n\n\n\n", "\n");

        winnerTemp = winnerTemp.replaceAll("﹍", "");
        winnerTemp = winnerTemp.replaceAll("\n\n", "\n");
        winners = winnerTemp.replaceFirst("\n", "");
        schedule = temp;

        exit = false;

        backButton = new JButton("Back");
        exitButton = new JButton("Exit");
        nextButton = new JButton("Next");
        dialog = null;

        page = 1;
    }

    public void showDialog() {
        page = 1;
        exit = false;
        setListeners();
        changePage(page);
    }

    private void changePage(int page) {

        Object[] options = null;
        String msg = "";

        switch (page) {
            case 1:
                options = new Object[]{exitButton, nextButton};
                msg = "PARAMETERS:\n\nGame Number: " + gameNumber + "\n\n" +
                        "Number of cards: " + numOfCards + "\n\n" +
                        "Number of winners: " + numOfWinners + "\n\n" +
                        "Number of days: " + days;
                break;
            case 2:
                options = new Object[]{backButton, exitButton, nextButton};
                msg = "BALL SEQUENCE:\n\n" + schedule;
                break;
            case 3:
                options = new Object[]{backButton, exitButton};
                msg = "WINNERS:\n\n" + winners;
                break;
        }

        JTextArea textArea = new JTextArea(msg);
        textArea.setEditable(false);
        textArea.setFont(textArea.getFont().deriveFont(16f));
        JScrollPane sp = new JScrollPane(textArea);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JOptionPane optionPane = new JOptionPane(sp);
        optionPane.setMessageType(JOptionPane.PLAIN_MESSAGE);
        optionPane.setPreferredSize(new Dimension(450, 250));
        optionPane.setOptions(options);


        dialog = optionPane.createDialog(null, "Game Info (" + page + "/3)");

        if (!exit) {
            dialog.setModal(true);
            dialog.setVisible(true);
        }

    }

    private void setListeners() {
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