package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

/**
 * Entity class for object trainer.
 * 
 * @author Tamás Rózsa 445653
 */
@Entity(name = "Trainer")
@Table(name = "TRAINER")
public class Trainer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private String passwordHash;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String userName;

    @NotNull
    @Column(nullable = false)
    private String name;
    
    @NotNull
    @Column(nullable = false)
    private String surname;
    
    @NotNull
    @Past
    @Column(nullable = false)
    private LocalDate born;

    @Column(name = "trnr_admin", nullable = false)
    private boolean admin = false;

    @OneToMany
    private Set<Pokemon> pokemons = new HashSet<>();
    
    public Trainer() {
    }

    public Trainer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<Pokemon> getPokemons() {
        return pokemons;
    }
    
    public void addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
    }
    
    public void deletePokemon(Pokemon pokemon) {
        pokemons.remove(pokemon);
    }
    
    @Override
    public String toString() {
        return "Trainer{ " + "id = " + id + ", name = " + name + ", surname = " + surname + ", born = " + born + " }";
    }

    @Override
    public int hashCode() {
        final int hash = 5;
        int result = 1;
        result = hash * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Trainer)) {
            return false;
        }
        final Trainer other = (Trainer) obj;
        return Objects.equals(this.getUserName(), other.getUserName());
    }
}