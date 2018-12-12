package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO class for change Trainer password.
 *
 * @author Jiří Medveď 38451
 */
public class TrainerChangePasswordDTO {

    private Long trainerId;

    @NotNull
    @Size(min = TrainerDTOConstants.PASSWORD_MIN_LENGTH, max = TrainerDTOConstants.PASSWORD_MAX_LENGTH)
    private String oldPassword;

    @NotNull
    @Size(min = TrainerDTOConstants.PASSWORD_MIN_LENGTH, max = TrainerDTOConstants.PASSWORD_MAX_LENGTH)
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

    @Override
    public String toString() {
        return "TrainerChangePasswordDTO{" + "trainerId=" + trainerId + ", oldPassword=" + oldPassword + ", newPassword=" + newPassword + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.trainerId);
        hash = 23 * hash + Objects.hashCode(this.oldPassword);
        hash = 23 * hash + Objects.hashCode(this.newPassword);
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
        final TrainerChangePasswordDTO other = (TrainerChangePasswordDTO) obj;
        if (!Objects.equals(this.oldPassword, other.oldPassword)) {
            return false;
        }
        if (!Objects.equals(this.newPassword, other.newPassword)) {
            return false;
        }
        if (!Objects.equals(this.trainerId, other.trainerId)) {
            return false;
        }
        return true;
    }

}
