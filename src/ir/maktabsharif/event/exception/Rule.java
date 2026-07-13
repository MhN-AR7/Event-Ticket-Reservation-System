package ir.maktabsharif.event.exception;

import java.util.function.Function;

public class Rule {
    private Rule() {}

    public static void check(boolean condition,
                             Function<String, ? extends RuntimeException> exceptionFunction,
                             String message) {
        if (condition) throw exceptionFunction.apply(message);
    }
}
