package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.unknown;

import com.fasterxml.jackson.databind.JsonNode;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Collects records of deserialized devices for which no specialized deserialization strategy is defined. If a device
 * class is recorded as unknown for the first time, a corresponding warning is logged, including additional information
 * about it's JSON format.
 */
public class UnknownDeviceCollector {
    private final static Logger log = LoggerFactory.getLogger(UnknownDeviceCollector.class);

    public static final UnknownDeviceCollector instance = new UnknownDeviceCollector();

    public static class UnknownDeviceEntry {
        public final UnknownDevice device;
        public final JsonNode jsonNode;

        public UnknownDeviceEntry(final UnknownDevice device, final JsonNode jsonNode) {
            this.device = device;
            this.jsonNode = jsonNode;
        }

        @Override
        public boolean equals(final Object o) {
            final UnknownDeviceEntry that;

            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            that = (UnknownDeviceEntry) o;
            return Objects.equals(getDeviceCategory(this.device), getDeviceCategory(that.device)) &&
                    Objects.equals(getDeviceType(this.device), getDeviceType(that.device)) &&
                    Objects.equals(getDeviceManufacturer(this.device), getDeviceManufacturer(that.device)) &&
                    Objects.equals(getDeviceModel(this.device), getDeviceModel(that.device)) &&
                    Objects.equals(getDeviceCustomName(this.device), getDeviceCustomName(that.device));
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                    getDeviceCategory(this.device),
                    getDeviceType(this.device),
                    getDeviceManufacturer(this.device),
                    getDeviceModel(this.device),
                    getDeviceCustomName(this.device));
        }
    }

    private final Set<UnknownDeviceEntry> entries;

    private UnknownDeviceCollector() {
        this.entries = new HashSet<>();
    }

    private boolean add(final UnknownDeviceEntry unknownDeviceEntry) {
        if (this.entries.add(unknownDeviceEntry)) {
            if (getDeviceType(unknownDeviceEntry.device) != null)
                log.warn("""
                                Unknown device found:
                                    type={}, category={},
                                    manufacturer={}, model={},
                                    customName={}, id={}
                                    
                                    Help us to support additional devices and create an issue on
                                    {} with the following content:
                                    {}
                                """,
                        getDeviceCategory(unknownDeviceEntry.device),
                        getDeviceType(unknownDeviceEntry.device),
                        getDeviceManufacturer(unknownDeviceEntry.device),
                        getDeviceModel(unknownDeviceEntry.device),
                        getDeviceCustomName(unknownDeviceEntry.device),
                        getDeviceId(unknownDeviceEntry.device),
                        "https://github.com/dvdgeisler/DirigeraClient",
                        unknownDeviceEntry.jsonNode);
            return true;
        }
        return false;
    }

    public boolean add(final UnknownDevice unknownDevice, final JsonNode jsonNode) {
        return this.add(new UnknownDeviceEntry(unknownDevice, jsonNode));
    }

    private static DeviceCategory getDeviceCategory(final UnknownDevice device) {
        return Optional.ofNullable(device).map(d -> d.type).orElse(null);
    }

    private static DeviceType getDeviceType(final UnknownDevice device) {
        return Optional.ofNullable(device).map(d -> d.deviceType).orElse(null);
    }

    private static String getDeviceManufacturer(final UnknownDevice device) {
        return Optional.ofNullable(device).map(d -> d.attributes).map(a -> a.manufacturer).orElse(null);
    }

    private static String getDeviceModel(final UnknownDevice device) {
        return Optional.ofNullable(device).map(d -> d.attributes).map(a -> a.model).orElse(null);
    }

    private static String getDeviceCustomName(final UnknownDevice device) {
        return Optional.ofNullable(device).map(d -> d.attributes).map(a -> a.state).map(s -> s.customName).orElse(null);
    }

    private static String getDeviceId(final UnknownDevice device) {
        return Optional.ofNullable(device).map(d -> d.id).orElse(null);
    }
}
