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
  public String payload_off;
  public String payload_auto;
  public String payload_manual;
  public String command_topic;
  public String state_off;
  public String state_auto;
  public String state_manual;
  public String state_topic;
  public String value_template;
  public DeviceAvailability availability;

  public AirPurifierConfig(String object_id, String unique_id, String name, Device device,
      String payload_off, String payload_auto, String payload_manual, String command_topic,
      String state_off, String state_auto, String state_manual, String state_topic,
      String value_template, DeviceAvailability availability) {
    this.object_id = object_id;
    this.unique_id = unique_id;
    this.name = name;
    this.device = device;
    this.payload_off = payload_off;
    this.payload_auto = payload_auto;
    this.payload_manual = payload_manual;
    this.command_topic = command_topic;
    this.state_off = state_off;
    this.state_auto = state_auto;
    this.state_manual = state_manual;
    this.state_topic = state_topic;
    this.value_template = value_template;
    this.availability = availability;
  }

  public AirPurifierConfig() {
  }
}
