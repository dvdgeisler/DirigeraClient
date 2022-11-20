package de.dvdgeisler.iot.dirigera.client.api.model.device.outlet;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Aqarasmartplug extends DeviceTest {
    final static String JSON = """
            {
              "id" : "3eb744f5-ed9a-43ae-a6ee-ecce18d21c91_1",
              "type" : "outlet",
              "deviceType" : "outlet",
              "createdAt" : "2022-11-15T14:44:46.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-15T14:45:05.000Z",
              "attributes" : {
                "customName" : "Outlet 1",
                "firmwareVersion" : "",
                "hardwareVersion" : "1",
                "manufacturer" : "LUMI",
                "model" : "lumi.plug.maeu01",
                "serialNumber" : "370S01109354",
                "isOn" : false,
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
                "canReceive" : [ "customName", "isOn" ]
              },
              "room" : {
                "id" : "297071f0-e98a-4ba0-8c05-9b88a1dbc6c4",
                "name" : "Living Room ",
                "color" : "ikea_green_no_65",
                "icon" : "rooms_arm_chair"
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ ],
              "isHidden" : false
            }
            """;

    public Aqarasmartplug() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertTrue(device instanceof OutletDevice);
    }
}