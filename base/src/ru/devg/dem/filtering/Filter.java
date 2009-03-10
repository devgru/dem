package ru.devg.dem.filtering;

import ru.devg.dem.quanta.*;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.17
 */
public abstract class Filter<E extends Event> implements Handler<E>, FilterStrategy {

    @SuppressWarnings("unchecked")
    public final boolean handleIfPossible(Event event){
        boolean canHandle = canHandle(event);
        if(canHandle){
            handle((E)event);
        }
        return canHandle;
    }
}
