import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameInfoPanel extends JPanel {

    private final BingoSimulation simulation;
    private final int gameNumber;
    private final int numOfCards;
    private final int numOfWinners;
    private final int days;
    private String schedule;
    private String winners;
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

//        String temp = simulation.getScheduleString();
//        String winnerTemp = simulation.getWinnerSchedule();

//        temp = temp.substring(temp.indexOf("DAY 1", temp.indexOf("DAY 1") + 1));
//
//        temp = temp.replaceAll("﹉", "");
//        temp = temp.replaceAll("┊", "");
//        temp = temp.replaceAll("┅", "");
//        temp = temp.replaceAll("﹍", "");
//        temp = temp.replaceAll("\n\n\n\n", "\n");

//        winnerTemp = winnerTemp.replaceAll("﹍", "");
//        winnerTemp = winnerTemp.replaceAll("\n\n", "\n");
//        winners = winnerTemp.replaceFirst("\n", "");
//        schedule = temp;

        formatSchedule(simulation.getBingoBalls(), simulation.getSchedule());
        formatWinners(simulation.getWinnersAM(), simulation.getWinnersPM());

        exit = false;

        backButton = new JButton("\uD83E\uDC68");
        exitButton = new JButton("❌");
        nextButton = new JButton("\uD83E\uDC6A");
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
                msg = "<h1 style=\"font-family: Verdana, Geneva, sans-serif; padding-top: 0; margin-top: 0;\">Parameters</h1>\r\n"
                		+ "\r\n"
                		+ "<h3 style=\"font-family: Arial Rounded MT, sans-serif;\">Game Number:&nbsp;<span style=\"margin: 0; font-weight: normal; font-size: 1em\">" + gameNumber + "</span></h3>\r\n"
                		+ "<h3 style=\"font-family: Arial Rounded MT, sans-serif;\">Number of cards: <span style=\"margin: 0; font-weight: normal; font-size: 1em\">" + numOfCards + "</span></h3>\r\n"
                		+ "<h3 style=\"font-family: Arial Rounded MT, sans-serif\">Number of winners: <span style=\"margin: 0; font-weight: normal; font-size: 1em\">" + numOfWinners + "</span></h3>\r\n"
                		+ "<h3 style=\"font-family: Arial Rounded MT, sans-serif\">Number of days: <span style=\"margin: 0; font-weight: normal; font-size: 1em\">" + days + "</span></h3>";
                System.out.println(days);
                break;
            case 2:
                options = new Object[]{backButton, exitButton, nextButton};
                msg = "<h1 style=\"font-family: Verdana, Geneva, sans-serif; padding-top: 0; margin-top: 0\">Bingo Ball Sequence:</h1>\n\n" + schedule;
                break;
            case 3:
                options = new Object[]{backButton, exitButton};
                msg = "<h1 style=\"font-family: Verdana, Geneva, sans-serif; padding-top: 0; margin-top: 0\">Winners:</h1>\n\n" + winners;
                break;
        }

        JEditorPane textArea = new JEditorPane("text/html", msg);
        textArea.setEditable(false);
//        textArea.setFont(textArea.getFont().deriveFont(16f));
        textArea.setCaretPosition(0);
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

    private void formatSchedule(ArrayList<Integer> sequence, int[][] ballSchedule) {
        String temp = "";

        // use days
        int round = 1;
        int seqIndex = 0;
        for (int day = 1; day < days+1; day++) {
            temp += "<h2 style=\"margin-bottom: 0; font-size: 1.2em\"><strong>DAY " + day + "</strong></h2>\n";
            temp += "<p style=\"font-family: Arial Rounded MT, sans-serif;\"><strong><span style=\"font-size: 1.05em\">AM (ROUND " + round + "):</span></strong>";

            // AM Round
            for (int i = 0; i < ballSchedule[0][day-1]; i++) {
                temp += " " + String.format("%02d", sequence.get(seqIndex));
                seqIndex++;
            }

            round++;

            temp += "</p>\n";
            temp += "<p style=\"font-family: Arial Rounded MT, sans-serif;\"><strong><span style=\"font-size: 1.05em\">PM (ROUND " + round + "):</span></strong>";

            // PM Round
            for (int i = 0; i < ballSchedule[1][day-1]; i++) {
                temp += " " + String.format("%02d", sequence.get(seqIndex));
                seqIndex++;
            }

            round++;

            temp += "</p>\n";
            temp += "<br>\n";
        }

        schedule = temp;
    }

    private void formatWinners(ArrayList<ArrayList<Integer>> winnersAM, ArrayList<ArrayList<Integer>> winnersPM) {
        String temp = "";

        int round = 1;
        for (int day = 1; day <= days; day++) {
            temp += "<h2 style=\"margin-bottom: 0; font-size: 1.2em\"><strong>DAY " + day + "</strong></h2>\n";
            temp += "<p style=\"font-family: Arial Rounded MT, sans-serif;\"><strong><span style=\"font-size: 1.05em\">AM (ROUND " + round + "):</span></strong>";

            // AM Round
            if (!winnersAM.get(day-1).isEmpty()) {
                for (Integer id: winnersAM.get(day-1)) {
                    temp += " #" + String.format("%06d", id);
                }
            } else {
                temp += " N/A";
            }

            round++;
            temp += "</p>\n";
            temp += "<p style=\"font-family: Arial Rounded MT, sans-serif;\"><strong><span style=\"font-size: 1.05em\">PM (ROUND " + round + "):</span></strong>";

            // PM Round
            if (!winnersPM.get(day-1).isEmpty()) {
                for (Integer id: winnersPM.get(day-1)) {
                    temp += " #" + String.format("%06d", id);
                }
            } else {
                temp += " N/A";
            }

            round++;
            temp += "</p>\n";
            temp += "<br>\n";
        }

        winners = temp;
    }

}