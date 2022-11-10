package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.model.auth.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class TokenStore {
    private final static Logger log = LoggerFactory.getLogger(TokenStore.class);
    private final String tokenFile;
    private String accessToken;

    public TokenStore(@Value("${dirigera.token.store:dirigera_access_token}") final String tokenFile) {
        this.tokenFile = tokenFile;
        try {
            log.info("Load access token");
            this.loadAccessToken();
        } catch (IOException e) {
            log.error("Cannot read access token: {}", e.getMessage());
        }
    }

    public boolean hasAccessToken() {
        return this.accessToken != null && !this.accessToken.isBlank();
    }

    public void setAccessToken(final Token token) {
        this.accessToken = token.access_token;
        try {
            this.saveAccessToken();
        } catch (IOException e) {
            log.error("Error while saving new access token {}: {}", token.access_token, e.getMessage());
        }
    }

    public String getAccessToken() throws IOException {
        if(this.accessToken == null)
            throw new IOException("No access token available");
        return this.accessToken;
    }

    public void setBearerAuth(final HttpHeaders httpHeaders) {
        try {
            httpHeaders.setBearerAuth(this.getAccessToken());
        } catch (final IOException e) {
            log.error("Cannot set Bearer-Auth token in HTTP header: {}", e.getMessage());
        }
    }

    private void saveAccessToken() throws IOException {
        final BufferedWriter writer;

        writer = new BufferedWriter(new FileWriter(this.tokenFile));
        writer.write(this.accessToken);
        writer.close();
    }

    private void loadAccessToken() throws IOException {
        final BufferedReader reader;

        this.accessToken = null;
        reader = new BufferedReader(new FileReader(this.tokenFile));
        this.accessToken = reader.readLine();
        reader.close();

        if (this.accessToken != null && this.accessToken.isBlank())
            this.accessToken = null;
    }
}
