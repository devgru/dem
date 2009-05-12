package ru.devg.dem.quanta;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
 */
public abstract class Source<E extends Event> {
    protected final Handler<? super E> target;

    public Source(Handler<? super E> target) {
        assert target != null;
        this.target = target;
    }

    protected final void fire(E event) {
        target.handle(event);
    }
}
