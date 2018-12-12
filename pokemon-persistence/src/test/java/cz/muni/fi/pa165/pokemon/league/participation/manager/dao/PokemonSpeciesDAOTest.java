package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonSpeciesBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.utils.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Unit tests for PokemonSpeciesDAO implementations.
 *
 * @author Tibor Zauko
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners({TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@Transactional
@RunWith(SpringRunner.class)
public class PokemonSpeciesDAOTest {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private PokemonSpeciesDAO dao;

    private PokemonSpecies mudkip;

    @Before
    public void setUp() {
        mudkip = new PokemonSpeciesBuilder()
                .speciesName("Mudkip")
                .primaryType(PokemonType.WATER)
                .build();
        dao.createPokemonSpecies(mudkip);
    }

    private PokemonSpeciesBuilder bulbasaurBuilder() {
        return new PokemonSpeciesBuilder()
                .speciesName("Bulbasaur")
                .primaryType(PokemonType.GRASS)
                .secondaryType(PokemonType.POISON);
    }

    private PokemonSpeciesBuilder marshtompBuilder() {
        return new PokemonSpeciesBuilder()
                .speciesName("Marshtomp")
                .primaryType(PokemonType.WATER)
                .secondaryType(PokemonType.GROUND)
                .evolvesFrom(mudkip);
    }

    /**
     * Test of createPokemonSpecies method with correctly created entity.
     */
    @Test
    public void testCreateUnevolvedPokemonSpecies() {
        PokemonSpecies bulbasaur = bulbasaurBuilder().build();

        dao.createPokemonSpecies(bulbasaur);

        assertThat(em.contains(bulbasaur)).isTrue();

        PokemonSpecies found = dao.findPokemonSpeciesById(bulbasaur.getId());
        assertThat(found)
                .isNotNull()
                .isEqualToComparingFieldByField(bulbasaur);

        assertThat(dao.getAllPokemonSpecies())
                .isNotNull()
                .hasSize(2);
    }

    /**
     * Test of createPokemonSpecies method with correctly created entity.
     */
    @Test
    public void testCreateEvolvedPokemonSpecies() {
        PokemonSpecies marshtomp = marshtompBuilder().build();

        dao.createPokemonSpecies(marshtomp);

        assertThat(em.contains(marshtomp)).isTrue();

        PokemonSpecies found = dao.findPokemonSpeciesById(marshtomp.getId());
        assertThat(found)
                .isNotNull()
                .isEqualToComparingFieldByField(marshtomp);

        assertThat(dao.getAllPokemonSpecies())
                .isNotNull()
                .hasSize(2);
    }
    /**
     * Test of createPokemonSpecies method with correctly created entity.
     */
    @Test
    public void testCreateTwoBranchEvolvedPokemonSpecies() {
        PokemonSpecies marshtomp = marshtompBuilder().build();
        PokemonSpecies mershtomp = marshtompBuilder().speciesName("Mershtomp").build();

        dao.createPokemonSpecies(marshtomp);
        dao.createPokemonSpecies(mershtomp);

        assertThat(em.contains(marshtomp)).isTrue();
        assertThat(em.contains(mershtomp)).isTrue();

        PokemonSpecies found = dao.findPokemonSpeciesById(marshtomp.getId());
        assertThat(found)
                .isNotNull()
                .isNotEqualTo(dao.findPokemonSpeciesById(mershtomp.getId()));

        assertThat(dao.getAllPokemonSpecies())
                .isNotNull()
                .hasSize(3);
    }

    /**
     * Tests whether invalid species does get rejected (case null species).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullPokemonSpecies() {
        dao.createPokemonSpecies(null);
    }

    /**
     * Tests whether invalid species does get rejected (case non null id).
     */
    @Test(expected = PersistenceException.class)
    public void testCreatePokemonSpeciesWithNonNullId() {
        PokemonSpecies species = bulbasaurBuilder()
                .id(10L)
                .build();

        dao.createPokemonSpecies(species);
    }

    /**
     * Tests whether invalid species does get rejected (case null species name).
     */
    @Test(expected = ConstraintViolationException.class)
    public void testCreatePokemonSpeciesWithNullSpeciesName() {
        PokemonSpecies species = bulbasaurBuilder()
                .speciesName(null)
                .build();

        dao.createPokemonSpecies(species);
    }

    /**
     * Tests whether invalid species does get rejected (case empty species name).
     */
    @Test(expected = ConstraintViolationException.class)
    public void testCreatePokemonSpeciesWithEmptySpeciesName() {
        PokemonSpecies species = bulbasaurBuilder()
                .speciesName("")
                .build();

        dao.createPokemonSpecies(species);
    }

    /**
     * Tests whether invalid species does get rejected (case null primary type).
     */
    @Test(expected = ConstraintViolationException.class)
    public void testCreatePokemonSpeciesWithNullPrimaryType() {
        PokemonSpecies species = bulbasaurBuilder()
                .primaryType(null)
                .build();

        dao.createPokemonSpecies(species);
    }

    /**
     * Test of updatePokemonSpecies method.
     */
    @Test
    public void testUpdatePokemonSpecies() {
        // Updated species (we won't use marshtompBuilder, since all fields will change):
        PokemonSpecies marshtomp = new PokemonSpeciesBuilder()
                .speciesName("Mashrtomp")
                .primaryType(PokemonType.FIRE)
                .build();

        dao.createPokemonSpecies(marshtomp);

        em.detach(marshtomp);

        marshtomp.setSpeciesName("Marshtomp");
        marshtomp.setPrimaryType(PokemonType.WATER);
        marshtomp.setSecondaryType(PokemonType.GROUND);
        marshtomp.setEvolvesFrom(mudkip);

        dao.updatePokemonSpecies(marshtomp);

        assertThat(dao.findPokemonSpeciesById(marshtomp.getId()))
                .isNotNull()
                .isEqualToComparingFieldByField(marshtomp);
        // Check that mudkip didn't change:
        assertThat(dao.findPokemonSpeciesById(mudkip.getId()))
                .isNotNull()
                .isEqualToComparingFieldByField(mudkip);
    }

    /**
     * Tests whether invalid updated species gets rejected (case null species).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNullPokemonSpecies() {
        dao.updatePokemonSpecies(null);
    }

    /**
     * Tests whether invalid updated species gets rejected.
     *
     * This one update test is enough invalid entity coverage - since constraints
     * are defined on entity, this is enough to verify, that update is
     * performed through entity validating code path.
     */
    @Test(expected = ConstraintViolationException.class)
    public void testUpdatePokemonSpeciesWithNullSpeciesName() {
        PokemonSpecies marshtomp = marshtompBuilder().build();

        dao.createPokemonSpecies(marshtomp);

        em.detach(marshtomp);

        marshtomp.setSpeciesName(null);

        dao.updatePokemonSpecies(marshtomp);
        em.flush();
    }

    /**
     * Test of deletePokemonSpecies method.
     */
    @Test
    public void testDeletePokemonSpecies() {
        PokemonSpecies bulbasaur = bulbasaurBuilder().build();
        dao.createPokemonSpecies(bulbasaur);

        assertThat(dao.getAllPokemonSpecies())
                .isNotNull()
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(mudkip, bulbasaur);

        dao.deletePokemonSpecies(mudkip);

        assertThat(dao.getAllPokemonSpecies())
                .isNotNull()
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(bulbasaur);
    }

    /**
     * Test of deletePokemonSpecies method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNullPokemonSpecies() {
        dao.deletePokemonSpecies(null);
    }

    /**
     * Test of deletePokemonSpecies method - transient entity.
     */
    @Test
    public void testDeleteTransientPokemonSpecies() {
        dao.deletePokemonSpecies(bulbasaurBuilder().build());
    }

    @Test
    public void testDeleteDetachedPokemonSpecies() {
        PokemonSpecies bulbasaur = bulbasaurBuilder().build();
        dao.createPokemonSpecies(bulbasaur);
        em.detach(bulbasaur);
        dao.deletePokemonSpecies(bulbasaur);
    }

    /**
     * Test of findPokemonSpeciesById method.
     */
    @Test
    public void testFindNonexistentPokemonSpeciesById() {
        assertThat(dao.findPokemonSpeciesById(Long.MAX_VALUE))
                .isNull();
    }

    /**
     * Test of findPokemonSpeciesById method when given null ID.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindNonexistentPokemonSpeciesByNullId() {
        dao.findPokemonSpeciesById(null);
    }

    /**
     * Test whether correct expection is thrown for invalid arguments.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindPokemonSpeciesByIdWithNullId() {
        dao.findPokemonSpeciesById(null);
    }

    /**
     * Test of getAllPokemonSpecies method, of class PokemonSpeciesDAO.
     */
    @Test
    public void testGetAllPokemonSpecies() {
        assertThat(dao.getAllPokemonSpecies())
                .isNotNull()
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(mudkip);

        PokemonSpecies bulbasaur = bulbasaurBuilder().build();
        PokemonSpecies marshtomp = marshtompBuilder().build();

        dao.createPokemonSpecies(bulbasaur);

        assertThat(dao.getAllPokemonSpecies())
                .isNotNull()
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(mudkip, bulbasaur);

        dao.createPokemonSpecies(marshtomp);

        assertThat(dao.getAllPokemonSpecies())
                .isNotNull()
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(mudkip, marshtomp, bulbasaur);
    }
    
    /**
     * Test of getAllEvolutionsOfPokemonSpecies method, of class PokemonSpeciesDAO.
     */
    @Test
    public void testGetAllEvolutionsOfPokemonSpecies() {
        assertThat(dao.getAllEvolutionsOfPokemonSpecies(mudkip))
                .isNotNull()
                .isEmpty();

        PokemonSpecies bulbasaur = bulbasaurBuilder().build();
        PokemonSpecies marshtomp = marshtompBuilder().build();
        PokemonSpecies other = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.WATER)
                .evolvesFrom(mudkip)
                .speciesName("Faketomp")
                .build();
        
        PokemonSpecies saurbulba = bulbasaurBuilder()
                .speciesName("Saurbulba")
                .evolvesFrom(other)
                .build();

        dao.createPokemonSpecies(bulbasaur);
        dao.createPokemonSpecies(marshtomp);
        dao.createPokemonSpecies(other);
        dao.createPokemonSpecies(saurbulba);

        assertThat(dao.getAllEvolutionsOfPokemonSpecies(mudkip))
                .isNotNull()
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(marshtomp, other);
    }
    
    /**
     * Test whether correct expection is thrown for invalid arguments.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllEvolutionsOfNullPokemonSpecies() {
        dao.getAllEvolutionsOfPokemonSpecies(null);
    }


}
