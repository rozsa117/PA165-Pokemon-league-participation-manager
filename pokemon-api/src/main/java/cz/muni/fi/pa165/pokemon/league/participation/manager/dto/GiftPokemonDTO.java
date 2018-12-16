package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * DTO for gifting Pokemon to other trainers.
 * 
 * @author Tibor Zauko 433531
 */
public class GiftPokemonDTO {
    
    @NotNull
    private Long id;
    
    private Long requestingTrainerId;
    
    @NotNull
    private Long giftedTrainerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestingTrainerId() {
        return requestingTrainerId;
    }

    public void setRequestingTrainerId(Long requestingTrainerId) {
        this.requestingTrainerId = requestingTrainerId;
    }

    public Long getGiftedTrainerId() {
        return giftedTrainerId;
    }

    public void setGiftedTrainerId(Long giftedTrainerId) {
        this.giftedTrainerId = giftedTrainerId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.requestingTrainerId);
        hash = 19 * hash + Objects.hashCode(this.giftedTrainerId);
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
        final GiftPokemonDTO other = (GiftPokemonDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.requestingTrainerId, other.requestingTrainerId)) {
            return false;
        }
        return Objects.equals(this.giftedTrainerId, other.giftedTrainerId);
    }

    @Override
    public String toString() {
        return "GiftPokemonDTO{" + "id=" + id + ", requestingTrainerId=" + requestingTrainerId + ", giftedTrainerId=" + giftedTrainerId + '}';
    }

}
