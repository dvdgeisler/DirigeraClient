package de.dvdgeisler.iot.dirigera.client.examples.linkdevices;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.*;
import de.dvdgeisler.iot.dirigera.client.api.model.device.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Links to devices
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class LinkDevices {
    private final static Logger log = LoggerFactory.getLogger(LinkDevices.class);

    @Bean
    public CommandLineRunner run(final DirigeraApi api) {
        return (String... args) -> {
            final List<Device<DeviceAttributes<DeviceStateAttributes>, DeviceConfigurationAttributes>> receivingDevices;
            final List<Device<DeviceAttributes<DeviceStateAttributes>, DeviceConfigurationDefaultAttributes>> controllingDevices;
            final Scanner in;
            final int receivingDeviceIndex;
            final int controllingDeviceIndex;

            in = new Scanner(System.in);

            receivingDevices = api.device.all()
                    .flatMapMany(Flux::fromIterable)
                    .filter(d -> !d.capabilities.canReceive.isEmpty())
                    .collectList()
                    .block();

            System.out.println("Select receiving device:");
            System.out.printf("No. %40s %20s %20s %30s %s%n", "Device Id.", "Device Type", "Custom Name", "Created At", "Receivable Commands");
            for (int i = 0; i < receivingDevices.size(); i++) {
                System.out.printf("%02d  %40s %20s %20s %30s [%s]%n",
                        i,
                        receivingDevices.get(i).id,
                        receivingDevices.get(i).deviceType,
                        receivingDevices.get(i).attributes.state.customName,
                        receivingDevices.get(i).createdAt,
                        String.join(", ", receivingDevices.get(i).capabilities.canReceive));
            }
            System.out.print("Enter the number (No.) of the device that you want to set as the receiving device.: ");
            receivingDeviceIndex = in.nextInt();

            controllingDevices = api.device.controller.all()
                    .flatMapMany(Flux::fromIterable)
                    .filter(d -> !Objects.equals(d.id, receivingDevices.get(receivingDeviceIndex).id))
                    .filter(d -> !this.commonCapabilities(d, receivingDevices.get(receivingDeviceIndex)).isEmpty())
                    .filter(d -> !receivingDevices.get(receivingDeviceIndex).remoteLinks.contains(d.id))
                    .collectList()
                    .block();

            if (controllingDevices.isEmpty()) {
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
                        controllingDevices.get(i).attributes.state.customName,
                        controllingDevices.get(i).createdAt,
                        String.join(", ", this.commonCapabilities(controllingDevices.get(i), receivingDevices.get(receivingDeviceIndex)))
                );
            }
            System.out.print("Enter the number (No.) of the device that you want to set as the controlling device.: ");
            controllingDeviceIndex = in.nextInt();

            api.device.controller
                    .addRemoteLink(
                            controllingDevices.get(controllingDeviceIndex),
                            receivingDevices.get(receivingDeviceIndex))
                    .block();
            log.info("Devices successfully linked: controller={}, receiver={}",
                    controllingDevices.get(controllingDeviceIndex).id,
                    receivingDevices.get(receivingDeviceIndex).id);
        };
    }

    private List<String> commonCapabilities(final Device<?, ?> d0, final Device<?, ?> d1) {
        return d0.capabilities.canSend
                .stream()
                .filter(cmd -> d1.capabilities.canReceive.contains(cmd))
                .toList();
    }

    public static void main(String[] args) {
        SpringApplication.run(LinkDevices.class, args).close();
    }


}
