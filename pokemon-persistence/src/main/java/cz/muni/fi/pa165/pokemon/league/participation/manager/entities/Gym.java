package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.ArrayList;
import java.util.List;
;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import javax.persistence.OneToMany;

/**
 * Entity class for object gym.
 *
 * @author Michal Mokros 456442
 */
@Entity(name = "Gym")
@Table(name = "GYM")
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String location;

    @NotNull
    @Enumerated
    @Column(nullable = false)
    private PokemonType type;

    @NotNull
    @OneToOne
    @JoinColumn(name = "trainer_id", nullable = false, unique = true)
    private Trainer gymLeader;

    /*
     * Needed for LEFT JOIN. 
     */
    @OneToMany(mappedBy = "gym")
    private List<Badge> badges;
    
    
    public Gym() {
    }

    public Gym(Long id) {
        this.id = id;
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
                "id= " + getId() +
                ", location= " + getLocation() +
                ", type= " + getType() +
                ", gymLeader= " + getGymLeader() +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Gym)) return false;
        final Gym gym = (Gym) o;
        return Objects.equals(getLocation(), gym.getLocation());
    }

    @Override
    public int hashCode() {
        final int hash = 13;
        int result = 1;
        result = hash * result + ((location == null) ? 0 : location.hashCode());
        return result;
    }
}
