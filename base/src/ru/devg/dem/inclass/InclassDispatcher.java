package ru.devg.dem.inclass;

import ru.devg.dem.bounding.Filter;
import ru.devg.dem.inclass.binding.ClassWorker;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.bundles.Dispatcher;

import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.17
 */
public final class InclassDispatcher<E extends Event>
        implements Handler<E> {

    private final Dispatcher<Event> handlers;

    public InclassDispatcher(Object handler) throws ClassIsUnbindableException {
        this(handler, false);
    }

    public InclassDispatcher(Object handler, boolean strictPrioritization) throws ClassIsUnbindableException {
        List<? extends Filter<?>> list = new ClassWorker(handler).bindClassElements();
        if (strictPrioritization) {
            ensureStrictPrioritization(list);
        }
        handlers = new Dispatcher<Event>(list);
    }

    private void ensureStrictPrioritization(List<? extends Filter> handlers) throws ClassIsUnbindableException {
        Object previousElement = handlers.get(0);
        for (int i = 1; i < handlers.size(); i++) {
            Object element = handlers.get(i);
            if (element.equals(previousElement)) {
                throw new ClassIsUnbindableException("you required strict prioritization, but some " +
                        "methods or fields have same priority. It was: " + element + " and " + previousElement);
            }
            previousElement = element;
        }
    }

    public final void handle(E event) {
        handlers.handle(event);
    }

}