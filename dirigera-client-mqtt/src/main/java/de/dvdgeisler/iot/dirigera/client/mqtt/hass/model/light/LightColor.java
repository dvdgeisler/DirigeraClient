package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LightColor {
    public Float h;
    public Float s;
    public Float r;
    public Float g;
    public Float b;
    public Float c;
    public Float w;
    public Float x;
    public Float y;

    public LightColor(final Float h, final Float s, final Float r, final Float g, final Float b, final Float c, final Float w, final Float x, final Float y) {
        this.h = h;
        this.s = s;
        this.r = r;
        this.g = g;
        this.b = b;
        this.c = c;
        this.w = w;
        this.x = x;
        this.y = y;
    }

    public LightColor() {
    }
}
