package de.dvdgeisler.iot.dirigera.client.api.model.device.repeater;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TRADFRIsignalrepeater extends DeviceTest {
    final static String JSON = """
            {
              "id" : "510f26fc-e896-4a97-a02e-779d7de3e22e_1",
              "type" : "repeater",
              "deviceType" : "repeater",
              "createdAt" : "2022-11-12T13:02:48.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-12T13:02:55.000Z",
              "attributes" : {
                "customName" : "Signalförstärkare 1",
                "firmwareVersion" : "2.3.086",
                "hardwareVersion" : "1",
                "manufacturer" : "IKEA of Sweden",
                "model" : "TRADFRI signal repeater",
                "productCode" : "E1746",
                "serialNumber" : "D0CF5EFFFE7A11D8",
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
                "canReceive" : [ "customName" ]
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

    public TRADFRIsignalrepeater() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertTrue(device instanceof RepeaterDevice);
    }
}