package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.RestApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.blindscontroller.BlindsControllerAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.blindscontroller.BlindsControllerConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.blindscontroller.BlindsControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.blindscontroller.BlindsControllerStateAttributes;

public class BlindsControllerDeviceApi extends ControllerDeviceApi<
        BlindsControllerStateAttributes,
        BlindsControllerAttributes,
        BlindsControllerConfigurationAttributes,
        BlindsControllerDevice> {

    public BlindsControllerDeviceApi(final RestApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return super.isInstance(device) && device instanceof BlindsControllerDevice;
    }

}
