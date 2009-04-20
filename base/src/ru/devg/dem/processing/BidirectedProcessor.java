package ru.devg.dem.processing;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.182
 */
public abstract class BidirectedProcessor<LEFT extends Event, RIGHT extends Event> {

    private final TheThing<LEFT, RIGHT> right;
    private final TheThing<RIGHT, LEFT> left;

    public BidirectedProcessor(TheThing<RIGHT, LEFT> left, TheThing<LEFT, RIGHT> right) {
        this.right = right;
        this.left = left;
    }

    protected final void fireLeft(LEFT event) {
        left.handle(event);
    }

    protected final void fireRight(RIGHT event) {
        right.handle(event);
    }


}
