package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightColorAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightStateAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerPlaybackPositionAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerStateAttributes;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import reactor.core.publisher.Mono;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SpeakerDeviceApi extends DefaultDeviceApi<
    SpeakerStateAttributes,
    SpeakerAttributes,
    SpeakerConfigurationAttributes,
    SpeakerDevice> {

  public SpeakerDeviceApi(final ClientApi clientApi) {
    super(clientApi);
  }

  @Override
  protected boolean isInstance(final Device<?, ?> device) {
    return device instanceof SpeakerDevice;
  }

  public Mono<SpeakerDevice> mute(final SpeakerDevice device) {
      return this.assertCapability(device, "isMuted")
          .flatMap(d -> this.setStateAttribute(d, createStateAttribute(Optional.of(true), Optional.empty())));
  }

  public Mono<SpeakerDevice> unmute(final SpeakerDevice device) {
    return this.assertCapability(device, "isMuted")
        .flatMap(d -> this.setStateAttribute(d, createStateAttribute(Optional.of(false), Optional.empty())));
  }

  public Mono<SpeakerDevice> setVolume(final SpeakerDevice device, final Integer volume) {
    return this.assertCapability(device, "volume")
        .flatMap(d -> this.setStateAttribute(d, createStateAttribute(Optional.empty(), Optional.of(volume))));
  }

  private SpeakerStateAttributes createStateAttribute(Optional<Boolean> isMuted, Optional<Integer> volume) {
    final SpeakerStateAttributes attributes = new SpeakerStateAttributes();

    isMuted.ifPresent(b -> attributes.isMuted = b);

    volume.ifPresent(i -> attributes.volume = i);

    return attributes;
  }
}
