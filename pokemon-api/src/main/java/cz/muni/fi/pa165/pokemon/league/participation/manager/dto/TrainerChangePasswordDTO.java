package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

//import static cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTOConstants;
        

import static cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTOConstants.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO class for change Trainer password.
 *
 * @author Jiří Medveď 38451
 */
public class TrainerChangePasswordDTO {

    private Long trainerId;
    private String oldPassword;
    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String newPassword;

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
