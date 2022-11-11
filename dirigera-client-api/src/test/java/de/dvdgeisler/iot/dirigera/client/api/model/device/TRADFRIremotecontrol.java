package de.dvdgeisler.iot.dirigera.client.api.model.device;

import de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller.LightControllerDevice;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TRADFRIremotecontrol extends DeviceTest {
    final static String JSON = """
            {
              "id" : "58050dc9-8a41-43bb-8782-a1d6d284c9e5_1",
              "type" : "controller",
              "deviceType" : "lightController",
              "createdAt" : "2022-11-06T13:56:47.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-10T21:19:41.000Z",
              "attributes" : {
                "customName" : "Fernbedienung Büro",
                "model" : "TRADFRI remote control",
                "manufacturer" : "IKEA of Sweden",
                "firmwareVersion" : "1.2.214",
                "hardwareVersion" : "1",
                "productCode" : "E1524",
                "batteryPercentage" : 2,
                "isOn" : false,
                "lightLevel" : 1,
                "permittingJoin" : false,
                "otaStatus" : "upToDate",
                "otaState" : "updateComplete",
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
                "id" : "3aee21e2-1cae-475c-90e2-128888658f9c",
                "name" : "Büro",
                "color" : "ikea_green_no_65",
                "icon" : "rooms_desk"
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ ],
              "isHidden" : false
            }
            """;

    public TRADFRIremotecontrol() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?> device) {
        assertTrue(device instanceof LightControllerDevice);
    }
}