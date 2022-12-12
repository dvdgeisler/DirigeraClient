package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway.*;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.List;
import java.util.TimeZone;

public class GatewayDeviceApi extends DeviceApi<
        GatewayStateAttributes,
        GatewayAttributes,
        GatewayConfigurationAttributes,
        GatewayDevice> {

    public GatewayDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return device instanceof GatewayDevice;
    }


    public Mono<GatewayDevice> permittingJoin(final GatewayDevice device) {
        final GatewayStateAttributes attributes;

        attributes = new GatewayStateAttributes();
        attributes.permittingJoin = true;

        return this.assertCapability(device, "permittingJoin")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<GatewayDevice> forbidJoin(final GatewayDevice device) {
        final GatewayStateAttributes attributes;

        attributes = new GatewayStateAttributes();
        attributes.permittingJoin = false;

        return this.assertCapability(device, "permittingJoin")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<GatewayDevice> setUserConsent(final GatewayDevice device, final GatewayUserConsentName name, final GatewayUserConsentValue value) {
        final GatewayStateAttributes attributes;

        attributes = new GatewayStateAttributes();
        attributes.userConsents = List.of(new GatewayUserConsent(name, value));

        return this.assertCapability(device, "userConsents")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<GatewayDevice> setLogLevel(final GatewayDevice device, final Integer logLevel) {
        final GatewayStateAttributes attributes;

        attributes = new GatewayStateAttributes();
        attributes.logLevel = logLevel;

        return this.assertCapability(device, "logLevel")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<GatewayDevice> setTime(final GatewayDevice device, final LocalTime time) {
        final GatewayStateAttributes attributes;

        attributes = new GatewayStateAttributes();
        attributes.time = time;

        return this.assertCapability(device, "time")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<GatewayDevice> setTimeZone(final GatewayDevice device, final TimeZone timeZone) {
        final GatewayStateAttributes attributes;

        attributes = new GatewayStateAttributes();
        attributes.timezone = timeZone;

        return this.assertCapability(device, "timezone")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<GatewayDevice> setCountryCode(final GatewayDevice device, final String countryCode) {
        final GatewayStateAttributes attributes;

        attributes = new GatewayStateAttributes();
        attributes.countryCode = countryCode;

        return this.assertCapability(device, "countryCode")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<GatewayDevice> setCoordinates(final GatewayDevice device, final GatewayCoordinates coordinates) {
        final GatewayStateAttributes attributes;

        attributes = new GatewayStateAttributes();
        attributes.coordinates = coordinates;

        return this.assertCapability(device, "coordinates")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }
}
