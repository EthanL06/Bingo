package bingo;

import panels.ParentPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Parent class of BingoSimulation and BingoCards
 */
public class BingoParent {

    private final BingoSimulation simulation;
    private final ParentPanel parentPanel;
    private final int maxBingoCards;

    public BingoParent(int seed, int numOfBingoCards, int numOfDays, int numOfWinners, String filePath, ParentPanel parentPanel) {
        simulation = new BingoSimulation(seed, numOfBingoCards, numOfDays, numOfWinners);
        maxBingoCards = numOfBingoCards;
        this.parentPanel = parentPanel;

        startThreads(filePath);
    }

    /**
     * Starts thee threads that allow the program to simultaneously generate the .txt files and the PNG files
     * as well as the panel indicating that the files are being generated
     * @param filePath The user selected file path where generated files will be stored
     */
    private void startThreads(String filePath) {

        System.out.println("Reached start threads");

        Thread bingoCardGenerator = new Thread(new BingoCardFileGenerator(filePath, simulation.getPrintCards()), "Card Generator");
        Thread textFileGenerator = new Thread(new TextFileGenerator(filePath, simulation.getScheduleString(), simulation.getWinnerSchedule()));

        Runnable loadingRunnable = () -> {

            final boolean[] forceStop = {false};

            JButton stopBtn = new JButton("Stop Generating");

            Object[] options = {stopBtn};

            JOptionPane pane = new JOptionPane();
            pane.setMessageType(JOptionPane.PLAIN_MESSAGE);
            pane.setMessage("The bingo cards and game are currently being generated. This window will close automatically once the operation is finished.");
            pane.setOptionType(JOptionPane.NO_OPTION);
            pane.setOptions(options);
            pane.setInitialValue(options[0]);
            JDialog dialog = pane.createDialog(null, "Generating...");
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setModal(false);
            dialog.setAlwaysOnTop(true);

            stopBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.setVisible(false);
                    pane.setVisible(false);

                    bingoCardGenerator.stop();
                    simulation.stopSimulation();

                    forceStop[0] = true;
                    JOptionPane.showMessageDialog(null, "Bingo cards and game have stopped generating. Application will be closed.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            });

            boolean hasLogged = false;

            dialog.setVisible(true);
            pane.setVisible(true);

            while (bingoCardGenerator.isAlive()) {
                if (!hasLogged) {
                    System.out.println("Generating bingo cards...");

                    hasLogged = true;
                }
            }

            if (!forceStop[0]) {

                hasLogged = false;
                while (!simulation.isSimulationComplete()) {
                    if (!hasLogged) {
                        System.out.println("Simulation still running");
                        hasLogged = true;
                    }
                }

                pane.setVisible(false);
                dialog.setVisible(false);

                System.out.println("Bingo cards successfully generated.");
                JOptionPane.showMessageDialog(null, "Bingo cards and game have been successfully generated and simulated.", "Generation Complete", JOptionPane.PLAIN_MESSAGE);
                parentPanel.changePanel("menu");
            }

        };

        Thread loading = new Thread(loadingRunnable);

        loading.start();
        bingoCardGenerator.start();
        textFileGenerator.start();
    }

    public int getMaxBingoCards() {
        return maxBingoCards;
    }

    public BingoCard getCard(int id) {
        return simulation.getCard(id);
    }

    public BingoSimulation getSimulation() {
        return simulation;
    }
}