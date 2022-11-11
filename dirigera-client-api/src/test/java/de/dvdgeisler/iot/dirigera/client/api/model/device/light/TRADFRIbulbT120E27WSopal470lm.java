package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TRADFRIbulbT120E27WSopal470lm extends DeviceTest {
    final static String JSON = """
            {
              "id" : "69e38aac-6682-4962-af1c-05d75709b4b9_1",
              "type" : "light",
              "deviceType" : "light",
              "createdAt" : "2022-11-06T14:18:05.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-10T17:21:41.000Z",
              "attributes" : {
                "customName" : "Licht Büro",
                "model" : "TRADFRIbulbT120E27WSopal470lm",
                "manufacturer" : "IKEA of Sweden",
                "firmwareVersion" : "1.0.012",
                "hardwareVersion" : "1",
                "serialNumber" : "540F57FFFE2066B4",
                "productCode" : "LED1937T5",
                "isOn" : false,
                "startupOnOff" : "startOn",
                "lightLevel" : 83,
                "colorTemperature" : 2702,
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
                "id" : "3aee21e2-1cae-475c-90e2-128888658f9c",
                "name" : "Büro",
                "color" : "ikea_green_no_65",
                "icon" : "rooms_desk"
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ "58050dc9-8a41-43bb-8782-a1d6d284c9e5_1" ],
              "isHidden" : false
            }
            """;

    public TRADFRIbulbT120E27WSopal470lm() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?> device) {
        assertInstanceOf(LightDevice.class, device);
    }
}