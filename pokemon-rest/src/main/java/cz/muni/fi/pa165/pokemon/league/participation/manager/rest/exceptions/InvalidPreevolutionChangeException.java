package cz.muni.fi.pa165.pokemon.league.participation.manager.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown in case wrong preevolution was chosen.
 * 
 * @author Tamás Rózsa 445653
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "The choosen preevolution change is not valid.")
public class InvalidPreevolutionChangeException extends RuntimeException {

    public InvalidPreevolutionChangeException(String msg, Exception ex) {
                super(msg,ex);
    }
}
