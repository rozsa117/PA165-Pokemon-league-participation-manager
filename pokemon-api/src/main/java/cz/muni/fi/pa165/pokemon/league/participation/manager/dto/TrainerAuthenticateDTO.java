package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO class for autheticate Trainer.
 *
 * @author Jiří Medveď 38451
 */
public class TrainerAuthenticateDTO {

    @NotNull
    private Long trainerId;

    @NotNull
    @Size(min = TrainerDTOConstants.PASSWORD_MIN_LENGTH, max = TrainerDTOConstants.PASSWORD_MAX_LENGTH)
    private String password;

    public Long getUserId() {
        return trainerId;
    }

    public void setUserId(Long userId) {
        this.trainerId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TrainerAuthenticateDTO{" + "trainerId=" + trainerId + ", password=" + password + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.trainerId);
        hash = 23 * hash + Objects.hashCode(this.password);
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
        final TrainerAuthenticateDTO other = (TrainerAuthenticateDTO) obj;
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.trainerId, other.trainerId)) {
            return false;
        }
        return true;
    }

}
