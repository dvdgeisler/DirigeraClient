package de.dvdgeisler.iot.dirigera.client.examples.websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Opens a websocket
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class WebSockets {
    private final static Logger log = LoggerFactory.getLogger(WebSockets.class);

    @Bean
    public CommandLineRunner run(final DirigeraApi api, final ClientApi capi, final ObjectMapper json) {

        return (String... args) -> {
            api.pairIfRequired().block(); // pair gateway if required

            api.websocket(event -> {
                try {
                    log.debug("Received websocket message: {}", json.writeValueAsString(event));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(WebSockets.class, args);
    }


}
