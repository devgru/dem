package dem.processing;

import dem.quanta.Event;
import dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public abstract class BiProcessor<L extends Event, R extends Event> {

    public BiProcessor(final BiConnector<R, L> left,
                       final BiConnector<L, R> right) {

        LeftProcessor leftProcessor = new LeftProcessor(left);
        RightProcessor rightProcessor = new RightProcessor(right);

        leftProcessor.setPair(rightProcessor);
        rightProcessor.setPair(leftProcessor);

        left.setHandler(rightProcessor);
        right.setHandler(leftProcessor);

    }

    protected boolean fireLeft(L event) {
        return true;
    }

    protected boolean fireRight(R event) {
        return true;
    }

    private abstract static class CommonProcessor<E extends Event>
            extends Processor<E> {

        private CommonProcessor<?> pair = null;
        protected boolean alive = true;

        public void setPair(CommonProcessor<?> pair) {
            this.pair = pair;
        }

        private void die() {
            alive = false;
            pair = null;
        }

        protected CommonProcessor(Handler<? super E> target) {
            super(target);
        }

        protected void finalize() throws Throwable {
            super.finalize();
            if (pair != null) {
                pair.die();
                pair = null;
            }
        }
    }

    private class LeftProcessor extends CommonProcessor<L> {
        public LeftProcessor(BiConnector<R, L> left) {
            super(left);
        }

        public void handle(L event) {
            if (alive && fireLeft(event)) fire(event);
        }
    }

    private class RightProcessor extends CommonProcessor<R> {
        public RightProcessor(BiConnector<L, R> right) {
            super(right);
        }

        public void handle(R event) {
            if (alive && fireRight(event)) fire(event);
        }
    }
}
