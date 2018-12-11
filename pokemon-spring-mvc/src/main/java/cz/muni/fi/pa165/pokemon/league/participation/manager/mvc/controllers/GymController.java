package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.GymFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Gym controller.
 *
 * @author Tibor Zauko 433531
 */
@Controller
@RequestMapping("/gym")
public class GymController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GymController.class);

    @Inject
    private GymFacade gymFacade;

    @Inject
    private BadgeFacade badgeFacade;

    @Inject
    private TrainerFacade trainerFacade;

    @RequestMapping("/list")
    public String list(Model model, Principal principal) {
        List<GymDTO> gyms = gymFacade.getAllGyms();
        List<BadgeDTO> badges = getBadgesOfCallingUser(principal);
        List<AbstractMap.SimpleImmutableEntry<GymDTO, BadgeDTO>> gymBadgePairs = gyms.stream()
                .map((GymDTO gym) -> {
                    BadgeDTO badge = badges.stream()
                            .filter((BadgeDTO b) -> b.getGym().getId().equals(gym.getId()))
                            .findFirst()
                            .orElse(null);
                    return new AbstractMap.SimpleImmutableEntry<>(gym, badge);
                })
                .collect(Collectors.toList());
        model.addAttribute("allGymBadgePairs", gymBadgePairs);
        return "gym/list";
    }

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

}
