package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceAttributes<_DeviceStateAttributes extends DeviceStateAttributes> {
    public String model;
    public String manufacturer;
    public String firmwareVersion;
    public String hardwareVersion;
    public String serialNumber;

    @JsonUnwrapped
    public _DeviceStateAttributes state;

    public DeviceAttributes() {
    }

    public DeviceAttributes(final String model,
                            final String manufacturer,
                            final String firmwareVersion,
                            final String hardwareVersion,
                            final String serialNumber,
                            final _DeviceStateAttributes state) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.firmwareVersion = firmwareVersion;
        this.hardwareVersion = hardwareVersion;
        this.serialNumber = serialNumber;
        this.state = state;
    }
}
