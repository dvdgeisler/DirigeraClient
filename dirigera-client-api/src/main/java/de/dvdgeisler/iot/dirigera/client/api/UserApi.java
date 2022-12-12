package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.user.User;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.user.UserName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserApi {
    private final ClientApi clientApi;

    public UserApi(final ClientApi clientApi) {
        this.clientApi = clientApi;
    }

    public Mono<List<User>> all() {
        return this.clientApi.user.users();
    }

    public Mono<User> current() {
        return this.clientApi.user.getUser();
    }

    public Mono<User> refresh(final User user) {
        return this.clientApi.user.users()
                .flatMapMany(Flux::fromIterable)
                .filter(u -> u.uid == user.uid)
                .single();
    }

    public Mono<Void> delete(final User user) {
        return this.clientApi.user.deleteUser(user.uid.toString());
    }

    public Mono<User> setUsername(final String name) {
        return this.clientApi.user.setUserName(new UserName(name))
                .then(this.current())
                .flatMap(this::refresh);
    }
}
