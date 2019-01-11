package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonSpeciesBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.PokemonDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.PokemonSpeciesDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.CircularEvolutionChainException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.DataAccessException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

/**
 * Tests for Pokemon Species Service
 *
 * @author Michal Mokros 456442
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class PokemonSpeciesServiceTest {

    @Mock
    private PokemonSpeciesDAO pokemonSpeciesDAO;

    @Mock
    private PokemonDAO pokemonDAO;

    @Inject
    @InjectMocks
    private PokemonSpeciesServiceImpl pokemonSpeciesService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    public PokemonSpeciesServiceTest() {
    }

    private PokemonSpecies pokemonSpeciesPichu;
    private PokemonSpecies pokemonSpeciesPikachu;
    private PokemonSpecies pokemonSpeciesRaichu;
    private PokemonSpecies pokemonSpeciesPreevolutionOne;
    private PokemonSpecies pokemonSpeciesPreevolutionTwo;
    private PokemonSpecies exceptionalPokemonSpecies;

    private static final PersistenceException PE = new PersistenceException("This should be wrapped in DataAccessException");

    @Before
    public void setUp() {
        pokemonSpeciesPichu = new PokemonSpeciesBuilder()
                .id(10L)
                .speciesName("Pichu")
                .evolvesFrom(null)
                .primaryType(PokemonType.ELECTRIC)
                .secondaryType(PokemonType.NORMAL)
                .build();

        pokemonSpeciesPikachu = new PokemonSpeciesBuilder()
                .id(11L)
                .speciesName("Pikachu")
                .evolvesFrom(pokemonSpeciesPichu)
                .primaryType(PokemonType.ELECTRIC)
                .secondaryType(PokemonType.FIGHTING)
                .build();

        pokemonSpeciesRaichu = new PokemonSpeciesBuilder()
                .id(12L)
                .speciesName("Raichu")
                .evolvesFrom(pokemonSpeciesPikachu)
                .primaryType(PokemonType.ELECTRIC)
                .secondaryType(PokemonType.FLYING)
                .build();

        exceptionalPokemonSpecies = new PokemonSpeciesBuilder()
                .id(14L)
                .speciesName("Broken Species")
                .evolvesFrom(null)
                .primaryType(PokemonType.GHOST)
                .secondaryType(PokemonType.FAIRY)
                .build();

        pokemonSpeciesPreevolutionOne = new PokemonSpeciesBuilder()
                .id(20L)
                .speciesName("Preev")
                .evolvesFrom(pokemonSpeciesRaichu)
                .primaryType(PokemonType.ELECTRIC)
                .secondaryType(PokemonType.FIGHTING)
                .build();

        pokemonSpeciesPreevolutionTwo = new PokemonSpeciesBuilder()
                .id(21L)
                .speciesName("Preev2")
                .evolvesFrom(pokemonSpeciesPreevolutionOne)
                .primaryType(PokemonType.ELECTRIC)
                .secondaryType(PokemonType.FIGHTING)
                .build();

        when(pokemonSpeciesDAO.findPokemonSpeciesById(pokemonSpeciesPichu.getId())).thenReturn(pokemonSpeciesPichu);
        when(pokemonSpeciesDAO.findPokemonSpeciesById(pokemonSpeciesPikachu.getId())).thenReturn(pokemonSpeciesPikachu);
        when(pokemonSpeciesDAO.findPokemonSpeciesById(pokemonSpeciesRaichu.getId())).thenReturn(pokemonSpeciesRaichu);
        when(pokemonSpeciesDAO.findPokemonSpeciesById(exceptionalPokemonSpecies.getId())).thenThrow(PE);
        when(pokemonSpeciesDAO.getAllEvolutionsOfPokemonSpecies(pokemonSpeciesPichu)).thenReturn(Collections.singletonList(pokemonSpeciesPikachu));
        when(pokemonSpeciesDAO.getAllEvolutionsOfPokemonSpecies(pokemonSpeciesPikachu)).thenReturn(Collections.singletonList(pokemonSpeciesRaichu));
        when(pokemonSpeciesDAO.getAllEvolutionsOfPokemonSpecies(pokemonSpeciesRaichu)).thenReturn(new ArrayList<>());
        when(pokemonSpeciesDAO.getAllEvolutionsOfPokemonSpecies(exceptionalPokemonSpecies)).thenThrow(PE);
        when(pokemonSpeciesDAO.getAllPokemonSpecies()).thenReturn(Arrays.asList(pokemonSpeciesPichu, pokemonSpeciesRaichu, pokemonSpeciesPikachu));
        doThrow(PE).when(pokemonSpeciesDAO).createPokemonSpecies(exceptionalPokemonSpecies);
        doThrow(PE).when(pokemonSpeciesDAO).updatePokemonSpecies(exceptionalPokemonSpecies);
        doThrow(PE).when(pokemonSpeciesDAO).deletePokemonSpecies(exceptionalPokemonSpecies);
    }

    @Test
    public void createPokemonSpeciesTest() throws EvolutionChainTooLongException {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonSpeciesService.createPokemonSpecies(exceptionalPokemonSpecies));
        assertThatExceptionOfType(EvolutionChainTooLongException.class)
                .isThrownBy(() -> pokemonSpeciesService.createPokemonSpecies(pokemonSpeciesPreevolutionTwo));

        pokemonSpeciesService.createPokemonSpecies(pokemonSpeciesPichu);
        verify(pokemonSpeciesDAO, atLeastOnce()).createPokemonSpecies(pokemonSpeciesPichu);
    }

    @Test
    public void changeTypingTest() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonSpeciesService.changeTyping(exceptionalPokemonSpecies, PokemonType.BUG, PokemonType.DARK));
        pokemonSpeciesService.changeTyping(pokemonSpeciesPichu, PokemonType.DARK, PokemonType.DRAGON);
        verify(pokemonSpeciesDAO, atLeastOnce()).updatePokemonSpecies(pokemonSpeciesPichu);
        assertThat(pokemonSpeciesPichu.getPrimaryType()).isEqualTo(PokemonType.DARK);
        assertThat(pokemonSpeciesPichu.getSecondaryType()).isEqualTo(PokemonType.DRAGON);
    }

    @Test
    public void changePreevolutionTest() throws EvolutionChainTooLongException, CircularEvolutionChainException {
        pokemonSpeciesPikachu.setEvolvesFrom(null);
        assertThatExceptionOfType(EvolutionChainTooLongException.class)
                .isThrownBy(() -> pokemonSpeciesService.changePreevolution(pokemonSpeciesPichu, pokemonSpeciesPreevolutionTwo));
        pokemonSpeciesPreevolutionOne.setEvolvesFrom(null);
        assertThatExceptionOfType(CircularEvolutionChainException.class)
                .isThrownBy(() -> pokemonSpeciesService.changePreevolution(pokemonSpeciesPreevolutionOne, pokemonSpeciesPreevolutionTwo));
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonSpeciesService.changePreevolution(exceptionalPokemonSpecies, pokemonSpeciesPikachu));
            pokemonSpeciesService.changePreevolution(pokemonSpeciesRaichu, pokemonSpeciesPreevolutionTwo);
        verify(pokemonSpeciesDAO, atLeastOnce()).updatePokemonSpecies(pokemonSpeciesRaichu);
        assertThat(pokemonSpeciesRaichu.getEvolvesFrom()).isEqualToComparingFieldByField(pokemonSpeciesPreevolutionTwo);
    }

    @Test
    public void removeTest() throws EntityIsUsedException {
        assertThatExceptionOfType(EntityIsUsedException.class)
                .isThrownBy(() -> pokemonSpeciesService.remove(pokemonSpeciesPikachu));
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonSpeciesService.remove(exceptionalPokemonSpecies));
        pokemonSpeciesService.remove(pokemonSpeciesPreevolutionTwo);
        verify(pokemonSpeciesDAO, atLeastOnce()).deletePokemonSpecies(pokemonSpeciesPreevolutionTwo);
    }

    @Test
    public void findPokemonSpeciesByIdTest() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonSpeciesService.findPokemonSpeciesById(exceptionalPokemonSpecies.getId()));
        assertThat(pokemonSpeciesService.findPokemonSpeciesById(pokemonSpeciesPikachu.getId())).isEqualTo(pokemonSpeciesPikachu);
    }

    @Test
    public void getAllPokemonSpeciesTest() {
        assertThat(pokemonSpeciesService.getAllPokemonSpecies())
                .containsOnly(pokemonSpeciesPichu, pokemonSpeciesPikachu, pokemonSpeciesRaichu);
    }
    
    @Test
    public void getAllEvolutionsOfPokemonSpeciesTest() {
        assertThat(pokemonSpeciesService.getAllEvolutionsOfPokemonSpecies(pokemonSpeciesPichu))
                .isNotNull()
                .containsExactly(pokemonSpeciesPikachu);
        assertThat(pokemonSpeciesService.getAllEvolutionsOfPokemonSpecies(pokemonSpeciesRaichu))
                .isNotNull()
                .isEmpty();
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> pokemonSpeciesService.getAllEvolutionsOfPokemonSpecies(exceptionalPokemonSpecies));
    }
}
