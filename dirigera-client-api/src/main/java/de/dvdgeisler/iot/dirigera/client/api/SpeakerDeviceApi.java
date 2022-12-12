package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.speaker.*;
import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.*;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.music.MusicPlayItem;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.music.MusicPlayList;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.music.MusicPlaybackState;
import reactor.core.publisher.Mono;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SpeakerDeviceApi extends DefaultDeviceApi<
        SpeakerStateAttributes,
        SpeakerAttributes,
        SpeakerConfigurationAttributes,
        SpeakerDevice> {

    public SpeakerDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return device instanceof SpeakerDevice;
    }

    public Mono<SpeakerDevice> mute(final SpeakerDevice device) {
        return this.assertCapability(device, "isMuted")
                .flatMap(d -> this.setStateAttribute(d, createStateAttribute(
                        Optional.of(true),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty())));
    }

    public Mono<SpeakerDevice> unmute(final SpeakerDevice device) {
        return this.assertCapability(device, "isMuted")
                .flatMap(d -> this.setStateAttribute(d, createStateAttribute(
                        Optional.of(false),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty())));
    }

    public Mono<SpeakerDevice> setVolume(final SpeakerDevice device, final Integer volume) {

        if (volume < 0 || volume > 100)
            return Mono.error(new IllegalArgumentException("Volume must be between 0 and 100"));

        return this.assertCapability(device, "volume")
                .flatMap(d -> this.setStateAttribute(d, createStateAttribute(
                        Optional.empty(),
                        Optional.of(volume),
                        Optional.empty(),
                        Optional.empty())));
    }

    public Mono<SpeakerDevice> setPlayback(final SpeakerDevice device, final MusicPlaybackState playbackState) {
        return this.assertCapability(device, "playback")
                .flatMap(d -> this.setStateAttribute(d, createStateAttribute(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(playbackState),
                        Optional.empty())));
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

    public Mono<SpeakerDevice> setAudio(final SpeakerDevice device, final SpeakerAudioAttributes audio) {
        return this.assertCapability(device, "playbackAudio")
                .flatMap(d -> this.setStateAttribute(d, createStateAttribute(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(audio))));
    }

    public Mono<SpeakerDevice> setAudio(final SpeakerDevice device, final MusicPlayItem playItem) {
        final SpeakerAudioAttributes audio;

        audio = new SpeakerAudioAttributes();
        audio.playItem = playItem;
        return this.setAudio(device, audio);
    }

    public Mono<SpeakerDevice> setNextAudio(final SpeakerDevice device, final MusicPlayItem playItem) {
        final SpeakerAudioAttributes audio;

        audio = new SpeakerAudioAttributes();
        audio.nextPlayItem = playItem;
        return this.setAudio(device, audio);
    }

    public Mono<SpeakerDevice> setNextAudio(final SpeakerDevice device, final MusicPlayList playList) {
        final SpeakerAudioAttributes audio;

        audio = new SpeakerAudioAttributes();
        audio.playList = playList;
        return this.setAudio(device, audio);
    }

    private SpeakerStateAttributes createStateAttribute(final Optional<Boolean> isMuted, final Optional<Integer> volume, final Optional<MusicPlaybackState> playback, final Optional<SpeakerAudioAttributes> audio) {
        final SpeakerStateAttributes attributes;

        attributes = new SpeakerStateAttributes();
        isMuted.ifPresent(b -> attributes.isMuted = b);
        volume.ifPresent(i -> attributes.volume = i);
        playback.ifPresent(p -> attributes.playback = p);
        audio.ifPresent(a -> attributes.playbackAudio = a);

        return attributes;
    }
}
