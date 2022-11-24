package de.dvdgeisler.iot.dirigera.client.examples.listdevices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Lists all known devices
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class ListDevices {
    private final static Logger log = LoggerFactory.getLogger(ListDevices.class);
    private final ObjectWriter writer;

    public ListDevices(final ObjectMapper objectMapper) {
        this.writer = objectMapper.writerWithDefaultPrettyPrinter();
    }

    @Bean
    public CommandLineRunner runListDevices(final DirigeraApi api) {
        return (String... args) -> {
            api.pairIfRequired().block();

            api.device.all() // fetch all devices from hub
                    .flatMapMany(Flux::fromIterable)
                    .map(device -> Map.of(
                            "apiStatus", Device.class.equals(device.getClass()) ? "Not supported" : "Supported",
                            "model", device.getClass(),
                            "data", device))
                    .collectList()
                    .flatMap(this::toJSON)
                    .doOnSuccess(log::info)
                    .doOnError(err -> log.error(err.getMessage()))
                    .block();
        };
    }

    private Mono<String> toJSON(final Object o) {
        try {
            return Mono.just(this.writer.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ListDevices.class, args).close();
    }


}
