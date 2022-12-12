package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.ota.OtaAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceAttributes;

import java.time.LocalDateTime;

public class GatewayAttributes extends DeviceAttributes<GatewayStateAttributes> {
    public LocalDateTime identifyStarted;
    public Integer identifyPeriod;
    public Boolean coredump;
    public Boolean isOn;

    @JsonUnwrapped
    public OtaAttributes ota;

    @JsonUnwrapped
    public GatewayBackendAttributes backend;

    public GatewayAttributes(
            final String model,
            final String manufacturer,
            final String firmwareVersion,
            final String hardwareVersion,
            final String serialNumber,
            final LocalDateTime identifyStarted,
            final Integer identifyPeriod,
            final Boolean coredump,
            final Boolean isOn,
            final GatewayStateAttributes state,
            final OtaAttributes ota,
            final GatewayBackendAttributes backend) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.identifyStarted = identifyStarted;
        this.identifyPeriod = identifyPeriod;
        this.coredump = coredump;
        this.isOn = isOn;
        this.ota = ota;
        this.backend = backend;
    }

    public GatewayAttributes() {
    }
}
