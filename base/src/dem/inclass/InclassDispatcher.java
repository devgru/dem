package dem.inclass;

import dem.bounding.Filter;
import dem.bundles.Dispatcher;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.mutables.DelayedInitializationSource;
import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Log;

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

        this(target, strictPrioritization,
                ClassWorker.DEFAULT_BINDERS);

    }

    public InclassDispatcher(Object target, boolean strictPrioritization,
                             List<? extends AbstractBinder> binders)
            throws ClassIsUnbindableException {

        List<? extends Filter<?>> list =
                new ClassWorker(target, binders).bindClassElements();

        object = target;
        if (strictPrioritization) {
            ensureStrictPrioritization(list);
        }
        setTarget(new Dispatcher<Event>(list));
    }

    private static void ensureStrictPrioritization(List<? extends Filter> handlers)
            throws ClassIsUnbindableException {

        Filter previousElement = null;
        for (Filter element : handlers) {
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