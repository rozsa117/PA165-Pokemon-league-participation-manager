package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO class for object Trainer.
 *
 * @author Jiří Medveď 38451
 */
public class TrainerDTO {

    private Long id;

    private String userName;

    private String name;

    private String surname;

    private LocalDate born;

    private boolean admin;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TrainerDTO{ " + "id = " + id + ", name = " + name + ", surname = " + surname + ", born = " + born + " }";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.userName) + Objects.hashCode(this.id);
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
        final TrainerDTO other = (TrainerDTO) obj;
        if (!Objects.equals(this.userName, other.userName) || !Objects.equals(this.id, id)) {
            return false;
        }
        return true;
    }

}
