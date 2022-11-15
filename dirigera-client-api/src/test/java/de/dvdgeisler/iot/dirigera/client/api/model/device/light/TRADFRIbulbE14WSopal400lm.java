package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TRADFRIbulbE14WSopal400lm extends DeviceTest {
    final static String JSON = """
            {
              "id" : "7db6e5df-8540-4275-aa4b-bc20cdc7ba86_1",
              "type" : "light",
              "deviceType" : "light",
              "createdAt" : "2022-11-12T12:48:50.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-12T12:48:59.000Z",
              "attributes" : {
                "customName" : "Ljuskälla 1",
                "firmwareVersion" : "2.3.087",
                "hardwareVersion" : "1",
                "manufacturer" : "IKEA of Sweden",
                "model" : "TRADFRI bulb E14 WS opal 400lm",
                "productCode" : "",
                "serialNumber" : "90FD9FFFFE4B9854",
                "isOn" : true,
                "startupOnOff" : "startOn",
                "lightLevel" : 100,
                "colorMode" : "temperature",
                "colorTemperature" : 2702,
                "colorTemperatureMax" : 2202,
                "colorTemperatureMin" : 4000,
                "identifyPeriod" : 0,
                "identifyStarted" : "2000-01-01T00:00:00.000Z",
                "permittingJoin" : false,
                "otaPolicy" : "autoUpdate",
                "otaProgress" : 0,
                "otaScheduleEnd" : "00:00",
                "otaScheduleStart" : "00:00",
                "otaState" : "readyToCheck",
                "otaStatus" : "upToDate"
              },
              "capabilities" : {
                "canSend" : [ ],
                "canReceive" : [ "customName", "isOn", "lightLevel", "colorTemperature" ]
              },
              "room" : {
                "id" : "decd27b3-f54a-4211-9a8f-e7bf70f832eb",
                "name" : "A",
                "color" : "ikea_green_no_65",
                "icon" : "rooms_arm_chair"
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ ],
              "isHidden" : false
            }
            """;

    public TRADFRIbulbE14WSopal400lm() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?, ?> device) {
        assertInstanceOf(LightDevice.class, device);
    }
}