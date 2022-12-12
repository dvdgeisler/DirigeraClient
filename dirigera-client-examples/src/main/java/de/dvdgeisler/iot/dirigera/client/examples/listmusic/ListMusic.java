package de.dvdgeisler.iot.dirigera.client.examples.listmusic;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.ClientApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.stream.Collectors;

/**
 * Dump everything
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {ClientApi.class})
public class ListMusic {
    private final static Logger log = LoggerFactory.getLogger(ListMusic.class);

    @Bean
    public CommandLineRunner run(final ClientApi api) {
        return (String... args) -> {
            api.oauth.pairIfRequired().block();

            api.music.music().doOnSuccess(music -> {
                log.info("Playlists: [{}]", music.playlists
                        .stream()
                        .map(p->p.title)
                        .collect(Collectors.joining(", ")));
                log.info("Favorites: [{}]", music.favorites
                        .stream()
                        .map(p->p.title)
                        .collect(Collectors.joining(", ")));
            }).block();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ListMusic.class, args).close();
    }


}
