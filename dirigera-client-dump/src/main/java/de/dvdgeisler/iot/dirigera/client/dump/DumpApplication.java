package de.dvdgeisler.iot.dirigera.client.dump;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Dump everything
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {ClientApi.class})
public class DumpApplication {
    private final static Logger log = LoggerFactory.getLogger(DumpApplication.class);
    private final ObjectWriter writer;

    public DumpApplication(final ObjectMapper objectMapper) {
        this.writer = objectMapper.writerWithDefaultPrettyPrinter();
    }

    @Bean
    public CommandLineRunner run(final ClientApi api) {
        return (String... args) -> {
            api.oauth.pairIfRequired().block();

            api.dump()
                    .map(this::deleteSensitiveData)
                    .flatMap(this::toJSON)
                    .doOnSuccess(System.out::println)
                    .doOnError(err -> log.error(err.getMessage()))
                    .block();
        };
    }

    private Map deleteSensitiveData(final Map map) {
        map.remove("users");
        map.remove("user");
        return map;
    }

    private Mono<String> toJSON(final Object o) {
        try {
            return Mono.just(this.writer.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(DumpApplication.class, args).close();
    }


}
