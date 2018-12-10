package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeTypingDTO;
import org.junit.Before;
import org.junit.Test;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonSpeciesFacade;
import java.util.Arrays;
import java.util.Map;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.BeforeClass;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import org.springframework.util.MultiValueMap;

/**
 * This is the controller class of Pokemon Species test
 *
 * @author Jiří Medveď 38451
 */
public class PokemonSpeciesControllerTest extends AbstractTest {

    private static final Long NON_EXISTENT_ID = 1000L;

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

    private final static String POKEMON_SPECIES_URI = "/pokemonSpecies";

    @Override
    @Before
    public void setUp() {
        super.setUp();

        when(pokemonSpeciesFacade.findPokemonSpeciesById(pikachuDTO.getId())).thenReturn(pikachuDTO);
        when(pokemonSpeciesFacade.findPokemonSpeciesById(raichuDTO.getId())).thenReturn(raichuDTO);
        when(pokemonSpeciesFacade.findPokemonSpeciesById(rockDTO.getId())).thenReturn(rockDTO);
    }

    @Test
    public void listTest() throws Exception {

        when(pokemonSpeciesFacade.getAllPokemonSpecies()).thenReturn(Arrays.asList(pikachuDTO, raichuDTO, rockDTO));

        mvc.perform(MockMvcRequestBuilders.get(POKEMON_SPECIES_URI + "/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("pokemonSpecies/list"))
                .andExpect(forwardedUrl("pokemonSpecies/list"))
                .andExpect(model().attribute("allPokemonSpecies", hasSize(3)))
                .andExpect(model().attribute("allPokemonSpecies", hasItem(
                        allOf(
                                hasProperty("id", is(pikachuDTO.getId())),
                                hasProperty("primaryType", is(pikachuDTO.getPrimaryType())),
                                hasProperty("secondaryType", is(pikachuDTO.getSecondaryType())),
                                hasProperty("evolvesFrom", is(pikachuDTO.getEvolvesFrom()))
                        )
                )))
                .andExpect(model().attribute("allPokemonSpecies", hasItem(
                        allOf(
                                hasProperty("id", is(raichuDTO.getId())),
                                hasProperty("primaryType", is(raichuDTO.getPrimaryType())),
                                hasProperty("secondaryType", is(raichuDTO.getSecondaryType())),
                                hasProperty("evolvesFrom", is(raichuDTO.getEvolvesFrom()))
                        )
                )))
                .andExpect(model().attribute("allPokemonSpecies", hasItem(
                        allOf(
                                hasProperty("id", is(rockDTO.getId())),
                                hasProperty("primaryType", is(rockDTO.getPrimaryType())),
                                hasProperty("secondaryType", is(rockDTO.getSecondaryType())),
                                hasProperty("evolvesFrom", is(rockDTO.getEvolvesFrom()))
                        )
                )));

        verify(pokemonSpeciesFacade, atLeastOnce()).getAllPokemonSpecies();

    }

    @Test
    public void detailTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(POKEMON_SPECIES_URI + "/detail/" + raichuDTO.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("pokemonSpecies/detail"))
                .andExpect(forwardedUrl("pokemonSpecies/detail"))
                .andExpect(model().attribute("pokemonSpecies", notNullValue()))
                .andExpect(model().attribute("pokemonSpecies",
                        allOf(
                                hasProperty("id", is(raichuDTO.getId())),
                                hasProperty("primaryType", is(raichuDTO.getPrimaryType())),
                                hasProperty("secondaryType", is(raichuDTO.getSecondaryType())),
                                hasProperty("evolvesFrom", is(raichuDTO.getEvolvesFrom()))
                        )
                ));

    }

    @Test
    public void detailNonExistentPokemonSpeciesTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(POKEMON_SPECIES_URI + "/detail/" + NON_EXISTENT_ID.toString()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/list"))
                .andExpect(flash().attribute("alert_danger", notNullValue()));

    }

    @Test
    public void changeTypingGetTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(POKEMON_SPECIES_URI + "/changeTyping/" + pikachuDTO.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("pokemonSpecies/changeTyping"))
                .andExpect(forwardedUrl("pokemonSpecies/changeTyping"))
                .andExpect(model().attribute("pokemonSpeciesToUpdate", notNullValue()))
                .andExpect(model().attribute("pokemonSpeciesToUpdate",
                        allOf(
                                hasProperty("id", is(pikachuDTO.getId())),
                                hasProperty("primaryType", is(pikachuDTO.getPrimaryType())),
                                hasProperty("secondaryType", is(pikachuDTO.getSecondaryType())),
                                hasProperty("evolvesFrom", is(pikachuDTO.getEvolvesFrom()))
                        )));

    }

    @Test
    public void changeTypingGetNonExistentPokemonSpeciesTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(POKEMON_SPECIES_URI + "/changeTyping/" + NON_EXISTENT_ID.toString()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/list"))
                .andExpect(flash().attribute("alert_danger", notNullValue()));

    }

    @Test
    public void changeTypingPostTest() throws Exception {

        ChangeTypingDTO changeTypingDTO = new ChangeTypingDTO();

        changeTypingDTO.setId(pikachuDTO.getId());
        changeTypingDTO.setPrimaryType(PokemonType.FIRE);
        changeTypingDTO.setSecondaryType(PokemonType.DARK);

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/changeTyping/" + pikachuDTO.getId().toString())
                .param("primaryType", changeTypingDTO.getPrimaryType().toString())
                .param("secondaryType", changeTypingDTO.getSecondaryType().toString())
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/list"))
                .andExpect(flash().attribute("alert_danger", nullValue()))
                .andExpect(flash().attribute("alert_warning", nullValue()))
                .andExpect(flash().attribute("alert_success", notNullValue()));

        verify(pokemonSpeciesFacade, atLeastOnce()).changeTyping(changeTypingDTO);

    }

}
