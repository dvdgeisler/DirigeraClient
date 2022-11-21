package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum DeviceState {
    ON,
    OFF;

}
