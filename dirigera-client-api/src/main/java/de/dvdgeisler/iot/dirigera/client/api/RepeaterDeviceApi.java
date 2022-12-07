package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.repeater.RepeaterAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.repeater.RepeaterConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.repeater.RepeaterDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.repeater.RepeaterStateAttributes;

public class RepeaterDeviceApi extends DefaultDeviceApi<
        RepeaterStateAttributes,
        RepeaterAttributes,
        RepeaterConfigurationAttributes,
        RepeaterDevice> {

    public RepeaterDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return device instanceof RepeaterDevice;
    }

}
