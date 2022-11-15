package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller.LightControllerAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller.LightControllerConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller.LightControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller.LightControllerStateAttributes;

public class LightControllerDeviceApi extends ControllerDeviceApi<
        LightControllerStateAttributes,
        LightControllerAttributes,
        LightControllerConfigurationAttributes,
        LightControllerDevice> {

    public LightControllerDeviceApi(final ClientApi clientApi) {
        super(clientApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return super.isInstance(device) && device instanceof LightControllerDevice;
    }

}
