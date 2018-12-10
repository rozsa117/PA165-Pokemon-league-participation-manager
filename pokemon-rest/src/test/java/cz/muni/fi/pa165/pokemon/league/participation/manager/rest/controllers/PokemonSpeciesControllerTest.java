/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.rest.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;

import static cz.muni.fi.pa165.pokemon.league.participation.manager.ApiUris.*;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangePreevolutionDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeTypingDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.CircularEvolutionChainException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonSpeciesFacade;
import java.util.Arrays;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.BeforeClass;

/**
 * This is the controller class of Pokemon Species test
 *
 * @author Jiří Medveď 38451
 */
public class PokemonSpeciesControllerTest extends AbstractTest {

    private static final String CAUSE_MESSAGE = "Cause text";
    private static final Exception causeEx = new Exception(CAUSE_MESSAGE);

    private static final PokemonSpeciesDTO pikachuDTO = new PokemonSpeciesDTO();
    private static final PokemonSpeciesDTO raichuDTO = new PokemonSpeciesDTO();
    private static final PokemonSpeciesDTO rockDTO = new PokemonSpeciesDTO();

    @MockBean
    private PokemonSpeciesFacade pokemonSpeciesFacade;

    @BeforeClass
    public static void setUpClass() {

        pikachuDTO.setId(1L);
        pikachuDTO.setSpeciesName("Electric");
        pikachuDTO.setPrimaryType(PokemonType.ELECTRIC);

        raichuDTO.setId(2L);
        raichuDTO.setSpeciesName("Electric");
        raichuDTO.setPrimaryType(PokemonType.ELECTRIC);
        raichuDTO.setEvolvesFrom(pikachuDTO);

        rockDTO.setId(3l);
        rockDTO.setSpeciesName("Rock");
        rockDTO.setPrimaryType(PokemonType.ROCK);
        rockDTO.setSecondaryType(PokemonType.GROUND);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getPokemonSpeciesList() throws Exception {

        when(pokemonSpeciesFacade.getAllPokemonSpecies()).thenReturn(Arrays.asList(pikachuDTO, raichuDTO, rockDTO));

        mvc.perform(MockMvcRequestBuilders.get(POKEMON_SPECIES_URI)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(pikachuDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].speciesName", is(pikachuDTO.getSpeciesName())));

        verify(pokemonSpeciesFacade, atLeastOnce()).getAllPokemonSpecies();

    }

    @Test
    public void findPokemonSpeciesById() throws Exception {

        when(pokemonSpeciesFacade.findPokemonSpeciesById(pikachuDTO.getId())).thenReturn(pikachuDTO);

        mvc.perform(MockMvcRequestBuilders.get(POKEMON_SPECIES_URI + "/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("speciesName", is(pikachuDTO.getSpeciesName())));

        verify(pokemonSpeciesFacade, atLeastOnce()).findPokemonSpeciesById(pikachuDTO.getId());

    }

    @Test
    public void createPokemonSpecies() throws Exception {

        PokemonSpeciesCreateDTO psCreateDTO = new PokemonSpeciesCreateDTO();
        psCreateDTO.setSpeciesName(raichuDTO.getSpeciesName());
        psCreateDTO.setPrimaryType(raichuDTO.getPrimaryType());
        psCreateDTO.setSecondaryType(raichuDTO.getSecondaryType());
        psCreateDTO.setEvolvesFromId(raichuDTO.getEvolvesFrom().getId());

        when(pokemonSpeciesFacade.createPokemonSpecies(psCreateDTO)).thenReturn(raichuDTO.getId());
        when(pokemonSpeciesFacade.findPokemonSpeciesById(raichuDTO.getId())).thenReturn(raichuDTO);

        String inputJson = super.mapToJson(psCreateDTO);

        mvc.perform(MockMvcRequestBuilders.post(POKEMON_SPECIES_URI + "/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(raichuDTO.getId().intValue())));
//        String contentAsString = result.getResponse().getContentAsString();

        verify(pokemonSpeciesFacade, atLeastOnce()).createPokemonSpecies(psCreateDTO);
    }

    @Test
    public void createPokemonSpeciesWithEvolutionChainTooLongException() throws Exception {

        PokemonSpeciesCreateDTO psCreateDTO = new PokemonSpeciesCreateDTO();
        psCreateDTO.setSpeciesName(raichuDTO.getSpeciesName());
        psCreateDTO.setPrimaryType(raichuDTO.getPrimaryType());
        psCreateDTO.setSecondaryType(raichuDTO.getSecondaryType());
        psCreateDTO.setEvolvesFromId(raichuDTO.getEvolvesFrom().getId());

        when(pokemonSpeciesFacade.createPokemonSpecies(psCreateDTO)).thenThrow(new EvolutionChainTooLongException());

        String inputJson = super.mapToJson(psCreateDTO);

        mvc.perform(MockMvcRequestBuilders.post(POKEMON_SPECIES_URI + "/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotAcceptable());

        verify(pokemonSpeciesFacade, atLeastOnce()).createPokemonSpecies(psCreateDTO);
    }

    @Test
    public void changePreevolution() throws Exception {

        ChangePreevolutionDTO changePreevolutionDTO = new ChangePreevolutionDTO();
        changePreevolutionDTO.setId(rockDTO.getId());
        changePreevolutionDTO.setEvolvesFrom(pikachuDTO.getId());

        String inputJson = super.mapToJson(changePreevolutionDTO);

        mvc.perform(MockMvcRequestBuilders.post(POKEMON_SPECIES_URI + "/changePreevolution")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);
    }

    @Test
    public void changePreevolutionWithEvolutionChainTooLongException() throws Exception {

        ChangePreevolutionDTO changePreevolutionDTO = new ChangePreevolutionDTO();
        changePreevolutionDTO.setId(rockDTO.getId());
        changePreevolutionDTO.setEvolvesFrom(rockDTO.getId());

        String inputJson = super.mapToJson(changePreevolutionDTO);

        doThrow(new EvolutionChainTooLongException(causeEx.getMessage(), causeEx))
                .when(pokemonSpeciesFacade).changePreevolution(changePreevolutionDTO);

        mvc.perform(MockMvcRequestBuilders.post(POKEMON_SPECIES_URI + "/changePreevolution")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("error", is(CAUSE_MESSAGE)));

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);
    }

    @Test
    public void changePreevolutionWithCircularEvolutionChainException() throws Exception {

        ChangePreevolutionDTO changePreevolutionDTO = new ChangePreevolutionDTO();
        changePreevolutionDTO.setId(rockDTO.getId());
        changePreevolutionDTO.setEvolvesFrom(rockDTO.getId());

        String inputJson = super.mapToJson(changePreevolutionDTO);

        doThrow(new CircularEvolutionChainException(causeEx.getMessage(), causeEx))
                .when(pokemonSpeciesFacade).changePreevolution(changePreevolutionDTO);

        mvc.perform(MockMvcRequestBuilders.post(POKEMON_SPECIES_URI + "/changePreevolution")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("error", is(CAUSE_MESSAGE)));

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);
    }

    @Test
    public void changePreevolutionWithNoSuchEntityException() throws Exception {

        ChangePreevolutionDTO changePreevolutionDTO = new ChangePreevolutionDTO();
        changePreevolutionDTO.setId(rockDTO.getId());
        changePreevolutionDTO.setEvolvesFrom(rockDTO.getId());

        String inputJson = super.mapToJson(changePreevolutionDTO);

        doThrow(new NoSuchEntityException(causeEx.getMessage(), causeEx))
                .when(pokemonSpeciesFacade).changePreevolution(changePreevolutionDTO);

        mvc.perform(MockMvcRequestBuilders.post(POKEMON_SPECIES_URI + "/changePreevolution")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error", is(CAUSE_MESSAGE)));

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);
    }

    @Test
    public void changeTyping() throws Exception {

        ChangeTypingDTO changeTypingDTO = new ChangeTypingDTO();

        changeTypingDTO.setId(pikachuDTO.getId());
        changeTypingDTO.setPrimaryType(PokemonType.FIRE);
        changeTypingDTO.setSecondaryType(PokemonType.DARK);

        String inputJson = super.mapToJson(changeTypingDTO);

        mvc.perform(MockMvcRequestBuilders.post(POKEMON_SPECIES_URI + "/changeTyping")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(pokemonSpeciesFacade, atLeastOnce()).changeTyping(changeTypingDTO);
    }

    @Test
    public void removePokemonSpecies() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete(POKEMON_SPECIES_URI + "/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(pokemonSpeciesFacade, atLeastOnce()).removePokemonSpecies(1l);
    }

    @Test
    public void removePokemonSpeciesWithEntityIsUsedException() throws Exception {

        doThrow(new EntityIsUsedException(causeEx.getMessage(), causeEx))
                .when(pokemonSpeciesFacade).removePokemonSpecies(1l);

        mvc.perform(MockMvcRequestBuilders.delete(POKEMON_SPECIES_URI + "/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("error", is(CAUSE_MESSAGE)));

        verify(pokemonSpeciesFacade, atLeastOnce()).removePokemonSpecies(1l);
    }

    @Test
    public void getAllEvolutionsOfPokemonSpecies() throws Exception {

        when(pokemonSpeciesFacade.getAllEvolutionsOfPokemonSpecies(pikachuDTO.getId()))
                .thenReturn(Arrays.asList(raichuDTO));

        mvc.perform(MockMvcRequestBuilders.get(POKEMON_SPECIES_URI + "/1/allEvolutions")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(raichuDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].speciesName", is(raichuDTO.getSpeciesName())));

        verify(pokemonSpeciesFacade, atLeastOnce())
                .getAllEvolutionsOfPokemonSpecies(pikachuDTO.getId());

    }

}
