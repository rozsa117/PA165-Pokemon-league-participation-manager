package cz.muni.fi.pa165.pokemon.league.participation.manager.rest.exceptions;

import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Rest exception handler
 *
 * @author Jiří Medveď 38451
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ErrorMessage handleResourceNotFoundException(Exception ex) {
        return new ErrorMessage((ex.getCause() == null ? "Unknown cause" : ex.getCause().getMessage()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = {InvalidParameterException.class, 
        InvalidPreevolutionChangeException.class,
        EntityIsUsedException.class})
    protected ErrorMessage handleInvalidParameterException(Exception ex) {
        return new ErrorMessage((ex.getCause() == null ? "Invalid parameters" : ex.getCause().getMessage()));
    }

}
