package ru.devg.dem.bundles.one2one.onclass;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.filtering.TypeBoundedHandler;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.List;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.17
 */
public final class InplaceDispatcher<E extends Event>
        extends TypeBoundedHandler<E> {

    private final List<Filter<?>> handlers;

    private Handler<E> handlerForOrphans = null;

    public final void handle(E event) {
        for (Filter<?> binder : handlers) {
            if (binder.handleIfPossible(event)) return;
        }
        if (handlerForOrphans != null) {
            handlerForOrphans.handle(event);
        }
    }

    public InplaceDispatcher(InplaceDispatchable<E> handler) throws NoSuchMethodError,NoSuchFieldError {
        super(handler.getBoundClass());
        handlers = new ClassWorker(handler).result();
    }


}