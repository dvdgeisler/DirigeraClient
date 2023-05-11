package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.airpurifier;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirPurifierConfig {
  public String object_id;
  public String unique_id;
  public String name;
  public Device device;
  public String payload_low;
  public String payload_medium;
  public String payload_high;
  public String payload_auto;
  public String command_topic;
  public String state_low;
  public String state_medium;
  public String state_high;
  public String state_auto;
  public String state_topic;
  public String value_template;
  public DeviceAvailability availability;

  public AirPurifierConfig(String object_id, String unique_id, String name, Device device,
      String payload_low, String payload_auto, String payload_medium, String command_topic,
      String state_low, String state_auto, String state_medium, String state_topic,
      String value_template, DeviceAvailability availability) {
    this.object_id = object_id;
    this.unique_id = unique_id;
    this.name = name;
    this.device = device;
    this.payload_low = payload_low;
    this.payload_auto = payload_auto;
    this.payload_medium = payload_medium;
    this.command_topic = command_topic;
    this.state_low = state_low;
    this.state_auto = state_auto;
    this.state_medium = state_medium;
    this.state_topic = state_topic;
    this.value_template = value_template;
    this.availability = availability;
  }

  public AirPurifierConfig() {
  }
}
