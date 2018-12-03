package cz.muni.fi.pa165.pokemon.league.participation.manager.service.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import cz.muni.fi.pa165.pokemon.league.participation.manager.common.PersistenceApplicationContext;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Service layer configuration.
 * 
 * @author Tamás Rózsa 445653
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = {"cz.muni.fi.pa165.pokemon.league.participation.manager.service","cz.muni.fi.pa165.pokemon.league.participation.manager.facade"})
public class ServiceConfiguration {

    @Bean
    public Mapper getMapper(){
        DozerBeanMapper mapper =  new DozerBeanMapper();
        mapper.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
        return mapper;
    }
}
