package store.membership;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.membership.MembershipManager;
import store.membership.MembershipService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MembershipServiceTest {
    private MembershipService membershipService;

    @BeforeEach
    void setUp() {
        MembershipManager membershipManager = new MembershipManager();
        membershipService = new MembershipService(membershipManager);
    }

    @DisplayName("한도를 적용하여 제공될 최종 할인 금액을 반환한다.")
    @Test
    void applyMembershipDiscount() {
        int applyMembershipDiscount = membershipService.applyMembershipDiscount("1", 3000);

        assertEquals(3000, applyMembershipDiscount);
    }

    @DisplayName("퍼센트가 적용된 할인 금액을 반환한다.")
    @Test
    void calculateMembershipDiscount() {
        int discountPoint = membershipService.calculateMembershipDiscount(10000);

        assertEquals(3000, discountPoint);
    }

    @DisplayName("할인 누적 프로세스를 진행한다.")
    @Test
    void 할인_누적_프로세스를_진행한다() {
        int calculatedDiscountPoint = membershipService.calculateMembershipDiscount(10000);
        int applyMembershipDiscountPoint = membershipService.applyMembershipDiscount("1", calculatedDiscountPoint);

        assertEquals(3000, applyMembershipDiscountPoint);

        int calculatedDiscountPoint2 = membershipService.calculateMembershipDiscount(20000);
        int applyMembershipDiscountPoint2 = membershipService.applyMembershipDiscount("1", calculatedDiscountPoint2);

        assertEquals(6000, calculatedDiscountPoint2);
        assertEquals(5000, applyMembershipDiscountPoint2);
    }
}