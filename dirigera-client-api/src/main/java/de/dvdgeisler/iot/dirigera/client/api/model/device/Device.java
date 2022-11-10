package de.dvdgeisler.iot.dirigera.client.api.model.device;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.dvdgeisler.iot.dirigera.client.api.model.Identifier;
import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller.LightControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor.MotionSensorDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller.SoundControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;

import java.time.LocalDateTime;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "deviceType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GatewayDevice.class, name = "gateway"),
        @JsonSubTypes.Type(value = LightDevice.class, name = "light"),
        @JsonSubTypes.Type(value = LightControllerDevice.class, name = "lightController"),
        @JsonSubTypes.Type(value = SoundControllerDevice.class, name = "soundController"),
        @JsonSubTypes.Type(value = MotionSensorDevice.class, name = "motionSensor"),
})
public class Device<_Attributes extends DeviceAttributes> extends Identifier {
    public DeviceCategory type;
    public DeviceType deviceType;
    public LocalDateTime createdAt;
    public Boolean isReachable;
    public LocalDateTime lastSeen;
    public _Attributes attributes;
    public DeviceCapabilities capabilities;

    public Device() {
    }

    public Device(final String id, final DeviceCategory type, final DeviceType deviceType, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final _Attributes attributes, final DeviceCapabilities capabilities, final List<DeviceSet> deviceSet, final List<String> remoteLinks) {
        super(id);
        this.type = type;
        this.deviceType = deviceType;
        this.createdAt = createdAt;
        this.isReachable = isReachable;
        this.lastSeen = lastSeen;
        this.attributes = attributes;
        this.capabilities = capabilities;
        this.deviceSet = deviceSet;
        this.remoteLinks = remoteLinks;
    }

    public List<DeviceSet> deviceSet;
    public List<String> remoteLinks;
}
