package ru.devg.dem.processing.external;

import ru.devg.dem.processing.BiConnector;
import ru.devg.dem.processing.BiProcessor;
import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.182
 */
public final class ExternalBiProcessor<LEFT extends Event, RIGHT extends Event>
        extends BiProcessor<LEFT, RIGHT> {

    private final BiProcessorStrategy<LEFT, RIGHT> strategy;

    public ExternalBiProcessor(final BiConnector<RIGHT, LEFT> left,
                               final BiConnector<LEFT, RIGHT> right,
                               BiProcessorStrategy<LEFT, RIGHT> strategy) {
        super(left, right);

        this.strategy = strategy;
    }

    protected boolean fireLeft(LEFT event) {
        return strategy.processLeft(event);
    }

    protected boolean fireRight(RIGHT event) {
        return strategy.processRight(event);
    }
}
