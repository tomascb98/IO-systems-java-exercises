package edu.epam.fop.lambdas;
@FunctionalInterface
public interface TriFunction <T, E, U, O>{
    O apply(T first, E second, U third);
}
