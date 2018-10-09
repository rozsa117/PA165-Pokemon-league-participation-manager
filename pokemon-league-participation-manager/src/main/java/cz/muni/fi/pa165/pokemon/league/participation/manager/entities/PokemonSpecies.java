package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.Objects;

/**
 * Entity class for object PokemonSpecies.
 *
 * @author Jiří Medveď 38451
 */
public final class PokemonSpecies {

    private final Long id;
    private final String speciesName;
    private final PokemonType primaryType;
    private final PokemonType secondaryType;
    private final PokemonSpecies evolverFrom;

    public PokemonSpecies(Long id, String speciesName, PokemonType primaryType,
            PokemonType secondaryType, PokemonSpecies evolverFrom) {
        this.id = id;
        this.speciesName = speciesName;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        this.evolverFrom = evolverFrom;
    }

    public Long getId() {
        return id;
    }

    public String getSpecieName() {
        return speciesName;
    }

    public PokemonType getPrimaryType() {
        return primaryType;
    }

    public PokemonType getSecondaryType() {
        return secondaryType;
    }

    public PokemonSpecies getEvolverFrom() {
        return evolverFrom;
    }

    @Override
    public String toString() {
        return "PokemonSpecies{" + "id=" + id
                + ", speciesName=" + speciesName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
