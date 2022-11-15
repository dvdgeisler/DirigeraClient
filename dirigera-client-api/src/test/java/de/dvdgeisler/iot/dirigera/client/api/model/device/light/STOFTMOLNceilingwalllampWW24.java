package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class STOFTMOLNceilingwalllampWW24 extends DeviceTest {
    final static String JSON = """
            {
              "id" : "0e973453-35be-4639-bdf3-a13321418ac4_1",
              "type" : "light",
              "deviceType" : "light",
              "createdAt" : "2022-11-06T14:00:04.000Z",
              "isReachable" : false,
              "lastSeen" : "2022-11-11T12:07:28.000Z",
              "attributes" : {
                "customName" : "Licht 3",
                "model" : "STOFTMOLN ceiling/wall lamp WW24",
                "manufacturer" : "IKEA of Sweden",
                "firmwareVersion" : "1.0.006",
                "hardwareVersion" : "1",
                "productCode" : "T2035",
                "isOn" : true,
                "startupOnOff" : "startOn",
                "lightLevel" : 74,
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
                "id" : "d9b0676a-bc48-46f4-b806-191785c14418",
                "name" : "Badezimmer",
                "color" : "ikea_lilac_no_3",
                "icon" : "rooms_bathtub"
              },
              "deviceSet" : [ {
                "id" : "69ff079d-d1de-4a55-a63b-054b84ae03f8",
                "name" : "Licht Bad",
                "icon" : "no_icon"
              } ],
              "remoteLinks" : [ "e1f8345d-6d3c-4b09-ae1e-282f20a691a5_1" ],
              "isHidden" : false
            }
            """;

    public STOFTMOLNceilingwalllampWW24() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertInstanceOf(LightDevice.class, device);
    }
}