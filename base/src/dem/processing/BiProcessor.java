package dem.processing;

import dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public abstract class BiProcessor<LEFT extends Event, RIGHT extends Event> {

    public BiProcessor(final BiConnector<RIGHT, LEFT> left,
                       final BiConnector<LEFT, RIGHT> right) {

        right.setHandler(new Processor<LEFT>(left) {
            public boolean process(LEFT event) {
                return fireLeft(event);
            }
        });

        left.setHandler(new Processor<RIGHT>(right) {
            public boolean process(RIGHT event) {
                return fireRight(event);
            }
        });

    }

    protected boolean fireLeft(LEFT event) {
        return true;
    }

    protected boolean fireRight(RIGHT event) {
        return true;
    }

}
