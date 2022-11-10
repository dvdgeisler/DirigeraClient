package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

public class GatewayPersistentMode {
    public final static GatewayPersistentMode ACTIVE = new GatewayPersistentMode(true);
    public final static GatewayPersistentMode INACTIVE = new GatewayPersistentMode(false);
    public boolean isActive;

    public GatewayPersistentMode(final boolean isActive) {
        this.isActive = isActive;
    }

    public GatewayPersistentMode() {
    }
}
