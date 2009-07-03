package dem.quanta;

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
        assert event != null;
        target.handle(event);
    }

    @Override
    public String toString() {
        return "Source\n" + Log.offset("target is " + target);
    }

}
