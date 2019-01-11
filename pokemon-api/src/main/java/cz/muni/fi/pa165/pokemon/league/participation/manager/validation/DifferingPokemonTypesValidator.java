package cz.muni.fi.pa165.pokemon.league.participation.manager.validation;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of validator for DifferingPokemonTypes annotation.
 * 
 * @author Tibor Zauko 433531
 */
public class DifferingPokemonTypesValidator implements ConstraintValidator<DifferingPokemonTypes, Object> {

    final static Logger LOGGER = LoggerFactory.getLogger(DifferingPokemonTypesValidator.class);

    private DifferingPokemonTypes annotation;

    @Override
    public void initialize(DifferingPokemonTypes annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(Object annotatedObject, ConstraintValidatorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Members to be validated: {}, {}", annotation.primaryTypeMember(), annotation.secondaryTypeMember());
        }

        PokemonType prim = null, sec = null;

        Field fPrim, fSec;
        try {
            fPrim = annotatedObject.getClass().getDeclaredField(annotation.primaryTypeMember());
            fPrim.setAccessible(true);
            if (PokemonType.class.isAssignableFrom(fPrim.getType())) {
                prim = (PokemonType) fPrim.get(annotatedObject);
            } else {
                LOGGER.debug("{} member is not a PokemonType or its subclass", annotation.primaryTypeMember());
                throw new RuntimeException("Error while obtaining member "+ annotation.primaryTypeMember() + ": member is not of type PokemonType or its subclass");
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Error while reading member " + annotation.primaryTypeMember() + " on class " + annotatedObject.getClass().getName());
        }
        try {
            fSec = annotatedObject.getClass().getDeclaredField(annotation.secondaryTypeMember());
            fSec.setAccessible(true);
            if (PokemonType.class.isAssignableFrom(fSec.getType())) {
                sec = (PokemonType) fSec.get(annotatedObject);
            } else {
                LOGGER.debug("{} member is not a PokemonType or its subclass", annotation.secondaryTypeMember());
                throw new RuntimeException("Error while obtaining member "+ annotation.secondaryTypeMember() + ": member is not of type PokemonType or its subclass");
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Error while reading member " + annotation.secondaryTypeMember() + " on class " + annotatedObject.getClass().getName());
        }
        return !Objects.equals(prim, sec);
    }

}
