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
        classTree = new ClassTree<E>(null);
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

        private BoundedHandler<E> target = null;

        protected final Map<Class<? extends E>, ClassTree<? extends E>> subclasses
                = new HashMap<Class<? extends E>, ClassTree<? extends E>>();

        public <B extends E> void add(BoundedHandler<B> handler) {
            Class<B> beta = handler.getBoundClass();
            for (ClassTree<? extends E> subtree  : subclasses.values()) {
                Class<? extends E> alpha = subtree.getBoundClass();

                if (alpha.equals(beta)) {
                    //same classes - shit
                    throw new RuntimeException();//todo specify
                } else if (alpha.isAssignableFrom(beta)) {
                    //alpha is a parent - simply add alpha to beta subclasses
                    ClassTree<? super B> subtree2 = (ClassTree<? super B>) subtree;
                    subtree2.subclasses.put(beta, new ClassTree<B>(handler));
                    return;

                } else if (beta.isAssignableFrom(alpha)) {
                    ClassTree<? extends B> subtree2 = (ClassTree<? extends B>) subtree;

                    ClassTree<B> value = new ClassTree<B>(handler);
                    value.add(subtree2.target);
                    subclasses.remove(alpha);
                    subclasses.put(beta, value);
                    return;
                }
            }
            subclasses.put(beta,new ClassTree<B>(handler));
        }

        public ClassTree(BoundedHandler<E> target) {
            super(target.getBoundClass());
            this.target = target;
        }

        public void handle(E event) {
            target.handle(event);//todo rewrite
        }

        @Override
        public boolean handleIfPossible(Event event) {
            return super.handleIfPossible(event);//todo rewrite!
        }
    }
}
