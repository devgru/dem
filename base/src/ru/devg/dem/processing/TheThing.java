package ru.devg.dem.processing;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.0
 */
public abstract class TheThing<LEFT extends Event, RIGHT extends Event>
        extends Source<LEFT> implements Handler<RIGHT> {

    public TheThing(Handler<? super LEFT> target) {
        super(target);
    }

}
