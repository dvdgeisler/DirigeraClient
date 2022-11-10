package de.dvdgeisler.iot.dirigera.client.api.model;

public class StepAttributes {
    public Integer transitionTime;
    public Integer stepSize;
    public StepAttributesType type;

    public StepAttributes(final Integer transitionTime, final Integer stepSize, final StepAttributesType type) {
        this.transitionTime = transitionTime;
        this.stepSize = stepSize;
        this.type = type;
    }

    public StepAttributes() {
    }
}
