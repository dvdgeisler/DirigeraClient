package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.environmentsensor;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnvironmentSensorConfig {

   public String object_id;
   public String unique_id;
   public String name;
   public Device device;

   public String command_topic;
   public String state_topic;
   public String schema;

   public String currentTemperature;
   public String currentRH;
   public String currentPM25;
   public String maxMeasuredPM25;
   public String minMeasuredPM25;
   public String vocIndex;
   public String identifyStarted;
   public String identifyPeriod;

   public String value_template;
   public DeviceAvailability availability;

   public EnvironmentSensorConfig(String object_id, String unique_id, String name, Device device,
            String currentTemperature, String currentRH, String currentPM25, String maxMeasuredPM25,
            String minMeasuredPM25, String vocIndex, String identifyStarted, String identifyPeriod,
            String value_template, DeviceAvailability availability, final String command_topic, final String state_topic, final String schema) {
      this.object_id = object_id;
      this.unique_id = unique_id;
      this.name = name;
      this.device = device;

      this.currentTemperature=currentTemperature;
      this.currentRH=currentRH;
      this.currentPM25=currentPM25;
      this.maxMeasuredPM25=maxMeasuredPM25;
      this.minMeasuredPM25=minMeasuredPM25;
      this.vocIndex=vocIndex;
      this.identifyStarted=identifyStarted;
      this.identifyPeriod=identifyPeriod;

      this.value_template = value_template;
      this.availability = availability;
      this.command_topic = command_topic;
      this.state_topic = state_topic;
      this.schema = schema;
   }

   public EnvironmentSensorConfig() {
   }

}
