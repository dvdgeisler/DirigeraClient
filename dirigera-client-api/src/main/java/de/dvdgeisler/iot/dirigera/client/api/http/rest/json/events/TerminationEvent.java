package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import java.time.LocalDateTime;

public class TerminationEvent extends Event<TerminationData> {
    public TerminationEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType deviceStateChanged, final TerminationData eventData) {
        super(id, time, specversion, source, deviceStateChanged, eventData);
    }

    public TerminationEvent() {
    }
}
