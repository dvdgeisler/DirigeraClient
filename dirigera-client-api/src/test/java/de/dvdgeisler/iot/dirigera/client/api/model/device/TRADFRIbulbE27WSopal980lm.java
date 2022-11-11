package de.dvdgeisler.iot.dirigera.client.api.model.device;

import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TRADFRIbulbE27WSopal980lm extends DeviceTest {
    final static String JSON = """
            {
              "id" : "ce2b3713-9333-45b4-b255-103ba0f86fe4_1",
              "type" : "light",
              "deviceType" : "light",
              "createdAt" : "2022-11-06T15:49:32.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-10T22:44:54.000Z",
              "attributes" : {
                "customName" : "Wohnzimmer StehEcke",
                "model" : "TRADFRI bulb E27 WS opal 980lm",
                "manufacturer" : "IKEA of Sweden",
                "firmwareVersion" : "2.3.087",
                "hardwareVersion" : "1",
                "productCode" : "LED1545G12E27EU",
                "isOn" : false,
                "startupOnOff" : "startOn",
                "lightLevel" : 44,
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

    public TRADFRIbulbE27WSopal980lm() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?> device) {
        assertInstanceOf(LightDevice.class, device);
    }
}