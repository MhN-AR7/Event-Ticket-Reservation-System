package ir.maktabsharif.event.exception;

public class EventCancelledException extends RuntimeException {
    public EventCancelledException(String message) {
        super(message);
    }
}
