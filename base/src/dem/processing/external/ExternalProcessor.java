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

    private final ProcessorStrategy<E> strategy;

    public ExternalProcessor(Handler<? super E> target, ProcessorStrategy<E> strategy) {
        super(target);
        this.strategy = strategy;
    }

    public void handle(E event) {
        if (strategy.process(event)) fire(event);
    }

    @Override
    public String toString() {
        return "External processor (target is " + target + "; strategy is " + strategy + ")";
    }

}
