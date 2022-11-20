package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.Home;
import de.dvdgeisler.iot.dirigera.client.api.model.auth.Token;
import de.dvdgeisler.iot.dirigera.client.api.model.device.*;
import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayEnvironment;
import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayPersistentMode;
import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class DirigeraApi {

    public static class ControllerDeviceApiWrapper extends ControllerDeviceApi<
            DeviceStateAttributes,
            DeviceAttributes<DeviceStateAttributes>,
            DeviceConfigurationDefaultAttributes,
            Device<DeviceAttributes<DeviceStateAttributes>, DeviceConfigurationDefaultAttributes>> {

        public final LightControllerDeviceApi light;
        public final ShortcutControllerDeviceApi shortcut;
        public final SoundControllerDeviceApi sound;
        public final BlindsControllerDeviceApi blinds;

        public ControllerDeviceApiWrapper(
                final ClientApi clientApi,
                final LightControllerDeviceApi light,
                final ShortcutControllerDeviceApi shortcut,
                final SoundControllerDeviceApi sound, final BlindsControllerDeviceApi blinds) {
            super(clientApi);
            this.light = light;
            this.shortcut = shortcut;
            this.sound = sound;
            this.blinds = blinds;
        }
    }
    public static class DeviceApiWrapper extends DeviceApi<
            DeviceStateAttributes,
            DeviceAttributes<DeviceStateAttributes>,
            DeviceConfigurationAttributes,
            Device<DeviceAttributes<DeviceStateAttributes>, DeviceConfigurationAttributes>>  {

        public final ControllerDeviceApiWrapper controller;

        public final GatewayDeviceApi gateway;
        public final LightControllerDeviceApi lightController;
        public final LightDeviceApi light;
        public final MotionSensorDeviceApi motionSensor;
        public final OutletDeviceApi outlet;
        public final RepeaterDeviceApi repeater;
        public final ShortcutControllerDeviceApi shortcutController;
        public final SoundControllerDeviceApi soundController;
        public final AirPurifierDeviceApi airPurifier;
        public final BlindsControllerDeviceApi blindsController;
        public final BlindsDeviceApi blinds;

        public DeviceApiWrapper(
                final ClientApi clientApi,
                final GatewayDeviceApi gateway,
                final LightControllerDeviceApi lightController,
                final LightDeviceApi light,
                final MotionSensorDeviceApi motionSensor,
                final OutletDeviceApi outlet,
                final RepeaterDeviceApi repeater,
                final ShortcutControllerDeviceApi shortcutController,
                final SoundControllerDeviceApi soundController,
                final AirPurifierDeviceApi airPurifier,
                final BlindsControllerDeviceApi blindsController, final BlindsDeviceApi blinds) {
            super(clientApi);
            this.controller = new ControllerDeviceApiWrapper(clientApi, lightController, shortcutController, soundController, blindsController);
            this.gateway = gateway;
            this.lightController = lightController;
            this.light = light;
            this.motionSensor = motionSensor;
            this.outlet = outlet;
            this.repeater = repeater;
            this.shortcutController = shortcutController;
            this.soundController = soundController;
            this.airPurifier = airPurifier;
            this.blindsController = blindsController;
            this.blinds = blinds;
        }

        @Override
        protected boolean isInstance(final Device<?, ?> device) {
            return device instanceof Device<?,?>;
        }
    }
    private final ClientApi clientApi;

    public final DeviceApiWrapper device;
    public final RoomApi room;
    public final SceneApi scene;
    public final UserApi user;

    public DirigeraApi(final ClientApi clientApi) {
        this.clientApi = clientApi;
        this.device = new DeviceApiWrapper(
                clientApi,
                new GatewayDeviceApi(clientApi),
                new LightControllerDeviceApi(clientApi),
                new LightDeviceApi(clientApi),
                new MotionSensorDeviceApi(clientApi),
                new OutletDeviceApi(clientApi),
                new RepeaterDeviceApi(clientApi),
                new ShortcutControllerDeviceApi(clientApi),
                new SoundControllerDeviceApi(clientApi),
                new AirPurifierDeviceApi(clientApi),
                new BlindsControllerDeviceApi(clientApi),
                new BlindsDeviceApi(clientApi));
        this.room = new RoomApi(clientApi);
        this.scene = new SceneApi(clientApi);
        this.user = new UserApi(clientApi);
    }

    public Mono<Token> pair() {
        return this.clientApi.oauth.pair();
    }

    public boolean isPaired() {
        return this.clientApi.oauth.isPaired();
    }

    public Mono<Token> pairIfRequired() {
        return this.clientApi.oauth.pairIfRequired();
    }

    public Mono<Home> getHome() {
        return this.clientApi.home();
    }

    public Mono<Map> dump() {
        return this.clientApi.dump();
    }

    public Mono<GatewayStatus> status() {
        return this.clientApi.gateway.status();
    }

    public Mono<GatewayStatus> checkFirmwareUpdate() {
        return this.clientApi.gateway.checkFirmwareUpdate()
                .flatMap(v->this.clientApi.gateway.status());
    }

    public Mono<GatewayStatus> installFirmwareUpdate() {
        return this.clientApi.gateway.installFirmwareUpdate()
                .flatMap(v->this.clientApi.gateway.status());
    }

    public Mono<GatewayStatus> setFirmwareEnvironment(final GatewayEnvironment environment) {
        return this.clientApi.gateway.setFirmwareEnvironment(environment)
                .flatMap(v->this.clientApi.gateway.status());
    }

    public Mono<GatewayStatus> activatePersistentMode() {
        return this.clientApi.gateway.setPersistentMode(new GatewayPersistentMode(true))
                .flatMap(v->this.clientApi.gateway.status());
    }

    public Mono<GatewayStatus> deactivatePersistentMode() {
        return this.clientApi.gateway.setPersistentMode(new GatewayPersistentMode(false))
                .flatMap(v->this.clientApi.gateway.status());
    }
}
