package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.EnvironmentSensorDeviceApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor.EnvironmentSensorAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor.EnvironmentSensorDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.environmentsensor.EnvironmentSensorConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class HassEnvironmentSensorEventHandler extends HassDeviceEventHandler<EnvironmentSensorDevice> {

   private static final String HASS_COMPONENT = "sensor";
   private static final String TOPIC_PM25 = "pm25";
   private static final String TOPIC_HUMIDITY = "humidity";
   private static final String TOPIC_TEMPERATURE = "temperature";
   private static final String TOPIC_VOCINDEX = "vocindex";
   private static final String DEFAULT_VALUE = "{{value}}";

   private final EnvironmentSensorDeviceApi api;


   public HassEnvironmentSensorEventHandler(
            final MqttClient mqtt,
            final DirigeraApi api,
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}")
            final String topicPrefix,
            final ObjectMapper objectMapper) {
      super(mqtt, api, EnvironmentSensorDevice.class, topicPrefix, objectMapper);

      this.api = api.device.environmentSensor;
      Objects.requireNonNull(this.api.all()
                        .block())
               .forEach(this::onDeviceCreated);
   }

   @Override
   protected void onDeviceCreated(EnvironmentSensorDevice device) {
      final EnvironmentSensorConfig config;

      config = new EnvironmentSensorConfig();
      config.unique_id = config.object_id = device.id;
      config.name = getDefaultName(device);
      config.device = this.getDeviceConfig(device);

      config.command_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_SET);
      config.state_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_STATE);
      config.schema = "json";

      config.currentTemperature = DEFAULT_VALUE;
      config.currentRH = DEFAULT_VALUE;
      config.currentPM25 = DEFAULT_VALUE;
      config.maxMeasuredPM25 = DEFAULT_VALUE;
      config.minMeasuredPM25 = DEFAULT_VALUE;
      config.vocIndex = DEFAULT_VALUE;
      config.value_template = DEFAULT_VALUE;

      config.availability = new DeviceAvailability();
      config.availability.topic = this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY);

      config.availability.payload_available = this.toJSON(DeviceAvailabilityState.ONLINE);
      config.availability.payload_not_available = this.toJSON(DeviceAvailabilityState.OFFLINE);
      this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_CONFIG), config);
      this.onDeviceStateChanged(device);
   }

   @Override
   protected void onDeviceStateChanged(EnvironmentSensorDevice device) {
      getAttributes(device).ifPresent(state ->
               this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_STATE), state));

      getCurrentPM25(device).ifPresent(value ->
               this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_PM25), value));
      getCurrentRH(device).ifPresent(value ->
               this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_HUMIDITY), value));
      getCurrentTemperature(device).ifPresent(value ->
               this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_TEMPERATURE), value));
      getVocIndex(device).ifPresent(value ->
               this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_VOCINDEX), value));
      getAvailability(device).ifPresent(s ->
               this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), s));
   }

   @Override
   protected void onDeviceRemoved(EnvironmentSensorDevice device) {
      this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), DeviceAvailabilityState.OFFLINE);
      this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_REMOVE), null);
      this.unsubscribe(this.getTopic(device, HASS_COMPONENT, TOPIC_SET));
   }

   public static Optional<EnvironmentSensorAttributes> getAttributes(final EnvironmentSensorDevice device) {
      return Optional.of(device)
               .map(d->d.attributes);
   }

   public static Optional<Integer> getCurrentPM25(final EnvironmentSensorDevice device) {
      return Optional.of(device)
               .map(d->d.attributes)
               .map(d->d.currentPM25);
   }

   public static Optional<Integer> getCurrentRH(final EnvironmentSensorDevice device) {
      return Optional.of(device)
               .map(d->d.attributes)
               .map(d->d.currentRH);
   }

   public static Optional<Integer> getCurrentTemperature(final EnvironmentSensorDevice device) {
      return Optional.of(device)
               .map(d->d.attributes)
               .map(d->d.currentTemperature);
   }

   public static Optional<Integer> getVocIndex(final EnvironmentSensorDevice device) {
      return Optional.of(device)
               .map(d->d.attributes)
               .map(d->d.vocIndex);
   }
}
