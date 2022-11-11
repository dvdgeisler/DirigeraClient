package de.dvdgeisler.iot.dirigera.client.api.model.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class DeviceTest {
    protected final String json;
    protected ObjectMapper objectMapper;

    public DeviceTest(final String json) {
        this.json = json;
    }

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules();
    }

    @Test
    public void deserialize() throws JsonProcessingException {
        final Device<?> device;

        device = this.objectMapper.readValue(this.json, Device.class);
        this.validateDeserialize(device);
    }

    public abstract void validateDeserialize(final Device<?> device);
}
