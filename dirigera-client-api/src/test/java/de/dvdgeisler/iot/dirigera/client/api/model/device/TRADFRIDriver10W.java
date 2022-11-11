package de.dvdgeisler.iot.dirigera.client.api.model.device;

import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;

import static org.junit.jupiter.api.Assertions.*;

class TRADFRIDriver10W extends DeviceTest {
    final static String JSON = """
            {
              "id" : "b88dcfa3-4659-4c5e-b8cd-32c3b78a9a82_1",
              "type" : "light",
              "deviceType" : "light",
              "createdAt" : "2022-11-06T19:11:28.000Z",
              "isReachable" : false,
              "lastSeen" : "2022-11-08T03:18:13.000Z",
              "attributes" : {
                "customName" : "Küche klein",
                "model" : "TRADFRI Driver 10W",
                "manufacturer" : "IKEA of Sweden",
                "firmwareVersion" : "1.0.002",
                "hardwareVersion" : "1",
                "productCode" : "ICPSHC2410EUIL2",
                "isOn" : true,
                "startupOnOff" : "startOn",
                "lightLevel" : 62,
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
                "canReceive" : [ "customName", "isOn", "lightLevel" ]
              },
              "room" : {
                "id" : "2569129f-8693-46a3-a97d-d065f710a776",
                "name" : "Küche",
                "color" : "ikea_yellow_no_24",
                "icon" : "rooms_cutlery"
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ ],
              "isHidden" : false
            }
            """;

    public TRADFRIDriver10W() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?> device) {
        assertInstanceOf(LightDevice.class, device);
    }
}