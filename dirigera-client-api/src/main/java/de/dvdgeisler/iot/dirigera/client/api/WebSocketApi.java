package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.events.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final ClientApi api;
    private final Thread thread;
    private final AtomicBoolean running;
    private final List<Consumer<Event>> listeners;

    public WebSocketApi(final ClientApi api) {
        this.listeners = new ArrayList<>();
        this.api = api;
        this.running = new AtomicBoolean(false);
        this.thread = new Thread(this::run, "websocket");
        this.thread.start();
    }

    private void run() {
        this.running.set(true);
        do {
            try {
                this.loop();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        } while (this.running.get());
    }

    private void loop() {
        if (!this.api.oauth.isPaired())
            return;
        this.api.websocket(this::onEvent).block();
    }

    private synchronized void onEvent(final Event event) {
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

    public void stop() throws InterruptedException {
        this.running.set(false);
        this.thread.interrupt();
        while (this.thread.isAlive()) {
            Thread.sleep(100);
            this.thread.interrupt();
        }
    }
}
