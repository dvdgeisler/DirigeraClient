package de.dvdgeisler.iot.dirigera.client.api.model.deviceset;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.Identifier;

public class DeviceSet<_Attributes extends DeviceSetAttributes> extends Identifier {
    @JsonUnwrapped
    public _Attributes attributes;

    public DeviceSet() {
    }

    public DeviceSet(final String id, final _Attributes attributes) {
        super(id);
        this.attributes = attributes;
    }
}
