package de.dvdgeisler.iot.dirigera.client.api.model.device;

import de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor.MotionSensorDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller.SoundControllerDevice;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TRADFRImotionsensor extends DeviceTest {
    final static String JSON = """
            {
              "id" : "3cacf806-fdad-41af-a9c1-fd1e653604a4_1",
              "type" : "sensor",
              "deviceType" : "motionSensor",
              "createdAt" : "2022-11-10T03:27:23.000Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-10T15:38:51.000Z",
              "attributes" : {
                "customName" : "S",
                "firmwareVersion" : "24.4.5",
                "hardwareVersion" : "1",
                "manufacturer" : "IKEA of Sweden",
                "model" : "TRADFRI motion sensor",
                "productCode" : "E1745",
                "serialNumber" : "84BA20FFFE358C61",
                "batteryPercentage" : 100,
                "isOn" : false,
                "lightLevel" : 1,
                "permittingJoin" : false,
                "otaPolicy" : "autoUpdate",
                "otaProgress" : 0,
                "otaScheduleEnd" : "00:00",
                "otaScheduleStart" : "00:00",
                "otaState" : "readyToCheck",
                "otaStatus" : "upToDate",
                "sensorConfig" : {
                  "scheduleOn" : false,
                  "onDuration" : 300,
                  "schedule" : {
                    "onCondition" : {
                      "time" : "22:00"
                    },
                    "offCondition" : {
                      "time" : "06:00"
                    }
                  }
                }
              },
              "capabilities" : {
                "canSend" : [ "isOn", "lightLevel" ],
                "canReceive" : [ "customName" ]
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ ],
              "onDuration" : 300,
              "isHidden" : false,
              "sensorConfig" : {
                "scheduleOn" : false,
                "onDuration" : 300,
                "schedule" : {
                  "onCondition" : {
                    "time" : "22:00"
                  },
                  "offCondition" : {
                    "time" : "06:00"
                  }
                }
              }
            }
            """;

    public TRADFRImotionsensor() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?> device) {
        assertTrue(device instanceof MotionSensorDevice);
    }
}