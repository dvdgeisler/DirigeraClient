package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TRADFRIbulbE27CWSopal600lm extends DeviceTest {
    final static String JSON = """
            {
              "id" : "27b79220-228f-47ce-aaa1-d39183d1dae4_1",
              "type" : "light",
              "deviceType" : "light",
              "createdAt" : "2022-11-11T15:23:54.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-11T15:24:23.000Z",
              "attributes" : {
                "customName" : "Ljuskälla 1",
                "firmwareVersion" : "2.3.093",
                "hardwareVersion" : "1",
                "manufacturer" : "IKEA of Sweden",
                "model" : "TRADFRI bulb E27 CWS opal 600lm",
                "productCode" : "LED1624G9E27EU",
                "serialNumber" : "90FD9FFFFE64CA5E",
                "isOn" : true,
                "startupOnOff" : "startOn",
                "lightLevel" : 67,
                "colorMode" : "color",
                "colorHue" : 80.00922295920168,
                "colorSaturation" : 0.8956041131978718,
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
                "canReceive" : [ "customName", "isOn", "lightLevel", "colorHue", "colorSaturation" ]
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

    public TRADFRIbulbE27CWSopal600lm() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertInstanceOf(LightDevice.class, device);
    }
}