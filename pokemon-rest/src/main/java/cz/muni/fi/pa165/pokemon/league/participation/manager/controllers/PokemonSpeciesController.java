package cz.muni.fi.pa165.pokemon.league.participation.manager.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.ApiUris;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangePreevolutionDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeTypingDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.CircularEvolutionChainException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidPreevolutionChangeException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonSpeciesFacade;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This is the controller class of Pokemon Species to handle all the calls of rest api.
 * 
 * @author Tamás Rózsa 445653
 */
@RestController
@RequestMapping(ApiUris.POKEMON_SPECIES_URI)
public class PokemonSpeciesController {

    final static Logger logger = LoggerFactory.getLogger(PokemonSpeciesController.class);
    
    @Inject
    private PokemonSpeciesFacade pokemonSpeciesFacade;
    
    @RequestMapping(
            value = "/changeTyping",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void changeTyping(@Valid @RequestBody ChangeTypingDTO newTyping) {
        logger.debug("rest changeTyping({})", newTyping);
        try {
            pokemonSpeciesFacade.changeTyping(newTyping);
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
    
    @RequestMapping(
            value = "/changePreevlution",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void changePreevolution(@Valid @RequestBody ChangePreevolutionDTO newPreevolution) {
        logger.debug("rest changePreevolution({})", newPreevolution);
        try {
            pokemonSpeciesFacade.changePreevolution(newPreevolution);
        }
        catch(CircularEvolutionChainException | EvolutionChainTooLongException ex) {
            throw new InvalidPreevolutionChangeException();
        }
        catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
    
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE
    )
    public final void removePokemonSpecies(@PathVariable("id") long id) {
        logger.debug("rest removePokemonSpecies({})", id);
        try {
            pokemonSpeciesFacade.removePokemonSpecies(id);
        }
        catch(EntityIsUsedException ex) {
            throw new EntityUsedException();
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
    
    @RequestMapping(
            value = "/create",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public final PokemonSpeciesDTO createPokemonSpecies(@Valid @RequestBody PokemonSpeciesCreateDTO species) {
        logger.debug("rest createPokemonSpecies({})", species);
        try {
            Long id = pokemonSpeciesFacade.createPokemonSpecies(species);
            return pokemonSpeciesFacade.findPokemonSpeciesById(id);
        }
        catch(Exception ex) {
            throw new InvalidParameterException();
        }
    }
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public final PokemonSpeciesDTO findPokemonSpeciesById(@RequestBody long id) {
        logger.debug("rest findPokemonSpeciesById({})", id);
        try {
            return pokemonSpeciesFacade.findPokemonSpeciesById(id);
        }
        catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public final List<PokemonSpeciesDTO> getAllPokemonSpecies() {
        logger.debug("rest getAllPokemonSpecies()");
        try {
            return pokemonSpeciesFacade.getAllPokemonSpecies();
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
    
    @RequestMapping(
            value = "/evolutionsofpokemonspecies",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<PokemonSpeciesDTO> getAllEvolutionsOfPokemonSpecies(@RequestBody long speciesId) {
        logger.debug("rest getAllEvolutionsOfPokemonSpecies({})", speciesId);
        try {
            return pokemonSpeciesFacade.getAllEvolutionsOfPokemonSpecies(speciesId);
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
}
