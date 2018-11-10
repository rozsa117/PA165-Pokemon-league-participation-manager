package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.Objects;

/**
 * DTO for PokemonSpecies.
 * 
 * @author Tibor Zauko 433531
 */
public class PokemonSpeciesDTO {

    private Long speciesId;

    private String speciesName;

    private PokemonType primaryType;

    private PokemonType secondaryType;

    private PokemonSpeciesDTO preevolution;

    public Long getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Long speciesId) {
        this.speciesId = speciesId;
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

    public PokemonSpeciesDTO getPreevolution() {
        return preevolution;
    }

    public void setPreevolution(PokemonSpeciesDTO preevolution) {
        this.preevolution = preevolution;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.speciesId);
        hash = 97 * hash + Objects.hashCode(this.speciesName);
        hash = 97 * hash + Objects.hashCode(this.primaryType);
        hash = 97 * hash + Objects.hashCode(this.secondaryType);
        hash = 97 * hash + Objects.hashCode(this.preevolution);
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
        if (!Objects.equals(this.speciesId, other.speciesId)) {
            return false;
        }
        if (this.primaryType != other.primaryType) {
            return false;
        }
        if (this.secondaryType != other.secondaryType) {
            return false;
        }
        return Objects.equals(this.preevolution, other.preevolution);
    }

    @Override
    public String toString() {
        return "PokemonSpeciesDTO{" + "speciesId=" + speciesId + ", speciesName=" + speciesName + ", primaryType=" + primaryType + ", secondaryType=" + secondaryType + ", preevolution=" + preevolution + '}';
    }
    
}
