package de.dvdgeisler.iot.dirigera.client.mqtt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.events.Event;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class MqttEventHandler<E extends Event> {
    private final static Logger log = LoggerFactory.getLogger(MqttEventHandler.class);
    private final MqttClient mqtt;
    private final DirigeraApi api;
    private final ObjectMapper objectMapper;

    public MqttEventHandler(
            final MqttClient mqtt,
            final DirigeraApi api,
            final Class<E> eventType, final ObjectMapper objectMapper) {
        this.mqtt = mqtt;
        this.api = api;
        this.objectMapper = objectMapper;

        this.api.pairIfRequired().block();

        log.info("Subscribe {} to Dirigera websocket: event={}", this.getClass().getSimpleName(), eventType.getSimpleName());
        this.api.websocket(this::onDirigeraEvent, eventType);
    }

    protected MqttClient getMqtt() {
        return this.mqtt;
    }

    protected DirigeraApi getApi() {
        return this.api;
    }

    protected <T> void publish(final String topic, final T payload, final int qos, final boolean retained) {
        final String json;

        json = this.toJSON(payload);
        try {
            log.debug("Publish to MQTT: topic={}, payload={}, qos={}, retained={}", topic, json, qos, retained);
            this.mqtt.publish(topic, json.getBytes(StandardCharsets.UTF_8), qos, retained);
        } catch (MqttException e) {
            log.error(e.getMessage());
        }
    }

    protected <T> void publish(final String topic, final T payload) {
        this.publish(topic, payload, 1, true);
    }

    protected <T> void subscribe(final String topic, final Class<T> payloadType, final Consumer<T> consumer) {
        log.debug("Subscribe to MQTT topic: topic={}, payload={}", topic, payloadType.getSimpleName());
        try {
            this.mqtt.subscribe(topic, (t, message) -> {
                log.debug("MQTT message received: topic={}, payload={}", t, this.toJSON(message));
                consumer.accept(this.fromJSON(message.getPayload(), payloadType));
            });
        } catch (MqttException e) {
            log.error(e.getMessage());
        }
    }

    protected void unsubscribe(final String topic) {
        log.debug("Unsubscribe from MQTT topic: topic={}", topic);
        try {
            this.mqtt.unsubscribe(topic);
        } catch (MqttException e) {
            log.error(e.getMessage());
        }
    }

    protected abstract void onDirigeraEvent(final E event);

    protected <T> String toJSON(final T object) {
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    protected <T> T fromJSON(final String s, final Class<T> type) {
        String es;
        if (type.isEnum()) {
            for (final T e : type.getEnumConstants()) {
                es = Optional.of(e)
                        .map(c -> c.getClass())
                        .map(c -> c.getAnnotation(JsonProperty.class))
                        .map(JsonProperty::value)
                        .orElse(String.valueOf(e));
                if (Objects.equals(es, s))
                    return e;
            }
        }
        try {
            return this.objectMapper.readValue(s, type);
        } catch (final JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    protected <T> T fromJSON(final byte[] s, final Class<T> type) {
        return this.fromJSON(new String(s), type);
    }

}
