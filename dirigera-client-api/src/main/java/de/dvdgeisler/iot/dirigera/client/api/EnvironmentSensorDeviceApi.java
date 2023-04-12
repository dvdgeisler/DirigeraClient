package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor.EnvironmentSensorAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor.EnvironmentSensorConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor.EnvironmentSensorDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor.EnvironmentSensorStateAttributes;

public class EnvironmentSensorDeviceApi extends ControllerDeviceApi<
        EnvironmentSensorStateAttributes,
        EnvironmentSensorAttributes,
        EnvironmentSensorConfigurationAttributes,
        EnvironmentSensorDevice> {

    public EnvironmentSensorDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return super.isInstance(device) && device instanceof EnvironmentSensorDevice;
    }
}
