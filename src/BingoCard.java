import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class BingoCard {

    private final int id;
    private String filePath;
    private final int[][] card;
    private int[][] displayCard;
    private final HashMap<Integer, Coordinate> squares;

    public BingoCard(int id) {
        this.id = id;
        this.filePath = null;
        card = new int[5][5];
        squares = new HashMap<>();

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

    public void chooseNumber(int number) {
        if (squares.containsKey(number)) {
            Coordinate coordinate = squares.get(number);
            card[coordinate.getRow()][coordinate.getColumn()] = 0;
        }

        System.out.println("\nNUMBER: " + number + toString());
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

        return win;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public String toString() {
        return "\n==========================\n" + "Card ID: " + id + "\n\n" + Arrays.deepToString(card).replace("], ", "]\n") + "\n==========================\n";
    }
}
