package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SceneInfo {
    public String name;
    public String icon;

    public SceneInfo(final String name, final String icon) {
        this.name = name;
        this.icon = icon;
    }

    public SceneInfo() {
    }
}
