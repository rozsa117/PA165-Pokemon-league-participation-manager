package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Exception for situations when an entity can't be removed because it is still
 * in use somewhere or it is already used in such a context that using it for
 * in again is not permissible (i.e. trying to assign a trainer to two gyms
 * as leader, trainer trying to challenge a gym that is led by per).
 *
 * @author Tibor Zauko 433531
 */
public class EntityIsUsedException extends Exception {

    public EntityIsUsedException() {
    }

    public EntityIsUsedException(String msg) {
        super(msg);
    }

    public EntityIsUsedException(String message, Throwable cause) {
        super(message, cause);
    }
}
