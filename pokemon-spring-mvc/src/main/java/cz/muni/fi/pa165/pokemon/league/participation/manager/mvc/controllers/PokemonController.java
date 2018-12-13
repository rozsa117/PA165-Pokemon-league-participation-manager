package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymAndBadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.GymFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.security.TrainerIdContainingUser;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Pokemon controller.
 *
 * @author Jiří Medveď 38451
 */
@Controller
@RequestMapping("/pokemon")
public class PokemonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonController.class);

    @Inject
   private PokemonFacade pokemonFacade; 
    
    @Inject
    private GymFacade gymFacade;

    @Inject
    private BadgeFacade badgeFacade;

    @Inject
    private TrainerFacade trainerFacade;

    @RequestMapping("/list")
    public String list(Model model, Authentication authentication) {
        List<PokemonDTO> pokemons =  null;
        TrainerDTO principal = trainerFacade.getTrainerWithId(getPrincipalId(authentication));
        model.addAttribute("principal",principal);
        try {
            pokemons = pokemonFacade.getPokemonOfTrainer(getPrincipalId(authentication));
        } catch (NoSuchEntityException ex) {
            LOGGER.warn("Trainer got deleted in the meantime");
//            pokemons = pokemonFacade.getAllGyms().stream()
//                    .map(gym -> new GymAndBadgeDTO(gym, null))
//                    .collect(Collectors.toList());
        }
        LOGGER.debug("Got Pokemons " , pokemons);
        model.addAttribute("pokemons", pokemons);
        return "pokemon/list";
    }

/*    
    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable long id, Model model, RedirectAttributes ra, Principal principal) {
        GymDTO gym = gymFacade.findGymById(id);
        if (gym == null) {
            ra.addAttribute("alert_warning", MessageFormat.format(I18n.getStringFromTextsBundle("entity.does.not.exist"), I18n.getStringFromTextsBundle("gym"), id));
            return "gym/list";
        }
        BadgeDTO badge = getBadgesOfCallingUser(principal).stream()
                .filter(b -> b.getGym().getId().equals(gym.getId()))
                .findFirst()
                .orElse(null);
        model.addAttribute("gym", gym);
        model.addAttribute("badge", badge);
        return "gym/detail";
    }

    private List<BadgeDTO> getBadgesOfCallingUser(Principal principal) {
        List<BadgeDTO> badges = new ArrayList<>();
        if (principal == null) {
            return badges;
        }
        TrainerDTO trainer = trainerFacade.findTrainerByUsername(principal.getName());
        try {
            badges = trainer == null ? badges : badgeFacade.findBadgesOfTrainer(trainer.getId());
        } catch (NoSuchEntityException ex) {
            LOGGER.warn("Trainer deleted while retrieving his badges", ex);
        }
        return badges;
    }
*/

    private Long getPrincipalId(Authentication authentication) {
        return ((TrainerIdContainingUser) authentication.getPrincipal()).getTrainerId();
    }
}
