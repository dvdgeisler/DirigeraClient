package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.speaker;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;

import java.time.LocalDateTime;
import java.util.List;

public class SpeakerDevice extends Device<SpeakerAttributes, SpeakerConfigurationAttributes> {

  public SpeakerDevice() {
  }

  public SpeakerDevice(
      final String id,
      final LocalDateTime createdAt,
      final Boolean isReachable,
      final LocalDateTime lastSeen,
      final SpeakerAttributes attributes,
      final DeviceCapabilities capabilities,
      final List<String> remoteLinks,
      final SpeakerConfigurationAttributes speakerConfigurationAttributes) {
    super(id, DeviceCategory.SPEAKER, DeviceType.SPEAKER, createdAt, isReachable, lastSeen, attributes, capabilities,
        remoteLinks, speakerConfigurationAttributes);
  }
}
