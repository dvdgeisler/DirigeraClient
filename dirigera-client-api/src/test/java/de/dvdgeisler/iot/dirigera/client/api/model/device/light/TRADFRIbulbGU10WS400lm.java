package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TRADFRIbulbGU10WS400lm extends DeviceTest {
    final static String JSON = """
            {
              "id" : "d6e34694-c1d4-44d7-bba0-7c26fdc8aa8b_1",
              "type" : "light",
              "deviceType" : "light",
              "createdAt" : "2022-11-12T12:53:03.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-12T12:53:11.000Z",
              "attributes" : {
                "customName" : "Ljuskälla 1",
                "firmwareVersion" : "2.3.087",
                "hardwareVersion" : "1",
                "manufacturer" : "IKEA of Sweden",
                "model" : "TRADFRI bulb GU10 WS 400lm",
                "productCode" : "",
                "serialNumber" : "90FD9FFFFE690658",
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

    public TRADFRIbulbGU10WS400lm() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?> device) {
        assertInstanceOf(LightDevice.class, device);
    }
}