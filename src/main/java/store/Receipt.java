package store;

import java.util.List;

public class Receipt {
    private List<Item> purchaseItems;
    private List<Item> giftItems;
    private Payment payment;

    public Receipt(List<Item> purchaseItems, List<Item> giftItems, Payment payment) {
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
