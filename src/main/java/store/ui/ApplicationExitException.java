package store.ui;

public class ApplicationExitException extends RuntimeException {
    public ApplicationExitException(String message) {
        super(message);
    }
}