package de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset;

public class Room extends DeviceSet<RoomAttributes> {
    public Room() {
    }

    public Room(final String id, final RoomAttributes attributes) {
        super(id, attributes);
    }
}
