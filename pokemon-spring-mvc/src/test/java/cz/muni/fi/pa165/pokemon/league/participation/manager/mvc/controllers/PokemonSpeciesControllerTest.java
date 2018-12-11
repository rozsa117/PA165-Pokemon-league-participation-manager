package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangePreevolutionDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeTypingDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesCreateDTO;
import org.junit.Before;
import org.junit.Test;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.CircularEvolutionChainException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonSpeciesFacade;
import java.util.Arrays;
import java.util.Map;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.BeforeClass;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;

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
        when(pokemonSpeciesFacade.getAllPokemonSpecies()).thenReturn(Arrays.asList(pikachuDTO, raichuDTO, rockDTO));
    }

    @Test
    public void listTest() throws Exception {

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
                .post(POKEMON_SPECIES_URI + "/changeTyping/"
                        + changeTypingDTO.getId().toString())
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

    @Test
    public void changeTypingPostNonExistentPokemonSpeciesTest() throws Exception {

        ChangeTypingDTO changeTypingDTO = new ChangeTypingDTO();

        changeTypingDTO.setId(NON_EXISTENT_ID);
        changeTypingDTO.setPrimaryType(PokemonType.FIRE);
        changeTypingDTO.setSecondaryType(PokemonType.DARK);

        doThrow(new NoSuchEntityException())
                .when(pokemonSpeciesFacade).changeTyping(changeTypingDTO);

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/changeTyping/" + NON_EXISTENT_ID.toString())
                .param("primaryType", changeTypingDTO.getPrimaryType().toString())
                .param("secondaryType", changeTypingDTO.getSecondaryType().toString())
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/list"))
                .andExpect(flash().attribute("alert_danger", notNullValue()))
                .andExpect(flash().attribute("alert_warning", nullValue()))
                .andExpect(flash().attribute("alert_success", nullValue()));

        verify(pokemonSpeciesFacade, atLeastOnce()).changeTyping(changeTypingDTO);

    }

    @Test
    public void changeTypingPostInvalidPrimaryTypeTest() throws Exception {

        String INVALID_TYPE = "XXXX";

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/changeTyping/" + pikachuDTO.getId().toString())
                .param("primaryType", INVALID_TYPE)
                .param("secondaryType", PokemonType.DARK.toString())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("pokemonSpecies/changeTyping"))
                .andExpect(forwardedUrl("pokemonSpecies/changeTyping"))
                .andExpect(model().attribute("primaryType_error", is(true)));
    }

    @Test
    public void changeTypingPostInvalidSecondaryTypeTest() throws Exception {

        String INVALID_TYPE = "XXXX";

//        ChangeTypingDTO changeTypingDTO = new ChangeTypingDTO();
        //       changeTypingDTO.setId(pikachuDTO.getId());
        //       changeTypingDTO.setPrimaryType(null);
        //       changeTypingDTO.setSecondaryType(PokemonType.DARK);
        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/changeTyping/" + pikachuDTO.getId().toString())
                .param("primaryType", PokemonType.DARK.toString())
                .param("secondaryType", INVALID_TYPE)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("pokemonSpecies/changeTyping"))
                .andExpect(forwardedUrl("pokemonSpecies/changeTyping"))
                .andExpect(model().attribute("secondaryType_error", is(true)));
    }

    @Test
    public void changePreevolutionGetTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(POKEMON_SPECIES_URI + "/changePreevolution/" + pikachuDTO.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("pokemonSpecies/changePreevolution"))
                .andExpect(forwardedUrl("pokemonSpecies/changePreevolution"))
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
    public void changePreevolutionGetNonExistentPokemonSpeciesTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(POKEMON_SPECIES_URI + "/changePreevolution/" + NON_EXISTENT_ID.toString()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/list"))
                .andExpect(flash().attribute("alert_danger", notNullValue()));

    }

    @Test
    public void changePreevolutionPostTest() throws Exception {

        ChangePreevolutionDTO changePreevolutionDTO = new ChangePreevolutionDTO();
        changePreevolutionDTO.setId(rockDTO.getId());
        changePreevolutionDTO.setEvolvesFrom(pikachuDTO.getId());

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/changePreevolution/"
                        + changePreevolutionDTO.getId().toString())
                .param("evolvesFrom", changePreevolutionDTO
                        .getEvolvesFrom().toString())
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/list"))
                .andExpect(flash().attribute("alert_danger", nullValue()))
                .andExpect(flash().attribute("alert_warning", nullValue()))
                .andExpect(flash().attribute("alert_success", notNullValue()));

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);

    }

    @Test
    public void changePreevolutionPostNonExistentPokemonSpeciesTest() throws Exception {

        ChangePreevolutionDTO changePreevolutionDTO = new ChangePreevolutionDTO();
        changePreevolutionDTO.setId(NON_EXISTENT_ID);
        changePreevolutionDTO.setEvolvesFrom(pikachuDTO.getId());

        doThrow(new NoSuchEntityException())
                .when(pokemonSpeciesFacade).changePreevolution(changePreevolutionDTO);

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/changePreevolution/"
                        + changePreevolutionDTO.getId().toString())
                .param("evolvesFrom", changePreevolutionDTO
                        .getEvolvesFrom().toString())
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/list"))
                .andExpect(flash().attribute("alert_danger", notNullValue()))
                .andExpect(flash().attribute("alert_warning", nullValue()))
                .andExpect(flash().attribute("alert_success", nullValue()));

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);

    }

    @Test
    public void changePreevolutionPostCircularEvolutionTest() throws Exception {

        ChangePreevolutionDTO changePreevolutionDTO = new ChangePreevolutionDTO();
        changePreevolutionDTO.setId(pikachuDTO.getId());
        changePreevolutionDTO.setEvolvesFrom(pikachuDTO.getId());

        doThrow(new CircularEvolutionChainException())
                .when(pokemonSpeciesFacade).changePreevolution(changePreevolutionDTO);

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/changePreevolution/"
                        + changePreevolutionDTO.getId().toString())
                .param("evolvesFrom", changePreevolutionDTO
                        .getEvolvesFrom().toString())
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/changePreevolution/"
                        + changePreevolutionDTO.getId().toString()))
                .andExpect(flash().attribute("alert_danger", nullValue()))
                .andExpect(flash().attribute("alert_warning", notNullValue()))
                .andExpect(flash().attribute("alert_success", nullValue()));

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);

    }

    @Test
    public void changePreevolutionPostEvolutionChainTooLongTest() throws Exception {

        ChangePreevolutionDTO changePreevolutionDTO = new ChangePreevolutionDTO();
        changePreevolutionDTO.setId(pikachuDTO.getId());
        changePreevolutionDTO.setEvolvesFrom(pikachuDTO.getId());

        doThrow(new EvolutionChainTooLongException())
                .when(pokemonSpeciesFacade).changePreevolution(changePreevolutionDTO);

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/changePreevolution/"
                        + changePreevolutionDTO.getId().toString())
                .param("evolvesFrom", changePreevolutionDTO
                        .getEvolvesFrom().toString())
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/changePreevolution/"
                        + changePreevolutionDTO.getId().toString()))
                .andExpect(flash().attribute("alert_danger", nullValue()))
                .andExpect(flash().attribute("alert_warning", notNullValue()))
                .andExpect(flash().attribute("alert_success", nullValue()));

        verify(pokemonSpeciesFacade, atLeastOnce()).changePreevolution(changePreevolutionDTO);

    }

    @Test
    public void newTest() throws Exception {
        
        mvc.perform(MockMvcRequestBuilders
                .get(POKEMON_SPECIES_URI + "/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pokemonSpecies/create"))
                .andExpect(forwardedUrl("pokemonSpecies/create"))
                .andExpect(model().attribute("pokemonSpeciesCreate", notNullValue()))
                .andExpect(model().attribute("allSpecies", hasSize(greaterThan(0))))
                .andExpect(model().attribute("allTypes", is(PokemonType.values())))
                ;

    }

    @Test
    public void createTest() throws Exception {

        PokemonSpeciesCreateDTO psCreateDTO = new PokemonSpeciesCreateDTO();

        psCreateDTO.setSpeciesName(raichuDTO.getSpeciesName());
        psCreateDTO.setPrimaryType(raichuDTO.getPrimaryType());
        psCreateDTO.setSecondaryType(raichuDTO.getSecondaryType());
        psCreateDTO.setEvolvesFromId(raichuDTO.getEvolvesFrom().getId());

        when(pokemonSpeciesFacade.createPokemonSpecies(psCreateDTO)).thenReturn(raichuDTO.getId());

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/create")
                .param("speciesName", psCreateDTO
                        .getSpeciesName().toString())
                .param("primaryType", psCreateDTO
                        .getPrimaryType().toString())
                .param("secondaryType",
                        (psCreateDTO.getSecondaryType() != null
                        ? psCreateDTO.getSecondaryType().toString()
                        : null))
                .param("evolvesFromId", psCreateDTO
                        .getEvolvesFromId().toString())
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/list"))
                .andExpect(flash().attribute("alert_danger", nullValue()))
                .andExpect(flash().attribute("alert_warning", nullValue()))
                .andExpect(flash().attribute("alert_success", notNullValue()));

        verify(pokemonSpeciesFacade, atLeastOnce()).createPokemonSpecies(psCreateDTO);

    }

    @Test
    public void createInvalidPrimaryTypeTest() throws Exception {

        String INVALID_TYPE = "XXXX";

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/create")
                .param("primaryType", INVALID_TYPE)
                .param("secondaryType", "")
                .param("evolvesFromId", "1")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("pokemonSpecies/create"))
                .andExpect(forwardedUrl("pokemonSpecies/create"))
                .andExpect(model().attribute("primaryType_error", is(true)));

    }

    @Test
    public void createInvalidSecondaryTypeTest() throws Exception {

        String INVALID_TYPE = "XXXX";

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/create")
                .param("primaryType", PokemonType.FIRE.toString())
                .param("secondaryType", INVALID_TYPE)
                .param("evolvesFromId", "1")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("pokemonSpecies/create"))
                .andExpect(forwardedUrl("pokemonSpecies/create"))
                .andExpect(model().attribute("secondaryType_error", is(true)));

    }

    @Test
    public void createEvolutionChainTooLongTest() throws Exception {

        PokemonSpeciesCreateDTO psCreateDTO = new PokemonSpeciesCreateDTO();

        psCreateDTO.setSpeciesName(raichuDTO.getSpeciesName());
        psCreateDTO.setPrimaryType(raichuDTO.getPrimaryType());
        psCreateDTO.setSecondaryType(raichuDTO.getSecondaryType());
        psCreateDTO.setEvolvesFromId(raichuDTO.getEvolvesFrom().getId());

        doThrow(new EvolutionChainTooLongException())
                .when(pokemonSpeciesFacade).createPokemonSpecies(psCreateDTO);

        mvc.perform(MockMvcRequestBuilders
                .post(POKEMON_SPECIES_URI + "/create")
                .param("speciesName", psCreateDTO
                        .getSpeciesName().toString())
                .param("primaryType", psCreateDTO
                        .getPrimaryType().toString())
                .param("secondaryType",
                        (psCreateDTO.getSecondaryType() != null
                        ? psCreateDTO.getSecondaryType().toString()
                        : ""))
                .param("evolvesFromId", psCreateDTO
                        .getEvolvesFromId().toString())
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/pokemonSpecies/create"))
                .andExpect(flash().attribute("alert_danger", nullValue()))
                .andExpect(flash().attribute("alert_warning", notNullValue()))
                .andExpect(flash().attribute("alert_success", nullValue()));

    }

}
