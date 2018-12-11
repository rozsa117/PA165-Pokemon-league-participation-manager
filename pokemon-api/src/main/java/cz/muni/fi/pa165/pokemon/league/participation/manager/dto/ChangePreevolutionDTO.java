package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * DTO for changing preevolution of a Pokemon species.
 * 
 * @author Tibor Zauko 433531
 */
public class ChangePreevolutionDTO {

    @NotNull
    private Long id;

    private Long evolvesFrom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(Long evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.evolvesFrom);
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
        final ChangePreevolutionDTO other = (ChangePreevolutionDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.evolvesFrom, other.evolvesFrom);
    }

    @Override
    public String toString() {
        return "ChangePreevolutionDTO{" + "speciesId=" + id + ", preevolutionId=" + evolvesFrom + '}';
    }

}
