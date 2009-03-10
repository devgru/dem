package ru.devg.dem.quanta;

/**
 * Provider is a third <i>quantum</i> of the framework.
 * Provider is useful when you are creating or using
 * containers for Handler.
 *
 * You should implement provider prior to implementimg {@link Handler}
 * or extending {@link ru.devg.dem.filtering.TypeBoundedHandler}.
 *
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.15
 * @see Event
 * @see Handler
 */
public interface Provider<H extends Handler> {
    public H getHandler();
}
