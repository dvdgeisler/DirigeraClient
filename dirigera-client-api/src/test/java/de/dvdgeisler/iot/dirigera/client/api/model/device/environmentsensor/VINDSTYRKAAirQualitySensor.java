package de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VINDSTYRKAAirQualitySensor extends DeviceTest {
    final static String JSON = """
            {
                 "id" : "XXXXXXXXXXXXXXXXXXX",
                 "type" : "sensor",
                 "deviceType" : "environmentSensor",
                 "createdAt" : "2023-03-10T13:23:42.000Z",
                 "isReachable" : true,
                 "lastSeen" : "2023-03-26T14:10:16.000Z",
                 "attributes" : {
                   "customName" : "Luftsensor",
                   "firmwareVersion" : "1.0.11",
                   "hardwareVersion" : "1",
                   "manufacturer" : "IKEA of Sweden",
                   "model" : "VINDSTYRKA",
                   "productCode" : "E2112",
                   "serialNumber" : "XXXXXXXXXXXX",
                   "currentTemperature" : 19,
                   "currentRH" : 52,
                   "currentPM25" : 2,
                   "maxMeasuredPM25" : 999,
                   "minMeasuredPM25" : 0,
                   "vocIndex" : 112,
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
                   "id" : "255c7093-f8bc-4ea6-aa32-e352f673b716",
                   "name" : "Schlafzimmer ",
                   "color" : "ikea_yellow_no_24",
                   "icon" : "rooms_bed"
                 },
                 "deviceSet" : [ ],
                 "remoteLinks" : [ ],
                 "isHidden" : false
            }
            """;

    public VINDSTYRKAAirQualitySensor() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertTrue(device instanceof EnvironmentSensorDevice);
    }
}