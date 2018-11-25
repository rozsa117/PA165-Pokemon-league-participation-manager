package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Non abstract specialization of Spring's DataAccessException.
 *
 * @author Tibor Zauko 433531
 */
public class DataAccessException extends org.springframework.dao.DataAccessException {

    public DataAccessException(String msg) {
        super(msg);
    }

    public DataAccessException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

}
