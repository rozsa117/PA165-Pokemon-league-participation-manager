/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author jiri21
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PokemonDAOTest extends AbstractTestNGSpringContextTests {

    @Inject
    private PokemonDAO pokemonDao;

    public PokemonDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @BeforeMethod
    public void setUp() {
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setNickname("Pokemon1");
        pokemonDao.createPokemon(pokemon1);
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");

    }

}
