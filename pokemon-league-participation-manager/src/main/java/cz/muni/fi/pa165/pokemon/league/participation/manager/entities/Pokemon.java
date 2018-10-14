
package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Entity class representing a Pokemon caught by a trainer.
 *
 * @author Tibor Zauko 433531
 */
@Entity(name = "Pokemon")
@Table(name = "POKEMON")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "species_id", nullable = false)
    private PokemonSpecies species;

    @NotNull
    private String nickname;

    // level is a reserved keyword in SQL-99
    @Min(value = 1, message = "Level can't be less than 1")
    @Max(value = 100, message = "Level can't be higher than 100")
    @Column(name = "pkmn_level", nullable = false)
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    public Pokemon() {
        this(null);
    }

    public Pokemon(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PokemonSpecies getSpecies() {
        return species;
    }

    public void setSpecies(PokemonSpecies species) {
        this.species = species;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final Pokemon other = (Pokemon) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return new StringBuilder("Pokemon{")
                .append("id=")
                .append(id)
                .append(", species=")
                .append(species)
                .append(", nickname=")
                .append(nickname)
                .append(", level=")
                .append(level)
                .append(", trainer=")
                .append(trainer)
                .append('}')
                .toString();
    }

}
