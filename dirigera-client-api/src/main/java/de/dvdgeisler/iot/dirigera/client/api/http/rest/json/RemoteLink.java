package de.dvdgeisler.iot.dirigera.client.api.http.rest.json;

import java.util.List;

public class RemoteLink {
    public List<String> targetIds;

    public RemoteLink() {
    }

    public RemoteLink(final List<String> targetIds) {
        this.targetIds = targetIds;
    }
}
