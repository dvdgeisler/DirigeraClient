package de.dvdgeisler.iot.dirigera.dirigeraclient.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DirigeraRequest {
    private final static Logger log = LoggerFactory.getLogger(DirigeraRequest.class);
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final HttpsURLConnection con;

    private DirigeraRequest(HttpsURLConnection con) {
        this.con = con;
    }

    public static DirigeraRequest create(final String method, final URL url) throws IOException {
        final HttpsURLConnection con;

        log.debug("Create {} request to {}", method, url);
        con = (HttpsURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setInstanceFollowRedirects(false);
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        return new DirigeraRequest(con);
    }

    public Map<String, Object> readResponse() throws IOException {
        final int code;

        code = this.con.getResponseCode();
        if (code < 200 || code >= 300)
            throw new DirigeraRequestException(readError());
        return readJSON(this.con.getInputStream());
    }

    public static Map<String, Object> readJSON(final InputStream is) throws DirigeraRequestException {
        final Map<String, Object> payload;

        try {
            payload = OBJECT_MAPPER.readValue(is, HashMap.class);
            log.debug("Read json payload: {}", OBJECT_MAPPER.writeValueAsString(payload));
            return payload;
        } catch (IOException e) {
            throw new DirigeraRequestException(Map.of(
                    "error", "JSON-Parser",
                    "message", String.format("Cannot deserialize error messge: %s", e.getMessage())));
        }
    }

    public Map<String, Object> readError() throws DirigeraRequestException {
        final InputStream is;

        is = this.con.getErrorStream();
        if (is != null)
            return readJSON(is);
        return Map.of(
                "error", "Unknown",
                "message", "No error message available");
    }

    public void writePayload(final Map<String, Object> payload) throws DirigeraRequestException {
        try {
            writePayload(this.con.getOutputStream(), payload);
        } catch (IOException e) {
            throw new DirigeraRequestException("Write Payload", e.getMessage());
        }
    }

    public static void writePayload(final OutputStream os, final Map<String, Object> payload) throws DirigeraRequestException {
        final String query;

        query = payload.entrySet()
                .stream()
                .map((kv) -> String.format("%s=%s", kv.getKey(), kv.getValue()))
                .collect(Collectors.joining("&"));
        log.debug("Write query payload: {}", query);

        try {
            os.write(query.getBytes(StandardCharsets.US_ASCII));
        } catch (IOException e) {
            throw new DirigeraRequestException("Write Payload", e.getMessage());
        }
    }
}
