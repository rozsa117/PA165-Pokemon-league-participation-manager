package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import static cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTOConstants.*;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * DTO class for create Trainer.
 *
 * @author Jiří Medveď 38451
 */
public class TrainerCreateDTO {

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @NotNull
    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH)
    private String userName;

    @NotNull
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH)
    private String name;

    @NotNull
    @Size(min = SURNAME_MIN_LENGTH, max = SURNAME_MAX_LENGTH)
    private String surname;

    @NotNull
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate born;

    @NotNull
    private boolean admin;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "TrainerCreateDTO{" + "password=" + password + ", userName=" 
                + userName + ", name=" + name + ", surname=" + surname 
                + ", born=" + born + ", admin=" + admin + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.password);
        hash = 79 * hash + Objects.hashCode(this.userName);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.surname);
        hash = 79 * hash + Objects.hashCode(this.born);
        hash = 79 * hash + (this.admin ? 1 : 0);
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
        final TrainerCreateDTO other = (TrainerCreateDTO) obj;
        if (this.admin != other.admin) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.born, other.born)) {
            return false;
        }
        return true;
    }

}
