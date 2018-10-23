/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.builders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
/**
 * Builder class for Gym.
 * 
 * @author Tamás Rózsa 445653
 */
public class GymBuilder {
    
    private Long id = null;
    private String location = null;
    private PokemonType type = null;
    private Trainer gymLeader = null;
    
    public Gym build() {
        Gym gym = new Gym(id);
        gym.setLocation(location);
        gym.setType(type);
        gym.setGymLeader(gymLeader);
        return gym;
    }
    
    public GymBuilder id(Long id) {
        this.id = id;
        return this;
    }
    public GymBuilder location(String location) {
        this.location = location;
        return this;
    }
    public GymBuilder type(PokemonType type) {
        this.type = type;
        return this;
    }
    public GymBuilder gymLeader(Trainer gymLeader) {
        this.gymLeader = gymLeader;
        return this;
    }
}
