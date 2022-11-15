package de.dvdgeisler.iot.dirigera.client.api.model.device.shortcutcontroller;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStateCommand;

public class ShortcutControllerStateCommand extends DeviceStateCommand<ShortcutControllerStateAttributes> {

    public ShortcutControllerStateCommand(final Integer transitionTime, final Integer period, final ShortcutControllerStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public ShortcutControllerStateCommand() {
    }
}
