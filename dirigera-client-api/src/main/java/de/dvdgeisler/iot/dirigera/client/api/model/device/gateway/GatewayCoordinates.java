package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

public class GatewayCoordinates {
    public Float latitude;
    public Float longitude;

    public GatewayCoordinates(final Float latitude, final Float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GatewayCoordinates() {
    }
}
