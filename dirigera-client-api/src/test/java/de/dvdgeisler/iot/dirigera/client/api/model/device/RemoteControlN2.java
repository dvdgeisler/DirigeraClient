package de.dvdgeisler.iot.dirigera.client.api.model.device;

import de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller.LightControllerDevice;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RemoteControlN2 extends DeviceTest {
    final static String JSON = """
            {
              "id" : "26d8025a-1b5b-4248-991b-f85b250d6199_1",
              "type" : "controller",
              "deviceType" : "lightController",
              "createdAt" : "2022-11-02T21:15:16.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-10T03:05:55.000Z",
              "attributes" : {
                "customName" : "Remote",
                "model" : "Remote Control N2",
                "manufacturer" : "IKEA of Sweden",
                "firmwareVersion" : "1.0.024",
                "hardwareVersion" : "1",
                "serialNumber" : "84B4DBFFFE221943",
                "productCode" : "E2001",
                "batteryPercentage" : 100,
                "isOn" : false,
                "lightLevel" : 1,
                "permittingJoin" : false,
                "otaStatus" : "upToDate",
                "otaState" : "readyToCheck",
                "otaProgress" : 0,
                "otaPolicy" : "autoUpdate",
                "otaScheduleStart" : "00:00",
                "otaScheduleEnd" : "00:00"
              },
              "capabilities" : {
                "canSend" : [ "isOn", "lightLevel" ],
                "canReceive" : [ "customName" ]
              },
              "room" : {
                "id" : "300918c6-bd34-428c-8b86-c03e95ff981b",
                "name" : "MyRoom",
                "color" : "ikea_red_no_39",
                "icon" : "none"
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ ],
              "isHidden" : false
            }
            """;

    public RemoteControlN2() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?> device) {
        assertTrue(device instanceof LightControllerDevice);
    }
}