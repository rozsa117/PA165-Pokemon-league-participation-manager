package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;

/**
 * Builder class for Pokemon Species.
 *
 * @author Tibor Zauko 433531
 */
public class PokemonSpeciesBuilder {

    private Long id = null;
    private String speciesName = null;
    private PokemonType primaryType = null;
    private PokemonType secondaryType = null;
    private PokemonSpecies evolvesFrom = null;

    public PokemonSpecies build() {
        PokemonSpecies builtSpecies = new PokemonSpecies(id);
        builtSpecies.setPrimaryType(primaryType);
        builtSpecies.setSecondaryType(secondaryType);
        builtSpecies.setSpeciesName(speciesName);
        builtSpecies.setEvolvesFrom(evolvesFrom);
        return builtSpecies;
    }

    public PokemonSpeciesBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PokemonSpeciesBuilder speciesName(String speciesName) {
        this.speciesName = speciesName;
        return this;
    }

    public PokemonSpeciesBuilder primaryType(PokemonType primaryType) {
        this.primaryType = primaryType;
        return this;
    }

    public PokemonSpeciesBuilder secondaryType(PokemonType secondaryType) {
        this.secondaryType = secondaryType;
        return this;
    }

    public PokemonSpeciesBuilder evolvesFrom(PokemonSpecies evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
        return this;
    }

}
