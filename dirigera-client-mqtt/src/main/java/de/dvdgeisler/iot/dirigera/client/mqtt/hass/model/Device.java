package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device {
    public List<String> identifiers;
    public String manufacturer;
    public String model;
    public String hw_version;
    public String sw_version;
    public String name;
    public String via_device;

    public Device(final List<String> identifiers, final String manufacturer, final String model, final String hw_version, final String sw_version, final String name, final String via_device) {
        this.identifiers = identifiers;
        this.manufacturer = manufacturer;
        this.model = model;
        this.hw_version = hw_version;
        this.sw_version = sw_version;
        this.name = name;
        this.via_device = via_device;
    }

    public Device() {
    }
}
