package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset;

public class RoomAttributes extends DeviceSetAttributes {
    public String color;

    public RoomAttributes(final String name, final String icon, final String color) {
        super(name, icon);
        this.color = color;
    }

    public RoomAttributes() {
    }
}
