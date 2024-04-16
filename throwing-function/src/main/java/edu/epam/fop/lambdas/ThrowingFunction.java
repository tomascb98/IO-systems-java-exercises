package edu.epam.fop.lambdas;

import java.util.function.Function;
/**
 * A functional interface representing a function that may throw a checked exception.
 *
 * @param <T> The type of the input to the function.
 * @param <U> The type of the result of the function.
 * @param <E> The type of checked exception that may be thrown.
 */
@FunctionalInterface
public interface ThrowingFunction<T, U, E extends Throwable> {
    /**
     * Applies this function to the given argument.
     *
     * @param argument The function argument.
     * @return The function result.
     * @throws E If an exception occurs during the function application.
     */
    U apply(T argument) throws E;

    /**
     * Converts a ThrowingFunction into a regular Function, handling checked exceptions by wrapping them in RuntimeException.
     *
     * @param <T>              The type of the input to the function.
     * @param <U>              The type of the result of the function.
     * @param <P>              The type of checked exception that may be thrown by the ThrowingFunction.
     * @param throwingFunction The ThrowingFunction to convert.
     * @return A Function that wraps any checked exceptions in RuntimeException.
     */
    static <T, U, P extends Throwable> Function<T, U> quiet(ThrowingFunction<T, U, P> throwingFunction) {
        if (throwingFunction == null) return null;
        return (argument) -> {
            U returnValue;
            try {
                returnValue = throwingFunction.apply(argument);
            } catch (Throwable e) {
                // Wrapping the checked exception in a RuntimeException and rethrowing.
                throw new RuntimeException(e);
            }
            return returnValue;
        };
    }
}

