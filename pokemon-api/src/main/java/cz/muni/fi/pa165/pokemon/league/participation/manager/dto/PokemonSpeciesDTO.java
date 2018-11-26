package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.Objects;

/**
 * DTO for PokemonSpecies.
 * 
 * @author Tibor Zauko 433531
 */
public class PokemonSpeciesDTO {

    private Long id;

    private String speciesName;

    private PokemonType primaryType;

    private PokemonType secondaryType;

    private PokemonSpeciesDTO evolvesFrom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
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

    public PokemonSpeciesDTO getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(PokemonSpeciesDTO evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.speciesName);
        hash = 97 * hash + Objects.hashCode(this.primaryType);
        hash = 97 * hash + Objects.hashCode(this.secondaryType);
        hash = 97 * hash + Objects.hashCode(this.evolvesFrom);
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
        final PokemonSpeciesDTO other = (PokemonSpeciesDTO) obj;
        if (!Objects.equals(this.speciesName, other.speciesName)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.primaryType != other.primaryType) {
            return false;
        }
        if (this.secondaryType != other.secondaryType) {
            return false;
        }
        return Objects.equals(this.evolvesFrom, other.evolvesFrom);
    }

    @Override
    public String toString() {
        return "PokemonSpeciesDTO{" + "id=" + id + ", speciesName=" + speciesName + ", primaryType=" + primaryType + ", secondaryType=" + secondaryType + ", evolvesFrom=" + evolvesFrom + '}';
    }
    
}
