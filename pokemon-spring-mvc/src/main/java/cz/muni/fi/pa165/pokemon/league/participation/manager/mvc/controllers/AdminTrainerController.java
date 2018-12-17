package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ResourceBundle;

/**
 * Controller class for Trainer for Admins.
 *
 * @author Michal Mokros 456442
 */
@Controller
@RequestMapping("/admin/trainer")
public class AdminTrainerController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminTrainerController.class);

    @Inject
    TrainerFacade trainerFacade;

    /**
     * Get Controller for creating new Trainer
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String createTrainer(Model model) {
        LOGGER.debug("mvc GET newTrainer()");
        model.addAttribute("trainerCreate", new TrainerCreateDTO());
        return "admin/trainer/new";
    }

    /**
     * POST controller for creating new trainer
     * @param formBean DTO for creating new trainer
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("trainerCreate") TrainerCreateDTO formBean,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("mvc POST new()");
        //formBean.setBorn(LocalDate.parse(date));

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                LOGGER.trace("ObjectError: {}", ge);
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
                LOGGER.trace("FieldError: {}", fe);
            });

            return "admin/trainer/new";
        }

        Long id;

        try {
            id = trainerFacade.createTrainer(formBean);
        } catch (NoAdministratorException ex) {
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("trainer.no.administrator"));
            return "redirect:" + uriComponentsBuilder.path("/admin/trainer/create").build().encode().toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success", 
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.created.successfully"), I18n.getLocalizedMessageOrReturnKey("trainer"), id));
        return "redirect:" + uriComponentsBuilder.path("/trainer/list").toUriString();
    }

    /**
     * Sets trainer as Admin
     * @param id of the trainer
     * @return JSP page
     */
    @RequestMapping(value = "/setAdmin/{id}", method = RequestMethod.GET)
    public String setAdmin(@PathVariable long id,
                           RedirectAttributes redirectAttributes,
                           UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("mvc POST setAdmin({})", id);

        try {
            trainerFacade.setAdmin(id, true);
        } catch (NoAdministratorException ex) {
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("trainer.no.administrator"));
            return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.successfully.updated"), I18n.getLocalizedMessageOrReturnKey("trainer")));
        return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
    }

    /**
     * Removes admin role from trainer
     * @param id of the trainer
     * @return JSP page
     */
    @RequestMapping(value = "/unsetAdmin/{id}", method = RequestMethod.GET)
    public String unsetAdmin(@PathVariable long id,
                        RedirectAttributes redirectAttributes,
                        UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("mvc POST unsetAdmin({})", id);

        try {
            trainerFacade.setAdmin(id, false);
        } catch (NoAdministratorException ex) {
            redirectAttributes.addFlashAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey("trainer.no.administrator"));
            return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success",
                MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.successfully.updated"), I18n.getLocalizedMessageOrReturnKey("trainer")));
        return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
    }
}
