package cz.muni.fi.pa165.pokemon.league.participation.manager.enums.deserializers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;

/**
 * Null tolerating deserializer for PokemonType enum.
 *
 * @author Tibor Zauko 433531
 */
public class PokemonTypeEnumDeserializer extends AbstractGenericTypeAwareEnumDeserializer<PokemonType> {

    public PokemonTypeEnumDeserializer() {
        super(PokemonType.class);
    }
    
}
