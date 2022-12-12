package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.DeviceSet;

import java.time.LocalDateTime;

public class DeviceSetEvent extends Event<DeviceSet> {
    public DeviceSetEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType type, final DeviceSet deviceSet) {
        super(id, time, specversion, source, type, deviceSet);
    }

    public DeviceSetEvent() {
    }
}
