package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * User extension implementing remaining methods mandated by
 * TrainerIdUserDetails
 *
 * @author Tibor Zauko 433531
 */
public class TrainerIdContainingUser extends User implements TrainerIdUserDetails {

    private final Long trainerId;

    /**
     * Construct a new Ueer instance.
     *
     * @param username Passed to User constructor.
     * @param password Passed to User constructor.
     * @param authorities Passed to User constructor.
     * @param trainerId Trainer ID to store.
     */
    public TrainerIdContainingUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long trainerId) {
        super(username, password, authorities);
        this.trainerId = trainerId;
    }

    /**
     * Construct a new User instance.
     *
     * @param username Passed to User constructor.
     * @param password Passed to User constructor.
     * @param enabled Passed to User constructor.
     * @param accountNonExpired Passed to User constructor.
     * @param credentialsNonExpired Passed to User constructor.
     * @param accountNonLocked Passed to User constructor.
     * @param authorities Passed to User constructor.
     * @param trainerId Trainer ID to store.
     */
    public TrainerIdContainingUser(
            String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            Long trainerId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.trainerId = trainerId;
    }

    @Override
    public Long getTrainerId() {
        return trainerId;
    }

}
