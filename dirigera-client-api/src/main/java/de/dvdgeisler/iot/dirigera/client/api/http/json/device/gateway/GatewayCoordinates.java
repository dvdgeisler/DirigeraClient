package de.dvdgeisler.iot.dirigera.client.api.http.json.device.gateway;

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
