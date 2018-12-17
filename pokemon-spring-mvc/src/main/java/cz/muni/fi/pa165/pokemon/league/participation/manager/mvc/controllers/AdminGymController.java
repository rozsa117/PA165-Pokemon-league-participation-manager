package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeGymLeaderDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.GymFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Controller class for gmys for admins.
 *
 * @author Tibor Zauko 433531
 */
@Controller
@RequestMapping("/admin/gym")
public class AdminGymController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminGymController.class);

    @Inject
    private GymFacade gymFacade;

    @Inject
    private TrainerFacade trainerFacade;

    /**
     * Get controller for changing leader of gym.
     *
     * @param id Id of gym.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/changeLeader/{id}", method = RequestMethod.GET)
    public String changeLeader(@PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) {

        GymDTO gym = gymFacade.findGymById(id);
        LOGGER.debug("mvc GET gym changeLeader({})", id);
        if (gym == null) {
            redirectAttributes.addFlashAttribute("alert_danger",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("gym"), id));
            return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
        }
        model.addAttribute("gymToUpdate", gym);
        List<TrainerDTO> leaders = gymFacade.getAllGyms().stream().map((GymDTO g) -> g.getGymLeader()).collect(Collectors.toList());
        List<TrainerDTO> possibleTrainers = trainerFacade.getAllTrainers().stream()
                .filter(t -> t.equals(gym.getGymLeader()) || !leaders.contains(t))
                .collect(Collectors.toList());
        model.addAttribute("possibleTrainers", possibleTrainers);
        return "admin/gym/changeLeader";
    }

    /**
     * Post controller for changing leader of gym.
     *
     * @param gymToUpdate DTO describing the gym leader change.
     * @param id Id of gym to change typing.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/changeLeader/{id}", method = RequestMethod.POST)
    public String changeLeader(@Valid @ModelAttribute("gymToUpdate") ChangeGymLeaderDTO gymToUpdate,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            @PathVariable long id) {

        LOGGER.debug("mvc POST gym changeLeader({})", gymToUpdate);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey(ge.getDefaultMessage()));
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "admin/gym/changeLeader";
        }
        try {
            gymFacade.changeGymLeader(gymToUpdate);
        } catch (EntityIsUsedException ex) {
            model.addAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("gym.leader.used.elsewhere"));
            return "admin/gym/changeLeader";
        } catch (NoSuchEntityException ex) {
            redirectAttributes.addFlashAttribute("alert_danger",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entities.do.not.exists"), I18n.getLocalizedMessageOrReturnKey("gym.or.trainer")));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.successfully.updated"), I18n.getLocalizedMessageOrReturnKey("gym")));
        return "redirect:" + uriComponentsBuilder.path("/gym/list").build().encode().toUriString();
    }

    /**
     * Get controller for creating new pokemon species.
     *
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {

        LOGGER.debug("mvc GET gym create");
        model.addAttribute("gymCreate", new GymCreateDTO());
        createModelAttributes(model);
        return "admin/gym/create";
    }

    /**
     * post controller for creating new pokemon species.
     *
     * @param formBean DTO for creating new pokemon species.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("gymCreate") GymCreateDTO formBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) {

        LOGGER.debug("mvc POST gym create");
        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey(ge.getDefaultMessage()));
            });
            bindingResult.getFieldErrors().stream().map((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
                return fe;
            }).forEachOrdered((fe) -> {
                LOGGER.trace("FieldError: {}", fe);
            });
            createModelAttributes(model);
            return "admin/gym/create";
        }
        Long id;
        try {
            id = gymFacade.createGym(formBean);
        } catch (EntityIsUsedException ex) {
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("gym.leader.used.elsewhere"));
            return "redirect:" + uriComponentsBuilder.path("/admin/pokemonSpecies/create").build().encode().toUriString();
        } catch (NoSuchEntityException ex) {
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("trainer"), formBean.getGymLeaderID()));
            return "redirect:" + uriComponentsBuilder.path("/admin/pokemonSpecies/create").build().encode().toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.created.successfully"), I18n.getLocalizedMessageOrReturnKey("gym"), id));
        return "redirect:" + uriComponentsBuilder.path("/gym/list").toUriString();
    }

    private void allTypes(Model model) {
        model.addAttribute("allTypes", PokemonType.values());
    }

    private void createModelAttributes(Model model) {
        List<TrainerDTO> leaders = gymFacade.getAllGyms().stream().map((GymDTO g) -> g.getGymLeader()).collect(Collectors.toList());
        List<TrainerDTO> possibleTrainers = trainerFacade.getAllTrainers().stream()
                .filter(t -> !leaders.contains(t))
                .collect(Collectors.toList());
        model.addAttribute("possibleTrainers", possibleTrainers);
        allTypes(model);
    }

}
