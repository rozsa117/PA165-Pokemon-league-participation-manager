package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.config;

import cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.security.TrainerFacadeAuthenticationProvider;
import javax.inject.Inject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configure user authentication.
 *
 * @author Tibor Zauko 433531
 */
@Configuration
@EnableWebSecurity
@EnableWebMvc
@ComponentScan(basePackages = "cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private TrainerFacadeAuthenticationProvider provider;

    public SecurityConfig() {
        super();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/favicon.ico", "/", "/pokemonSpecies/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
                .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

}
