package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.Objects;

/**
 * Entity class for object PokemonSpecies.
 * 
 * @author Jiří Medveď 38451
 */
public class PokemonSpecies {
    
    private Long id;
    private String specieName;
    private PokemonType primaryType;
    private PokemonType secondaryType;
    private PokemonSpecies evolverFrom;

    public PokemonSpecies(Long id, String specieName, PokemonType primaryType, PokemonType secondaryType, PokemonSpecies evolverFrom) {
        this.id = id;
        this.specieName = specieName;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        this.evolverFrom = evolverFrom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecieName() {
        return specieName;
    }

    public void setSpecieName(String specieName) {
        this.specieName = specieName;
    }

    public PokemonType getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(PokemonType primaryType) {
        this.primaryType = primaryType;
    }

    public PokemonType getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(PokemonType secondaryType) {
        this.secondaryType = secondaryType;
    }

    public PokemonSpecies getEvolverFrom() {
        return evolverFrom;
    }

    public void setEvolverFrom(PokemonSpecies evolverFrom) {
        this.evolverFrom = evolverFrom;
    }

    @Override
    public String toString() {
        return "PokemonSpecies{" + "id=" + id + ", specieName=" + specieName + ", primaryType=" + primaryType + ", secondaryType=" + secondaryType + ", evolverFrom=" + evolverFrom + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PokemonSpecies other = (PokemonSpecies) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }



}
