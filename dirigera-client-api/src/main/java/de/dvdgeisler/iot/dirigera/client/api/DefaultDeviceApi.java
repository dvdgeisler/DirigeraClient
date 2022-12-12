package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceConfigurationDefaultAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceStateAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.*;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Objects;

public abstract class DefaultDeviceApi<
        _DeviceStateAttributes extends DeviceStateAttributes,
        _Attributes extends DeviceAttributes<_DeviceStateAttributes>,
        _DeviceConfigurationAttributes extends DeviceConfigurationDefaultAttributes,
        _Device extends Device<_Attributes, _DeviceConfigurationAttributes>>
        extends DeviceApi<
                        _DeviceStateAttributes,
                        _Attributes,
                        _DeviceConfigurationAttributes,
                        _Device> {
    private final static Logger log = LoggerFactory.getLogger(DefaultDeviceApi.class);

    public DefaultDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    public Mono<_Device> setRoom(final _Device device, final Room room) {
        final DeviceConfigurationDefaultAttributes attributes;

        attributes = new DeviceConfigurationDefaultAttributes();
        attributes.room = room;
        return this.refresh(device)
                .filter(d -> d.configuration.room == null || !Objects.equals(d.configuration.room.id, room.id))
                .flatMap(d -> this.setConfigurationAttribute(d, attributes));
    }

    public Mono<_Device> removeRoom(final _Device device) {
        final DeviceConfigurationDefaultAttributes attributes;

        attributes = new DeviceConfigurationDefaultAttributes();
        attributes.room = new Room();
        return this.refresh(device)
                .filter(d -> d.configuration.room != null)
                .flatMap(d -> this.setConfigurationAttribute(d, attributes));
    }

    public Mono<_Device> hide(final _Device device) {
        final DeviceConfigurationDefaultAttributes attributes;

        attributes = new DeviceConfigurationDefaultAttributes();
        attributes.isHidden = true;
        return this.refresh(device)
                .filter(d -> !d.configuration.isHidden)
                .flatMap(d -> this.setConfigurationAttribute(d, attributes));
    }

    public Mono<_Device> visible(final _Device device) {
        final DeviceConfigurationDefaultAttributes attributes;

        attributes = new DeviceConfigurationDefaultAttributes();
        attributes.isHidden = false;
        return this.refresh(device)
                .filter(d -> d.configuration.isHidden)
                .flatMap(d -> this.setConfigurationAttribute(d, attributes));
    }

}
