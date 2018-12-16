package cz.muni.fi.pa165.pokemon.league.participation.manager.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.util.Arrays;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * DifferingPokemonTypes annotation for verifying whether the given Pokemon types
 * fields differ.
 * 
 * @author Tibor Zauko 433531
 * Based on AllOrNothing annotation from fi-muni/PA165.
 */
@Target( { TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DifferingPokemonTypesValidator.class)
@Documented
public @interface DifferingPokemonTypes {
    String primaryTypeMember();
    String secondaryTypeMember();

    String message() default "pokemon.types.should.differ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};    
    
}
