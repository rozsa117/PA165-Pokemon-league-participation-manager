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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("allPokemonSpecies", pokemonSpeciesFacade.getAllPokemonSpecies());
        return "pokemonSpecies/list";
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id,
        Model model,
        RedirectAttributes redirectAttributes,
        UriComponentsBuilder uriComponentsBuilder) {
        
        if (pokemonSpeciesFacade.findPokemonSpeciesById(id) == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "No pokemon species with id " + id + " exists");
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        model.addAttribute("pokemonSpecies", pokemonSpeciesFacade.findPokemonSpeciesById(id));
        return "pokemonSpecies/detail";
    }
    
    @RequestMapping(value = "/changeTyping/{id}", method = RequestMethod.GET)
    public String changeTyping(@PathVariable long id,
        Model model, 
        RedirectAttributes redirectAttributes,
        UriComponentsBuilder uriComponentsBuilder) {
        
        if (pokemonSpeciesFacade.findPokemonSpeciesById(id) == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "No pokemon species with id " + id + " exists");
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        model.addAttribute("pokemonSpeciesToUpdate", pokemonSpeciesFacade.findPokemonSpeciesById(id));
        return "pokemonSpecies/changeTyping";
    }

    @RequestMapping(value = "/changeTyping/{id}", method = RequestMethod.POST)
    public String changeTyping(@Valid @ModelAttribute("pokemonSpeciesToUpdate") ChangeTypingDTO pokemonSpeciesToUpdate,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes,
        UriComponentsBuilder uriComponentsBuilder,
        @PathVariable long id) {

        pokemonSpeciesToUpdate.setId(id);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                log.trace("ObjectError: {}", ge);
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "pokemonSpecies/changeTyping";
        }
        try {
            pokemonSpeciesFacade.changeTyping(pokemonSpeciesToUpdate);
        } catch (NoSuchEntityException ex) {
            java.util.logging.Logger.getLogger(PokemonSpeciesController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alert_warning", "No pokemon species with id " + pokemonSpeciesToUpdate.getId() + " exists");
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success", "Pokemon species was succesfully updated");
        return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
    }
    
    @RequestMapping(value = "/changePreevolution/{id}", method = RequestMethod.GET)
    public String changePreevolution(@PathVariable long id,
        Model model, 
        RedirectAttributes redirectAttributes,
        UriComponentsBuilder uriComponentsBuilder) {
        
        if (pokemonSpeciesFacade.findPokemonSpeciesById(id) == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "No pokemon species with id " + id + " exists");
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        model.addAttribute("pokemonSpeciesToUpdate", pokemonSpeciesFacade.findPokemonSpeciesById(id));
        return "pokemonSpecies/changePreevolution";
    }

    @RequestMapping(value = "/changePreevolution/{id}", method = RequestMethod.POST)
    public String changePreevolution(@Valid @ModelAttribute("pokemonSpeciesToUpdate") ChangePreevolutionDTO pokemonSpeciesToUpdate,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes,
        UriComponentsBuilder uriComponentsBuilder,
        @PathVariable long id) {
        
        pokemonSpeciesToUpdate.setId(id);

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                log.trace("ObjectError: {}", ge);
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "pokemonSpecies/changePreevolution";
        }
        try {
            pokemonSpeciesFacade.changePreevolution(pokemonSpeciesToUpdate);
        } catch (NoSuchEntityException ex) {
            java.util.logging.Logger.getLogger(PokemonSpeciesController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alert_danger", "No pokemon species with id " + pokemonSpeciesToUpdate.getId() + " exists");
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        } catch (CircularEvolutionChainException ex) {
            java.util.logging.Logger.getLogger(PokemonSpeciesController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alert_warning", "The choosen preevolution is circular");
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/changePreevolution/" + pokemonSpeciesToUpdate.getId()).build().encode().toUriString();
        } catch (EvolutionChainTooLongException ex) {            
            java.util.logging.Logger.getLogger(PokemonSpeciesController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alert_warning", "The choosen evolution chain is too long");
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/changePreevolution/" + pokemonSpeciesToUpdate.getId()).build().encode().toUriString();
        
        }
        redirectAttributes.addFlashAttribute("alert_success", "Pokemon species was succesfully updated");
        return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String createNewPokemonSpecies(Model model) {
        log.debug("createNewPokemonSpecies()");

        model.addAttribute("pokemonSpeciesCreate", new PokemonSpeciesCreateDTO());

        return "pokemonSpecies/create";
    }
    
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
        @Valid @ModelAttribute("pokemonSpeciesCreate") PokemonSpeciesCreateDTO formBean,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes,
        UriComponentsBuilder uriComponentsBuilder) {
        
        log.debug("create(formBean={})", formBean);
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
            return "dogs/create";
        }

        Long id = null;
        try {
            id = pokemonSpeciesFacade.createPokemonSpecies(formBean);
        } catch (EvolutionChainTooLongException ex) {
            java.util.logging.Logger.getLogger(PokemonSpeciesController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alert_warning", "The choosen evolution chain is too long");
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/create").build().encode().toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success", "Pokemon species with " + id + " was created successfuly.");
        return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").toUriString();
    }
    
    @ModelAttribute("allTypes")
    public PokemonType[] allTypes() {
        log.debug("allTypes()");
        return PokemonType.values();
    }
    
    @ModelAttribute("allSpecies")
    public List<PokemonSpeciesDTO> allSpecies() {
        log.debug("allSpecies()");
        return pokemonSpeciesFacade.getAllPokemonSpecies();
    }

}
