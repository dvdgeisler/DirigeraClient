package de.dvdgeisler.iot.dirigera.client.examples.homekit;

import de.dvdgeisler.iot.dirigera.client.api.http.homekit.AuthenticationStore;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.HomekitApi;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.HomekitPairSetupApi;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.HomekitPairVerificationApi;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto.EdDSASigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;

/**
 *
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {HomekitPairSetupApi.class, EdDSASigner.class})
public class HomeKit {
    private final static Logger log = LoggerFactory.getLogger(HomeKit.class);

    private final HomekitPairVerificationApi pairVerificationApi;
    private final AuthenticationStore astore;

    public HomeKit(final HomekitPairVerificationApi pairVerificationApi,
                   final AuthenticationStore astore) {
        this.pairVerificationApi = pairVerificationApi;
        this.astore = astore;
    }

    @Bean
    public CommandLineRunner run(final HomekitApi api, final HomekitPairVerificationApi pvApi) {
        return (String... args) -> {

            pvApi.getCryptographer().block();
            Map res = api.test();

            System.out.println();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(HomeKit.class, args).close();
    }


}
