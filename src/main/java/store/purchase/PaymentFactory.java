package store.purchase;

import store.membership.MembershipService;

import java.util.List;

public class PaymentFactory {
    public Payment create(List<Item> purchaseItems, List<Item> giftItems, MembershipService membershipService, String userId) {
        Payment payment = new Payment(purchaseItems, giftItems);
        payment.applyMembershipDiscount(membershipService, userId);
        return payment;
    }
}