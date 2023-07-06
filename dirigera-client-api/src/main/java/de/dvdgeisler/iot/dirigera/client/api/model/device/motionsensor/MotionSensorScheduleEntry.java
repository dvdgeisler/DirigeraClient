package de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor;

public class MotionSensorScheduleEntry {
    public String time;
    public int offset;

    public MotionSensorScheduleEntry() {
    }

    public MotionSensorScheduleEntry(String time, int offset) {
        this.time = time;
        this.offset = offset;
    }
}
