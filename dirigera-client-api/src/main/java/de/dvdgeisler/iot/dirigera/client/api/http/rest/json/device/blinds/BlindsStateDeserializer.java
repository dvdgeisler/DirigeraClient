package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.blinds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

/**
 * Enables deserialization of empty string into a BlindsState (results into null).
 *
 * @Todo: This is a workaround to make the blinds controller properly deserializing the
 * json dump, reported in issue https://github.com/dvdgeisler/DirigeraClient/issues/19
 */
public class BlindsStateDeserializer extends JsonDeserializer<BlindsState> {
    private final static Logger log = LoggerFactory.getLogger(BlindsStateDeserializer.class);

    @Override
    public BlindsState deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JacksonException {
        final String value;
        Field enumField;
        String name;

        value = p.getValueAsString();
        if (value == null)
            return null;

        try {
            for (BlindsState blindsState : BlindsState.values()) {
                enumField = BlindsState.class.getField(blindsState.name());
                name = Optional.of(enumField)
                        .map(field -> field.getAnnotation(JsonProperty.class))
                        .map(JsonProperty::value)
                        .orElse(enumField.getName());

                if (Objects.equals(name, value))
                    return blindsState;
            }
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage());
        }

        log.warn("Cannot deserialize BlindState \"{}\"", value);
        return null;
    }
}
