package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerRenameDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import java.text.MessageFormat;
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

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Controller class for Trainer
 *
 * @author Michal Mokros 456442
 */
@Controller
@RequestMapping("/trainer")
public class TrainerController {

    final static Logger LOGGER = LoggerFactory.getLogger(TrainerController.class);

    @Inject
    TrainerFacade trainerFacade;

    @Inject
    PokemonFacade pokemonFacade;

    /**
     * Controller method for listing all trainers
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        LOGGER.debug("mvc list()");
        model.addAttribute("allTrainers", trainerFacade.getAllTrainers());
        return "trainer/list";
    }

    /**
     * Controller method for detailed trainer
     * @param id of the trainer
     * @param model data to be displayed
     * @return JSP page name
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("mvc detail({})", id);
        TrainerDTO trainerDTO = trainerFacade.getTrainerWithId(id);

        if (trainerDTO == null) {
            redirectAttributes.addFlashAttribute("alert_danger",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("trainer"), id));
            return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
        } else {
            model.addAttribute("trainer", trainerDTO);
        }

        try {
            model.addAttribute("pokemons", pokemonFacade.getPokemonOfTrainer(id));
        } catch (NoSuchEntityException ex) {
            redirectAttributes.addFlashAttribute("alert_warning",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("trainer"), id));
            return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
        }
        
        return "trainer/detail";
    }

    /**
     * GET controller for renaming of trainer
     * @param id of trainer to be renamed
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/rename/{id}", method = RequestMethod.GET)
    public String rename(@PathVariable long id,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("mvc GET rename({})", id);

        if (trainerFacade.getTrainerWithId(id) == null) {
            redirectAttributes.addFlashAttribute("alert_danger",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("trainer"), id));
            return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
        }

        model.addAttribute("trainerToUpdate", trainerFacade.getTrainerWithId(id));
        return "trainer/rename";
    }

    /**
     * POST controller for renaming of trainer
     * @param formBean DTO for rename
     * @param model data to be displayed
     * @param id of renamed trainer
     * @return JSP page
     */
    @RequestMapping(value = "/rename/{id}", method = RequestMethod.POST)
    public String rename(@Valid @ModelAttribute("trainerToUpdate") TrainerRenameDTO formBean,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder,
                         @PathVariable long id) {
        LOGGER.debug("mvc POST rename({})", id);
        formBean.setTrainerId(id);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey(ge.getDefaultMessage()));
            });

            bindingResult.getFieldErrors().forEach((fe) -> {
                LOGGER.trace(fe.getField() + "_error", true);
            });

            return "trainer/rename";
        }

        trainerFacade.renameTrainer(formBean);
        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.successfully.updated"), I18n.getLocalizedMessageOrReturnKey("trainer")));
        return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
    }

    /**
     * GET controller of changing of trainer password
     * @param id of the trainer
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/changePassword/{id}", method = RequestMethod.GET)
    public String changePassword(@PathVariable long id,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("mvc GET changePassword({})", id);

        if (trainerFacade.getTrainerWithId(id) == null) {
            redirectAttributes.addFlashAttribute("alert_danger",
                    MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("trainer"), id));
            return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
        }

        TrainerChangePasswordDTO trainerChangePasswordDTO = new TrainerChangePasswordDTO();
        trainerChangePasswordDTO.setTrainerId(id);
        model.addAttribute("trainerToUpdate", trainerChangePasswordDTO);
        return "trainer/changePassword";
    }

    @RequestMapping(value = "/changePassword/{id}", method = RequestMethod.POST)
    public String changePassword(@Valid @ModelAttribute("trainerToUpdate") TrainerChangePasswordDTO formBean,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 UriComponentsBuilder uriComponentsBuilder,
                                 @PathVariable long id) {
        LOGGER.debug("mvc POST changePassword({})", id);
        formBean.setTrainerId(id);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey(ge.getDefaultMessage()));
            });

            bindingResult.getFieldErrors().forEach((fe) -> {
                LOGGER.trace(fe.getField() + "_error", true);
            });

            return "trainer/changePassword";
        }

        if (!trainerFacade.changePassword(formBean)) {
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("trainer.wrong.old.password"));
            return "redirect:" + uriComponentsBuilder.path("/trainer/changePassword/" + formBean.getTrainerId()).build().encode().toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.successfully.updated"), I18n.getLocalizedMessageOrReturnKey("trainer")));
        return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
    }
}
