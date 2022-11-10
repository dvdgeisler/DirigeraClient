package de.dvdgeisler.iot.dirigera.client.api.http.json.device.motionsensor;

import java.time.LocalTime;

public class MotionSensorScheduleEntry {
    public LocalTime time;

    public MotionSensorScheduleEntry() {
    }

    public MotionSensorScheduleEntry(final LocalTime time) {
        this.time = time;
    }
}
