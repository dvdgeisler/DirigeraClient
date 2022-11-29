package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller.SoundControllerAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller.SoundControllerConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller.SoundControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller.SoundControllerStateAttributes;

public class SoundControllerDeviceApi extends ControllerDeviceApi<
        SoundControllerStateAttributes,
        SoundControllerAttributes,
        SoundControllerConfigurationAttributes,
        SoundControllerDevice> {

    public SoundControllerDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return super.isInstance(device) && device instanceof SoundControllerDevice;
    }

}
