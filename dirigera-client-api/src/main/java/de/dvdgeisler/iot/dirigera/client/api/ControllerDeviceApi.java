package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.RestApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.RemoteLink;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceConfigurationDefaultAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceStateAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory.CONTROLLER;
import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory.SENSOR;

public abstract class ControllerDeviceApi<
        _DeviceStateAttributes extends DeviceStateAttributes,
        _Attributes extends DeviceAttributes<_DeviceStateAttributes>,
        _DeviceConfigurationAttributes extends DeviceConfigurationDefaultAttributes,
        _Device extends Device<_Attributes, _DeviceConfigurationAttributes>>
        extends DefaultDeviceApi<
        _DeviceStateAttributes,
        _Attributes,
        _DeviceConfigurationAttributes,
        _Device> {
    private final static Logger log = LoggerFactory.getLogger(ControllerDeviceApi.class);

    public ControllerDeviceApi(final RestApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return device.type == CONTROLLER || device.type == SENSOR;
    }

    public Mono<List<Device<?, ?>>> getRemoteLinks(final _Device device) {
        return this.clientApi.device.devices()
                .flatMapMany(Flux::fromIterable)
                .filter(d -> d.remoteLinks.contains(device.id))
                .collectList();
    }

    public Mono<_Device> setRemoteLink(final _Device device, final List<Device<?, ?>> targets) {
        final Mono<_Device> cache;

        for (final Device<?, ?> target : targets)
            if (device.capabilities.canSend.stream().noneMatch(target.capabilities.canReceive::contains))
                return Mono.error(new IllegalArgumentException(String.format("" +
                                "Device %s cannot be linked with device %s. " +
                                "Sending capabilities does not match the receiving device's capabilities: " +
                                "canSend=[%s], canReceive=[%s]",
                        device.id,
                        target.id,
                        String.join(", ", device.capabilities.canSend),
                        String.join(", ", target.capabilities.canReceive))));

        cache = this.refresh(device).cache();
        return cache.flatMap(d -> {
            final RemoteLink remoteLink;

            remoteLink = new RemoteLink();
            remoteLink.targetIds = targets.stream().map(target -> target.id).toList();
            return this.clientApi.remoteLink
                    .updateRemoteLinks(d.id, remoteLink)
                    .thenReturn(d);
        });
    }

    public Mono<_Device> setRemoteLink(final _Device device, final Device<?, ?> target) {
        return this.setRemoteLink(device, List.of(target));
    }

    public Mono<_Device> addRemoteLink(final _Device device, final Device<?, ?> target) {
        return this.getRemoteLinks(device)
                .filter(remoteLinks ->
                        remoteLinks.stream()
                                .map(remoteLink -> remoteLink.id)
                                .noneMatch(id -> id.equals(target.id)))
                .map(remoteLinks -> Stream.concat(remoteLinks.stream(), Stream.of(target)).toList())
                .flatMap(remoteLinks -> this.setRemoteLink(device, remoteLinks));
    }

    public Mono<_Device> removeRemoteLink(final _Device device, final Device<?, ?> target) {
        return this.getRemoteLinks(device)
                .flatMapMany(Flux::fromIterable)
                .filter(d -> !d.id.equals(target.id))
                .collectList()
                .flatMap(remoteLinks -> this.setRemoteLink(device, remoteLinks));
    }
}
