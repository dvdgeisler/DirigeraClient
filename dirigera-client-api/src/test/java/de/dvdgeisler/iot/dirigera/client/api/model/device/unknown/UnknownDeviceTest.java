package de.dvdgeisler.iot.dirigera.client.api.model.device.unknown;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UnknownDeviceTest extends DeviceTest {
    final static String JSON = "{\"id\" : \"123\"}";

    public UnknownDeviceTest() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertTrue(device instanceof UnknownDevice);
    }
}