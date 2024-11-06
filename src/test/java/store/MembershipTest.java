package store;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MembershipTest {

    @Test
    void 사용금액이_8000원을_초과하면_예외가_발생한다() {
        assertDoesNotThrow(() -> new Membership(8000));

        assertThatThrownBy(() -> new Membership(8001))
                .isInstanceOf(IllegalArgumentException.class);
    }

}