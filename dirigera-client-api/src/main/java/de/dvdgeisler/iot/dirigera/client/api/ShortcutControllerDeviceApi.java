package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.shortcutcontroller.ShortcutControllerAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.shortcutcontroller.ShortcutControllerConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.shortcutcontroller.ShortcutControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.shortcutcontroller.ShortcutControllerStateAttributes;

public class ShortcutControllerDeviceApi extends ControllerDeviceApi<
        ShortcutControllerStateAttributes,
        ShortcutControllerAttributes,
        ShortcutControllerConfigurationAttributes,
        ShortcutControllerDevice> {

    public ShortcutControllerDeviceApi(final ClientApi clientApi) {
        super(clientApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return super.isInstance(device) && device instanceof ShortcutControllerDevice;
    }


}
