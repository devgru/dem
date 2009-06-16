package dem.processing;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public abstract class BiProcessor<L extends Event, R extends Event> {

    public BiProcessor(final BiConnector<R, L> left,
                       final BiConnector<L, R> right) {

        LeftProcessor leftProcessor = new LeftProcessor(left);
        RightProcessor rightProcessor = new RightProcessor(right);

        CommonProcessor.bind(leftProcessor, rightProcessor);

        left.setTarget(rightProcessor);
        right.setTarget(leftProcessor);

    }

    protected boolean fireLeft(L event) {
        return true;
    }

    protected boolean fireRight(R event) {
        return true;
    }

    private abstract static class CommonProcessor<E extends Event>
            extends Source<E> implements Handler<E> {

        private CommonProcessor<?> pair = null;
        protected boolean alive = true;

        public static void bind(CommonProcessor<?> pair1, CommonProcessor<?> pair2) {
            pair1.pair = pair2;
            pair2.pair = pair1;
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
