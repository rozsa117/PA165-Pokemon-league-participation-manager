package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonSpeciesFacade;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    PokemonSpeciesFacade pokemonSpeceisFacade;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("allPokemonSpecies", pokemonSpeceisFacade.getAllPokemonSpecies());
        return "pokemonSpecies/list";
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("pokemonSpecies", pokemonSpeceisFacade.findPokemonSpeciesById(id));
        return "pokemonSpecies/detail";
    }
}
