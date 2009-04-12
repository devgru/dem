package ru.devg.dem.inclass;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.inclass.binding.ClassWorker;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.EnumSet;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.17
 */
public final class InclassDispatcher<E extends Event>
        implements Handler<E> {

    private final List<Filter<?>> handlers;

    private final boolean broadcastMode;

    public InclassDispatcher(Object handler) throws ClassIsUnbindableException {
        this(handler, EnumSet.noneOf(Configuration.class));
    }

    public InclassDispatcher(Object handler, EnumSet<Configuration> config) throws ClassIsUnbindableException {
        handlers = new ClassWorker(handler, config).bindClassElements();
        broadcastMode = config.contains(Configuration.broadcast);
    }

    public final void handle(E event) {
        for (Filter<?> binder : handlers) {
            if (binder.handleIfPossible(event) && !broadcastMode) return;
        }
    }


}