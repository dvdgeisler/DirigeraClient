package de.dvdgeisler.iot.dirigera.client.examples.dump;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;

/**
 * Dump everything
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class Dump {
    private final static Logger log = LoggerFactory.getLogger(Dump.class);
    private final ObjectWriter writer;

    public Dump(final ObjectMapper objectMapper) {
        this.writer = objectMapper.writerWithDefaultPrettyPrinter();
    }

    @Bean
    public CommandLineRunner run(final DirigeraApi api) {
        return (String... args) -> {
            api.pairIfRequired().block();

            api.dump()
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
        SpringApplication.run(Dump.class, args).close();
    }


}
