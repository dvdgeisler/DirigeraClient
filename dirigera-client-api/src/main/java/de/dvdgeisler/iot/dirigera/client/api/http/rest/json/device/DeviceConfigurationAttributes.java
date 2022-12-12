package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.DeviceSet;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceConfigurationAttributes {
    public String customIcon;
    public List<DeviceSet> deviceSet;

    public DeviceConfigurationAttributes(final String customIcon, final List<DeviceSet> deviceSet) {
        this.customIcon = customIcon;
        this.deviceSet = deviceSet;
    }

    public DeviceConfigurationAttributes() {
    }
}
