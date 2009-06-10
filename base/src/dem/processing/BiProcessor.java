package dem.processing;

import dem.quanta.Event;
import dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public abstract class BiProcessor<LEFT extends Event, RIGHT extends Event> {

    public BiProcessor(final BiConnector<RIGHT, LEFT> left,
                       final BiConnector<LEFT, RIGHT> right) {

        LeftProcessor leftProcessor = new LeftProcessor(left);
        RightProcessor rightProcessor = new RightProcessor(right);

        leftProcessor.setPair(rightProcessor);
        rightProcessor.setPair(leftProcessor);

        left.setHandler(rightProcessor);
        right.setHandler(leftProcessor);

    }

    protected boolean fireLeft(LEFT event) {
        return true;
    }

    protected boolean fireRight(RIGHT event) {
        return true;
    }

    private abstract static class CommonProcessor<X extends Event>
            extends Processor<X> {

        private CommonProcessor<?> pair = null;
        protected boolean alive = true;

        public void setPair(CommonProcessor<?> pair) {
            this.pair = pair;
        }

        private void die() {
            alive = false;
        }

        protected CommonProcessor(Handler<? super X> target) {
            super(target);
        }

        protected void finalize() throws Throwable {
            super.finalize();
            pair.die();
        }
    }

    private class LeftProcessor extends CommonProcessor<LEFT> {
        public LeftProcessor(BiConnector<RIGHT, LEFT> left) {
            super(left);
        }

        public void handle(LEFT event) {
            if (alive && fireLeft(event)) fire(event);
        }
    }

    private class RightProcessor extends CommonProcessor<RIGHT> {
        public RightProcessor(BiConnector<LEFT, RIGHT> right) {
            super(right);
        }

        public void handle(RIGHT event) {
            if (alive && fireRight(event)) fire(event);
        }
    }
}
