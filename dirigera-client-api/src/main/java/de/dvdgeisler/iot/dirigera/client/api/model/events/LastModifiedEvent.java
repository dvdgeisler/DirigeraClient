package de.dvdgeisler.iot.dirigera.client.api.model.events;

import java.time.LocalDateTime;

public class LastModifiedEvent extends Event<LastModifiedData> {
    public LastModifiedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType deviceStateChanged, final LastModifiedData eventData) {
        super(id, time, specversion, source, deviceStateChanged, eventData);
    }

    public LastModifiedEvent() {
    }
}
