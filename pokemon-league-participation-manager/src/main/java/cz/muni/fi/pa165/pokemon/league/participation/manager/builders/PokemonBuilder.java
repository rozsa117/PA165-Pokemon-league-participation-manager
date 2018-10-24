package cz.muni.fi.pa165.pokemon.league.participation.manager.builders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import java.time.LocalDateTime;

/**
 * Builder class for Pokemon.
 *
 * @author Jiří Medveď 38451
 */
public class PokemonBuilder {

    private Long id;
    private PokemonSpecies species;
    private String nickname;
    private int level;
    private Trainer trainer;
    private LocalDateTime dateTimeOfCapture;

    public Pokemon build() {
        Pokemon pokemon = new Pokemon(id);
        pokemon.setSpecies(species);
        pokemon.setNickname(nickname);
        pokemon.setLevel(level);
        pokemon.setTrainer(trainer);
        pokemon.setDateTimeOfCapture(dateTimeOfCapture);
        return pokemon;
    }

    public PokemonBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PokemonBuilder pokemonSpecies(PokemonSpecies species) {
        this.species = species;
        return this;
    }

    public PokemonBuilder nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public PokemonBuilder level(int level) {
        this.level = level;
        return this;
    }

    public PokemonBuilder trainer(Trainer trainer) {
        this.trainer = trainer;
        return this;
    }

    public PokemonBuilder dateTimeOfCapture(LocalDateTime dateTimeOfCapture) {
        this.dateTimeOfCapture = dateTimeOfCapture;
        return this;
    }

}
