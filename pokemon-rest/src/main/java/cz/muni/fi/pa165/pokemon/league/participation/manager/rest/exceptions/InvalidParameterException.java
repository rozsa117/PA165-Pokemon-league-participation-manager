package cz.muni.fi.pa165.pokemon.league.participation.manager.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown in case the parameter was invalid.
 * 
 * @author Tamás Rózsa 445653
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid parameter.")
public class InvalidParameterException extends RuntimeException {

        public InvalidParameterException() {
    }
        public InvalidParameterException(String msg, Exception ex) {
        super(msg,ex);
    }
}
