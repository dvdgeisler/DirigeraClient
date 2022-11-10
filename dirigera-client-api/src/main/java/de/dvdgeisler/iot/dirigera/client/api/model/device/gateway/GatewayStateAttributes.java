package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class GatewayStateAttributes {
    public String customName;
    public Boolean permittingJoin;
    public List<Map<String, String>> userConsents;
    public Integer logLevel;
    public LocalTime time;
    public TimeZone timezone;
    public String countryCode;
    public GatewayCoordinates coordinates;

    public GatewayStateAttributes(final String customName, final Boolean permittingJoin, final List<Map<String, String>> userConsents, final Integer logLevel, final LocalTime time, final TimeZone timezone, final String countryCode, final GatewayCoordinates coordinates) {
        this.customName = customName;
        this.permittingJoin = permittingJoin;
        this.userConsents = userConsents;
        this.logLevel = logLevel;
        this.time = time;
        this.timezone = timezone;
        this.countryCode = countryCode;
        this.coordinates = coordinates;
    }

    public GatewayStateAttributes() {
    }
}
