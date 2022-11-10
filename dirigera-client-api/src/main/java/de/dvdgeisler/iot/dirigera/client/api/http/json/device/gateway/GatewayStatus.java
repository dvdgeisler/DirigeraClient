package de.dvdgeisler.iot.dirigera.client.api.http.json.device.gateway;

import de.dvdgeisler.iot.dirigera.client.api.http.json.Identifier;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.*;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.DeviceSet;

import java.time.LocalDateTime;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class GatewayStatus extends Identifier {
    public DeviceCategory type;
    public DeviceType deviceType;
    public LocalDateTime createdAt;
    public Boolean isReachable;
    public LocalDateTime lastSeen;
    public GatewayAttributes attributes;
    public DeviceCapabilities capabilities;
    public List<DeviceSet> deviceSet;
    public List<String> remoteLinks;
    public Boolean isHidden;
    public String apiVersion;

    public GatewayStatus(final String id, final DeviceCategory type, final DeviceType deviceType, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final GatewayAttributes attributes, final DeviceCapabilities capabilities, final List<DeviceSet> deviceSet, final List<String> remoteLinks, final Boolean isHidden, final String apiVersion) {
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
        this.isHidden = isHidden;
        this.apiVersion = apiVersion;
    }

    public GatewayStatus() {
    }
}
