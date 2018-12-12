package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerRenameDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller class for Trainer
 *
 * @author Michal Mokros 456442
 */
@Controller
@RequestMapping("/trainer")
public class TrainerController {

    final static Logger log = LoggerFactory.getLogger(TrainerController.class);
    ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());

    @Inject
    TrainerFacade trainerFacade;

    /**
     * Controller method for listing all trainers
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("mvc list()");
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
        log.debug("mvc detail({})", id);

        if (trainerFacade.getTrainerWithId(id) == null) {
            redirectAttributes.addFlashAttribute("alert_danger", String.format(messages.getString("trainer.does.not.exists"), id));
            return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
        }

        model.addAttribute("trainer", trainerFacade.getTrainerWithId(id));
        return "trainer/detail";
    }

    /**
     * Get Controller for creating new Trainer
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String createTrainer(Model model) {
        log.debug("mvc GET newTrainer()");
        model.addAttribute("trainerCreate", new TrainerCreateDTO());
        return "trainer/create";
    }

    /**
     * POST controller for creating new trainer
     * @param formBean DTO for creating new trainer
     * @return JSP page
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("trainerCreate") TrainerCreateDTO formBean,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder) {
        log.debug("mvc POST create()");
        //formBean.setBorn(LocalDate.parse(date.toString(), DateTimeFormatter.ofPattern("mm/dd/yyyy")));

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                log.trace("ObjectError: {}", ge);
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            });

            return "trainer/create";
        }

        Long id;

        try {
            id = trainerFacade.createTrainer(formBean);
        } catch (NoAdministratorException ex) {
            redirectAttributes.addFlashAttribute("alert_warning", messages.getString("trainer.no.administrator"));
            return "redirect:" + uriComponentsBuilder.path("/trainer/create").build().encode().toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success", String.format(messages.getString("trainer.created.successfully"), id));
        return "redirect:" + uriComponentsBuilder.path("/trainer/list").toUriString();
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
        log.debug("mvc GET rename({})", id);

        if (trainerFacade.getTrainerWithId(id) == null) {
            redirectAttributes.addFlashAttribute("alert_danger", String.format(messages.getString("trainer.does.not.exists"), id));
            return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
        }

        TrainerRenameDTO trainerRenameDTO = new TrainerRenameDTO();
        trainerRenameDTO.setTrainerId(id);
        model.addAttribute("trainerToUpdate", trainerRenameDTO);
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
        log.debug("mvc POST rename({})", id);
        formBean.setTrainerId(id);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                log.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", messages.getString(ge.getDefaultMessage()));
            });

            bindingResult.getFieldErrors().forEach((fe) -> {
                log.trace(fe.getField() + "_error", true);
            });

            return "trainer/rename";
        }

        trainerFacade.renameTrainer(formBean);
        redirectAttributes.addFlashAttribute("alert_success", messages.getString("trainer.updated.successfully"));
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
        log.debug("mvc GET changePassword({})", id);

        if (trainerFacade.getTrainerWithId(id) == null) {
            redirectAttributes.addFlashAttribute("alert_danger", String.format(messages.getString("trainer.does.not.exists"), id));
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
        log.debug("mvc POST changePassword({})", id);
        formBean.setTrainerId(id);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                log.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", messages.getString(ge.getDefaultMessage()));
            });

            bindingResult.getFieldErrors().forEach((fe) -> {
                log.trace(fe.getField() + "_error", true);
            });

            return "trainer/changePassword";
        }

        if (!trainerFacade.changePassword(formBean)) {
            redirectAttributes.addFlashAttribute("alert_warning", messages.getString("trainer.wrong.old.password"));
            return "redirect:" + uriComponentsBuilder.path("/trainer/changePassword/" + formBean.getTrainerId()).build().encode().toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success", messages.getString("trainer.updated.successfully"));
        return "redirect:" + uriComponentsBuilder.path("/trainer/list").build().encode().toUriString();
    }


}
