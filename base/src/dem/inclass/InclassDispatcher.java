package dem.inclass;

import dem.bounding.Filter;
import dem.bundles.Dispatcher;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Log;
import dem.mutables.DelayedInitializationSource;

import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.17
 */
public final class InclassDispatcher<E extends Event>
        extends DelayedInitializationSource<E> implements Handler<E> {

    private final Object object;

    public InclassDispatcher(Object target)
            throws ClassIsUnbindableException {
        this(target, false);
    }

    public InclassDispatcher(Object target, boolean strictPrioritization)
            throws ClassIsUnbindableException {

        List<? extends Filter<?>> list =
                new ClassWorker(target,
                        ClassWorker.DEFAULT_BINDERS,
                        ClassWorker.DEFAULT_WRAPPERS
                ).bindClassElements();

        object = target;
        close(strictPrioritization, list);
    }

    public InclassDispatcher(Object target, boolean strictPrioritization,
                             List<? extends AbstractBinder> binders, List<? extends Wrapper> wrappers)
            throws ClassIsUnbindableException {

        List<? extends Filter<?>> list =
                new ClassWorker(target, binders, wrappers).bindClassElements();

        object = target;
        close(strictPrioritization, list);
    }

    private void close(boolean strictPrioritization, List<? extends Filter<?>> list)
            throws ClassIsUnbindableException {
        if (strictPrioritization) {
            ensureStrictPrioritization(list);
        }

        setTarget(new Dispatcher<Event>(list));
    }

    private static void ensureStrictPrioritization(List<? extends Filter> handlers)
            throws ClassIsUnbindableException {

        Object previousElement = handlers.get(0);
        for (int i = 1; i < handlers.size(); i++) {
            Object element = handlers.get(i);
            if (element.equals(previousElement)) {
                throw new ClassIsUnbindableException("you required strict prioritization, but some " +
                        "methods or fields have same priority. " +
                        "It was: " + element + " and " + previousElement);
            }
            previousElement = element;
        }
    }

    public void handle(E event) {
        fire(event);
    }

    @Override
    public String toString() {
        return "In-class dispatcher\n" +
                Log.offset("object is " + object + "\n" +
                        "handlers are contained in " + target);
    }

}