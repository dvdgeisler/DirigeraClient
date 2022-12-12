package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.Identifier;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SceneActionDevice.class, name = "device"),
})
public class SceneAction extends Identifier {
    public SceneActionType type;

    public SceneAction() {
    }

    public SceneAction(final String id, final SceneActionType type) {
        super(id);
        this.type = type;
    }
}
