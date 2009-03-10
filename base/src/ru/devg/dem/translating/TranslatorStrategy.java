package ru.devg.dem.translating;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
public interface TranslatorStrategy<FROM extends Event,TO extends Event>{
    public abstract TO translate(FROM from);
}
