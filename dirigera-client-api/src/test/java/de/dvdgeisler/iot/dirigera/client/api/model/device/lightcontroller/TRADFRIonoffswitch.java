package de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.lightcontroller.LightControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TRADFRIonoffswitch extends DeviceTest {
    final static String JSON = """
            {
              "id" : "e1f8345d-6d3c-4b09-ae1e-282f20a691a5_1",
              "type" : "controller",
              "deviceType" : "lightController",
              "createdAt" : "2022-11-07T17:02:57.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-09T08:14:31.000Z",
              "attributes" : {
                "customName" : "Fernbedienung Bad",
                "model" : "TRADFRI on/off switch",
                "manufacturer" : "IKEA of Sweden",
                "firmwareVersion" : "24.4.5",
                "hardwareVersion" : "1",
                "productCode" : "E1743",
                "batteryPercentage" : 100,
                "isOn" : false,
                "lightLevel" : 1,
                "blindsCurrentLevel" : 0,
                "blindsState" : "",
                "permittingJoin" : false,
                "otaStatus" : "upToDate",
                "otaState" : "readyToCheck",
                "otaProgress" : 0,
                "otaPolicy" : "autoUpdate",
                "otaScheduleStart" : "00:00",
                "otaScheduleEnd" : "00:00"
              },
              "capabilities" : {
                "canSend" : [ "isOn", "lightLevel", "blindsState" ],
                "canReceive" : [ "customName" ]
              },
              "room" : {
                "id" : "d9b0676a-bc48-46f4-b806-191785c14418",
                "name" : "Badezimmer",
                "color" : "ikea_lilac_no_3",
                "icon" : "rooms_bathtub"
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ ],
              "isHidden" : false
            }
            """;

    public TRADFRIonoffswitch() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertInstanceOf(LightControllerDevice.class, device);
    }
}