import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BingoSimulation {

    private final int days;
    private final int maxWinners;
    private final HashMap<Integer, BingoCard> cards;
    private final HashSet<Integer> chosenBalls;
    private final ArrayList<BingoCard> winners;
    private final BingoCard[] printCards; // array of BingoCards that will be used to generate bingo card files
    private final Thread simulation;
    private boolean isSimComplete;

    public BingoSimulation(int seed, int numOfCards, int days, int maxWinners) {

        this.days = days;
        this.maxWinners = maxWinners;
        cards = new HashMap<>();
        chosenBalls = new HashSet<>();
        winners = new ArrayList<>();
        printCards = new BingoCard[numOfCards];
        isSimComplete = false;

        RandomNumber.setSeed(seed);

        for (int i = 1; i <= numOfCards; i++) {
            BingoCard card = new BingoCard(i);

            cards.put(i, card);
            printCards[i-1] = card;

            System.out.println(card);
        }

        simulation = new Thread(this::simulate);
        simulation.start();
    }

    private void simulate() {
        int numOfWinners = 0;
        HashSet<Integer> winnerIDs = new HashSet<>();

        while (numOfWinners < maxWinners) {

            int bingoBall = callBingoNumber();

            for (int i = 1; i <= cards.size(); i++) {
                if (numOfWinners < maxWinners && !winnerIDs.contains(i)) {
                    BingoCard card = cards.get(i);
                    card.chooseNumber(bingoBall);

                    if (card.checkWin()) {
                        winnerIDs.add(i);
                        winners.add(card);
                        numOfWinners++;
                    }
                }
            }
        }

        System.out.print("\nWINNERS: ");

        for (BingoCard winner : winners) {
            System.out.print(winner.getID() + " ");
        }

        System.out.println("\n");

        System.out.println("SIMULATION COMPLETE");
        isSimComplete = true;
    }

    private int callBingoNumber() {
        if (chosenBalls.size() >= 75) {
            return -1;
        }

        int randomNum = RandomNumber.nextNumber(75) + 1;

        while (chosenBalls.contains(randomNum)) {
            randomNum = RandomNumber.nextNumber(75) + 1;
        }

        chosenBalls.add(randomNum);

        return randomNum;
    }

    public int[][] getID(int id) {
        return cards.get(id).getCard();
    }

    public BingoCard[] getPrintCards() {
        return printCards;
    }

    public void stopSimulation() {
        simulation.stop();
    }

    public boolean isSimulationComplete() {
        return isSimComplete;
    }
}
