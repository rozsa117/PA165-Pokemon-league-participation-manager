package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonSpeciesBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.PokemonDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.DataAccessException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidPokemonEvolutionException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.LevelNotIncreasedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

/**
 * Tests for Pokemon Service
 *
 * @author Michal Mokros 456442
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class PokemonServiceTest {

    @Mock
    private PokemonDAO pokemonDAO;

    @Inject
    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    public PokemonServiceTest() {
    }

    private Pokemon pokemonPikachu;
    private Pokemon pokemonCharizard;
    private Pokemon exceptionalPokemon;

    private PokemonSpecies pokemonSpeciesRaichu;
    private PokemonSpecies pokemonSpeciesPikachu;
    private PokemonSpecies pokemonSpeciesPichu;
    private PokemonSpecies pokemonSpeciesCharmander;
    private PokemonSpecies pokemonSpeciesCharmeleon;
    private PokemonSpecies pokemonSpeciesCharizard;

    private Trainer trainerAsh;
    private Trainer trainerBrock;

    private static final PersistenceException PE = new PersistenceException("This should be wrapped in DataAccessException");

    @Before
    public void setUp() {
        pokemonSpeciesPichu = new PokemonSpeciesBuilder()
                .id(9L)
                .speciesName("Pichu")
                .evolvesFrom(null)
                .primaryType(PokemonType.ELECTRIC)
                .secondaryType(PokemonType.NORMAL)
                .build();

        pokemonSpeciesPikachu = new PokemonSpeciesBuilder()
                .id(10L)
                .speciesName("Pikachu")
                .evolvesFrom(pokemonSpeciesPichu)
                .primaryType(PokemonType.ELECTRIC)
                .secondaryType(PokemonType.NORMAL)
                .build();

        pokemonSpeciesCharmander = new PokemonSpeciesBuilder()
                .id(11L)
                .speciesName("Charmander")
                .evolvesFrom(null)
                .primaryType(PokemonType.FIRE)
                .secondaryType(PokemonType.FIGHTING)
                .build();

        pokemonSpeciesCharmeleon = new PokemonSpeciesBuilder()
                .id(12L)
                .speciesName("Charmeleon")
                .evolvesFrom(pokemonSpeciesCharmander)
                .primaryType(PokemonType.FIRE)
                .secondaryType(PokemonType.FIGHTING)
                .build();

        pokemonSpeciesCharizard = new PokemonSpeciesBuilder()
                .id(13L)
                .speciesName("Charizard")
                .evolvesFrom(pokemonSpeciesCharmeleon)
                .primaryType(PokemonType.FIRE)
                .secondaryType(PokemonType.FLYING)
                .build();

        pokemonSpeciesRaichu = new PokemonSpeciesBuilder()
                .id(14L)
                .speciesName("Raichu")
                .evolvesFrom(pokemonSpeciesPikachu)
                .primaryType(PokemonType.ELECTRIC)
                .secondaryType(PokemonType.DARK)
                .build();

        trainerAsh = new TrainerBuilder()
                .id(40L)
                .born(LocalDate.of(1997, 7, 11))
                .isAdmin(false)
                .name("Ash")
                .passwordHash("asdjahdkjfhs12312")
                .surname("Ketchup")
                .userName("Ash97")
                .build();

        trainerBrock = new TrainerBuilder()
                .id(41L)
                .born(LocalDate.of(1996, 8, 12))
                .isAdmin(false)
                .name("Brock")
                .passwordHash("adj12jndakj3")
                .surname("Noeye")
                .userName("Brocky")
                .build();

        pokemonPikachu = new PokemonBuilder()
                .id(100L)
                .level(40)
                .nickname("Pikachuchu")
                .trainer(trainerAsh)
                .pokemonSpecies(pokemonSpeciesPikachu)
                .dateTimeOfCapture(LocalDateTime.of(2018, 10, 11, 5, 30))
                .build();

        pokemonCharizard = new PokemonBuilder()
                .id(101L)
                .level(95)
                .nickname("Charry")
                .trainer(trainerAsh)
                .pokemonSpecies(pokemonSpeciesCharizard)
                .dateTimeOfCapture(LocalDateTime.of(2018, 2, 2, 14, 23))
                .build();

        exceptionalPokemon = new PokemonBuilder()
                .id(102L)
                .level(10)
                .nickname("Hacked Pokemon")
                .trainer(trainerBrock)
                .pokemonSpecies(pokemonSpeciesPichu)
                .dateTimeOfCapture(LocalDateTime.of(2018, 12, 1, 22, 48))
                .build();

        when(pokemonDAO.findPokemonById(pokemonPikachu.getId())).thenReturn(pokemonPikachu);
        when(pokemonDAO.findPokemonById(pokemonCharizard.getId())).thenReturn(pokemonCharizard);
        when(pokemonDAO.findPokemonById(exceptionalPokemon.getId())).thenThrow(PE);
        when(pokemonDAO.getPokemonOfTrainer(trainerAsh)).thenReturn(Arrays.asList(pokemonPikachu, pokemonCharizard));
        when(pokemonDAO.getAllPokemonOfSpecies(pokemonSpeciesPikachu)).thenReturn(Collections.singletonList(pokemonPikachu));
        when(pokemonDAO.getAllPokemonOfSpecies(pokemonSpeciesCharizard)).thenReturn(Collections.singletonList(pokemonCharizard));
        when(pokemonDAO.getAllPokemonOfSpecies(pokemonSpeciesCharmander)).thenReturn(new ArrayList<>());
        when(pokemonDAO.getAllPokemonOfSpecies(pokemonSpeciesCharmeleon)).thenReturn(new ArrayList<>());
        when(pokemonDAO.getPokemonOfTrainer(trainerBrock)).thenThrow(PE);
        doThrow(PE).when(pokemonDAO).createPokemon(exceptionalPokemon);
        doThrow(PE).when(pokemonDAO).updatePokemon(exceptionalPokemon);
        doThrow(PE).when(pokemonDAO).deletePokemon(exceptionalPokemon);
    }

    @Test
    public void testCreatePokemon() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonService.createPokemon(exceptionalPokemon));
        pokemonService.createPokemon(pokemonPikachu);
        verify(pokemonDAO, atLeastOnce()).createPokemon(pokemonPikachu);
    }

    @Test
    public void testRenamePokemon() {
        pokemonService.renamePokemon(pokemonPikachu, "renamedPokemon");
        assertThat(pokemonPikachu.getNickname()).isEqualTo("renamedPokemon");
        verify(pokemonDAO, atLeastOnce()).updatePokemon(pokemonPikachu);
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonService.renamePokemon(exceptionalPokemon, "OP"));
    }

    @Test
    public void testIncreasePokemonLevel() throws LevelNotIncreasedException {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonService.increasePokemonLevel(exceptionalPokemon, 99));
        assertThatExceptionOfType(LevelNotIncreasedException.class)
                .isThrownBy(() -> pokemonService.increasePokemonLevel(pokemonPikachu, 1));

        pokemonService.increasePokemonLevel(pokemonPikachu, 80);
        assertThat(pokemonPikachu.getLevel()).isEqualTo(80);
        verify(pokemonDAO, atLeastOnce()).updatePokemon(pokemonPikachu);
    }

    @Test
    public void evolvePokemonTest() throws InvalidPokemonEvolutionException {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonService.evolvePokemon(exceptionalPokemon, pokemonSpeciesPikachu));
        assertThatExceptionOfType(InvalidPokemonEvolutionException.class)
                .isThrownBy(() -> pokemonService.evolvePokemon(pokemonPikachu, pokemonSpeciesCharmander));

        pokemonService.evolvePokemon(pokemonPikachu, pokemonSpeciesRaichu);
        assertThat(pokemonPikachu.getSpecies()).isEqualToComparingFieldByField(pokemonSpeciesRaichu);
        verify(pokemonDAO, atLeastOnce()).updatePokemon(pokemonPikachu);
    }

    @Test
    public void releasePokemonTest() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonService.releasePokemon(exceptionalPokemon));
        pokemonService.releasePokemon(pokemonPikachu);
        verify(pokemonDAO, atLeastOnce()).deletePokemon(pokemonPikachu);
    }

    @Test
    public void findPokemonByIdTest() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonService.findPokemonById(exceptionalPokemon.getId()));
        assertThat(pokemonService.findPokemonById(pokemonPikachu.getId()))
                .isEqualTo(pokemonPikachu);
    }

    @Test
    public void giftPokemonTest() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonService.giftPokemon(exceptionalPokemon, trainerAsh));
        pokemonService.giftPokemon(pokemonPikachu, trainerBrock);
        assertThat(pokemonPikachu.getTrainer()).isEqualTo(trainerBrock);
        verify(pokemonDAO, atLeastOnce()).updatePokemon(pokemonPikachu);
    }

    @Test
    public void getPokemonOfTrainerTest() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonService.getPokemonOfTrainer(trainerBrock));
        assertThat(pokemonService.getPokemonOfTrainer(trainerAsh))
                .containsOnly(pokemonPikachu, pokemonCharizard);
    }
}
