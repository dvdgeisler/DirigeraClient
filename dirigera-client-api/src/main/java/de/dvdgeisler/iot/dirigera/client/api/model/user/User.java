package de.dvdgeisler.iot.dirigera.client.api.model.user;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    public UUID uid;
    public String name;
    public String audience;
    public String email;
    public LocalDateTime createdTimestamp;
    public String verifiedUid;
    public String role;
    public Integer remoteUser;

    public User(final UUID uid, final String name, final String audience, final String email, final LocalDateTime createdTimestamp, final String verifiedUid, final String role, final Integer remoteUser) {
        this.uid = uid;
        this.name = name;
        this.audience = audience;
        this.email = email;
        this.createdTimestamp = createdTimestamp;
        this.verifiedUid = verifiedUid;
        this.role = role;
        this.remoteUser = remoteUser;
    }

    public User() {
    }
}
