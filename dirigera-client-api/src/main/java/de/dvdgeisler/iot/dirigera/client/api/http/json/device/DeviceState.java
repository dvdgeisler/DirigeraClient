package de.dvdgeisler.iot.dirigera.client.api.http.json.device;

public class DeviceState<_Attributes> {
    public Integer transitionTime;
    public Integer period;
    public _Attributes attributes;

    public DeviceState(final Integer transitionTime, final Integer period, final _Attributes attributes) {
        this.transitionTime = transitionTime;
        this.period = period;
        this.attributes = attributes;
    }

    public DeviceState() {
    }
}
