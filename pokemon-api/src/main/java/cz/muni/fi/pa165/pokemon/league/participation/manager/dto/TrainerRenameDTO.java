package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import static cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTOConstants.*;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO class for change Trainer name and surname.
 *
 * @author Jiří Medveď 38451
 */
public class TrainerRenameDTO {

    private Long trainerId;

    @NotNull
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH)
    private String name;

    @NotNull
    @Size(min = SURNAME_MIN_LENGTH, max = SURNAME_MAX_LENGTH)
    private String surname;

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
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

    @Override
    public String toString() {
        return "TrainerRenameDTO{" + "trainerId=" + trainerId + ", name=" + name + ", surname=" + surname + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.trainerId);
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.surname);
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
        final TrainerRenameDTO other = (TrainerRenameDTO) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.trainerId, other.trainerId)) {
            return false;
        }
        return true;
    }
}
