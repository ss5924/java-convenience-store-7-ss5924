package store;

public class Membership {
    private static final int DISCOUNT_RATE = 30;
    private static final int MAX_LIMIT = 8000;

    public Membership(int accumulatedUsage) {
        if (accumulatedUsage > 8000) {
            throw new IllegalArgumentException("[ERROR] 멤버십 할인은 최대 8,000원까지 가능합니다.");
        }
    }

    public int getRemaining(int accumulatedUsage) {
        return MAX_LIMIT - accumulatedUsage;
    }
}
