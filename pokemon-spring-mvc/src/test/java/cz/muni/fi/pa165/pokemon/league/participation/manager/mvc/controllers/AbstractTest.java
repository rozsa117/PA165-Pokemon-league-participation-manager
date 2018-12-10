package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.config.TestContext;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This is abstract class for MVC controller tests
 *
 * @author Jiří Medveď 38451
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContext.class})
@WebAppConfiguration
public abstract class AbstractTest {

    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

}
