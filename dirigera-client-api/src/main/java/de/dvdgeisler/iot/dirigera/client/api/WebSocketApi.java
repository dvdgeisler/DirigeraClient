package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.RestApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class WebSocketApi {
    private final static Logger log = LoggerFactory.getLogger(WebSocketApi.class);

    private static class FilteredEventListener<E extends Event> implements Consumer<Event> {
        private final Class<E> filter;
        private final Consumer<E> listener;

        private FilteredEventListener(final Consumer<E> listener, final Class<E> filter) {
            this.filter = filter;
            this.listener = listener;
        }

        @Override
        public void accept(final Event event) {
            if (this.filter.isInstance(event))
                this.listener.accept((E) event);
        }
    }

    private final RestApi api;
    private final AtomicBoolean running;
    private final List<Consumer<Event>> listeners;

    public WebSocketApi(final RestApi api) {
        this.listeners = new ArrayList<>();
        this.api = api;
        this.running = new AtomicBoolean(false);

        Schedulers.boundedElastic().schedule(this::run);
    }

    private void run() {
        final Thread thread;
        final AtomicBoolean restart;

        thread = Thread.currentThread();
        restart = new AtomicBoolean(false);
        do {
            log.info("Start event handler thread: id={}, name={}", thread.getId(), thread.getName());
            restart.set(false);
            this.running.set(true);
            this.api.websocket(this::onEvent, this::isRunning)
                    .onErrorResume(error -> {
                        log.error("Error while listening to websocket events: {}", error.getMessage());
                        restart.set(true);
                        return Mono.delay(Duration.ofSeconds(1)).then();
                    })
                    .block();
            this.running.set(false);
            log.info("Finish event handler thread: id={}, name={}", thread.getId(), thread.getName());
        } while(restart.get());
    }

    private synchronized void onEvent(final Event event) {
        log.debug("Received Dirigera event: type={}, id={}, source={}, time={}", event.type, event.id, event.source, event.time);
        this.listeners.forEach(c -> c.accept(event));
    }

    public synchronized void addListener(final Consumer<Event> listener) {
        this.listeners.add(listener);
    }

    public synchronized <E extends Event> void addListener(final Consumer<E> listener, final Class<E> filter) {
        this.listeners.add(new FilteredEventListener<>(listener, filter));
    }

    public synchronized void removeListener(final Consumer<Event> listener) {
        this.listeners.remove(listener);
    }

    public synchronized <E extends Event> void removeListener(final Consumer<E> listener, final Class<E> filter) {
        this.listeners.removeIf(l -> l instanceof FilteredEventListener<?> && ((FilteredEventListener<?>) l).listener.equals(listener));
    }

    public boolean isRunning() {
        return this.running.get();
    }

    public void stop() {
        this.running.set(false);
    }
}
