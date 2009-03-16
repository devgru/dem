package ru.devg.dem.structures.dispatchers.inclass;

import ru.devg.dem.structures.dispatchers.inclass.binding.ClassWorker;
import ru.devg.dem.filtering.Filter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.List;
import java.util.EnumSet;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.17
 */
public final class InclassDispatcher<E extends Event>
        implements Handler<E> {

    private final List<Filter<?>> handlers;

    private final boolean broadcastMode;

    public InclassDispatcher(Object handler) throws NoSuchMethodError, NoSuchFieldError {
        this(handler,EnumSet.noneOf(Configuration.class));
    }

    public InclassDispatcher(Object handler, EnumSet<Configuration> config) throws NoSuchMethodError, NoSuchFieldError {
        this(handler, config, Event.class);
    }

    public InclassDispatcher(Object handler, Class<? extends Event> bound) throws NoSuchMethodError, NoSuchFieldError {
        this(handler, EnumSet.noneOf(Configuration.class),bound);
    }

    public InclassDispatcher(Object handler, EnumSet<Configuration> config,Class<? extends Event> bound) throws NoSuchMethodError, NoSuchFieldError {
        handlers = new ClassWorker(handler, config,bound).result();
        broadcastMode = config.contains(Configuration.broadcast);
    }

    public final void handle(E event) {
        for (Filter<?> binder : handlers) {
            if (binder.handleIfPossible(event) && !broadcastMode) return;
        }
    }


}