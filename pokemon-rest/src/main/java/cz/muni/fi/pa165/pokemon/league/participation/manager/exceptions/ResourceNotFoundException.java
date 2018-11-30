package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown in case the respected source was not found.
 * 
 * @author Tamás Rózsa 445653
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="The requested resource was not found")
public class ResourceNotFoundException extends RuntimeException {
} 

