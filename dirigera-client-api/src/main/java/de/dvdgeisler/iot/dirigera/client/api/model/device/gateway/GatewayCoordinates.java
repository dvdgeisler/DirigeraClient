package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

public class GatewayCoordinates {
    public Float latitude;
    public Float longitude;
    public Integer accuracy;

    public GatewayCoordinates(final Float latitude, final Float longitude, final Integer accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
    }

    public GatewayCoordinates() {
    }
}
