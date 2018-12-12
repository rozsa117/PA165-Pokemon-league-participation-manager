package cz.muni.fi.pa165.pokemon.league.participation.manager.service.config;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import cz.muni.fi.pa165.pokemon.league.participation.manager.utils.PersistenceApplicationContext;
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
    public Mapper dozer(){
        return DozerBeanMapperBuilder.buildDefault();
    }
}
