package dem.processing;

import dem.quanta.Event;
import dem.processing.external.ExternalProcessor;
import dem.processing.external.ProcessorStrategy;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public abstract class BiProcessor<LEFT extends Event, RIGHT extends Event> {

    public BiProcessor(final BiConnector<RIGHT, LEFT> left,
                       final BiConnector<LEFT, RIGHT> right) {

        right.setHandler(new ExternalProcessor<LEFT>(left,new ProcessorStrategy<LEFT>() {
            public boolean process(LEFT event) {
                return fireLeft(event);
            }
        }));

        left.setHandler(new ExternalProcessor<RIGHT>(right,new ProcessorStrategy<RIGHT>() {
            public boolean process(RIGHT event) {
                return fireRight(event);
            }
        }));

    }

    protected boolean fireLeft(LEFT event) {
        return true;
    }

    protected boolean fireRight(RIGHT event) {
        return true;
    }

}
