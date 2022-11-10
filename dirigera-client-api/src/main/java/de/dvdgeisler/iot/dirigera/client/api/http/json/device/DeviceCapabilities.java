package de.dvdgeisler.iot.dirigera.client.api.http.json.device;

import java.util.List;

public class DeviceCapabilities {
    public List<String> canReceive;
    public List<String> canSend;

    public DeviceCapabilities() {
    }

    public DeviceCapabilities(final List<String> canReceive, final List<String> canSend) {
        this.canReceive = canReceive;
        this.canSend = canSend;
    }
}
