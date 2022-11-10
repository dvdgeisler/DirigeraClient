package de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor;

public class MotionSensorConfig {
    public Boolean scheduleOn;
    public Integer onDuration;
    public MotionSensorSchedule schedule;

    public MotionSensorConfig() {
    }

    public MotionSensorConfig(final Boolean scheduleOn, final Integer onDuration, final MotionSensorSchedule schedule) {
        this.scheduleOn = scheduleOn;
        this.onDuration = onDuration;
        this.schedule = schedule;
    }
}
