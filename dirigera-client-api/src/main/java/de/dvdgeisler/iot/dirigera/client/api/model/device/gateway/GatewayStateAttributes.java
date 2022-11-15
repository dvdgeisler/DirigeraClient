package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStateAttributes;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GatewayStateAttributes extends DeviceStateAttributes {
    public Boolean permittingJoin;
    public List<GatewayUserConsent> userConsents;
    public Integer logLevel;
    public LocalTime time;
    public TimeZone timezone;
    public String countryCode;
    public GatewayCoordinates coordinates;

    public GatewayStateAttributes(final String customName, final Boolean permittingJoin, final List<GatewayUserConsent> userConsents, final Integer logLevel, final LocalTime time, final TimeZone timezone, final String countryCode, final GatewayCoordinates coordinates) {
        super(customName);
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
