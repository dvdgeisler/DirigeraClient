package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.shortcutcontroller.ShortcutControllerAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.shortcutcontroller.ShortcutControllerConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.shortcutcontroller.ShortcutControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.shortcutcontroller.ShortcutControllerStateAttributes;

public class ShortcutControllerDeviceApi extends ControllerDeviceApi<
        ShortcutControllerStateAttributes,
        ShortcutControllerAttributes,
        ShortcutControllerConfigurationAttributes,
        ShortcutControllerDevice> {

    public ShortcutControllerDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return super.isInstance(device) && device instanceof ShortcutControllerDevice;
    }


}
