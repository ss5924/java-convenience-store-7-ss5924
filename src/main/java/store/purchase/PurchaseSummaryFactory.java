package store.purchase;

import store.order.Order;
import store.order.OrderItem;
import store.product.InventoryItem;
import store.product.InventoryReadService;
import store.product.Product;
import store.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PurchaseSummaryFactory {
    public List<PurchaseSummary> createPurchaseSummaries(Order order, InventoryReadService inventoryReadService) {
        List<PurchaseSummary> purchaseSummaries = new ArrayList<>();

        order.getOrderItems().forEach(orderItem -> {
            PurchaseSummary purchaseSummary = createPurchaseSummary(orderItem, inventoryReadService);
            purchaseSummaries.add(purchaseSummary);
        });

        return purchaseSummaries;
    }

    private PurchaseSummary createPurchaseSummary(OrderItem orderItem, InventoryReadService inventoryReadService) {
        Product product = orderItem.getProduct();
        List<InventoryItem> inventoryItems = inventoryReadService.getInventoryItemsByProduct(product);

        Optional<Promotion> promotion = inventoryItems.stream()
                .map(InventoryItem::getPromotion)
                .filter(Objects::nonNull)
                .findFirst();
        int orderedQuantity = orderItem.getQuantity();
        int promotionStock = inventoryItems.stream()
                .filter(item -> item.getPromotion() != null)
                .mapToInt(InventoryItem::getQuantity)
                .sum();
        int noPromotionStock = inventoryItems.stream()
                .filter(item -> item.getPromotion() == null)
                .mapToInt(InventoryItem::getQuantity)
                .sum();

        return new PurchaseSummary(product, promotion.orElse(null), orderedQuantity, promotionStock, noPromotionStock);
    }

}
