package dem.bundles;

import dem.bounding.BoundedHandler;
import dem.quanta.Event;
import dem.quanta.Log;

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

    public boolean addHandler(BoundedHandler<? extends E> handler) {
        return classTree.add(handler);
    }

    public boolean removeHandler(BoundedHandler<? extends E> handler) {
        return classTree.remove(handler);
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
        public <B extends E> boolean add(BoundedHandler<B> handler) {
            Class<B> newOne = handler.getBoundClass();
            if (newOne == getBoundClass()) {//same class as root
                BoundedHandler<E> bh = (BoundedHandler<E>) handler;
                boolean targetWasNull = target == null;
                if (targetWasNull) {
                    target = bh;
                }
                return targetWasNull;
            } else if (getBoundClass().isAssignableFrom(newOne)) {//child of root class
                for (ClassTree<? extends E> subtree : subclasses.values()) {
                    Class<? extends E> subtreeClass = subtree.getBoundClass();

                    if (subtreeClass.isAssignableFrom(newOne)) {
                        //subtreeClass is a parent - simply add newOne to subtree
                        ClassTree<? super B> subtree2 = (ClassTree<? super B>) subtree;
                        return subtree2.add(handler);
                    } else if (newOne.isAssignableFrom(subtreeClass)) {
                        //newOne is a parent - remove subtree from this tree, add newOne, add subtreeClass to newOne as a child
                        ClassTree<? extends B> subtree2 = (ClassTree<? extends B>) subtree;
                        ClassTree<B> value = new ClassTree<B>(handler);
                        value.add(subtree2.target);
                        subclasses.remove(subtreeClass);
                        subclasses.put(newOne, value);
                        return true;
                    }
                }
                subclasses.put(newOne, new ClassTree<B>(handler));
                return true;
            } else {
                return false;
            }

        }

        @SuppressWarnings("unchecked")
        public <B extends E> boolean remove(BoundedHandler<B> handler) {
            Class<B> newOne = handler.getBoundClass();
            if (newOne == getBoundClass()) {
                target = null;
                return true;
            } else if (getBoundClass().isAssignableFrom(newOne)) {
                for (ClassTree<? extends E> subtree : subclasses.values()) {
                    Class<? extends E> subtreeClass = subtree.getBoundClass();

                    if (subtreeClass.isAssignableFrom(newOne)) {
                        //subtreeClass is a parent - simply add subtreeClass to newOne subclasses
                        ClassTree<? super B> subtree2 = (ClassTree<? super B>) subtree;
                        return subtree2.remove(handler);
                    }
                }
            }
            return false;
        }

        @Deprecated
        public void handle(E event) {
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

        @Override
        public String toString() {
            return "Class tree\n" + Log.offset("target is " + target + "\n"
                    + "sublclasses: \n" + Log.offset(Log.inline(subclasses.values())));
        }
    }

    @Override
    public String toString() {
        return "Class dispatcher\n" + Log.offset("classtree is " + classTree);
    }
}
