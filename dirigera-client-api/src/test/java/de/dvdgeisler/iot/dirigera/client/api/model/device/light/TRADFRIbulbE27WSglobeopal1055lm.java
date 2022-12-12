package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TRADFRIbulbE27WSglobeopal1055lm extends DeviceTest {
    final static String JSON = """
            {
              "id" : "672e6014-9c66-4e14-927e-bf55ab27fb46_1",
              "type" : "light",
              "deviceType" : "light",
              "createdAt" : "2022-11-06T14:05:11.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-10T22:44:54.000Z",
              "attributes" : {
                "customName" : "Wohnzimmer StehNeu",
                "model" : "TRADFRIbulbE27WSglobeopal1055lm",
                "manufacturer" : "IKEA of Sweden",
                "firmwareVersion" : "1.0.012",
                "hardwareVersion" : "1",
                "productCode" : "LED2003G10",
                "isOn" : false,
                "startupOnOff" : "startOn",
                "lightLevel" : 34,
                "colorTemperature" : 2403,
                "colorTemperatureMin" : 4000,
                "colorTemperatureMax" : 2202,
                "colorMode" : "temperature",
                "identifyStarted" : "2000-01-01T00:00:00.000Z",
                "identifyPeriod" : 0,
                "permittingJoin" : false,
                "otaStatus" : "upToDate",
                "otaState" : "readyToCheck",
                "otaProgress" : 0,
                "otaPolicy" : "autoUpdate",
                "otaScheduleStart" : "00:00",
                "otaScheduleEnd" : "00:00"
              },
              "capabilities" : {
                "canSend" : [ ],
                "canReceive" : [ "customName", "isOn", "lightLevel", "colorTemperature" ]
              },
              "room" : {
                "id" : "156c4c6e-4511-49e0-b71d-a91cf8298899",
                "name" : "Wohnzimmer",
                "color" : "ikea_green_no_65",
                "icon" : "rooms_sofa"
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ "1d59560b-8313-494a-be62-f7e7966b3acc_1" ],
              "isHidden" : false
            }
            """;

    public TRADFRIbulbE27WSglobeopal1055lm() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertInstanceOf(LightDevice.class, device);
    }
}