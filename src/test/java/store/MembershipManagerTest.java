package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MembershipManagerTest {
    private MembershipManager membershipManager;

    @BeforeEach
    void setUp() {
        membershipManager = new MembershipManager();
    }

    @DisplayName("Membership을 조회한다.")
    @Test
    void getMembership() {
        int remainPoint = membershipManager.getMembership("1").getRemainPoint();

        assertEquals(8000, remainPoint);
    }

    @DisplayName("멤버십 포인트의 차감 내역을 확인한다.")
    @Test
    void testGetMembership() {
        int discountPoint = membershipManager.getMembership("1").applyMembershipDiscount(5000);
        int remainPoint = membershipManager.getMembership("1").getRemainPoint();

        assertEquals(5000, discountPoint);
        assertEquals(3000, remainPoint);
    }

    @DisplayName("멤버십 포인트의 누적 차감 내역을 확인한다.")
    @Test
    void getMembershipDiscount() {
        int discountPoint = membershipManager.getMembership("1").applyMembershipDiscount(5000);
        int remainPoint = membershipManager.getMembership("1").getRemainPoint();

        assertEquals(5000, discountPoint);
        assertEquals(3000, remainPoint);

        discountPoint = membershipManager.getMembership("1").applyMembershipDiscount(5000);
        remainPoint = membershipManager.getMembership("1").getRemainPoint();

        assertEquals(3000, discountPoint);
        assertEquals(0, remainPoint);

        discountPoint = membershipManager.getMembership("1").applyMembershipDiscount(5000);
        remainPoint = membershipManager.getMembership("1").getRemainPoint();

        assertEquals(0, discountPoint);
        assertEquals(0, remainPoint);
    }

    @DisplayName("다른 유저의 멤버십 사용 내역을 확인한다")
    @Test
    void testGetMembership2() {
        int discountPoint = membershipManager.getMembership("1").applyMembershipDiscount(5000);
        int remainPoint = membershipManager.getMembership("1").getRemainPoint();

        assertEquals(5000, discountPoint);
        assertEquals(3000, remainPoint);

        int remainPoint2 = membershipManager.getMembership("2").getRemainPoint();

        assertEquals(8000, remainPoint2);
    }
}