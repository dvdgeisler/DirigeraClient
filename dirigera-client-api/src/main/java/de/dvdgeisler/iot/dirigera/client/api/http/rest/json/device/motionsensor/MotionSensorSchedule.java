package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.motionsensor;

public class MotionSensorSchedule {
    public MotionSensorScheduleEntry onCondition;
    public MotionSensorScheduleEntry offCondition;

    public MotionSensorSchedule() {
    }

    public MotionSensorSchedule(final MotionSensorScheduleEntry onCondition, final MotionSensorScheduleEntry offCondition) {
        this.onCondition = onCondition;
        this.offCondition = offCondition;
    }
}
