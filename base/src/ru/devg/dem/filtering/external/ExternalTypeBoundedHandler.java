package ru.devg.dem.filtering.external;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.filtering.UpperFilter;
import ru.devg.dem.filtering.TypeFilter;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.180
 */
public final class ExternalTypeBoundedHandler<E extends Event>
        extends UpperFilter<E> implements TypeFilter<E> {

    private final Class<E> bound;

    public ExternalTypeBoundedHandler(Handler<? super E> target, Class<E> bound) {
        super(target);
        this.bound = bound;
    }

    @SuppressWarnings("unchecked")
    public boolean handleIfPossible(Object event) {
        boolean canHandle = bound.isInstance(event);
        if(canHandle){
            fire((E) event);
        }
        return canHandle;
    }

    public boolean handleIfPossible(E event) {
        return handleIfPossible((Object) event);
    }
}
