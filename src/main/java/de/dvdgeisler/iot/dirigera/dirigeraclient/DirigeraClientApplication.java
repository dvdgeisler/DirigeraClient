package de.dvdgeisler.iot.dirigera.dirigeraclient;

import de.dvdgeisler.iot.dirigera.dirigeraclient.http.DirigeraRequestException;
import de.dvdgeisler.iot.dirigera.dirigeraclient.http.HttpsTrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class DirigeraClientApplication implements CommandLineRunner {
    private final static Logger log = LoggerFactory.getLogger(DirigeraClientApplication.class);

    private final DirigeraApi dirigeraApi;

    public DirigeraClientApplication(final DirigeraApi dirigeraApi) {
        this.dirigeraApi = dirigeraApi;

        try {
            HttpsTrustManager.allowAllSSL();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public CommandLineRunner run() {
        return this;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("Request authorization code");
        this.dirigeraApi.authorize();
        do {

            try {

            log.debug("Request authorization token");
            this.dirigeraApi.tokenExchange();
            } catch (DirigeraRequestException e) {
                log.error(e.getMessage());
                Thread.sleep(500);
                continue;
            }
            break;

        } while (true);
    }

    public static void main(String[] args) {
        SpringApplication.run(DirigeraClientApplication.class, args);
    }
}
