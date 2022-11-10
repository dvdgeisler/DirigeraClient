package de.dvdgeisler.iot.dirigera.client.api.model.device;

public class DeviceAttributes {
    public String model;
    public String manufacturer;
    public String firmwareVersion;
    public String hardwareVersion;
    public String serialNumber;

    public DeviceAttributes() {
    }

    public DeviceAttributes(final String model, final String manufacturer, final String firmwareVersion, final String hardwareVersion, final String serialNumber) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.firmwareVersion = firmwareVersion;
        this.hardwareVersion = hardwareVersion;
        this.serialNumber = serialNumber;
    }
}
