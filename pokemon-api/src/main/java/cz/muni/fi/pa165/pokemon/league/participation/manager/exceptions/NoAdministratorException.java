package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Exception when there are no administrator left.
 *
 * @author Jiří Medveď 38451
 */
public class NoAdministratorException extends Exception {

    /**
     * Creates a new instance of <code>NoAdministratorLeftException</code>
     * without detail message.
     */
    public NoAdministratorException() {
    }

    /**
     * Constructs an instance of <code>NoAdministratorLeftException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoAdministratorException(String msg) {
        super(msg);
    }
}
