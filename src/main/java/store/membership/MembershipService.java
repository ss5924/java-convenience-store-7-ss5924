package store.membership;

public class MembershipService {
    private static final double DISCOUNT_RATE = 0.3d;
    private final MembershipManager membershipManager;

    public MembershipService(MembershipManager membershipManager) {
        this.membershipManager = membershipManager;
    }

    public int applyMembershipDiscount(String userId, int discountPoint) {
        return membershipManager.getMembership(userId).applyMembershipDiscount(discountPoint);
    }

    public int calculateMembershipDiscount(int amount) {
        return (int) (amount * DISCOUNT_RATE);
    }
}
