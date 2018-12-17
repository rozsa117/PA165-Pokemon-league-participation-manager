package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonSpeciesFacade;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminPokemonSpeciesController.class);

    @Inject
    private PokemonSpeciesFacade pokemonSpeciesFacade;

    /**
     * Controller method for list all pokemon species.
     *
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        LOGGER.debug("mvc list()");
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

        LOGGER.debug("mvc detail({})", id);
        if (pokemonSpeciesFacade.findPokemonSpeciesById(id) == null) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon.species.singular"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemonSpecies/list").build().encode().toUriString();
        }
        model.addAttribute("pokemonSpecies", pokemonSpeciesFacade.findPokemonSpeciesById(id));
        return "pokemonSpecies/detail";
    }
}
