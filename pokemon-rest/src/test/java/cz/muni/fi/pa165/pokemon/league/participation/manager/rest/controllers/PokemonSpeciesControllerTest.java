/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.rest.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import org.mockito.Mock;

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
import org.mockito.Mockito;

public class PokemonSpeciesControllerTest extends AbstractTest {

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
        psCreateDTO.setPreevolutionId(raichuDTO.getEvolvesFrom().getId());

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
        psCreateDTO.setPreevolutionId(raichuDTO.getEvolvesFrom().getId());

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
        changePreevolutionDTO.setSpeciesId(rockDTO.getId());
        changePreevolutionDTO.setPreevolutionId(pikachuDTO.getId());

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
        changePreevolutionDTO.setSpeciesId(rockDTO.getId());
        changePreevolutionDTO.setPreevolutionId(rockDTO.getId());

        String inputJson = super.mapToJson(changePreevolutionDTO);
        
        doThrow(new EvolutionChainTooLongException()).when(pokemonSpeciesFacade).changePreevolution(changePreevolutionDTO);

        mvc.perform(MockMvcRequestBuilders.post(POKEMON_SPECIES_URI + "/changePreevolution")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotAcceptable());

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);
    }

    @Test
    public void changePreevolutionWithCircularEvolutionChainException() throws Exception {

        ChangePreevolutionDTO changePreevolutionDTO = new ChangePreevolutionDTO();
        changePreevolutionDTO.setSpeciesId(rockDTO.getId());
        changePreevolutionDTO.setPreevolutionId(rockDTO.getId());

        String inputJson = super.mapToJson(changePreevolutionDTO);
        
        doThrow(new CircularEvolutionChainException()).when(pokemonSpeciesFacade).changePreevolution(changePreevolutionDTO);

        mvc.perform(MockMvcRequestBuilders.post(POKEMON_SPECIES_URI + "/changePreevolution")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotAcceptable());

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);
    }

    @Test
    public void changePreevolutionWithNoSuchEntityException() throws Exception {

        ChangePreevolutionDTO changePreevolutionDTO = new ChangePreevolutionDTO();
        changePreevolutionDTO.setSpeciesId(rockDTO.getId());
        changePreevolutionDTO.setPreevolutionId(rockDTO.getId());

        String inputJson = super.mapToJson(changePreevolutionDTO);
        
        doThrow(new NoSuchEntityException()).when(pokemonSpeciesFacade).changePreevolution(changePreevolutionDTO);

        mvc.perform(MockMvcRequestBuilders.post(POKEMON_SPECIES_URI + "/changePreevolution")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);
    }

    
    @Test
    public void changeTyping() throws Exception {
        
        ChangeTypingDTO changeTypingDTO = new ChangeTypingDTO();
        
        changeTypingDTO.setSpeciesId(pikachuDTO.getId());
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
        
        doThrow(new EntityIsUsedException()).when(pokemonSpeciesFacade).removePokemonSpecies(1l);
        
        
        mvc.perform(MockMvcRequestBuilders.delete(POKEMON_SPECIES_URI + "/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotAcceptable());

        verify(pokemonSpeciesFacade, atLeastOnce()).removePokemonSpecies(1l);
    }

}
