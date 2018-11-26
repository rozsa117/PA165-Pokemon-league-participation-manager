package cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;

/**
 * Builder class for PokemonSpeciesDTO.
 * 
 * @author Tamás Rózsa 445653
 */
public class PokemonSpeciesDTOBuilder {
    
    private Long id;

    private String speciesName;

    private PokemonType primaryType;

    private PokemonType secondaryType;

    private PokemonSpeciesDTO evolvesFrom;
    
    public PokemonSpeciesDTO build() {
        PokemonSpeciesDTO pokemonSpeciesDTO = new PokemonSpeciesDTO();
        pokemonSpeciesDTO.setId(id);
        pokemonSpeciesDTO.setSpeciesName(speciesName);
        pokemonSpeciesDTO.setPrimaryType(primaryType);
        pokemonSpeciesDTO.setSecondaryType(secondaryType);
        pokemonSpeciesDTO.setEvolvesFrom(evolvesFrom);
        return pokemonSpeciesDTO;
    }
    
    public PokemonSpeciesDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }
    
    public PokemonSpeciesDTOBuilder speciesName(String speciesName) {
        this.speciesName = speciesName;
        return this;
    }
    
    public PokemonSpeciesDTOBuilder primaryType(PokemonType primaryType) {
        this.primaryType = primaryType;
        return this;
    }
    
    public PokemonSpeciesDTOBuilder secondaryType(PokemonType secondaryType) {
        this.secondaryType = secondaryType;
        return this;
    }
    
    public PokemonSpeciesDTOBuilder evolvesFrom(PokemonSpeciesDTO evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
        return this;
    }
}
