
package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import java.util.Objects;

/**
 * Entity class representing a Pokemon caught by a trainer.
 *
 * @author Tibor Zauko 433531
 */
public class Pokemon {

    private Long id;
    private PokemonSpecies species;
    private String nickname;
    private int level;
    private Trainer trainer;

    public Pokemon() {
        this(null, null, null, 1, null);
    }

    public Pokemon(Long id, PokemonSpecies species, String nickname, int level, Trainer trainer) {
        this.id = id;
        this.species = species;
        this.nickname = nickname;
        this.level = level;
        this.trainer = trainer;
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

}
