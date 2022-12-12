package de.dvdgeisler.iot.dirigera.client.api.model.device.speaker;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.speaker.SpeakerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SYMFONISKBookshelfS21 extends DeviceTest {
    final static String JSON = """
            {
              "id": "570b263c-99d7-4f5f-ab27-a39b2639da0c_1",
              "type": "speaker",
              "deviceType": "speaker",
              "createdAt": "2022-11-26T03:11:25.990Z",
              "isReachable": true,
              "lastSeen": "2022-11-26T03:11:25.990Z",
              "attributes": {
                "customName": "Zahrada Reproduktor",
                "model": "SYMFONISK Bookshelf S21",
                "manufacturer": "Sonos, Inc.",
                "firmwareVersion": "70.3-35220",
                "hardwareVersion": "1.20.3.3-2.0",
                "serialNumber": "34-7E-5C-F5-46-A0:6",
                "productCode": "S21",
                "identifyStarted": "0001-01-01T00:00:00.000Z",
                "identifyPeriod": 0,
                "playback": "playbackIdle",
                "playbackLastChangedTimestamp": "2022-11-26T03:11:25.990Z",
                "playbackAudio": {},
                "playbackPosition": {
                  "position": 0,
                  "timestamp": "2022-11-26T03:11:25.797Z"
                },
                "volume": 0,
                "isMuted": true,
                "audioGroup": "6975a151-910f-404c-93ea-ce54775bfacb"
              },
              "capabilities": {
                "canSend": [],
                "canReceive": [
                  "playback",
                  "playbackAudio",
                  "volume",
                  "isMuted"
                ]
              },
              "deviceSet": [],
              "remoteLinks": [],
              "isHidden": true
            }
            """;

    public SYMFONISKBookshelfS21() {
        super(JSON);
    }

    @Override
    public void validateDeserialize(final Device<?, ?> device) {
        assertTrue(device instanceof SpeakerDevice);
    }
}