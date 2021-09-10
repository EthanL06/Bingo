import java.util.Random;

public class RandomNumber {

    private static Random random;

    public static void setSeed(int seed) {
        random = new Random(seed);
    }

    public static int nextNumber() {
        return random.nextInt();
    }

    public static int nextNumber(int bound) {
        return random.nextInt(bound);
    }


}
