package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.soundcontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;

import java.time.LocalDateTime;
import java.util.List;

public class SoundControllerDevice extends Device<SoundControllerAttributes, SoundControllerConfigurationAttributes> {

    public SoundControllerDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final SoundControllerAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final SoundControllerConfigurationAttributes soundControllerConfigurationAttributes) {
        super(id, DeviceCategory.CONTROLLER, DeviceType.SOUND_CONTROLLER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, soundControllerConfigurationAttributes);
    }

    public SoundControllerDevice() {
    }
}
