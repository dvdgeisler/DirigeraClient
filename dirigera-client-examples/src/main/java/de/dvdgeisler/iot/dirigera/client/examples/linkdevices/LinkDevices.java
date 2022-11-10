package de.dvdgeisler.iot.dirigera.client.examples.linkdevices;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller.LightControllerDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor.MotionSensorDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller.SoundControllerDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Links to devices
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraClientApi.class})
public class LinkDevices {
    private final static Logger log = LoggerFactory.getLogger(LinkDevices.class);

    @Bean
    public CommandLineRunner run(final DirigeraClientApi api) {
        return (String... args) -> {
            final List<Device<?>> devices;
            final List<Device<?>> receivingDevices;
            final List<Device<?>> controllingDevices;
            final Scanner in;
            final int receivingDeviceIndex;
            final int controllingDeviceIndex;

            api.oauth.pairIfRequired().block();

            in = new Scanner(System.in);

            devices = api.device.devices().block();
            receivingDevices = devices.stream()
                    .filter(d->!d.capabilities.canReceive.isEmpty())
                    .toList();

            System.out.println("Select receiving device:");
            System.out.printf("No. %40s %20s %20s %30s %s%n", "Device Id.", "Device Type", "Custom Name", "Created At", "Receivable Commands");
            for (int i = 0; i < receivingDevices.size(); i++) {
                System.out.printf("%02d  %40s %20s %20s %30s [%s]%n",
                        i,
                        receivingDevices.get(i).id,
                        receivingDevices.get(i).deviceType,
                        this.getCustomName(receivingDevices.get(i)),
                        receivingDevices.get(i).createdAt,
                        String.join(", ", receivingDevices.get(i).capabilities.canReceive));
            }
            System.out.print("Enter the number (No.) of the device that you want to set as the receiving device.: ");
            receivingDeviceIndex = in.nextInt();

            controllingDevices = new ArrayList<>();
            for (int i = 0; i < devices.size(); i++) {
                if (i == receivingDeviceIndex)
                    continue;
                if (this.commonCapabilities(devices.get(i), receivingDevices.get(receivingDeviceIndex)).isEmpty())
                    continue;
                if (devices.get(receivingDeviceIndex).remoteLinks.contains(devices.get(i).id))
                    continue;
                controllingDevices.add(devices.get(i));
            }
            if(controllingDevices.isEmpty()) {
                log.info("No suitable controlling device found.");
                return;
            }

            System.out.println("Select controlling device:");
            System.out.printf("No. %40s %20s %20s %30s %s%n", "Device Id.", "Device Type", "Custom Name", "Created At", "Available Commands");
            for (int i = 0; i < controllingDevices.size(); i++) {
                System.out.printf("%02d  %40s %20s %20s %30s [%s]%n",
                        i,
                        controllingDevices.get(i).id,
                        controllingDevices.get(i).deviceType,
                        this.getCustomName(controllingDevices.get(i)),
                        controllingDevices.get(i).createdAt,
                        String.join(", ", this.commonCapabilities(controllingDevices.get(i), receivingDevices.get(receivingDeviceIndex)))
                        );
            }
            System.out.print("Enter the number (No.) of the device that you want to set as the controlling device.: ");
            controllingDeviceIndex = in.nextInt();

            api.remoteLink.updateRemoteLinks(
                    controllingDevices.get(controllingDeviceIndex).id,
                    Map.of("targetIds", List.of(receivingDevices.get(receivingDeviceIndex).id)))
                    .doOnSuccess(v -> log.info("Devices successfully linked: controller={}, receiver={}",
                            controllingDevices.get(controllingDeviceIndex).id,
                            receivingDevices.get(receivingDeviceIndex).id))
                    .block();
        };
    }

    private String getCustomName(final Device<?> device) {
        return switch (device.deviceType) {
            case GATEWAY -> ((GatewayDevice)device).attributes.state.customName;
            case LIGHT -> ((LightDevice)device).attributes.state.customName;
            case LIGHT_CONTROLLER -> ((LightControllerDevice)device).attributes.state.customName;
            case SOUND_CONTROLLER -> ((SoundControllerDevice)device).attributes.state.customName;
            case MOTION_SENSOR -> ((MotionSensorDevice)device).attributes.state.customName;
            default -> "";
        };
    }

    private List<String> commonCapabilities(final Device<?> d0, final Device<?> d1) {
        return d0.capabilities.canSend
                .stream()
                .filter(cmd-> d1.capabilities.canReceive.contains(cmd))
                .toList();
    }

    public static void main(String[] args) {
        SpringApplication.run(LinkDevices.class, args).close();
    }


}
