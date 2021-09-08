import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class BingoCard {

    private final int id;
    private String filePath;
    private final int[][] card;
    private int[][] displayCard;
    private final HashMap<Integer, Coordinate> squares;
    private boolean isWinner;
    private int roundWin;
    private int amountToWin; // the number of bingo balls called before card wins
    private String winDay;

    public BingoCard(int id) {
        this.id = id;
        this.filePath = null;
        card = new int[5][5];
        squares = new HashMap<>();
        isWinner = false;
        roundWin = -1;
        amountToWin = -1;
        winDay = null;

        fillCard();
    }

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

                card[row][col] = num;
                squares.put(num, new Coordinate(row, col));
                selectedNums.add(num);
            }

            min += 15;
            max += 15;
        }

        // 0 = Free space
        card[2][2] = 0;
        displayCard = Arrays.stream(card).map(int[]::clone).toArray(int[][]::new);  // clones card array to displayCard
    }

    public boolean chooseNumber(int number) {
        if (squares.containsKey(number)) {
            Coordinate coordinate = squares.get(number);
            card[coordinate.getRow()][coordinate.getColumn()] = 0;
//            System.out.println("\nNUMBER: " + number + toString());
            return true;
        }

//        System.out.println("\nNUMBER: " + number + toString());
        return false;
    }

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
            isWinner = true;
        }

        return win;
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

    public boolean isWinner() {
        return isWinner;
    }

    public String toString() {
        return "\n==========================\n" + "Card ID: " + id + "\n\n" + Arrays.deepToString(card).replace("], ", "]\n") + "\n==========================\n";
    }
}
