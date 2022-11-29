package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blindscontroller.BlindsControllerAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blindscontroller.BlindsControllerConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blindscontroller.BlindsControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blindscontroller.BlindsControllerStateAttributes;

public class BlindsControllerDeviceApi extends ControllerDeviceApi<
        BlindsControllerStateAttributes,
        BlindsControllerAttributes,
        BlindsControllerConfigurationAttributes,
        BlindsControllerDevice> {

    public BlindsControllerDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return super.isInstance(device) && device instanceof BlindsControllerDevice;
    }

}
