package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerStateAttributes;
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

    if(volume < 0 || volume > 100)
      return Mono.error(new IllegalArgumentException("Volume must be between 0 and 100"));

    return this.assertCapability(device, "volume")
        .flatMap(d -> this.setStateAttribute(d, createStateAttribute(Optional.empty(), Optional.of(volume))));
  }

  // TODO: If possible figure out how to start playback. Device does support it as can be seen in the output

  private SpeakerStateAttributes createStateAttribute(Optional<Boolean> isMuted, Optional<Integer> volume) {
    final SpeakerStateAttributes attributes = new SpeakerStateAttributes();

    isMuted.ifPresent(b -> attributes.isMuted = b);

    volume.ifPresent(i -> attributes.volume = i);

    return attributes;
  }
}
