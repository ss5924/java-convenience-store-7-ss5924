package store.membership;

public class Membership {
    private int remainPoint;

    Membership(int remainPoint) {
        this.remainPoint = remainPoint;
    }

    public synchronized int applyMembershipDiscount(int discountPoint) {
        int applyDiscountPoint = Math.min(discountPoint, this.remainPoint);
        this.remainPoint -= applyDiscountPoint;
        return applyDiscountPoint;
    }

    public int getRemainPoint() {
        return remainPoint;
    }
}
