package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.motionsensor;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class MotionSensorScheduleEntry {
    @JsonFormat(pattern="HH:mm")
    public LocalTime time;

    public MotionSensorScheduleEntry() {
    }

    public MotionSensorScheduleEntry(final LocalTime time) {
        this.time = time;
    }
}
