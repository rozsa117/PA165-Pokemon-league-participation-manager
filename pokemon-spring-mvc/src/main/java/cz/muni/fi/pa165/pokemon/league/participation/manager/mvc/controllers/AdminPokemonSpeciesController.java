package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangePreevolutionDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeTypingDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.CircularEvolutionChainException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonSpeciesFacade;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.validation.Valid;
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

/**
 * Controller class for Pokemon species for admins.
 *
 * @author Tamás Rózsa 445653
 */
@Controller
@RequestMapping("/admin/pokemonSpecies")
public class AdminPokemonSpeciesController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminPokemonSpeciesController.class);

    @Inject
    private PokemonSpeciesFacade pokemonSpeciesFacade;

    /**
     * Get controller for changing type of pokemon species.
     *
     * @param id Id of pokemon species to change typing.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/changeTyping/{id}", method = RequestMethod.GET)
    public String changeTyping(@PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) {

        LOGGER.debug("mvc GET changeTyping({})", id);
        if (pokemonSpeciesFacade.findPokemonSpeciesById(id) == null) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon.species.singular"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        model.addAttribute("pokemonSpeciesToUpdate", pokemonSpeciesFacade.findPokemonSpeciesById(id));
        return "admin/pokemonSpecies/changeTyping";
    }

    /**
     * Post controller for changing type of pokemon species.
     *
     * @param pokemonSpeciesToUpdate DTO of changing pokemon species.
     * @param id Id of pokemon species to change typing.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/changeTyping/{id}", method = RequestMethod.POST)
    public String changeTyping(@Valid @ModelAttribute("pokemonSpeciesToUpdate") ChangeTypingDTO pokemonSpeciesToUpdate,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            @PathVariable long id) {

        LOGGER.debug("mvc POST changeTyping({})", pokemonSpeciesToUpdate);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey(ge.getDefaultMessage()));
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "admin/pokemonSpecies/changeTyping";
        }
        try {
            pokemonSpeciesFacade.changeTyping(pokemonSpeciesToUpdate);
        } catch (NoSuchEntityException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon.species.singular"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("alert_success", MessageFormat.format(messages.getString("entity.successfully.updated"), messages.getString("pokemon.species.singular")));
        return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
    }

    /**
     * Get controller for changing preevolution of pokemon species.
     *
     * @param id Id of pokemon species to change preevolution.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/changePreevolution/{id}", method = RequestMethod.GET)
    public String changePreevolution(@PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) {

        LOGGER.debug("mvc GET changePreevolution({})", id);
        if (pokemonSpeciesFacade.findPokemonSpeciesById(id) == null) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon.species.singular"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        model.addAttribute("pokemonSpeciesToUpdate", pokemonSpeciesFacade.findPokemonSpeciesById(id));
        return "admin/pokemonSpecies/changePreevolution";
    }

    /**
     * Post controller for changing preevolution of pokemon species.
     *
     * @param pokemonSpeciesToUpdate DTO of pokemon species to change
     * preevolution.
     * @param id Id of pokemon species to change preevolution.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/changePreevolution/{id}", method = RequestMethod.POST)
    public String changePreevolution(@Valid @ModelAttribute("pokemonSpeciesToUpdate") ChangePreevolutionDTO pokemonSpeciesToUpdate,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            @PathVariable long id) {

        LOGGER.debug("mvc POST changePreevolution({})", id);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", I18n.getLocalizedMessageOrReturnKey(ge.getDefaultMessage()));
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "admin/pokemonSpecies/changePreevolution";
        }
        try {
            pokemonSpeciesFacade.changePreevolution(pokemonSpeciesToUpdate);
        } catch (NoSuchEntityException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon.species.singular"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        } catch (CircularEvolutionChainException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", messages.getString("pokemon.species.circular.evolution"));
            return "redirect:" + uriComponentsBuilder.path("/admin/pokemonSpecies/changePreevolution/" + pokemonSpeciesToUpdate.getId()).build().encode().toUriString();
        } catch (EvolutionChainTooLongException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", messages.getString("pokemon.species.evolution.chain.too.long"));
            return "redirect:" + uriComponentsBuilder.path("/admin/pokemonSpecies/changePreevolution/" + pokemonSpeciesToUpdate.getId()).build().encode().toUriString();

        }
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("alert_success", MessageFormat.format(messages.getString("entity.successfully.updated"), messages.getString("pokemon.species.singular")));
        return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
    }

    /**
     * Get controller for creating new pokemon species.
     *
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createNewPokemonSpecies(Model model) {

        LOGGER.debug("mvc GET createNewPokemonSpecies()");
        model.addAttribute("pokemonSpeciesCreate", new PokemonSpeciesCreateDTO());

        return "admin/pokemonSpecies/create";
    }

    /**
     * post controller for creating new pokemon species.
     *
     * @param formBean DTO for creating new pokemon species.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("pokemonSpeciesCreate") PokemonSpeciesCreateDTO formBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) {

        LOGGER.debug("mvc GET create()");
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
            return "admin/pokemonSpecies/create";
        }

        Long id;
        try {
            id = pokemonSpeciesFacade.createPokemonSpecies(formBean);
        } catch (EvolutionChainTooLongException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", messages.getString("pokemon.species.evolution.chain.too.long"));
            return "redirect:" + uriComponentsBuilder.path("/admin/pokemonSpecies/create").build().encode().toUriString();
        } catch (NoSuchEntityException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon.species.singular"), formBean.getEvolvesFromId()));
            return "redirect:" + uriComponentsBuilder.path("/admin/pokemonSpecies/create").build().encode().toUriString();
        }
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("alert_success", MessageFormat.format(messages.getString("entity.created.successfully"), messages.getString("pokemon.species.singular"), id));
        return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").toUriString();
    }

    /**
     * Model attribute for all pokemon types.
     *
     * @return Array of all pokemon types.
     */
    @ModelAttribute("allTypes")
    public PokemonType[] allTypes() {
        LOGGER.debug("mvc allTypes()");
        return PokemonType.values();
    }

    /**
     * Model attribute for all pokemon species.
     *
     * @return List of all pokemon species.
     */
    @ModelAttribute("allSpecies")
    public List<PokemonSpeciesDTO> allSpecies() {
        LOGGER.debug("mvc allSpecies()");
        return pokemonSpeciesFacade.getAllPokemonSpecies();
    }

}
