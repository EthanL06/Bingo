package bingo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Simulates a bingo game
 */

public class BingoSimulation {

    private final int days;
    private final int maxWinners;
    private final int seed;
    private final int numOfCards;
    private final HashMap<Integer, BingoCard> cards; // HashMap of all the bingo cards that will be simulated. The key is the card's ID
    private final HashSet<Integer> chosenBalls; // Set of all the bingo balls that have been called
    private final ArrayList<Integer> balls; // ArrayList of all the bingo balls that have been called
    private final ArrayList<BingoCard> winners; // ArrayList of all bingo card winners
    private ArrayList<ArrayList<Integer>> winnersAM; // ArrayList of all the winners that won in AM
    private ArrayList<ArrayList<Integer>> winnersPM; // ArrayList of all the winners that won in PM
    private final BingoCard[] printCards; // array of BingoCards that will be used to generate bingo card files
    private int[][] schedule; // Matrix of how many bingo balls will be called each round
    private String winnerSchedule; // Schedule matrix turned into a string with some formatting
    private volatile boolean isSimComplete;
    private boolean hasStopped;

    /**
     * Generates all bingo cards and starts the bingo simulation
     * @param seed Determines the sequence of randomly generated numbers
     * @param numOfCards The number of bingo cards to be generated
     * @param days The number of days that the bingo game will be played
     * @param maxWinners The number of winners the bingo game will have
     */
    public BingoSimulation(int seed, int numOfCards, int days, int maxWinners) {

        this.days = days;
        this.maxWinners = maxWinners;
        this.seed = seed;
        this.numOfCards = numOfCards;
        cards = new HashMap<>();
        chosenBalls = new HashSet<>();
        balls = new ArrayList<>();
        winners = new ArrayList<>();
        printCards = new BingoCard[numOfCards];
        isSimComplete = false;
        hasStopped = false;

        RandomNumber.setSeed(seed);

        System.out.println("Generating card objects...");
        for (int i = 1; i <= numOfCards; i++) {
            BingoCard card = new BingoCard(i);

            cards.put(i, card);
            printCards[i-1] = card;
        }

        simulate();
    }

    /**
     * The simulation of the bingo game. Will call the bingo ball numbers as well as check for winning bingo cards
     */

    private void simulate() {
        System.out.println("Simulating...");
        int numOfWinners = 0;
        HashSet<Integer> winnerIDs = new HashSet<>();

        while (!hasStopped && numOfWinners < maxWinners) {

            int bingoBall = callBingoNumber();

            for (int i = 1; i <= cards.size(); i++) {

                if (hasStopped || numOfWinners >= maxWinners) {
                    break;
                }

                if (!winnerIDs.contains(i)) {
                    BingoCard card = cards.get(i);
                    boolean containsNum = card.chooseNumber(bingoBall);

                    if (containsNum && card.checkWin()) {
                        winnerIDs.add(i);
                        winners.add(card);
                        card.setAmountToWin(balls.size());
                        numOfWinners++;
                    }
                }
            }
        }

        setSchedule();
        setWinnerSchedule();

        System.out.println("BALLS: (" + balls.size() + "): " + balls);

        System.out.println("SIMULATION COMPLETE");
        isSimComplete = true;
    }

    /**
     * Calls the bingo number to be marked on bingo cards
     * @return The called bingo number
     */
    private int callBingoNumber() {
        if (chosenBalls.size() >= 75) {
            return -1;
        }

        int randomNum = RandomNumber.nextNumber(75) + 1;

        while (chosenBalls.contains(randomNum)) {
            randomNum = RandomNumber.nextNumber(75) + 1;
        }

        chosenBalls.add(randomNum);
        balls.add(randomNum);

        return randomNum;
    }


    public BingoCard getCard(int id) {
        return cards.get(id);
    }

    public BingoCard[] getPrintCards() {
        return printCards;
    }

    public ArrayList<Integer> getBingoBalls() {
        return balls;
    }

    /**
     * Determines the amount of bingo balls called per round and fills in the schedule matrix
     */
    private void setSchedule() {
        int rounds = days * 2;
        int basePerRound = balls.size() / rounds;  // the base amount of balls called per day
        int remainingBalls = balls.size() % rounds;
        schedule = new int[2][days];

        for (int[] row: schedule) {
            Arrays.fill(row, basePerRound);
        }

        if (remainingBalls > 0) {
            int currentRow = 0;
            int col = 0;

            for (int i = remainingBalls; i > 0; i--) {
                if (currentRow == 0) {
                    schedule[currentRow][col]++;
                    currentRow = 1;
                } else {
                    schedule[currentRow][col]++;
                    currentRow = 0;
                    col++;
                }
            }
        }
    }

    public int[][] getSchedule() {
        return schedule;
    }


