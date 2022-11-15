package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DIRIGERAHubforsmartproducts extends DeviceTest {
    final static String JSON = """
            {
              "id" : "72a7eec1-eb69-477b-afaf-4bf9b5ec457b_1",
              "type" : "gateway",
              "deviceType" : "gateway",
              "createdAt" : "2022-09-14T11:32:34.910Z",
              "isReachable" : true,
              "lastSeen" : "2022-11-11T14:38:00.445Z",
              "attributes" : {
                "customName" : "Home",
                "model" : "DIRIGERA Hub for smart products",
                "manufacturer" : "IKEA of Sweden",
                "firmwareVersion" : "2.96.11",
                "hardwareVersion" : "P2.5",
                "serialNumber" : "72a7eec1-eb69-477b-afaf-4bf9b5ec457b",
                "identifyStarted" : "2022-11-08T03:57:14.000Z",
                "identifyPeriod" : -1,
                "otaStatus" : "upToDate",
                "otaState" : "readyToCheck",
                "otaProgress" : 0,
                "otaPolicy" : "autoDownload",
                "otaScheduleStart" : "00:00",
                "otaScheduleEnd" : "00:00",
                "permittingJoin" : false,
                "backendConnected" : true,
                "backendConnectionPersistent" : true,
                "backendOnboardingComplete" : true,
                "backendRegion" : "eu-central-1",
                "backendCountryCode" : "DE",
                "userConsents" : [ {
                  "name" : "analytics",
                  "value" : "disabled"
                }, {
                  "name" : "diagnostics",
                  "value" : "enabled"
                } ],
                "logLevel" : 3,
                "coredump" : false,
                "timezone" : "Europe/Berlin",
                "countryCode" : "XZ",
                "coordinates" : {
                  "latitude" : 0.03603603603604,
                  "longitude" : 0.21926294064827,
                  "accuracy" : -1
                },
                "isOn" : false
              },
              "capabilities" : {
                "canSend" : [ ],
                "canReceive" : [ "customName", "permittingJoin", "userConsents", "logLevel", "time", "timezone", "countryCode", "coordinates" ]
              },
              "deviceSet" : [ ],
              "remoteLinks" : [ ]
            }
            """;

    public DIRIGERAHubforsmartproducts() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?,?> device) {
        assertTrue(device instanceof GatewayDevice);
    }
}