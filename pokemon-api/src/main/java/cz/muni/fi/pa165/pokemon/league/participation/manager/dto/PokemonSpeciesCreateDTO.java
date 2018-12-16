package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.deserializers.PokemonTypeEnumDeserializer;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import cz.muni.fi.pa165.pokemon.league.participation.manager.validation.DifferingPokemonTypes;

/**
 * DTO for Pokemon species creation requests.
 * 
 * @author Tibor Zauko 433531
 */
@DifferingPokemonTypes(primaryTypeMember = "primaryType", secondaryTypeMember = "secondaryType")
public class PokemonSpeciesCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String speciesName;

    @NotNull
    @JsonDeserialize(using = PokemonTypeEnumDeserializer.class)
    private PokemonType primaryType;

    @JsonDeserialize(using = PokemonTypeEnumDeserializer.class)
    private PokemonType secondaryType;

    private Long evolvesFromId;

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

    public Long getEvolvesFromId() {
        return evolvesFromId;
    }

    public void setEvolvesFromId(Long evolvesFromId) {
        this.evolvesFromId = evolvesFromId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.speciesName);
        hash = 89 * hash + Objects.hashCode(this.primaryType);
        hash = 89 * hash + Objects.hashCode(this.secondaryType);
        hash = 89 * hash + Objects.hashCode(this.evolvesFromId);
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
        final PokemonSpeciesCreateDTO other = (PokemonSpeciesCreateDTO) obj;
        if (!Objects.equals(this.speciesName, other.speciesName)) {
            return false;
        }
        if (this.primaryType != other.primaryType) {
            return false;
        }
        if (this.secondaryType != other.secondaryType) {
            return false;
        }
        return Objects.equals(this.evolvesFromId, other.evolvesFromId);
    }

    @Override
    public String toString() {
        return "PokemonSpeciesCreateDTO{" + "speciesName=" + speciesName + ", primaryType=" + primaryType + ", secondaryType=" + secondaryType + ", preevolutionId=" + evolvesFromId + '}';
    }

}
