package cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;

/**
 * Builder class for GymDTO.
 * 
 * @author Tamás Rózsa 445653
 */
public class GymDTOBuilder {
    
    private Long id;
    
    private String location;
    
    private PokemonType type;
    
    private TrainerDTO gymLeader;
    
    public GymDTO build() {
        GymDTO gym = new GymDTO();
        gym.setId(id);
        gym.setLocation(location);
        gym.setType(type);
        gym.setGymLeader(gymLeader);
        return gym;
    }
    
    public GymDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }
    
    public GymDTOBuilder location(String location) {
        this.location = location;
        return this;
    }
    
    public GymDTOBuilder type(PokemonType type) {
        this.type = type;
        return this;
    }
    
    public GymDTOBuilder gymLeader(TrainerDTO gymLeader) {
        this.gymLeader = gymLeader;
        return this;
    }
}
