package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.*;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events.DeviceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class DeviceApi<
        _DeviceStateAttributes extends DeviceStateAttributes,
        _Attributes extends DeviceAttributes<_DeviceStateAttributes>,
        _DeviceConfigurationAttributes extends DeviceConfigurationAttributes,
        _Device extends Device<_Attributes, _DeviceConfigurationAttributes>> {
    private final static Logger log = LoggerFactory.getLogger(DeviceApi.class);
    protected final ClientApi clientApi;
    protected final WebSocketApi webSocketApi;

    public DeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        this.clientApi = clientApi;
        this.webSocketApi = webSocketApi;
    }

    protected abstract boolean isInstance(final Device<?,?> device);

    private Mono<_Device> cast(final Device<?,?> device) {
        if(this.isInstance(device))
            return Mono.just((_Device) device);
        return Mono.empty();
    }

    private Mono<_Device> setStateAttribute(
            final _Device device,
            final Duration transitionTime,
            final Duration period,
            final DeviceStateAttributes stateAttributes) {
        return this.clientApi.device.editDeviceState(device.id, List.of(new DeviceStateCommand<>(
                        Optional.ofNullable(transitionTime).map(Duration::getSeconds).map(Long::intValue).orElse(null),
                        Optional.ofNullable(period).map(Duration::getSeconds).map(Long::intValue).orElse(null),
                        stateAttributes)))
                .thenReturn(device.id)
                .flatMap(this.clientApi.device::device)
                .flatMap(this::cast);
    }

    protected Mono<_Device> setStateAttribute(final _Device device, final DeviceStateAttributes stateAttributes) {
        return this.setStateAttribute(device, null, null, stateAttributes);
    }

    protected Mono<_Device> setStateAttributeTransition(final _Device device, final Duration transitionTime, final DeviceStateAttributes stateAttributes) {
        return this.setStateAttribute(device, transitionTime, null, stateAttributes);
    }

    protected Mono<_Device> setStateAttributeAfterPeriod(final _Device device, final Duration period, final DeviceStateAttributes stateAttributes) {
        return this.setStateAttribute(device, null, period, stateAttributes);
    }

    protected Mono<_Device> setConfigurationAttribute(final _Device device, final DeviceConfigurationAttributes configurationAttributes) {
        return this.clientApi.device.editDeviceConfiguration(device.id, List.of(new DeviceConfigurationCommand<>(configurationAttributes)))
                .thenReturn(device.id)
                .flatMap(this.clientApi.device::device)
                .flatMap(this::cast);
    }

    protected Mono<_Device> assertCapability(final _Device device, final String attributeName) {
        return this.assertCapability(device, List.of(attributeName));
    }

    protected Mono<_Device> assertCapability(final _Device device, final List<String> attributeNames) {
        for (String attributeName : attributeNames)
            if (!device.capabilities.canReceive.contains(attributeName))
                return Mono.error(new IllegalArgumentException(String.format("Device %s does not support state attribute \"%s\"", device.id, attributeName)));
        return Mono.just(device);
    }

    public Mono<List<_Device>> all() {
        return this.clientApi.device.devices()
                .flatMapMany(Flux::fromIterable)
                .flatMap(this::cast)
                .collectList();
    }

    public Mono<_Device> refresh(final _Device device) {
        return this.clientApi.device.device(device.id)
                .flatMap(this::cast);
    }

    public Mono<_Device> setCustomName(final _Device device, final String customName) {
        return this.assertCapability(device, "customName")
                .flatMap(d -> this.setStateAttribute(d, new DeviceStateAttributes(customName)));
    }

    public Mono<_Device> setCustomIcon(final _Device device, final String customIcon) {
        return this.setConfigurationAttribute(device, new DeviceConfigurationAttributes(customIcon, null));
    }

    public Mono<_Device> setDeviceSets(final _Device device, final List<DeviceSet> deviceSets) {
        return this.setConfigurationAttribute(device, new DeviceConfigurationAttributes(null, deviceSets));
    }

    public Mono<_Device> addToDeviceSet(final _Device device, final DeviceSet deviceSet) {
        return this.refresh(device)
                .map(d -> d.configuration.deviceSet)
                .filter(deviceSets -> deviceSets.stream().map(ds -> ds.id).noneMatch(id -> Objects.equals(id, deviceSet.id)))
                .map(deviceSets -> Stream.concat(deviceSets.stream(), Stream.of(deviceSet)).toList())
                .flatMap(deviceSets -> this.setDeviceSets(device, deviceSets));
    }

    public Mono<_Device> removeFromDeviceSet(final _Device device, final DeviceSet deviceSet) {
        return this.refresh(device)
                .map(d->d.configuration.deviceSet)
                .flatMapMany(Flux::fromIterable)
                .filter(ds-> !Objects.equals(ds.id, deviceSet.id))
                .collectList()
                .flatMap(deviceSets -> this.setDeviceSets(device, deviceSets));
    }

    public void websocket(Consumer<DeviceEvent<_Device>> consumer) {
        this.webSocketApi.addListener(event -> {
            if(this.isInstance((Device<?, ?>) event.data))
                consumer.accept(event);
        }, DeviceEvent.class);
    }
}
