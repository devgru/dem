package dem.processing.external;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public final class ExternalProcessor<E extends Event>
        extends Source<E> implements Handler<E> {

    private final ProcessorStrategy<E> ps;

    public ExternalProcessor(Handler<? super E> target, ProcessorStrategy<E> ps) {
        super(target);
        this.ps = ps;
    }

    public void handle(E event) {
        if (ps.process(event)) fire(event);
    }

}
