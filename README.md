# Dirigera Client API

Unofficial client API for IKEA's new Smarthome Hub
[DIRIGERA](https://github.com/wjtje/DIRIGERA). The client API
uses DIRIGERA's REST interface at port 8443. The vast majority
of interfaces have been implemented. However, most are
barely tested, and some are known as inoperable.

## What is known to work

* Pair API with DIRIGERA
* Dump DIRIGERA's data model
* Fetch and edit devices
* (Un-)Link devices (e.g., light controller with light bulb)
* List and play music playlists and favorites
* Create, manipulate, and delete rooms, device-sets, and scenes
* Manipulate, and delete users

| Device Type         | API                                        | Home-Assistant Integration                 | Tested Devices                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
|---------------------|--------------------------------------------|--------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Gateway             | <span style="color:green"> &#10004;</span> | <span style="color:red">   **X**   </span> | <ul><li>DIRIGERA Hub for smart products</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| Repeater            | <span style="color:green"> &#10004;</span> | <span style="color:red">   **X**   </span> | <ul><li>TRADFRI signal repeater</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| Light               | <span style="color:green"> &#10004;</span> | <span style="color:green"> &#10004;</span> | <ul><li>STOFTMOLN ceiling/wall lamp WW24</li><li>FLOALT panel WS 60x60</li><li>TRADFRI bulb E27 CWS 806lm</li><li>TRADFRI bulb E27 CWS opal 600lm</li><li>TRADFRI bulb E27 WS opal 980lm</li><li>TRADFRI bulb E27 WS opal 1000lm</li><li>TRADFRI bulb E27 WS globe opal 1055lm</li><li>TRADFRI bulb T120 E27 WS opal 470lm</li><li>TRADFRI bulb E14 WS opal 400lm</li><li>TRADFRI bulb GU10 WS 400lm</li><li>TRADFRI bulb GU10 WS 345lm</li><li>TRADFRI Driver 10W</li><li>TRADFRI Driver 30W</li><li>GUNNARP panel round</li><li>LWA017 (Signify Netherlands B.V.)</li></ul> |
| Outlet              | <span style="color:green"> &#10004;</span> | <span style="color:green"> &#10004;</span> | <ul><li>ASKVADER on/off switch</li><li>TRADFRI control outlet</li><li>Aqara Smart Plug (lumi.plug.maeu01)</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| Air Purifier        | <span style="color:orange">**?**   </span> | <span style="color:red">   **X**   </span> | <ul><li>STARKVIND Air purifier</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| Blinds              | <span style="color:orange">**?**   </span> | <span style="color:orange">**?**   </span> | <ul><li>PRAKTLYSING cellular blind</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| Light-Controller    | <span style="color:green"> &#10004;</span> | <span style="color:red">   **X**   </span> | <ul><li>Remote Control N2</li><li>TRADFRI on/off switch</li><li>TRADFRI remote control</li><li>LEPTITER Recessed spot light</li><li>TRADFRI wireless dimmer</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                         |
| Blinds-Controller   | <span style="color:green"> &#10004;</span> | <span style="color:red">   **X**   </span> | <ul><li>TRADFRI open/close remote</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| Sound-Controller    | <span style="color:orange">**?**   </span> | <span style="color:red">   **X**   </span> | <ul><li>SYMFONISK Sound Controller</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| Shortcut-Controller | <span style="color:green"> &#10004;</span> | <span style="color:red">   **X**   </span> | <ul><li>TRADFRI SHORTCUT Button</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| Motion-Sensor       | <span style="color:green"> &#10004;</span> | <span style="color:red">   **X**   </span> | <ul><li>TRADFRI motion sensor</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| Speaker             | <span style="color:orange">**?**   </span> | <span style="color:red">   **X**   </span> | <ul><li>SYMFONISK Bookshelf S21</li></ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |

Legend:</br>
<span style="color:green"> &#10004;</span> fully functional</br>
<span style="color:orange">**?**   </span> implemented but not tested</br>
<span style="color:red">   **X**   </span> not implemented

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
    public CommandLineRunner run(final DirigeraApi api) {
        return (String... args) -> {
            api.device.light.all() // fetch all light devices from hub
                    .flatMapMany(Flux::fromIterable)
                    .flatMap(d -> api.device.light.turnOn(d)) // turn on lights
                    .flatMap(d -> api.device.light.setLevel(d, 100)) // set light level to 100%
                    .flatMap(d -> api.device.light.setTemperature(d, d.attributes.state.color.temperatureMax)) // set color temperature
                    .blockLast();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args).close();
    }
}
```

## How to contribute

1) The most significant pain point is the limitation of the API data model.
   You can help us to improve it, and to support progressively more devices.</br></br>
   To do so, run the [Dump Application](dirigera-client-dump/src/main/java/de/dvdgeisler/iot/dirigera/client/dump/DumpApplication.java).
   This application reads the data model of your DIRIGERA and outputs it as JSON. Based on the dump,
   we can determine at which points the API data model deviates or is
   incomplete. You may submit the generated dump as an issue to GitHub.</br></br>
2) Try it out and share your experience. Many functionalities are "blind" implemented. This means 
   that the endpoints have been reverse-engineered, and the transmitted data was derived from shared 
   dumps. However, only testing with real devices can verify whether the interface is implemented 
   correctly.

### Build and run the Dump Application

1) Clone repository:

```bash
git clone git@github.com:dvdgeisler/DirigeraClient.git
cd DirigeraClient
```

2) Build project:

```bash
./mvnw package
```

3) Run the Dump-Application:

```bash
java -jar ./dirigera-client-dump/target/dirigera-client-dump.jar
```
If the Dirigera Hub is not discovered automatically, 
you may specify the address and port via the parameters 
`--dirigera.hostname=<DIRIGERA-IP-ADDRESS>` and `--dirigera.hostname=8443`.

## Integration to Home Assistant

Lights and sockets can be integrated into Home Assistant via MQTT (more devices will follow).
Home Assistant will create entities for supported devices, based on its MQTT auto discovery approach.

Add `https://github.com/TheMrBooyah/hassio-repository` to your home assistant.
Update the configuration to your setup.

In order to get your 'Token', run
the [Dump Application](dirigera-client-dump/src/main/java/de/dvdgeisler/iot/dirigera/client/dump/DumpApplication.java).
This will ask you to pair your gateway. After successfully pairing the gateway a file 'dirigera_access_token' will be
created. Open the file with your favourite text editor and copy everything into the 'Token' field.

Start the addon, watch the logs for any errors and if everything went as expected, home assistant should have some new
devices/entities from your IKEA Smart Hub.

![](img/hass-integration.png)

## Other repos to dig in

* [TheMrBooyah/hassio-dirigera-client](https://github.com/TheMrBooyah/hassio-dirigera-client)
* [wjtje/DIRIGERA](https://github.com/wjtje/DIRIGERA)
* [mattias73andersson/dirigera-client-poc](https://github.com/mattias73andersson/dirigera-client-poc)
