package store.order;

import store.io.PromptHandler;

import java.time.LocalDateTime;

public class OrderProcessor {
    private final PromptHandler promptHandler;

    public OrderProcessor(PromptHandler promptHandler) {
        this.promptHandler = promptHandler;
    }

    public Order promptOrderUntilValidStock(LocalDateTime now) {
        return promptHandler.promptOrderUntilValidStock(now);
    }
}