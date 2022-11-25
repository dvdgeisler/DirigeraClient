package de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;

import java.time.LocalDateTime;
import java.util.List;

import static de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory.CONTROLLER;
import static de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType.SOUND_CONTROLLER;

public class SoundControllerDevice extends Device<SoundControllerAttributes, SoundControllerConfigurationAttributes> {

    public SoundControllerDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final SoundControllerAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final SoundControllerConfigurationAttributes soundControllerConfigurationAttributes) {
        super(id, CONTROLLER, SOUND_CONTROLLER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, soundControllerConfigurationAttributes);
    }

    public SoundControllerDevice() {
    }
}
