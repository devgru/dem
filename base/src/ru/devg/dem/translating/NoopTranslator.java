package ru.devg.dem.translating;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
public final class NoopTranslator<E extends Event> implements TranslatorStrategy<E,E>{
    public E translate(E event) {
        return event;
    }
}
