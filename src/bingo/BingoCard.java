package bingo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Card that holds the data of a single bingo card
 */
public class BingoCard {

    private final int id;
    private String filePath; // The file path to open the card's PNG file
    private final int[][] card; // Matrix that holds all the numbers of the card; changes depending on which numbers are marked
    private int[][] displayCard; // Matrix that holds all the numbers of the card; doesn't change
    private final HashMap<Integer, Coordinate> squares; // Holds all the bingo numbers and their corresponding coordinate on the matrix
    private final HashSet<Coordinate> chosenSquares; // Set of all the coordinates that have been chosen; used in marking the bingo numbers
    private boolean isWinner;
    private int roundWin;
    private int amountToWin; // The number of bingo balls that need to be called for the card to win
    private String winDay;
    private String winType; // Diagonal, Horizontal, Vertical

    /***
     * Sets initial values of instance variables
     * @param id the ID of the bingo card
     */
    public BingoCard(int id) {
        this.id = id;
        this.filePath = null;
        card = new int[5][5];
        squares = new HashMap<>();
        chosenSquares = new HashSet<>();
        isWinner = false;
        roundWin = -1;
        amountToWin = -1;
        winDay = null;
        winType = null;

        fillCard();
    }

    /***
     * Generates the bingo card's board numbers
     */
    private void fillCard() {
        int min = 1;
        int max = 16;

        HashSet<Integer> selectedNums = new HashSet<>();

        for (int col = 0; col < card[0].length; col++) {
            for (int row = 0; row < card.length; row++) {
                int num = RandomNumber.nextNumber(max - min) + min;

                while (selectedNums.contains(num)) {
                    num = RandomNumber.nextNumber(max - min) + min;
                }

                if (row == 2 && col == 2) {
                    num = 0;
                }

                card[row][col] = num;
                squares.put(num, new Coordinate(row, col));
                selectedNums.add(num);
            }

            min += 15;
            max += 15;
        }

        Coordinate freeSpace = new Coordinate(2, 2);
        chosenSquares.add(freeSpace);
        displayCard = Arrays.stream(card).map(int[]::clone).toArray(int[][]::new);  // clones card array to displayCard
    }


    /***
     * Checks if the called bingo number is found within the card
     * @param number The called bingo number
     * @return True if number is found in the card, else False
     */
    public boolean chooseNumber(int number) {
        if (squares.containsKey(number)) {
            Coordinate coordinate = squares.get(number);
            card[coordinate.getRow()][coordinate.getColumn()] = 0;
            chosenSquares.add(coordinate);
            return true;
        }

        return false;
    }

    /***
     * Checks if the card has won
     * @return True if the card wins, else False
     */
    public boolean checkWin() {
        // Check each row

        boolean win;

        for (int[] row: card) {
            win = true;
            for (int num : row) {
                if (num != 0) {
                    win = false;
                    break;
                }
            }

            if (win) {
                winType = "Horizontal";
                isWinner = true;
                return true;
            }
        }

        // Check each column

        for (int col = 0; col < card[0].length; col++) {
            win = true;

            for (int[] row : card) {
                if (row[col] != 0) {
                    win = false;
                    break;
                }
            }

            if (win) {
                winType = "Vertical";
                isWinner = true;
                return true;
            }
        }

        // Check diagonals
        win = true;

        for (int i = 0; i < 5; i++) {
            if (card[i][i] != 0) {
                win = false;
                break;
            }
        }

        if (win) {
            winType = "Diagonal";
            isWinner = true;
            return true;
        }

        win = true;
        int y = 4;
        for (int x = 0; x < 5; x++) {
            if (card[x][y] != 0) {
                win = false;
                break;
            }

            y--;
        }

        if (win) {
            winType = "Diagonal";
            isWinner = true;
        }

        return win;
    }

    public String getWinType() {
        return winType;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setAmountToWin(int amount) {
        amountToWin = amount;
    }

    public void setWinDay(String winDay) {
        this.winDay = winDay;
    }

    public void setRoundWin(int round) {
        roundWin = round;
    }

    public int getRoundWin() {
        return roundWin;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public int getID() {
        return id;
    }

    public int[][] getCard() {
        return displayCard;
    }

    public int getAmountToWin() {
        return amountToWin;
    }

    public String getWinDay() {
        return winDay;
    }

    public HashSet<Coordinate> getChosenSquares() {
        return chosenSquares;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public String toString() {
        return "\n==========================\n" + "Card ID: " + id + "\n\n" + Arrays.deepToString(card).replace("], ", "]\n") + "\n==========================\n";
    }
}