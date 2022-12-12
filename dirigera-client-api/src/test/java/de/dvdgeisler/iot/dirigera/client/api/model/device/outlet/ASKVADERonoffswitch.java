package de.dvdgeisler.iot.dirigera.client.api.model.device.outlet;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.outlet.OutletDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ASKVADERonoffswitch extends DeviceTest {
    final static String JSON = """
            {
              "id" : "e431f631-4459-4a74-9d99-6de93e866bb8_1",
              "type" : "outlet",
              "deviceType" : "outlet",
              "createdAt" : "2022-11-11T14:54:20.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-11T14:54:49.000Z",
              "attributes" : {
                "customName" : "Uttag 1",
                "firmwareVersion" : "1.0.002",
                "hardwareVersion" : "1",
                "manufacturer" : "IKEA of Sweden",
                "model" : "ASKVADER on/off switch",
                "productCode" : "E1836",
                "serialNumber" : "003C84FFFE8AC2EE",
                "isOn" : false,
                "startupOnOff" : "startPrevious",
                "lightLevel" : 100,
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
                "canReceive" : [ "customName", "isOn", "lightLevel" ]
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
            } ],
            "scenes" : [ ],
            "rooms" : [ {
              "id" : "decd27b3-f54a-4211-9a8f-e7bf70f832eb",
              "name" : "A",
              "color" : "ikea_green_no_65",
              "icon" : "rooms_arm_chair"
            }
            """;

    public ASKVADERonoffswitch() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertTrue(device instanceof OutletDevice);
    }
}