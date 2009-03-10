package ru.devg.dem.quanta;

/**
 * Handler is a second <i>quantum</i> of the framework.
 * It was created to provide the alterntive Observer idiom.
 *
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.15
 * @see Event
 * @see Provider
 */
public interface Handler<E extends Event>{

    /**
     * This method is the only method in {@link Handler this interface}.
     *
     * @param event an event that you handle.
     */
    public void handle(E event);
}
