package dem.bundles;

import dem.bounding.BoundedHandler;
import dem.quanta.Event;

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
        classTree.add(handler);
    }

    public void removeHandler(BoundedHandler<? extends E> handler) {
        classTree.remove(handler);
    }

    private static class ClassTree<E extends Event> extends BoundedHandler<E> {

        private BoundedHandler<E> target = null;

        protected final Map<Class<? extends E>, ClassTree<? extends E>> subclasses
                = new HashMap<Class<? extends E>, ClassTree<? extends E>>();

        public ClassTree(Class<E> bound) {
            super(bound);
        }

        public ClassTree(BoundedHandler<E> target) {
            super(target.getBoundClass());
            this.target = target;
        }

        @SuppressWarnings("unchecked")
        public <B extends E> void add(BoundedHandler<B> handler) {
            Class<B> beta = handler.getBoundClass();
            if (beta == getBoundClass()) {
                BoundedHandler<E> bh = (BoundedHandler<E>) handler;
                if (target == null) {
                    target = bh;
                } else {
                    throw new RuntimeException();//todo specify
                }
            } else if (getBoundClass().isAssignableFrom(beta)) {
                for (ClassTree<? extends E> subtree : subclasses.values()) {
                    Class<? extends E> alpha = subtree.getBoundClass();

                    if (alpha.isAssignableFrom(beta)) {
                        //alpha is a parent - simply add alpha to beta subclasses
                        ClassTree<? super B> subtree2 = (ClassTree<? super B>) subtree;
                        subtree2.add(handler);
                        return;
                    } else if (beta.isAssignableFrom(alpha)) {
                        //beta is a parent - remove alpha from list, add beta, add alpha to beta as a child
                        ClassTree<? extends B> subtree2 = (ClassTree<? extends B>) subtree;
                        ClassTree<B> value = new ClassTree<B>(handler);
                        value.add(subtree2.target);
                        subclasses.remove(alpha);
                        subclasses.put(beta, value);
                        return;
                    }
                }
                subclasses.put(beta, new ClassTree<B>(handler));
            } else {
                throw new RuntimeException();//todo specify
            }

        }

        public <B extends E> void remove(BoundedHandler<B> handler) {
            Class<B> beta = handler.getBoundClass();
            if (beta == getBoundClass()) {
                target = null;
            } else if (getBoundClass().isAssignableFrom(beta)) {
                for (ClassTree<? extends E> subtree : subclasses.values()) {
                    Class<? extends E> alpha = subtree.getBoundClass();

                    if (alpha == beta) {
                        //same classes
                        subtree.target = null;
                        return;
                    } else if (alpha.isAssignableFrom(beta)) {
                        //alpha is a parent - simply add alpha to beta subclasses
                        ClassTree<? super B> subtree2 = (ClassTree<? super B>) subtree;
                        subtree2.remove(handler);
                        return;
                    }
                }
            } else {
                throw new RuntimeException();//todo specify
            }
        }

        public void handle(E event) {
            handleIfPossible(event);
        }

        @Override
        public boolean handleIfPossible(Event event) {
            if (!getBoundClass().isInstance(event)) {
                return false;
            }
            if (event.getClass() == getBoundClass()) {
                return target != null && target.handleIfPossible(event);
            }

            for (ClassTree<? extends E> classTree : subclasses.values()) {
                boolean result = classTree.handleIfPossible(event);
                if (result) return true;
            }

            return target != null && target.handleIfPossible(event);

        }
    }
}
