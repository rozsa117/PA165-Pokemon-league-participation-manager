package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.deserializers.PokemonTypeEnumDeserializer;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import cz.muni.fi.pa165.pokemon.league.participation.manager.validation.DifferingPokemonTypes;

/**
 * DTO for requests for change of a Pokemon species' typing.
 * 
 * @author Tibor Zauko 433531
 */
@DifferingPokemonTypes(primaryTypeMember = "primaryType", secondaryTypeMember = "secondaryType")
public class ChangeTypingDTO {

    @NotNull
    private Long id;

    @NotNull
    @JsonDeserialize(using = PokemonTypeEnumDeserializer.class)
    private PokemonType primaryType;

    @JsonDeserialize(using = PokemonTypeEnumDeserializer.class)
    private PokemonType secondaryType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.primaryType);
        hash = 29 * hash + Objects.hashCode(this.secondaryType);
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
        final ChangeTypingDTO other = (ChangeTypingDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.primaryType != other.primaryType) {
            return false;
        }
        return this.secondaryType == other.secondaryType;
    }

    @Override
    public String toString() {
        return "ChangeTypingDTO{" + "id=" + id + ", primaryType=" + primaryType + ", secondaryType=" + secondaryType + '}';
    }
    
}