    /**
     * Takes the schedule matrix and turns it into a formatted String.
     * Used in schedule.txt
     * @return The schedule string
     */
    public String getScheduleString() {
        String outerBorder = "﹍﹍﹍" + generateBorder("﹍﹍﹍﹍﹍﹍");
        String innerBorder = "\n┊┅┅┅┅┅" + generateBorder("┅┅┅┅┅┅┅┅┅┅┅") + "┊\n";

        String scheduleString = outerBorder;

        scheduleString += "\n┊     ┊";

        for (int i = 1; i <= days; i++) {
            scheduleString += " DAY " + i + "    ┊";
        }

        scheduleString += innerBorder + "┊ A.M.┊";

        for (int row = 0; row < schedule.length; row++) {
            for (int col = 0; col < schedule[row].length; col++) {
                scheduleString += " " + String.format("%02d", schedule[row][col]) + " BALLS ┊";
            }

            if (row == 0) {
                scheduleString += innerBorder + "┊ P.M.┊";
            }
        }

        outerBorder = "﹉﹉﹉" + generateBorder("﹉﹉﹉﹉﹉﹉");
        scheduleString += "\n" + outerBorder;

        scheduleString += "\n\n\n";

        int index = 0;
        int round = 1;
        for (int day = 1; day <= days; day++) {
            scheduleString += "﹍﹍﹍\nDAY " + day + "\n\n  AM (ROUND " + round + "):";
            round++;

            // A.M.
            for (int count = 0; count < schedule[0][day-1]; count++) {
                scheduleString += " " + String.format("%02d", balls.get(index));
                index++;
            }

            scheduleString += "\n  PM (ROUND " + round + "):";
            round++;

            // P.M.
            for (int count = 0; count < schedule[1][day-1]; count++) {
                scheduleString += " " + String.format("%02d", balls.get(index));
                index++;
            }

            scheduleString += "\n\n﹉﹉﹉\n\n";
        }

        return scheduleString;
    }

    /**
     * Lists out all the bingo cards that have won and when they will win
     */
    private void setWinnerSchedule() {
        winnersAM = new ArrayList<>(days);
        winnersPM = new ArrayList<>(days);

        for (int i = 0; i < days; i++) {
            winnersAM.add(new ArrayList<>());
            winnersPM.add(new ArrayList<>());
        }

        for (BingoCard card: winners) {
            int amountToWin = card.getAmountToWin();
            int sumAmount = 0;
            int row = 0;  // 0 == AM    1 == PM
            int day = 0;

            for (int round = 0; round < days*2; round++) {

                sumAmount += schedule[row][day];

                if (sumAmount >= amountToWin) {
                    if (row == 0) {     // if the win happens in AM
                        winnersAM.get(day).add(card.getID());
                        card.setWinDay("Day " + (day+1) + " (A.M)");
                    } else {
                        winnersPM.get(day).add(card.getID());
                        card.setWinDay("Day " + (day+1) + " (P.M.)");
                    }
                    card.setRoundWin(round+1);

                    break;
                }

                day = row == 1 ? day+1 : day;
                row = row == 0 ? 1 : 0;
            }
        }

        winnerSchedule = "";
        int round = 1;

        for (int day = 1; day <= days; day++) {
            winnerSchedule += "﹍﹍﹍\nDAY " + day + "\n\n  AM (ROUND " + round + "):";
            round++;

            if (!winnersAM.get(day-1).isEmpty()) {
                for (Integer id: winnersAM.get(day-1)) {
                    winnerSchedule += " #" + String.format("%06d", id);
                }
            } else {
                winnerSchedule += " N/A";
            }

            winnerSchedule += "\n  PM (ROUND " + round + "):";
            round++;

            if (!winnersPM.get(day-1).isEmpty()) {
                for (Integer id: winnersPM.get(day-1)) {
                    winnerSchedule += " #" + String.format("%06d", id);
                }
            } else {
                winnerSchedule += " N/A";
            }

            winnerSchedule += "\n﹍﹍﹍\n\n";
        }
    }

    public String getWinnerSchedule() {
        return winnerSchedule;
    }

    public ArrayList<ArrayList<Integer>> getWinnersAM() {
        return winnersAM;
    }

    public ArrayList<ArrayList<Integer>> getWinnersPM() {
        return winnersPM;
    }

    private String generateBorder(String border) {
        String out = "";

        for (int i = 0; i < days; i++) {
            out += border;
        }

        return out;
    }

    public void stopSimulation() {
        hasStopped = true;
    }

    public boolean isSimulationComplete() {
        return isSimComplete;
    }

    public int getGameNumber() {
        return seed;
    }

    public int getNumOfCards() {
        return numOfCards;
    }

    public int getMaxWinners() {
        return maxWinners;
    }

    public int getDays() {
        return days;
    }
}