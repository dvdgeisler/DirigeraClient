package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TRADFRIbulbE27CWS806lm extends DeviceTest {
    final static String JSON = """
            {
              "id" : "936a74cf-7511-4fcd-88bd-b7445e9becaa_1",
              "type" : "light",
              "deviceType" : "light",
              "createdAt" : "2022-11-02T21:02:59.000Z",
              "isReachable" : false,
              "lastSeen" : "2022-11-11T05:08:36.000Z",
              "attributes" : {
                "customName" : "Death Star ",
                "model" : "TRADFRI bulb E27 CWS 806lm",
                "manufacturer" : "IKEA of Sweden",
                "firmwareVersion" : "1.0.021",
                "hardwareVersion" : "1",
                "serialNumber" : "94DEB8FFFE661818",
                "productCode" : "LED1924G9",
                "isOn" : true,
                "startupOnOff" : "startOn",
                "lightLevel" : 100,
                "colorHue" : 29.998059643457566,
                "colorSaturation" : 0.5730848705769356,
                "colorTemperature" : 4000,
                "colorTemperatureMin" : 4000,
                "colorTemperatureMax" : 2202,
                "colorMode" : "color",
                "identifyStarted" : "2022-11-09T00:54:48.000Z",
                "identifyPeriod" : 30,
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
                "canReceive" : [ "customName", "isOn", "lightLevel", "colorTemperature", "colorHue", "colorSaturation" ]
              },
              "room" : {
                "id" : "297071f0-e98a-4ba0-8c05-9b88a1dbc6c4",
                "name" : "Living Room ",
                "color" : "ikea_green_no_65",
                "icon" : "rooms_arm_chair"
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ "26d8025a-1b5b-4248-991b-f85b250d6199_1", "3cacf806-fdad-41af-a9c1-fd1e653604a4_1" ],
              "isHidden" : false
            }
            """;

    public TRADFRIbulbE27CWS806lm() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?> device) {
        assertInstanceOf(LightDevice.class, device);
    }
}