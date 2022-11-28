package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.*;
import de.dvdgeisler.iot.dirigera.client.api.model.music.MusicPlaybackState;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static de.dvdgeisler.iot.dirigera.client.api.model.music.MusicPlaybackState.*;

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
                .flatMap(d -> this.setStateAttribute(d, createStateAttribute(Optional.of(true), Optional.empty(), Optional.empty())));
    }

    public Mono<SpeakerDevice> unmute(final SpeakerDevice device) {
        return this.assertCapability(device, "isMuted")
                .flatMap(d -> this.setStateAttribute(d, createStateAttribute(Optional.of(false), Optional.empty(), Optional.empty())));
    }

    public Mono<SpeakerDevice> setVolume(final SpeakerDevice device, final Integer volume) {

        if (volume < 0 || volume > 100)
            return Mono.error(new IllegalArgumentException("Volume must be between 0 and 100"));

        return this.assertCapability(device, "volume")
                .flatMap(d -> this.setStateAttribute(d, createStateAttribute(Optional.empty(), Optional.of(volume), Optional.empty())));
    }

    public Mono<SpeakerDevice> setPlayback(final SpeakerDevice device, final MusicPlaybackState playbackState) {
        return this.assertCapability(device, "playback")
                .flatMap(d -> this.setStateAttribute(d, createStateAttribute(Optional.empty(), Optional.empty(), Optional.of(playbackState))));
    }

    public Mono<SpeakerDevice> play(final SpeakerDevice device) {
        return this.setPlayback(device, PLAYING);
    }

    public Mono<SpeakerDevice> pause(final SpeakerDevice device) {
        return this.setPlayback(device, PAUSED);
    }

    public Mono<SpeakerDevice> next(final SpeakerDevice device) {
        return this.setPlayback(device, NEXT);
    }

    public Mono<SpeakerDevice> previous(final SpeakerDevice device) {
        return this.setPlayback(device, PREVIOUS);
    }

    private SpeakerStateAttributes createStateAttribute(final Optional<Boolean> isMuted, final Optional<Integer> volume, final Optional<MusicPlaybackState> playback) {
        final SpeakerStateAttributes attributes = new SpeakerStateAttributes();

        isMuted.ifPresent(b -> attributes.isMuted = b);

        volume.ifPresent(i -> attributes.volume = i);

        playback.ifPresent(p -> attributes.playback = p);

        return attributes;
    }
}
