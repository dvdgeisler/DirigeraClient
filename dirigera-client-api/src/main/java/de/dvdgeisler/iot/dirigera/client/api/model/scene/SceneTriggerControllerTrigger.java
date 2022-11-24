package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SceneTriggerControllerTrigger {
    public List<String> days;
    public DeviceType controllerType;
    public Integer buttonIndex;
    public String deviceId;

    public SceneTriggerControllerTrigger(final List<String> days, final DeviceType controllerType, final Integer buttonIndex, final String deviceId) {
        this.days = days;
        this.controllerType = controllerType;
        this.buttonIndex = buttonIndex;
        this.deviceId = deviceId;
    }

    public SceneTriggerControllerTrigger() {
    }
}
