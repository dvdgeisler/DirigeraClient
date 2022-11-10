package de.dvdgeisler.iot.dirigera.client.api.model.integrations.alexa;

public class AlexaParameters {
    public String clientID;
    public String redirectURL;
    public String skillStage;

    public AlexaParameters() {
    }

    public AlexaParameters(final String clientID, final String redirectURL, final String skillStage) {
        this.clientID = clientID;
        this.redirectURL = redirectURL;
        this.skillStage = skillStage;
    }
}
