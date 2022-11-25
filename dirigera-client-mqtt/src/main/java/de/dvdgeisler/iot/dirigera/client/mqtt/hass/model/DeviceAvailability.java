package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model;


public class DeviceAvailability {
    public String topic;
    public String payload_available;
    public String payload_not_available;

    public DeviceAvailability(final String topic, final String payload_available, final String payload_not_available) {
        this.topic = topic;
        this.payload_available = payload_available;
        this.payload_not_available = payload_not_available;
    }

    public DeviceAvailability() {
    }
}
