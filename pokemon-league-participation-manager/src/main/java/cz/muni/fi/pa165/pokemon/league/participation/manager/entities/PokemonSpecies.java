package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Entity class for object PokemonSpecies.
 *
 * @author Jiří Medveď 38451
 */
@Entity
public class PokemonSpecies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String specieName;

    @NotNull
    @Enumerated
    private PokemonType primaryType;

    @Enumerated
    private PokemonType secondaryType;

    @OneToOne(optional = true)
    @JoinColumn(name = "evolvesFromId")
    private PokemonSpecies evolvesFrom;

    public PokemonSpecies() {
    }

    public PokemonSpecies(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public PokemonSpecies getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(PokemonSpecies evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    @Override
    public String toString() {
        return "PokemonSpecies{" + "id=" + id + ", specieName=" + specieName + '}';
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
