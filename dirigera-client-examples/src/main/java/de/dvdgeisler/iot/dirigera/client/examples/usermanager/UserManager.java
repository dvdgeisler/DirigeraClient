package de.dvdgeisler.iot.dirigera.client.examples.usermanager;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.user.User;
import de.dvdgeisler.iot.dirigera.client.api.model.user.UserName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Scanner;

/**
 * Manage users
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {ClientApi.class})
public class UserManager {
    private final static Logger log = LoggerFactory.getLogger(UserManager.class);
    private final ClientApi api;

    public UserManager(final ClientApi api) {
        this.api = api;
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            final Scanner in;
            List<User> users;
            User user;
            String command, username;
            boolean run;
            int userToDelete;

            in = new Scanner(System.in);

            this.api.oauth.pairIfRequired().block();

            do {
                users = this.api.user.users().block();
                user = this.api.user.getUser().block();
                System.out.printf("No. %36s %24s %30s %12s %18s %36s %s%n", "User Id", "Name", "EMail", "Role", "Audience", "Verified User Id", "Created At");
                for (int i = 0; i < users.size(); i++) {
                    System.out.printf("%02d%1s %36s %24s %30s %12s %18s %36s %s%n",
                            i,
                            user.uid.equals(users.get(i).uid) ? "*" : "",
                            users.get(i).uid,
                            users.get(i).name,
                            users.get(i).email,
                            users.get(i).role,
                            users.get(i).audience,
                            users.get(i).verifiedUid,
                            users.get(i).createdTimestamp);
                }
                System.out.println("Commands:");
                System.out.println("e: Edit username of current user (marked by *)");
                System.out.println("d: Delete any user except the current user");
                System.out.println("q: Quit");
                System.out.print("e,d,q:");
                command = in.nextLine();
                run = switch (command) {
                    case "e" -> {
                        System.out.printf("Name (%s): ", user.name);
                        username = in.nextLine();
                        if(username.isBlank() || username.equals(user.name)) {
                            log.info("No changes. User is not updated.");
                            yield true;
                        }
                        log.info("Change username forom \"{}\" to \"{}\"", user.name, username);
                        this.api.user.setUserName(new UserName(username)).block();
                        yield true;
                    }
                    case "d" -> {
                        System.out.print("Enter user No.: ");
                        userToDelete = Integer.parseInt(in.nextLine());
                        if(userToDelete < 0 || userToDelete >= users.size()) {
                            log.error("Invalid user selected. No. must be between {} and {}.", 0, users.size() - 1);
                            yield true;
                        }
                        if(users.get(userToDelete).uid.equals(user.uid)) {
                            log.error("You cant delete yourself");
                            yield true;
                        }
                        log.info("Delete user {} ({})", users.get(userToDelete).uid, users.get(userToDelete).name);
                        this.api.user.deleteUser(users.get(userToDelete).uid.toString()).block();
                        yield true;
                    }
                    case "q" -> {
                        yield false;
                    }
                    default -> {
                        log.error("Unknown command: {}", command);
                        yield true;
                    }
                };
            } while (run);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(UserManager.class, args).close();
    }


}
