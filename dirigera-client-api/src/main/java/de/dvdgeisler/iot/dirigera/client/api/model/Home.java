package de.dvdgeisler.iot.dirigera.client.api.model;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.model.music.Music;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.Scene;
import de.dvdgeisler.iot.dirigera.client.api.model.user.User;

import java.util.List;

public class Home {
    public GatewayDevice hub;
    public List<Device> devices;
    public List<User> users;
    public User user;
    public List<Scene> scenes;
    public List<Room> rooms;

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

    public List<DeviceSet> deviceSets;
    public Music music;
}
