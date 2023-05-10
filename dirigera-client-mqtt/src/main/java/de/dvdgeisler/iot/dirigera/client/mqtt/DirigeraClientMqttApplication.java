package de.dvdgeisler.iot.dirigera.client.mqtt;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.HassLightDeviceEventHandler;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.HassOutletDeviceEventHandler;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        DirigeraApi.class,
        HassLightDeviceEventHandler.class,
        HassOutletDeviceEventHandler.class})
public class DirigeraClientMqttApplication implements MqttCallback {
    private final static Logger log = LoggerFactory.getLogger(DirigeraClientMqttApplication.class);

    private final static int EXIT_SUCCESS = 0;
    private final static int EXIT_ERROR = 1;
    private final DirigeraApi api;
    private ConfigurableApplicationContext context;

    public DirigeraClientMqttApplication(
            final DirigeraApi api,
            final ConfigurableApplicationContext context) {
        this.api = api;
        this.context = context;
    }

    @Bean
    public MqttClient getMqttClient(@Value("${dirigera.mqtt.hostname:localhost}") final String host,
                                    @Value("${dirigera.mqtt.port:1883}") final Short port,
                                    @Value("${dirigera.mqtt.username:}") final String username,
                                    @Value("${dirigera.mqtt.password:}") final String password,
                                    @Value("${dirigera.mqtt.reconnect:true}") final Boolean reconnect,
                                    @Value("${dirigera.mqtt.timeout:0}") final Integer timeout,
                                    @Value("${dirigera.mqtt.keep-alive:2}") final Integer keepAliveInterval,
                                    @Value("${dirigera.mqtt.reconnect-delay:1}") final Integer reconnectDelay,
                                    final DirigeraApi api) throws MqttException {
        final MqttConnectOptions options;
        final MqttClient client;
        final String publisherId;
        final String uri;

        publisherId = api.status().map(s -> s.id).block();
        uri = String.format("tcp://%s:%d", host, port);
        client = new MqttClient(uri, publisherId);

        log.info("Connect to MQTT broker: host={}, port={}, publisherId={}, reconnect={}, timeout={}",
                host, port, publisherId, reconnect, timeout);

        options = new MqttConnectOptions();
        options.setKeepAliveInterval(keepAliveInterval);
        options.setMaxReconnectDelay(reconnectDelay);
        options.setAutomaticReconnect(reconnect);
        options.setConnectionTimeout(timeout);

        if (!username.isEmpty() && !password.isEmpty()) {
            options.setUserName(username);
            options.setPassword(password.toCharArray());
        }
        client.setCallback(this);

        try {
            client.connect(options);
        } catch (MqttException e) {
            log.error("Error while connecting to MQTT broker: {}", e.getMessage());
            this.exit(EXIT_ERROR);
        }

        log.info("Connection to MQTT broker successfully established");

        return client;
    }

    public static void main(String[] args) {
        SpringApplication.run(DirigeraClientMqttApplication.class, args);
    }

    public void exit(int status) {
        final ApplicationArguments args;

        log.info("Close WebSocket");
        this.api.websocket.stop();

        if (context != null && status == EXIT_ERROR) {
            log.info("Attempt to restart application");

            args = this.context.getBean(ApplicationArguments.class);

            Thread thread = new Thread(() -> {
                log.info("Close Spring Boot context");
                this.context.close();
                main(args.getSourceArgs());
            });

            thread.setDaemon(false);
            thread.start();
        } else {
            log.info("Exit application");
            Runtime.getRuntime().halt(status);
        }
    }

    @Override
    public void connectionLost(final Throwable cause) {
        log.error("Connection lost to MQTT broker: {}", cause.getMessage());
        exit(EXIT_ERROR);
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) {
        log.debug("Received message from MQTT: topic={}, message={}", topic, message);
    }

    @Override
    public void deliveryComplete(final IMqttDeliveryToken token) {
    }
}
