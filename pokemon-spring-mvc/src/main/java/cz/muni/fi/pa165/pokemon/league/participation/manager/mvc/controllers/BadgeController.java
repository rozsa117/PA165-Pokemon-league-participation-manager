package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeStatusChangeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidChallengeStatusChangeException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.GymFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.security.TrainerIdUserDetails;
import java.text.MessageFormat;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Badge controller.
 *
 * @author Tibor Zauko 433531
 */
@Controller
@RequestMapping("/badge")
public class BadgeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BadgeController.class);

    @Inject
    private GymFacade gymFacade;

    @Inject
    private BadgeFacade badgeFacade;

    @Inject
    private TrainerFacade trainerFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, Authentication authentication,
            @RequestParam(name = "trainerId", required = false) Long trainerId,
            RedirectAttributes ra, UriComponentsBuilder uriComponentsBuilder) {
        List<BadgeDTO> trainersBadges;
        if (trainerId == null) {
            trainerId = ((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId();
        }
        TrainerDTO trainer = trainerFacade.getTrainerWithId(trainerId);
        try {
            trainersBadges = badgeFacade.findBadgesOfTrainer(trainerId);
        } catch (NoSuchEntityException ex) {
            LOGGER.warn("Trainer got deleted in the meantime");
            ra.addAttribute("alert_danger",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("trainer"), trainerId));
            return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
        }
        model.addAttribute("trainer", trainer);
        model.addAttribute("badges", trainersBadges);
        return "badge/list";
    }

    @RequestMapping(value = "/challenges", method = RequestMethod.GET)
    public String challenges(Model model, Authentication authentication,
            UriComponentsBuilder uriComponentsBuilder,
            RedirectAttributes ra) {
        List<BadgeDTO> gymsChallenges;;
        GymDTO gym = gymFacade.findGymByLeader(((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId());
        if (gym == null) {
            ra.addAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("trainer.not.gym.leader"));
            return "redirect:" + uriComponentsBuilder.path("/badge/list").build().encode().toUriString();
        }
        Long gymId = gym.getId();
        try {
            gymsChallenges = badgeFacade.findBadgesOfGym(gymId);
        } catch (NoSuchEntityException ex) {
            LOGGER.warn("Gym got deleted in the meantime");
            ra.addAttribute("alert_danger",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("gym"), gymId));
            return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
        }
        model.addAttribute("badges", gymsChallenges);
        model.addAttribute("gym", gym);
        return "badge/challenges";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes ra, UriComponentsBuilder uriComponentsBuilder) {
        BadgeDTO badge = badgeFacade.findBadgeById(id);
        if (badge == null) {
            ra.addAttribute("alert_danger",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("badge"), id));
            return "redirect:" + uriComponentsBuilder.path("/badge/list").build().encode().toUriString();
        }
        model.addAttribute("badge", badge);
        return "badge/detail";
    }

    @RequestMapping(value = "/revoke/{id}", method = RequestMethod.GET)
    public String revoke(@PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        BadgeDTO badge = badgeFacade.findBadgeById(id);
        if (badge == null) {
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("badge")));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        }
        model.addAttribute("subject", badge.getTrainer().getName() + " " + badge.getTrainer().getSurname());
        model.addAttribute("postUrl", "/badge/revoke/" + badge.getId());
        model.addAttribute("msgKey", "badge.revoke.confirm");
        return "badge/confirmBadgeOperation";
    }

    @RequestMapping(value = "/revoke/{id}", method = RequestMethod.POST)
    public String revoke(@PathVariable long id,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        BadgeStatusChangeDTO badge = new BadgeStatusChangeDTO();
        badge.setBadgeId(id);
        badge.setRequestingTrainerId(((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId());
        try {
            badgeFacade.revokeBadge(badge);
        } catch (InsufficientRightsException ex) {
            LOGGER.debug("Insufficient rights", ex);
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("no.rights"));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        } catch (NoSuchEntityException ex) {
            LOGGER.debug("No entity", ex);
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entities.do.not.exist"), I18n.getLocalizedMessageOrReturnKey("badge.or.trainer")));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        } catch (InvalidChallengeStatusChangeException ex) {
            LOGGER.debug("Can't revoke", ex);
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("badge.not.revocable"));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.successfully.updated"), I18n.getLocalizedMessageOrReturnKey("badge")));
        return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
    }

    @RequestMapping(value = "/takeChallenge/{id}", method = RequestMethod.GET)
    public String takeChallenge(@PathVariable long id,
            @RequestParam(name = "challengeWon") boolean challengeWon,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        BadgeDTO badge = badgeFacade.findBadgeById(id);
        if (badge == null) {
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("badge")));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        }
        model.addAttribute("subject", badge.getTrainer().getName() + " " + badge.getTrainer().getSurname());
        model.addAttribute("postUrl", "/badge/takeChallenge/" + badge.getId());
        model.addAttribute("msgKey", challengeWon ? "badge.accept.confirm" : "badge.deny.confirm");
        model.addAttribute("requestParam", challengeWon);
        return "badge/confirmBadgeOperation";
    }

    @RequestMapping(value = "/takeChallenge/{id}", method = RequestMethod.POST)
    public String takeChallenge(
            @PathVariable long id,
            @RequestParam(name = "requestParam") boolean challengeWon,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        BadgeStatusChangeDTO badge = new BadgeStatusChangeDTO();
        badge.setBadgeId(id);
        badge.setRequestingTrainerId(((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId());
        try {
            if (challengeWon) {
                badgeFacade.winBadge(badge);
            } else {
                badgeFacade.loseBadge(badge);
            }
        } catch (InsufficientRightsException ex) {
            LOGGER.debug("Insufficient rights", ex);
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("no.rights"));
            return "redirect:" + uriComponentsBuilder.path("/badge/list").build().encode().toUriString();
        } catch (NoSuchEntityException ex) {
            LOGGER.debug("No entity", ex);
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entities.do.not.exist"), I18n.getLocalizedMessageOrReturnKey("badge.or.trainer")));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        } catch (InvalidChallengeStatusChangeException ex) {
            LOGGER.debug("Can't revoke", ex);
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("badge.not.issueable"));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.successfully.updated"), I18n.getLocalizedMessageOrReturnKey("badge")));
        return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
    }

    @RequestMapping(value = "/reissue/{id}", method = RequestMethod.GET)
    public String reissue(@PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        BadgeDTO badge = badgeFacade.findBadgeById(id);
        if (badge == null) {
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("badge")));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        }
        model.addAttribute("subject", badge.getTrainer().getName() + " " + badge.getTrainer().getSurname());
        model.addAttribute("postUrl", "/badge/reissue/" + badge.getId());
        model.addAttribute("msgKey", "badge.reissue.confirm");
        return "badge/confirmBadgeOperation";
    }

    @RequestMapping(value = "/reissue/{id}", method = RequestMethod.POST)
    public String reissue(
            @PathVariable long id,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        BadgeStatusChangeDTO badge = new BadgeStatusChangeDTO();
        badge.setBadgeId(id);
        badge.setRequestingTrainerId(((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId());
        try {
            badgeFacade.winBadge(badge);
        } catch (InsufficientRightsException ex) {
            LOGGER.debug("Insufficient rights", ex);
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("no.rights"));
            return "redirect:" + uriComponentsBuilder.path("/badge/list").build().encode().toUriString();
        } catch (NoSuchEntityException ex) {
            LOGGER.debug("No entity", ex);
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entities.do.not.exist"), I18n.getLocalizedMessageOrReturnKey("badge.or.trainer")));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        } catch (InvalidChallengeStatusChangeException ex) {
            LOGGER.debug("Can't revoke", ex);
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("badge.not.revoked"));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.successfully.updated"), I18n.getLocalizedMessageOrReturnKey("badge")));
        return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
    }

    @RequestMapping(value = "/rechallenge/{id}", method = RequestMethod.GET)
    public String rechallenge(@PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        BadgeDTO badge = badgeFacade.findBadgeById(id);
        if (badge == null) {
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("badge")));
            return "redirect:" + uriComponentsBuilder.path("/badge/challenges").build().encode().toUriString();
        }
        model.addAttribute("subject", badge.getGym().getLocation());
        model.addAttribute("postUrl", "/badge/rechallenge/" + badge.getId());
        model.addAttribute("msgKey", "badge.rechallenge.confirm");
        return "badge/confirmBadgeOperation";
    }

    @RequestMapping(value = "/rechallenge/{id}", method = RequestMethod.POST)
    public String rechallenge(
            @PathVariable long id,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        BadgeStatusChangeDTO badge = new BadgeStatusChangeDTO();
        badge.setBadgeId(id);
        badge.setRequestingTrainerId(((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId());
        try {
            badgeFacade.reopenChallenge(badge);
        } catch (InsufficientRightsException ex) {
            LOGGER.debug("Insufficient rights", ex);
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("no.rights"));
            return "redirect:" + uriComponentsBuilder.path("/badge/list").build().encode().toUriString();
        } catch (NoSuchEntityException ex) {
            LOGGER.debug("No entity", ex);
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entities.do.not.exist"), I18n.getLocalizedMessageOrReturnKey("badge.or.trainer")));
            return "redirect:" + uriComponentsBuilder.path("/badge/list").build().encode().toUriString();
        } catch (InvalidChallengeStatusChangeException ex) {
            LOGGER.debug("Can't revoke", ex);
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("badge.not.lost"));
            return "redirect:" + uriComponentsBuilder.path("/badge/list").build().encode().toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.successfully.updated"), I18n.getLocalizedMessageOrReturnKey("badge")));
        return "redirect:" + uriComponentsBuilder.path("/badge/list").build().encode().toUriString();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam(name = "gymId") long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        GymDTO gym = gymFacade.findGymById(id);
        if (gym == null) {
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("gym")));
            return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
        }
        model.addAttribute("subject", gym.getLocation());
        model.addAttribute("postUrl", "/badge/create");
        model.addAttribute("msgKey", "badge.create.confirm");
        model.addAttribute("requestParam", id);
        return "badge/confirmBadgeOperation";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
            @RequestParam(name = "requestParam", required = true) long gymId,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        BadgeCreateDTO badge = new BadgeCreateDTO();
        badge.setGymId(gymId);
        badge.setTrainerId(((TrainerIdUserDetails) authentication.getPrincipal()).getTrainerId());
        Long newId;
        try {
            newId = badgeFacade.createBadge(badge);
        } catch (NoSuchEntityException ex) {
            LOGGER.debug("No entity", ex);
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entities.do.not.exist"), I18n.getLocalizedMessageOrReturnKey("gym.or.trainer")));
            return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
        } catch (EntityIsUsedException ex) {
            LOGGER.debug("No entity", ex);
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("badge.trainer.is.gym.leader"));
            return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.created.successfully"), I18n.getLocalizedMessageOrReturnKey("badge"), newId));
        return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
    }

}
