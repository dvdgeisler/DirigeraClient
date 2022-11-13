# Dirigera Client API

Unofficial client API for IKEA's new Smarthome Hub
[DIRIGERA](https://github.com/wjtje/DIRIGERA). The client API
uses DIRIGERA's REST interface at port 8443. The vast majority
of interfaces have been implemented. However, most are
barely tested, and some are known as inoperable.

| API                                                                                                                                     | Endpoint Path   | Description                                                         |
|-----------------------------------------------------------------------------------------------------------------------------------------|-----------------|---------------------------------------------------------------------|
| [DirigeraClientApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientApi.java)                     | `/home/`        | Fetches the entire data model at once                               |
| [DirigeraClientOAuthApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientOAuthApi.java)           | `/oauth/`       | Authentication and pairing of the client API with the DIRIGERA.     |
| [DirigeraClientDeviceApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientDeviceApi.java)         | `/devices/`     | Fetch and edit device state. Remove devices from Gateway.           |
| [DirigeraClientGatewayApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientGatewayApi.java)       | `/hub/`         | Fetching the gateway's status. Check and perform firmware updates.  |
| [DirigeraClientDeviceSetApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientDeviceSetApi.java)   | `/device-set/`  | Fetch, create, edit, and drop device sets                           |
| [DirigeraClientMusicApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientMusicApi.java)           | `/music/`       | Fetch playlists and favorites.                                      |
| [DirigeraClientRemoteLinkApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientRemoteLinkApi.java) | `/remoteLinks/` | Link devices, i.e., controller/sensors to actors (e.g. light bulbs) |
| [DirigeraClientRoomApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientRoomApi.java)             | `/rooms/`       | Fetch, create, edit, and drop rooms                                 |
| [DirigeraClientSceneApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientSceneApi.java)           | `/scenes/`      | Fetch, create, edit, drop, and trigger scenes.                      |
| [DirigeraClientStepApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientStepApi.java)             | `/step/`        |                                                                     |
| [DirigeraClientUserApi](dirigera-client-api/src/main/java/de/dvdgeisler/iot/dirigera/client/api/DirigeraClientUserApi.java)             | `/users/`       | Fetch, edit, and drop users                                         |

## What is known to work
* Pair API with DIRIGERA
* Dump DIRIGERA's data model
* Fetch and edit devices, i.e.:
  * Gateway
    * DIRIGERA Hub for smart products
  * Light
    * STOFTMOLN ceiling/wall lamp WW24
    * TRADFRI bulb E27 CWS 806lm
    * TRADFRI bulb E27 CWS opal 600lm
    * TRADFRI bulb E27 WS globe opal 1055lm
    * TRADFRI bulb E27 WS opal 980lm
    * TRADFRI bulb T120 E27 WS opal 470lm
    * TRADFRI bulb E14 WS opal 400lm
    * TRADFRI Driver 10W
  * Light-Controller
    * Remote Control N2
    * TRADFRI on/off switch
    * TRADFRI remote control
  * Sound-Controller 
    * SYMFONISK Sound Controller
  * Motion-Sensor 
    * TRADFRI motion sensor
  * Shortcut-Controller
    * TRADFRI SHORTCUT Button
* Check for firmware updates
* Link devices (e.g., light controller with light bulb)
* Create, manipulate, and delete Device-Sets
* List music playlists and favorites
* Create, manipulate, and delete rooms
* Create, manipulate, and delete scenes (without actions and triggers)
* Manipulate, and delete users

### Example Code
The package `dirigera-client-examples` provides several example 
applications, which essentially cover the aforementioned points.
However, to give you a glimpse of the look and feel of the library, 
here's an example:
```java
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraClientApi.class})
public class MyApplication {
    @Bean
    public CommandLineRunner run(final DirigeraClientApi api) {
        return (String... args) -> {
            api.oauth.pairIfRequired().block(); // pair gateway if required

            api.device.devices() // fetch all devices from hub
                    .flatMapMany(Flux::fromIterable)
                    .filter(d -> d.deviceType == DeviceType.LIGHT) // filter by light devices
                    .cast(LightDevice.class)
                    .flatMap(d -> api.device.editDevice(d.id, List.of(LIGHT_ON, LIGHT_LEVEL_100))) // turn on lights
                    .blockLast();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args).close();
    }
}
```

## What does not work

* Everything which is not defined in API's Data-Model.
  * The API's data model is strictly typed, but there are still many 
    devices missing. Hence, it's very likely you have a device linked 
    to your gateway, which is not depicted by the API. In this case, the 
    JSON deserialization will likely fail as soon as one of the endpoints
    returns the respective device data.
  * You may help us to overcome this limitation by providing us your 
    Gateway-Dump (see [How to contribute](#How to contribute)).
* Scene actions and triggers.
* Assigning devices to scenes, device-sets, or rooms
* Unlink devices

### How to contribute

The most significant pain point is the limitation of the API data model. 
You can help us to improve it, and to support progressively more devices.

To do so, run the [Dump Application](dirigera-client-dump/src/main/java/de/dvdgeisler/iot/dirigera/client/dump/DumpApplication.java). 
This application reads the data model of your DIRIGERA and outputs it as JSON. Based on the dump, 
we can determine at which points the API data model deviates or is 
incomplete. You may submit the generated dump as an issue to GitHub.

#### Build and run the Dump Application
```bash
./mvnw package
java -jar ./dirigera-client-dump/target/dirigera-client-dump-0.0.1-SNAPSHOT.jar --dirigera.hostname=<DIRIGERA-IP-ADDRESS>
```

## Other repos to dig in

* [wjtje/DIRIGERA](https://github.com/wjtje/DIRIGERA)
* [mattias73andersson/dirigera-client-poc](https://github.com/mattias73andersson/dirigera-client-poc)
