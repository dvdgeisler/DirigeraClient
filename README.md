# Dirigera Client API

Unofficial client API for IKEA's new Smarthome Hub
[DIRIGERA](https://github.com/wjtje/DIRIGERA). The client API
uses DIRIGERA's REST interface at port 8443. The vast majority
of interfaces have been implemented. However, most are
barely tested, and some are known as inoperable.

## What is known to work
* Pair API with DIRIGERA
* Dump DIRIGERA's data model
* Fetch and edit devices, i.e.:
  * Gateway
    * DIRIGERA Hub for smart products
  * Repeater
    * TRADFRI signal repeater
  * Light & Driver
    * STOFTMOLN ceiling/wall lamp WW24
    * FLOALT panel WS 60x60
    * TRADFRI bulb E27 CWS 806lm
    * TRADFRI bulb E27 CWS opal 600lm
    * TRADFRI bulb E27 WS opal 980lm
    * TRADFRI bulb E27 WS opal 1000lm
    * TRADFRI bulb E27 WS globe opal 1055lm
    * TRADFRI bulb T120 E27 WS opal 470lm
    * TRADFRI bulb E14 WS opal 400lm
    * TRADFRI bulb GU10 WS 400lm
    * TRADFRI bulb GU10 WS 345lm
    * TRADFRI Driver 10W
    * TRADFRI Driver 30W
    * GUNNARP panel round
  * Light-Controller
    * Remote Control N2
    * TRADFRI on/off switch
    * TRADFRI remote control
    * LEPTITER Recessed spot light
    * TRADFRI wireless dimmer
  * Sound-Controller
    * SYMFONISK Sound Controller
  * Blinds-Controller
    * TRADFRI open/close remote
  * Motion-Sensor
    * TRADFRI motion sensor
  * Shortcut-Controller
    * TRADFRI SHORTCUT Button
  * Outlet
    * ASKVADER on/off switch
    * TRADFRI control outlet
    * Aqara Smart Plug (lumi.plug.maeu01)
  * Air Purifier
    * STARKVIND Air purifier
  * Blinds
    * PRAKTLYSING cellular blind
* Check for firmware updates
* (Un-)Link devices (e.g., light controller with light bulb)
* List music playlists and favorites
* Create, manipulate, and delete rooms
* Create, manipulate, and delete device-sets
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
    public CommandLineRunner run(final DirigeraApi api) {
        return (String... args) -> {
          api.pairIfRequired().block(); // pair gateway if required

          api.device.light.all() // fetch all light devices from hub
                  .flatMapMany(Flux::fromIterable)
                  .flatMap(d -> api.device.light.turnOn(d)) // turn on lights
                  .flatMap(d -> api.device.light.setLevel(d, 100)) // turn on lights
                  .flatMap(d -> api.device.light.setTemperature(d, d.attributes.state.color.temperatureMax)) // set color temperature
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
    Gateway-Dump (see [How to contribute](#how-to-contribute)).
* Scene actions and triggers.

### How to contribute

The most significant pain point is the limitation of the API data model. 
You can help us to improve it, and to support progressively more devices.

To do so, run the [Dump Application](dirigera-client-dump/src/main/java/de/dvdgeisler/iot/dirigera/client/dump/DumpApplication.java). 
This application reads the data model of your DIRIGERA and outputs it as JSON. Based on the dump, 
we can determine at which points the API data model deviates or is 
incomplete. You may submit the generated dump as an issue to GitHub.

#### Run the Dump Application
##### Prerequisites:
* Java 17 (or higher) has to be installed
  * Download Java the latest OpenJDK for your operating system [here](https://openjdk.org)
  * Unpack the zip file to a location of your choosing
  * Add `JAVA_HOME` to your environment variables with the following path `<PATH_TO_UNPACKED_ZIP_FILE>`
  * Add `%JAVA_HOME%\bin` to your path variable
  * Reboot your pc
  * Execute `java -version`
    * This should output something like:
    ```
      openjdk version "17.0.2" 2022-01-18
      OpenJDK Runtime Environment (build 17.0.2+8-86)
      OpenJDK 64-Bit Server VM (build 17.0.2+8-86, mixed mode, sharing)
    ```
* DIRIGERA Gateway is connected to the internet
  * Find the IP address of your DIRIGERA Gateway in your router.

##### Instructions
1) Download the latest [dirigera-client-dump.jar](https://github.com/dvdgeisler/DirigeraClient/releases)
2) Run `java -jar dirigira-client-dump.jar --dirigera.hostname=<ip address of DIRIGERA Gateway>`

## Integration to Home Assistant

Lights and sockets can be integrated into Home Assistant via MQTT (more devices will follow).
Home Assistant will create entities for supported devices, based on its MQTT auto discovery approach.

Add https://github.com/TheMrBooyah/hassio-repository to your home assistant.
Update the configuration to your setup.

In order to get your 'Token', run the [Dump Application](dirigera-client-dump/src/main/java/de/dvdgeisler/iot/dirigera/client/dump/DumpApplication.java). This will ask you to pair your gateway. After successfully pairing the gateway a file 'dirigera_access_token' will be created. Open the file with your favourite text editor and copy everything into the 'Token' field.

Start the addon, watch the logs for any errors and if everything went as expected, home assistant should have some new devices/entities from your IKEA Smart Hub.


![](img/hass-integration.png)

## Other repos to dig in

* [wjtje/DIRIGERA](https://github.com/wjtje/DIRIGERA)
* [mattias73andersson/dirigera-client-poc](https://github.com/mattias73andersson/dirigera-client-poc)
