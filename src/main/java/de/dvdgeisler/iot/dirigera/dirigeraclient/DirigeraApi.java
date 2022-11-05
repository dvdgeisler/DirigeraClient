package de.dvdgeisler.iot.dirigera.dirigeraclient;

import de.dvdgeisler.iot.dirigera.dirigeraclient.http.DirigeraRequest;
import de.dvdgeisler.iot.dirigera.dirigeraclient.http.DirigeraRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.US_ASCII;

@Component
public class DirigeraApi {
    private final static Logger log = LoggerFactory.getLogger(DirigeraApi.class);
    private final static MessageDigest DIGEST;
    private final static Base64.Encoder BASE64_ENCODER;
    private final static Base64.Decoder BASE64_DECODER;
    private final static String CODE_ALPHABET;
    private final static int CODE_LENGTH;

    static {
        try {
            DIGEST = MessageDigest.getInstance("sha256");
            BASE64_ENCODER = Base64.getUrlEncoder();
            BASE64_DECODER = Base64.getUrlDecoder();
            CODE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
            CODE_LENGTH = 128;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private final String hostname;
    private final short port;

    private String codeVerifier;
    private String code;
    private String accessToken;

    public DirigeraApi(
            @Value("${dirigera.hostname}") final String hostname,
            @Value("${dirigera.port:8443}") final short port) {
        this.hostname = hostname;
        this.port = port;
        this.code = null;
        this.accessToken = null;
    }

    public URL url(final String path) throws MalformedURLException {
        return new URL(String.format("https://%s:%d/v1/%s", this.hostname, this.port, path));
    }

    public DirigeraRequest request(final String method, final String path) throws IOException {
        return DirigeraRequest.create(method, this.url(path));
    }

    public DirigeraRequest post(final String path) throws IOException {
        return this.request("POST", path);
    }

    public DirigeraRequest get(final String path, final Map<String, Object> query) throws IOException {
        final String s;

        if (query.isEmpty())
            return this.request("GET", path);

        s = query.entrySet()
                .stream()
                .map((kv) -> String.format("%s=%s", kv.getKey(), kv.getValue()))
                .collect(Collectors.joining("&"));
        return this.request("GET", String.format("%s?%s", path, s));
    }

    public void authorize() throws IOException {
        final Map<String, Object> response;
        final DirigeraRequest request;

        this.generateCodeVerifier();

        request = this.get("oauth/authorize", Map.of(
                "audience", "homesmart.local",
                "response_type", "code",
                "code_challenge", this.getCodeChallenge(),
                "code_challenge_method", "S256"
        ));
        response = request.readResponse();
        if (!response.containsKey("code"))
            throw new DirigeraRequestException("Response", "Response does not provide 'code' field");
        this.code = response.get("code").toString();
    }

    public void tokenExchange() throws IOException {
        final Map<String, Object> response;
        final DirigeraRequest request;

        if(this.code == null)
            throw new DirigeraRequestException("MISSING CODE", "No token exchange code. Run 'authorize()' first");

        request = this.post("oauth/token");
        request.writePayload(Map.of(
                "code", this.code,
                "code_verifier", this.codeVerifier,
                "name", InetAddress.getLocalHost().getHostName(),
                "grant_type", "authorization_code"));
        response = request.readResponse();
        if (!response.containsKey("access_token"))
            throw new DirigeraRequestException("Response", "Response does not provide 'access_token' field");
        this.accessToken = response.get("access_token").toString();
    }

    private void generateCodeVerifier() {
        final StringBuilder code;
        final ThreadLocalRandom rnd;

        rnd = ThreadLocalRandom.current();
        code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++)
            code.append(CODE_ALPHABET.charAt(rnd.nextInt(0, CODE_ALPHABET.length())));

        this.codeVerifier = code.toString();
    }

    private String getCodeChallenge() {
        final String base64;
        final byte[] hash;

        hash = DIGEST.digest(this.codeVerifier.getBytes(US_ASCII));
        base64 = BASE64_ENCODER.encodeToString(hash);
        return base64.substring(0,base64.length()-1); //remove trailing '='
    }

}
