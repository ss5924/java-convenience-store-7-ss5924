package store.purchase;

import store.payment.Payment;

import java.util.List;

public class Purchase {
    private List<Item> purchaseItems;
    private List<Item> giftItems;
    private Payment payment;

    Purchase(List<Item> purchaseItems, List<Item> giftItems, Payment payment) {
        this.purchaseItems = purchaseItems;
        this.giftItems = giftItems;
        this.payment = payment;
    }

    public List<Item> getPurchaseItems() {
        return purchaseItems;
    }

    public List<Item> getGiftItems() {
        return giftItems;
    }

    public Payment getPayment() {
        return payment;
    }
}
