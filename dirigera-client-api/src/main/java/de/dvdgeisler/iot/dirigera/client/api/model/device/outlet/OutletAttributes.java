package de.dvdgeisler.iot.dirigera.client.api.model.device.outlet;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStartupMode;
import de.dvdgeisler.iot.dirigera.client.api.model.device.ota.OtaAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller.SoundControllerStateAttributes;

import java.time.LocalDateTime;

public class OutletAttributes extends DeviceAttributes {
    public String productCode;
    public DeviceStartupMode startupOnOff;
    public Boolean permittingJoin;
    public Integer identifyPeriod;
    public LocalDateTime identifyStarted;

    @JsonUnwrapped
    public OutletStateAttributes state;

    @JsonUnwrapped
    public OtaAttributes ota;

    public OutletAttributes(
            final String model,
            final String manufacturer,
            final String firmwareVersion,
            final String hardwareVersion,
            final String serialNumber,
            final String productCode,
            final DeviceStartupMode startupOnOff,
            final Boolean permittingJoin,
            final Integer identifyPeriod,
            final LocalDateTime identifyStarted,
            final OutletStateAttributes state,
            final OtaAttributes ota) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber);
        this.productCode = productCode;
        this.startupOnOff = startupOnOff;
        this.permittingJoin = permittingJoin;
        this.identifyPeriod = identifyPeriod;
        this.identifyStarted = identifyStarted;
        this.state = state;
        this.ota = ota;
    }

    public OutletAttributes() {
    }
}
