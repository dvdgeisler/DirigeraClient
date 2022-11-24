package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class LEPTITERRecessedspotlight extends DeviceTest {
    final static String JSON = """
            {
            "id" : "7e69e364-17b6-4c15-9c18-1835d0e44d87_1",
            "type" : "light",
            "deviceType" : "light",
            "createdAt" : "2022-11-13T12:58:16.000Z",
            "isReachable" : true,
            "lastSeen" : "2022-11-22T17:57:24.000Z",
            "attributes" : {
            "customName" : "Bathroom light",
            "firmwareVersion" : "2.3.087",
            "hardwareVersion" : "1",
            "manufacturer" : "IKEA of Sweden",
            "model" : "LEPTITER Recessed spot light",
            "productCode" : "T1820",
            "serialNumber" : "000D6FFFFE12EC29",
            "isOn" : false,
            "startupOnOff" : "startOn",
            "lightLevel" : 20,
            "colorMode" : "temperature",
            "colorTemperature" : 2500,
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
            "id" : "90b00dc0-6a6a-477b-82f1-f7ac47c6cd6d",
            "name" : "Bathroom",
            "color" : "ikea_lilac_no_3",
            "icon" : "rooms_bathtub"
            },
            "deviceSet" : [ ],
            "remoteLinks" : [ "57e25312-9e04-4fcc-adc3-2e57c9c89be0_1" ],
            "isHidden" : false
            }
            """;

    public LEPTITERRecessedspotlight() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertInstanceOf(LightDevice.class, device);
    }
}