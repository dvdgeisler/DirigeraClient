package de.dvdgeisler.iot.dirigera.client.api.http.homekit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Component
public class AuthenticationStore {
    private final static Logger log = LoggerFactory.getLogger(AuthenticationStore.class);
    private final File file;
    private String clientUsername;
    private String clientPassword;
    private String serverUsername;
    private BigInteger serverPublicKey;

    public AuthenticationStore(
            @Value("${dirigera.homekit.authentication-file:homekit.auth}") final String file,
            @Value("${dirigera.homekit.username:}") final String username,
            @Value("${dirigera.homekit.password:}") final String password) throws IOException, ClassNotFoundException {
        this.file = new File(file);
        this.clientUsername = Optional
                .ofNullable(username)
                .filter(Predicate.not(String::isBlank))
                .orElse(InetAddress.getLocalHost().getHostName());
        this.clientPassword = Optional
                .ofNullable(password)
                .filter(Predicate.not(String::isBlank))
                .orElse(UUID.randomUUID().toString());

        if (this.file.exists() && this.file.isFile())
            this.load();
    }

    public synchronized void load() throws IOException, ClassNotFoundException {
        final FileInputStream fis;
        final ObjectInputStream ois;

        log.info("Read authentication details from {}", this.file);

        fis = new FileInputStream(this.file);
        ois = new ObjectInputStream(fis);
        this.clientUsername = ois.readUTF();
        this.clientPassword = ois.readUTF();
        this.serverUsername = ois.readUTF();
        this.serverPublicKey = (BigInteger) ois.readObject();
    }

    public synchronized void save() throws IOException {
        final FileOutputStream fos;
        final ObjectOutputStream oos;

        log.info("Write authentication details to {}", this.file);

        fos = new FileOutputStream(this.file);
        oos = new ObjectOutputStream(fos);

        oos.writeUTF(this.clientUsername);
        oos.writeUTF(this.clientPassword);
        oos.writeUTF(this.serverUsername);
        oos.writeObject(this.serverPublicKey);
    }

    public synchronized void persist(
            final String clientUsername,
            final String clientPassword,
            final String serverUsername,
            final BigInteger serverPublicKey) throws IOException {
        this.clientUsername = clientUsername;
        this.clientPassword = clientPassword;
        this.serverUsername = serverUsername;
        this.serverPublicKey = serverPublicKey;
        this.save();
    }

    public synchronized String getClientUsername() {
        return this.clientUsername;
    }

    public synchronized void setClientUsername(final String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public synchronized String getClientPassword() {
        return this.clientPassword;
    }

    public synchronized void setClientPassword(final String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public synchronized String getServerUsername() {
        return this.serverUsername;
    }

    public synchronized void setServerUsername(final String serverUsername) {
        this.serverUsername = serverUsername;
    }

    public synchronized BigInteger getServerPublicKey() {
        return this.serverPublicKey;
    }

    public synchronized void setServerPublicKey(final BigInteger serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public boolean isValid() {
        if(this.serverPublicKey == null || this.serverPublicKey.toByteArray().length == 0)
            return false;
        if(this.serverUsername == null || this.serverUsername.isBlank())
            return false;
        return true;
    }
}
