package dem.bundles;

import dem.bounding.BoundedHandler;
import dem.quanta.Event;
import dem.quanta.Handler;

import java.util.HashMap;
import java.util.Map;

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

    public void addHandler(BoundedHandler<? extends E> handler) {
    }

    public void removeHandler(BoundedHandler<? extends E> handler) {
    }

    private static class ClassTree<E extends Event> extends BoundedHandler<E> {

        private Handler<E> target = null;

        protected final Map<Class<? extends E>, ClassTree<? extends E>> subclasses
                = new HashMap<Class<? extends E>, ClassTree<? extends E>>();

        public ClassTree(Class<E> bound) {
            super(bound);
        }

        public <X extends E> void add(Handler<X> target, Class<X> bound) {
            for (Map.Entry<Class<? extends E>, ClassTree<? extends E>> ctx : subclasses.entrySet()) {
                ClassTree<? extends E> ct = ctx.getValue();
                if (ct.getBoundClass().isAssignableFrom(bound)) {
                    if (bound.equals(ct.getBoundClass())) {
                        throw new RuntimeException();//todo specify
                    } else {
                        subclasses.put(bound, new ClassTree<X>(target, bound));
                        break;
                    }
                } else if (bound.isAssignableFrom(ct.getBoundClass())) {
                    subclasses.remove(ct.getBoundClass());
                    ClassTree<X> value = new ClassTree<X>(target, bound);
                    addSubtree((ClassTree<X>) ct, value);
                    subclasses.put(bound, value);
                }
            }
        }

        private <M extends E> void addSubtree(ClassTree<M> ct, ClassTree<M> value) {
            value.add(ct.target, ct.getBoundClass());
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
