package cz.muni.fi.pa165.pokemon.league.participation.manager.enums.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

/**
 * Generic null tolerating enum deserializer base class.
 *
 * @author Tibor Zauko 433531
 * @param <T> The specialized enum.
 */
public abstract class AbstractGenericTypeAwareEnumDeserializer<T extends Enum<T>> extends JsonDeserializer<T> {

    private final Class<T> type;
    
    protected AbstractGenericTypeAwareEnumDeserializer(Class<T> type) {
        this.type = type;
    }
    
    @Override
    public T deserialize(JsonParser jp, DeserializationContext dc)
            throws IOException, JsonProcessingException {
        String strValue = jp.getValueAsString();
        if (strValue == null) {
            return null;
        }
        T value;
        try {
            value = T.valueOf(type, strValue);
        } catch (IllegalArgumentException ex) {
            value = null;
        }
        return value;
    }
    
}
