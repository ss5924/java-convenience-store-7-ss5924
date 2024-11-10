package store.order;

import store.io.PromptHandler;

public class OrderProcessor {
    private final PromptHandler promptHandler;

    public OrderProcessor(PromptHandler promptHandler) {
        this.promptHandler = promptHandler;
    }

    public Order promptOrderUntilValidInputForm() {
        return promptHandler.promptOrderUntilValidInputForm();
    }
}