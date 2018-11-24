package cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils;

import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.DataAccessException;

/**
 * Class with static utility methods that wrap exceptions thrown by the supplied
 * calls.
 *
 * @author Tibor Zauko 433531
 */
public class DAOExceptionWrapper {

    /**
     * A functional interface that is similar to Runnable, but doesn't
     * imply threaded contexts and allows throwing of checked exceptions.
     */
    @FunctionalInterface
    public interface ThrowingProcedure {

        void run() throws Exception;

        default ThrowingProcedure andThen(ThrowingProcedure after) {
            return () -> {
                this.run();
                after.run();
            };
        }

        default ThrowingProcedure compose(ThrowingProcedure before) {
            return () -> {
                before.run();
                this.run();
            };
        }
        
    }
    /**
     * A functional interface that allows throwing checked exceptions
     * and is otherwise exactly like Supplier.
     */
    @FunctionalInterface
    public interface ThrowingSupplier<T> {

        public T get() throws Exception;

    }

    /**
     * Invoke the supplied procedure, catch and wrap any thrown exception
     * in DataAccessException.
     *
     * @param procedure a piece of runnable code to invoke.
     * @param exceptionMessage Message to use in exception, if it is thrown.
     */
    public static void withoutResult(ThrowingProcedure procedure, String exceptionMessage) {
        try {
            procedure.run();
        } catch (Exception ex) {
            throw new DataAccessException(exceptionMessage, ex);
        }
    }

    /**
     * Get result of supplier, catch and wrap any thrown exception
     * in DataAccessException.
     *
     * @param <R> The generic result type (inferred from supplier).
     * @param supplier the supplier to run.
     * @param exceptionMessage Message to use in exception, if it is thrown.
     * @return Result supplied by passed supplier.
     */
    public static <R> R withResult(ThrowingSupplier<R> supplier, String exceptionMessage) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            throw new DataAccessException(exceptionMessage, ex);
        }
    }

}
