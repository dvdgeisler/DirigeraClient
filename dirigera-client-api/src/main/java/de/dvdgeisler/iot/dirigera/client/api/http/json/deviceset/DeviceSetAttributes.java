package de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset;

public class DeviceSetAttributes {
    public String name;
    public String icon;

    public DeviceSetAttributes(final String name, final String icon) {
        this.name = name;
        this.icon = icon;
    }

    public DeviceSetAttributes() {
    }
}
