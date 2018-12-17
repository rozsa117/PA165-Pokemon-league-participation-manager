package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeGymTypeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymAndBadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.GymFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.security.TrainerIdUserDetails;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, Authentication authentication) {
        List<GymAndBadgeDTO> gymsAndBadges;
        try {
            gymsAndBadges = gymFacade.getAllGymsAndBadgesOfTrainer(((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId());
        } catch (NoSuchEntityException ex) {
            LOGGER.warn("Trainer got deleted in the meantime");
            gymsAndBadges = gymFacade.getAllGyms().stream()
                    .map(gym -> new GymAndBadgeDTO(gym, null))
                    .collect(Collectors.toList());
        }
        LOGGER.debug("Got GymsAndBadges", gymsAndBadges);
        model.addAttribute("allGymBadgePairs", gymsAndBadges);
        return "gym/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes ra, Authentication authentication, UriComponentsBuilder uriComponentsBuilder) {
        GymDTO gym = gymFacade.findGymById(id);
        if (gym == null) {
            ra.addAttribute("alert_danger",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("gym"), id));
            return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
        }
        BadgeDTO badge = getBadgesOfCallingUser(authentication).stream()
                .filter(b -> b.getGym().getId().equals(gym.getId()))
                .findFirst()
                .orElse(null);
        model.addAttribute("gym", gym);
        model.addAttribute("badge", badge);
        return "gym/detail";
    }

    @RequestMapping(value = "/changeType/{id}", method = RequestMethod.GET)
    public String changeType(@PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            Authentication authentication) {
        LOGGER.debug("mvc GET gym/changeType({})", id);
        GymDTO gym = gymFacade.findGymById(id);
        if (gym == null) {
            redirectAttributes.addFlashAttribute("alert_danger",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("gym"), id));
            return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
        }
        Long userId = ((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId();
        if (!gym.getGymLeader().getId().equals(userId)) {
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("no.rights"));
            return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
        }
        model.addAttribute("gymToUpdate", gym);
        allTypes(model);
        return "gym/changeType";
    }

    @RequestMapping(value = "/changeType/{id}", method = RequestMethod.POST)
    public String changeType(@PathVariable long id,
            @Valid @ModelAttribute("gymToUpdate") ChangeGymTypeDTO gymToUpdate,
            Authentication authentication,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", ge.getDefaultMessage());
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "pokemonSpecies/changeTyping";
        }
        gymToUpdate.setTrainerId(((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId());
        try {
            gymFacade.changeGymType(gymToUpdate);
        } catch (InsufficientRightsException ex) {
            LOGGER.debug("Insufficient rights", ex);
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("no.rights"));
            return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.successfully.updated"), I18n.getLocalizedMessageOrReturnKey("gym")));
        return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
    }

    private void allTypes(Model model) {
        model.addAttribute("allTypes", PokemonType.values());
    }

    private List<BadgeDTO> getBadgesOfCallingUser(Authentication authentication) {
        List<BadgeDTO> badges = new ArrayList<>();
        if (authentication == null) {
            return badges;
        }
        Long trainerId = ((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId();
        try {
            badges = badgeFacade.findBadgesOfTrainer(trainerId);
        } catch (NoSuchEntityException ex) {
            LOGGER.warn("Trainer got deleted in the meantime");
        }
        return badges;
    }

}
