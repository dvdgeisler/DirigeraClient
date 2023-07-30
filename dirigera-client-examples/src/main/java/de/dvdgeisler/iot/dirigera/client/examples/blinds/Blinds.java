package de.dvdgeisler.iot.dirigera.client.examples.blinds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.examples.redlightdistrict.RedlightDistrict;
import reactor.core.publisher.Flux;

@SpringBootApplication
@ComponentScan(basePackageClasses = { DirigeraApi.class })
public class Blinds {

	private final static Logger log = LoggerFactory.getLogger(RedlightDistrict.class);

	@Bean
	CommandLineRunner runBlindsHalfWay(final DirigeraApi api) {
		return (String... args) -> {
			api.device.blinds.all()
					.flatMapMany(Flux::fromIterable)
					.doOnNext(d -> log.info(
							"Found blinds '{}': id={}, blindsCurrentLevel={}, blindsState={}, blindsTargetLevel={}",
							d.id, d.attributes.state.customName, d.attributes.state.blindsCurrentLevel,
							d.attributes.state.blindsState, d.attributes.state.blindsTargetLevel))
					.flatMap(device -> api.device.blinds.setTargetLevel(device, 50))
					.blockLast();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Blinds.class, args).close();
	}

}
