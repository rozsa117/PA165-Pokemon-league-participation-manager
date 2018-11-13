package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import static cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTOConstants.*;
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
    @Size(min = SURNAME_MIN_LENGTH, max = SURNAME_MIN_LENGTH)
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
}
