package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception extending Spring's AuthenticationException for indicating
 * authentication failure when using TrainerFacadeAuthenticationProvider.
 *
 * @author Tibor Zauko
 */
public class TrainerFacadeAuthenticationException extends AuthenticationException {

    public TrainerFacadeAuthenticationException(String msg) {
        super(msg);
    }

    public TrainerFacadeAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

}
