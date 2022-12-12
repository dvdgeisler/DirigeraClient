package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.RestApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.repeater.RepeaterAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.repeater.RepeaterConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.repeater.RepeaterDevice;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.repeater.RepeaterStateAttributes;

public class RepeaterDeviceApi extends DefaultDeviceApi<
        RepeaterStateAttributes,
        RepeaterAttributes,
        RepeaterConfigurationAttributes,
        RepeaterDevice> {

    public RepeaterDeviceApi(final RestApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return device instanceof RepeaterDevice;
    }

}
