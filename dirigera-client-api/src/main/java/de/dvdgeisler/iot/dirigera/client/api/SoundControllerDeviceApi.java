package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.RestApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.soundcontroller.SoundControllerAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.soundcontroller.SoundControllerConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.soundcontroller.SoundControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.soundcontroller.SoundControllerStateAttributes;

public class SoundControllerDeviceApi extends ControllerDeviceApi<
        SoundControllerStateAttributes,
        SoundControllerAttributes,
        SoundControllerConfigurationAttributes,
        SoundControllerDevice> {

    public SoundControllerDeviceApi(final RestApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return super.isInstance(device) && device instanceof SoundControllerDevice;
    }

}
