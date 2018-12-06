/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.rest.controllers;


import java.io.IOException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.pokemon.league.participation.manager.RootWebContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RootWebContext.class)
@WebAppConfiguration
public abstract class AbstractTest {
   protected MockMvc mvc;
   @Autowired
   WebApplicationContext webApplicationContext;

   protected void setUp() {
      mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
   }
   protected String mapToJson(Object obj) throws JsonProcessingException {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
   }
   protected <T> T mapFromJson(String json, Class<T> clazz)
      throws JsonParseException, JsonMappingException, IOException {
      
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, clazz);
   }
}