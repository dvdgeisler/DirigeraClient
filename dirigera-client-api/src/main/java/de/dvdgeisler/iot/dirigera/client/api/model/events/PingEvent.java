package de.dvdgeisler.iot.dirigera.client.api.model.events;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.events.EventType.PING;

public class PingEvent extends Event {
    public PingEvent(final String id, final LocalDateTime time, final String specversion, final String source) {
        super(id, time, specversion, source, PING, null);
    }

    public PingEvent() {
    }
}
