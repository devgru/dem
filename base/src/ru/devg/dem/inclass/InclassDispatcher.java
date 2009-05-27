package ru.devg.dem.inclass;

import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.inclass.ClassWorker;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.17
 */
public final class InclassDispatcher<E extends Event>
        implements Handler<E> {

    private final List<TypeFilter<?>> handlers;

    private final boolean broadcastMode;

    public InclassDispatcher(Object handler) throws ClassIsUnbindableException {
        this(handler, new InclassDispatcherConfiguration(false, false));
    }

    public InclassDispatcher(Object handler, InclassDispatcherConfiguration config) throws ClassIsUnbindableException {
        handlers = new ClassWorker(handler, config).bindClassElements();
        broadcastMode = config.broadcast;
    }

    public final void handle(E event) {
        for (TypeFilter binder : handlers) {
            if (binder.handleIfPossible(event) && !broadcastMode) return;
        }
    }


}