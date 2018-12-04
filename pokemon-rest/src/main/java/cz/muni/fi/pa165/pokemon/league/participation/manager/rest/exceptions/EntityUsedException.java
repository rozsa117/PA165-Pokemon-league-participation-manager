package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown in case the entity cannot be deleted because it is used.
 * @author User
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "The requested entity cannot be deleted because it is used.")
public class EntityUsedException extends RuntimeException  {
}
