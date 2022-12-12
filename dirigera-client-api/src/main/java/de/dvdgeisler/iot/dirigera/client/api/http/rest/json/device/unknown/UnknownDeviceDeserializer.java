package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.unknown;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Deserializes an unknown device into an {@link UnknownDevice} instance. Furthermore, a corresponding record is created
 * in the {@link UnknownDeviceCollector} for analytical purposes.
 */
public class UnknownDeviceDeserializer extends StdDeserializer<UnknownDevice> {

    /**
     * Extends an {@link UnknownDevice} to overwrite its Jackson deserialization strategy
     */
    @JsonDeserialize(using = JsonDeserializer.None.class)
    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private static class UnknownDeviceWrapper extends UnknownDevice {
    }

    protected UnknownDeviceDeserializer() {
        super(UnknownDevice.class);
    }

    @Override
    public UnknownDevice deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        final JsonNode jsonNode;
        final UnknownDeviceWrapper defaultDevice;

        jsonNode = ctxt.readTree(p);
        defaultDevice = ctxt.readTreeAsValue(jsonNode, UnknownDeviceWrapper.class);
        UnknownDeviceCollector.instance.add(defaultDevice, jsonNode);

        return defaultDevice;
    }
}
