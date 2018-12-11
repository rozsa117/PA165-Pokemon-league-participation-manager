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
import java.util.logging.Level;
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
 * Controller class for Pokemon species.
 * 
 * @author Tamás Rózsa 445653
 */
@Controller
@RequestMapping("/pokemonSpecies")
public class PokemonSpeciesController {
    
    final static Logger log = LoggerFactory.getLogger(PokemonSpeciesController.class);
    
    @Inject
    PokemonSpeciesFacade pokemonSpeciesFacade;
    
    /**
     * Controller method for list all pokemon species.
     * 
     * @return Path to jsp page. 
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("mvc list()");
        model.addAttribute("allPokemonSpecies", pokemonSpeciesFacade.getAllPokemonSpecies());
        return "pokemonSpecies/list";
    }
    
    /**
     * Controller method for get details of pokemon species.
     * 
     * @param id Id of pokemon species to see details.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id,
        Model model,
        RedirectAttributes redirectAttributes,
        UriComponentsBuilder uriComponentsBuilder) {
        
        log.debug("mvc detail({})", id);
        if (pokemonSpeciesFacade.findPokemonSpeciesById(id) == null) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exists"), messages.getString("pokemon.species.singular"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        model.addAttribute("pokemonSpecies", pokemonSpeciesFacade.findPokemonSpeciesById(id));
        return "pokemonSpecies/detail";
    }
    
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
        
        log.debug("mvc GET changeTyping({})", id);
        if (pokemonSpeciesFacade.findPokemonSpeciesById(id) == null) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exists"), messages.getString("pokemon.species.singular"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        model.addAttribute("pokemonSpeciesToUpdate", pokemonSpeciesFacade.findPokemonSpeciesById(id));
        return "pokemonSpecies/changeTyping";
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

        log.debug("mvc POST changeTyping({})", pokemonSpeciesToUpdate);
        pokemonSpeciesToUpdate.setId(id);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
                log.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", messages.getString(ge.getDefaultMessage()));
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "pokemonSpecies/changeTyping";
        }
        try {
            pokemonSpeciesFacade.changeTyping(pokemonSpeciesToUpdate);
        } catch (NoSuchEntityException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
            redirectAttributes.addFlashAttribute("alert_warning", MessageFormat.format(messages.getString("entity.does.not.exists"), messages.getString("pokemon.species.singular"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
        redirectAttributes.addFlashAttribute("alert_success", messages.getString("pokemon.species.successfully.updated"));
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
        
        log.debug("mvc GET changePreevolution({})", id);
        if (pokemonSpeciesFacade.findPokemonSpeciesById(id) == null) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exists"), messages.getString("pokemon.species.singular"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        model.addAttribute("pokemonSpeciesToUpdate", pokemonSpeciesFacade.findPokemonSpeciesById(id));
        return "pokemonSpecies/changePreevolution";
    }

    /**
     * Post controller for changing preevolution of pokemon species.
     * 
     * @param pokemonSpeciesToUpdate DTO of pokemon species to change preevolution.
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
        
        log.debug("mvc POST changePreevolution({})", id);
        pokemonSpeciesToUpdate.setId(id);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
                log.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", messages.getString(ge.getDefaultMessage()));
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "pokemonSpecies/changePreevolution";
        }
        try {
            pokemonSpeciesFacade.changePreevolution(pokemonSpeciesToUpdate);
        } catch (NoSuchEntityException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exists"), messages.getString("pokemon.species.singular"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        } catch (CircularEvolutionChainException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
            redirectAttributes.addFlashAttribute("alert_warning", messages.getString("pokemon.species.circular.evolution"));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/changePreevolution/" + pokemonSpeciesToUpdate.getId()).build().encode().toUriString();
        } catch (EvolutionChainTooLongException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning",  messages.getString("pokemon.species.evolution.chain.too.long"));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/changePreevolution/" + pokemonSpeciesToUpdate.getId()).build().encode().toUriString();
        
        }
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
        redirectAttributes.addFlashAttribute("alert_success", messages.getString("pokemon.species.successfully.updated"));
        return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
    }
    
    /**
     * Get controller for creating new pokemon species.
     * 
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String createNewPokemonSpecies(Model model) {
        
        log.debug("mvc GET createNewPokemonSpecies()");
        model.addAttribute("pokemonSpeciesCreate", new PokemonSpeciesCreateDTO());

        return "pokemonSpecies/create";
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
        
        log.debug("mvc GET create()");
        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                log.trace("ObjectError: {}", ge);
            });
            bindingResult.getFieldErrors().stream().map((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
                return fe;
            }).forEachOrdered((fe) -> {
                log.trace("FieldError: {}", fe);
            });
            return "pokemonSpecies/create";
        }

        Long id;
        try {
            id = pokemonSpeciesFacade.createPokemonSpecies(formBean);
        } catch (EvolutionChainTooLongException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning",  messages.getString("pokemon.species.evolution.chain.too.long"));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/create").build().encode().toUriString();
        } catch (NoSuchEntityException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale()); 
            redirectAttributes.addFlashAttribute("alert_warning", String.format(messages.getString("pokemon.species.does.not.exists"), formBean.getEvolvesFromId()));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/create").build().encode().toUriString();
        }
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("alert_success", MessageFormat.format(messages.getString("pokemon.species.created.successfully"), messages.getString("pokemon.species.singular"), id));
        return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").toUriString();
    }
    
    /**
     * Model attribute for all pokemon types.
     * @return Array of all pokemon types.
     */
    @ModelAttribute("allTypes")
    public PokemonType[] allTypes() {
        log.debug("mvc allTypes()");
        return PokemonType.values();
    }
    
    /**
     * Model attribute for all pokemon species.
     * @return List of all pokemon species.
     */
    @ModelAttribute("allSpecies")
    public List<PokemonSpeciesDTO> allSpecies() {
        log.debug("mvc allSpecies()");
        return pokemonSpeciesFacade.getAllPokemonSpecies();
    }

}
