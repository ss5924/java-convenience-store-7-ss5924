package store.membership;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.membership.Membership;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MembershipTest {

    @DisplayName("한도를 초과한 금액을 요청하면 한도선 내에서 할인금액을 반환한다.")
    @Test
    void applyMembershipDiscount() {
        int discountPoint = new Membership(8000).applyMembershipDiscount(8001);

        assertEquals(8000, discountPoint);
    }

}