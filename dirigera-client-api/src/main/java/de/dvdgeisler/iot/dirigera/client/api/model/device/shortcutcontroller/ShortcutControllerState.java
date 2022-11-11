package de.dvdgeisler.iot.dirigera.client.api.model.device.shortcutcontroller;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceState;

public class ShortcutControllerState extends DeviceState<ShortcutControllerStateAttributes> {

    public ShortcutControllerState(final Integer transitionTime, final Integer period, final ShortcutControllerStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public ShortcutControllerState() {
    }
}
