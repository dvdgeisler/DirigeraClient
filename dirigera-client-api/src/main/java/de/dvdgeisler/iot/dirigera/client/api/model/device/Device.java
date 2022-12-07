package de.dvdgeisler.iot.dirigera.client.api.model.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.Identifier;
import de.dvdgeisler.iot.dirigera.client.api.model.device.airpurifier.AirPurifierDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blinds.BlindsDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blindscontroller.BlindsControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller.LightControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor.MotionSensorDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.outlet.OutletDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.repeater.RepeaterDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.shortcutcontroller.ShortcutControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller.SoundControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.unknown.UnknownDevice;

import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerDevice;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "deviceType",
        visible = true,
        defaultImpl = UnknownDevice.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GatewayDevice.class, name = "gateway"),
        @JsonSubTypes.Type(value = LightDevice.class, name = "light"),
        @JsonSubTypes.Type(value = LightControllerDevice.class, name = "lightController"),
        @JsonSubTypes.Type(value = SoundControllerDevice.class, name = "soundController"),
        @JsonSubTypes.Type(value = MotionSensorDevice.class, name = "motionSensor"),
        @JsonSubTypes.Type(value = OutletDevice.class, name = "outlet"),
        @JsonSubTypes.Type(value = ShortcutControllerDevice.class, name = "shortcutController"),
        @JsonSubTypes.Type(value = RepeaterDevice.class, name = "repeater"),
        @JsonSubTypes.Type(value = AirPurifierDevice.class, name = "airPurifier"),
        @JsonSubTypes.Type(value = BlindsControllerDevice.class, name = "blindsController"),
        @JsonSubTypes.Type(value = BlindsDevice.class, name = "blinds"),
        @JsonSubTypes.Type(value = SpeakerDevice.class, name = "speaker"),
})
public class Device<_Attributes extends DeviceAttributes, _Configuration extends DeviceConfigurationAttributes> extends Identifier {
    public DeviceCategory type;
    public DeviceType deviceType;
    public LocalDateTime createdAt;
    public Boolean isReachable;
    public LocalDateTime lastSeen;
    public _Attributes attributes;
    public DeviceCapabilities capabilities;
    public List<String> remoteLinks;

    @JsonUnwrapped
    public _Configuration configuration;

    public Device() {
    }

    public Device(
            final String id,
            final DeviceCategory type,
            final DeviceType deviceType,
            final LocalDateTime createdAt,
            final Boolean isReachable,
            final LocalDateTime lastSeen,
            final _Attributes attributes,
            final DeviceCapabilities capabilities,
            final List<String> remoteLinks,
            final _Configuration configuration) {
        super(id);
        this.type = type;
        this.deviceType = deviceType;
        this.createdAt = createdAt;
        this.isReachable = isReachable;
        this.lastSeen = lastSeen;
        this.attributes = attributes;
        this.capabilities = capabilities;
        this.remoteLinks = remoteLinks;
        this.configuration = configuration;
    }
}
