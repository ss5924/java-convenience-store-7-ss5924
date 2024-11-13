package store.order;

import store.ui.UserInterfaceHandler;

public class OrderProcessor {
    private final UserInterfaceHandler userInterfaceHandler;

    public OrderProcessor(UserInterfaceHandler userInterfaceHandler) {
        this.userInterfaceHandler = userInterfaceHandler;
    }

    public Order promptOrderUntilValidInputForm() {
        return userInterfaceHandler.promptOrderUntilValidInputForm();
    }
}