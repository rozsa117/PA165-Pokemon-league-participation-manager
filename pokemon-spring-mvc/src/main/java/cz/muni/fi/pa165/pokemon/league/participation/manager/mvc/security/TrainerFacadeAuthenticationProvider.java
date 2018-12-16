package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.security;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerAuthenticateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.exceptions.TrainerFacadeAuthenticationException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Authentication provider using TrainerFacade for authenticating users.
 *
 * @author Tibor Zauko 433531
 */
@Component
public class TrainerFacadeAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private TrainerFacade trainerFacade;

    @Override
    public Authentication authenticate(final Authentication a) throws AuthenticationException {
        final String name = a.getName();
        final String password = a.getCredentials().toString();
        TrainerDTO trainer = trainerFacade.findTrainerByUsername(name);
        if (trainer == null) {
            throw new TrainerFacadeAuthenticationException("User or password is incorrect");
        }
        TrainerAuthenticateDTO authDto = new TrainerAuthenticateDTO();
        authDto.setUserId(trainer.getId());
        authDto.setPassword(password);
        if (!trainerFacade.authenticate(authDto)) {
            throw new TrainerFacadeAuthenticationException("User or password is incorrect");
        }
        List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (trainer.isAdmin()) {
            auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        UserDetails user = new TrainerIdContainingUser(name, password, auths, trainer.getId());
        return new UsernamePasswordAuthenticationToken(user, password, auths);
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }

}
