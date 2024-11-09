package store.purchase;

import store.membership.MembershipService;

import java.util.List;

public class PaymentFactory {
    public Payment create(List<Item> purchaseItems, List<Item> giftItems, MembershipService membershipService, String userId, boolean isMembershipDiscount) {
        Payment payment = new Payment(purchaseItems, giftItems);
        if (isMembershipDiscount) {
            payment.applyMembershipDiscount(membershipService, userId);
        }
        return payment;
    }
}