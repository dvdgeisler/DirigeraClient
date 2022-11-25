package de.dvdgeisler.iot.dirigera.client.examples.firmwareupgrade;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

import static de.dvdgeisler.iot.dirigera.client.api.model.device.ota.OtaState.*;

/**
 *
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class FirmwareUpgrade {
    private final static Logger log = LoggerFactory.getLogger(FirmwareUpgrade.class);

    @Bean
    public CommandLineRunner runListDevices(final DirigeraApi api) {
        return (String... args) -> {
            final GatewayStatus status;

            api.pairIfRequired().block();

            status = api.status()
                    .filter(s -> s.attributes.ota.state == READY_TO_CHECK)
                    .switchIfEmpty(Mono.error(new RuntimeException("Gateway not ready")))
                    .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1000)))
                    .then(api.checkFirmwareUpdate())
                    .then(api.status())
                    .filter(s -> s.attributes.ota.state != CHECK_IN_PROGRESS)
                    .switchIfEmpty(Mono.error(new RuntimeException("Checking for new firmware timed out")))
                    .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1000)))
                    .block();
            if(status.attributes.ota.state == READY_TO_DOWNLOAD || status.attributes.ota.state == READY_TO_UPDATE) {
                log.info("Update firmware: status={}", status.attributes.ota.state);
                api.installFirmwareUpdate().block();
            } else
                log.info("Firmware not updated: status={}", status.attributes.ota.state);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(FirmwareUpgrade.class, args).close();
    }


}
