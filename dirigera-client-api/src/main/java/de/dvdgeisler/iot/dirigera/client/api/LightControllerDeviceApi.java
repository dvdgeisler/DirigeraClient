package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.lightcontroller.LightControllerAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.lightcontroller.LightControllerConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.lightcontroller.LightControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.lightcontroller.LightControllerStateAttributes;

public class LightControllerDeviceApi extends ControllerDeviceApi<
        LightControllerStateAttributes,
        LightControllerAttributes,
        LightControllerConfigurationAttributes,
        LightControllerDevice> {

    public LightControllerDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return super.isInstance(device) && device instanceof LightControllerDevice;
    }

}
