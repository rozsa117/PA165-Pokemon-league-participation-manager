package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;

import java.util.Objects;

/**
 * Entity class for object badge.
 *
 * @author Michal Mokros 456442
 */
public class Gym {

    private Long id;
    private String location;
    private PokemonType type;
    private Trainer gymLeader;

    public Gym() {
        this(null, null, null, null);
    }

    public Gym(Long id, String location, PokemonType type, Trainer gymLeader) {
        this.id = id;
        this.location = location;
        this.type = type;
        this.gymLeader = gymLeader;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public Trainer getGymLeader() {
        return gymLeader;
    }

    public void setGymLeader(Trainer gymLeader) {
        this.gymLeader = gymLeader;
    }

    @Override
    public String toString() {
        return "Gym{ " +
                "id= " + id +
                ", location= " + location +
                ", type= " + type +
                ", gymLeader= " + gymLeader +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Gym gym = (Gym) o;
        return Objects.equals(this.id, gym.id);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 19 * hash + (int) (this.id ^ (this.id >>> 16));
        return hash;
    }
}
