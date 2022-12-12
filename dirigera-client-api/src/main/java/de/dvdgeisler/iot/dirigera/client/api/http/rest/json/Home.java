package de.dvdgeisler.iot.dirigera.client.api.http.rest.json;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway.GatewayDevice;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.music.Music;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene.Scene;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.user.User;

import java.util.List;

public class Home {
    public GatewayDevice hub;
    public List<Device> devices;
    public List<User> users;
    public User user;
    public List<Scene> scenes;
    public List<Room> rooms;
    public List<DeviceSet> deviceSets;
    public Music music;

    public Home() {
    }

    public Home(final GatewayDevice hub, final List<Device> devices, final List<User> users, final User user, final List<Scene> scenes, final List<Room> rooms, final List<DeviceSet> deviceSets, final Music music) {
        this.hub = hub;
        this.devices = devices;
        this.users = users;
        this.user = user;
        this.scenes = scenes;
        this.rooms = rooms;
        this.deviceSets = deviceSets;
        this.music = music;
    }
}
