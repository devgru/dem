package ru.devg.dem.processing;

import ru.devg.dem.processing.external.ProcessorStrategy;
import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.182
 */
public abstract class BiProcessor<LEFT extends Event, RIGHT extends Event> {

    public BiProcessor(final BiConnector<RIGHT, LEFT> left,
                       final BiConnector<LEFT, RIGHT> right) {

        BiConnector.bind(right, new BiConnector<RIGHT, LEFT>() {
            public void handle(LEFT event) {
                if (fireLeft(event)) left.handle(event);
            }
        });

        BiConnector.bind(left, new BiConnector<LEFT, RIGHT>() {
            public void handle(RIGHT event) {
                if (fireRight(event)) right.handle(event);
            }
        });

    }

    protected abstract boolean fireLeft(LEFT event);

    protected abstract boolean fireRight(RIGHT event);

}
