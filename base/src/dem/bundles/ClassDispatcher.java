package dem.bundles;

import dem.bounding.BoundedHandler;
import dem.quanta.Event;
import dem.quanta.Handler;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
public final class ClassDispatcher<E extends Event>
        extends BoundedHandler<E>
        implements HandlingBundle<E, BoundedHandler<? extends E>> {

    private final ClassTree<E> classTree;

    public ClassDispatcher(Class<E> bound) {
        super(bound);
        classTree = new ClassTree<E>(bound);
    }

    @Override
    public boolean handleIfPossible(Event event) {
        return getBoundClass().isInstance(event) &&
                classTree.handleIfPossible(event);
    }

    public void handle(E event) {
        handleIfPossible(event);
    }

    public void addHandler(BoundedHandler<? extends E> handler){
    }

    public void removeHandler(BoundedHandler<? extends E> handler) {
    }

    private static class ClassTree<E extends Event> extends BoundedHandler<E> {

        private Handler<E> target = null;

        protected final List<ClassTree> subclasses
                = new LinkedList<ClassTree>();

        public ClassTree(Class<E> bound) {
            super(bound);
        }

        public ClassTree(Handler<E> target, Class<E> bound) {
            super(bound);
            this.target = target;
        }

        public void handle(E event) {
            target.handle(event);
        }

        @Override
        public boolean handleIfPossible(Event event) {
            return super.handleIfPossible(event);
        }
    }
}
