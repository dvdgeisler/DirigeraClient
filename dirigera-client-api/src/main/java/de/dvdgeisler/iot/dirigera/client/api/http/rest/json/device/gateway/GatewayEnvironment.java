package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway;

public class GatewayEnvironment {
    public static final GatewayEnvironment DEV = new GatewayEnvironment("dev");
    public static final GatewayEnvironment ALPHA = new GatewayEnvironment("alpha");

    public String environment;

    public GatewayEnvironment(final String environment) {
        this.environment = environment;
    }

    public GatewayEnvironment() {
    }
}
