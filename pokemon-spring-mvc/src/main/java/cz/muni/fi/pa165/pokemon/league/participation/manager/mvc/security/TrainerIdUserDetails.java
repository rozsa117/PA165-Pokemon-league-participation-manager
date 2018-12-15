package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interface that describes interface for UserDetails implementers containing
 * trainer ID.
 *
 * @author Tibor Zauko 433531
 */
public interface TrainerIdUserDetails extends UserDetails {

    /**
     * Return the trainer Id associated with the username stored as mandated by
     * UserDetails interface.
     *
     * @return The stored trainer ID.
     */
    Long getTrainerId();

}
