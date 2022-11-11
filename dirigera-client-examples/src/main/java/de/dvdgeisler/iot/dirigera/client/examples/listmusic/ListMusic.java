package de.dvdgeisler.iot.dirigera.client.examples.listmusic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraClientApi;
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
@ComponentScan(basePackageClasses = {DirigeraClientApi.class})
public class ListMusic {
    private final static Logger log = LoggerFactory.getLogger(ListMusic.class);

    @Bean
    public CommandLineRunner run(final DirigeraClientApi api) {
        return (String... args) -> {
            api.oauth.pairIfRequired().block();

            api.music.music().doOnSuccess(music -> {
                log.info("Playlists: [{}]", String.join(", ", music.playlists));
                log.info("Favorites: [{}]", String.join(", ", music.favorites));
            }).block();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ListMusic.class, args).close();
    }


}
