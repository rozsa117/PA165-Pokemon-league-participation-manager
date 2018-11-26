package cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import java.time.LocalDate;

/**
 * Builder for TrainerDTO.
 * 
 * @author Tamás Rózsa 445653
 */
public class TrainerDTOBuilder {
    
    private Long id;

    private String userName;

    private String name;

    private String surname;

    private LocalDate born;

    private boolean admin;

    public TrainerDTO build() {
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setId(id);
        trainerDTO.setUserName(userName);
        trainerDTO.setName(name);
        trainerDTO.setSurname(surname);
        trainerDTO.setBorn(born);
        trainerDTO.setAdmin(admin);
        return trainerDTO;
    }
    
    public TrainerDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }
    
    public TrainerDTOBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }
    
    public TrainerDTOBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public TrainerDTOBuilder surname(String surname) {
        this.surname = surname;
        return this;
    }
    
    public TrainerDTOBuilder born(LocalDate born) {
        this.born = born;
        return this;
    }
    
    public TrainerDTOBuilder admin(boolean admin) {
        this.admin = admin;
        return this;
    }
}
