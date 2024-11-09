package store.purchase;

import java.text.NumberFormat;

public class Receipt {
    private Purchase purchase;

    public Receipt(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        NumberFormat numberFormat = NumberFormat.getInstance();

        sb.append("==============W 편의점================\n");
        sb.append("상품명\t\t수량\t금액\n");

        for (Item item : purchase.getPurchaseItems()) {
            sb.append(item.getProduct().getName()).append("\t\t")
                    .append(item.getQuantity()).append("\t")
                    .append(item.getProduct().getPrice() * item.getQuantity()).append("\n");
        }

        if (purchase.getGiftItems().size() > 0) {
            sb.append("=============증\t정===============\n");
        }

        for (Item gift : purchase.getGiftItems()) {
            sb.append(gift.getProduct().getName()).append("\t\t")
                    .append(gift.getQuantity()).append("\n");
        }

        sb.append("====================================\n");

        Payment payment = purchase.getPayment();
        sb.append("총구매액\t\t").append(numberFormat.format(payment.getTotalPurchaseAmount())).append("\n");
        sb.append("행사할인\t\t-").append(numberFormat.format(payment.getPromotionDiscountAmount())).append("\n");
        sb.append("멤버십할인\t\t-").append(numberFormat.format(payment.getMembershipDiscountAmount())).append("\n");
        sb.append("내실돈\t\t").append(numberFormat.format(payment.getFinalPaymentAmount())).append("\n");

        return sb.toString();
    }

}
