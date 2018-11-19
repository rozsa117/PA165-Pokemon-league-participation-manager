package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.validation.DifferingPokemonTypes;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Entity class for object PokemonSpecies.
 *
 * @author Jiří Medveď 38451
 */
@Entity
@Table(name = "POKEMON_SPECIES")
@DifferingPokemonTypes(primaryTypeMember = "primaryType", secondaryTypeMember = "secondaryType")
public class PokemonSpecies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String speciesName;

    @NotNull
    @Enumerated
    @Column(nullable = false)
    private PokemonType primaryType;

    @Enumerated
    private PokemonType secondaryType;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "evolves_from_id")
    private PokemonSpecies evolvesFrom;

    public PokemonSpecies() {
    }

    public PokemonSpecies(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public PokemonType getPrimaryType() {
        return primaryType;
    }

    public PokemonType getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(PokemonType secondaryType) {
        this.secondaryType = secondaryType;
    }

    public PokemonSpecies getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(PokemonSpecies evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setPrimaryType(PokemonType primaryType) {
        this.primaryType = primaryType;
    }

    @Override
    public String toString() {
        return "PokemonSpecies{" + "id=" + id + ", speciesName=" + speciesName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.speciesName);
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

        if (!(obj instanceof PokemonSpecies)) {
            return false;
        }
        final PokemonSpecies other = (PokemonSpecies) obj;
        return Objects.equals(this.speciesName, other.getSpeciesName());
    }
    
}