package ru.devg.dem.inclass;

import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.inclass.binding.ClassWorker;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.17
 */
public final class InclassDispatcher<E extends Event>
        implements Handler<E> {

    private final List<? extends TypeFilter> handlers;

    public InclassDispatcher(Object handler) throws ClassIsUnbindableException {
        this(handler, new ClassWorkerConfiguration(false));
    }

    public InclassDispatcher(Object handler, ClassWorkerConfiguration  config) throws ClassIsUnbindableException {
        handlers = new ClassWorker(handler, config).bindClassElements();
    }

    public final void handle(E event) {
        for (TypeFilter binder : handlers) {
            if (binder.handleIfPossible(event)) return;
        }
    }


}