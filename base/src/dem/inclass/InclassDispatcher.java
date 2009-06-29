package dem.inclass;

import dem.bounding.Filter;
import dem.bundles.Dispatcher;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.quanta.Event;
import dem.quanta.Handler;
import dem.stuff.DelayedInitializationSource;

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

        object = target;

        List<? extends Filter<?>> list =
                new ClassWorker(target).bindClassElements();

        if (strictPrioritization) {
            ensureStrictPrioritization(list);
        }
        setTarget(new Dispatcher<Event>(list));
    }

    public InclassDispatcher(Object target, boolean strictPrioritization,
                             List<? extends AbstractBinder> binders, List<? extends Wrapper> wrappers)
            throws ClassIsUnbindableException {

        object = target;

        List<? extends Filter<?>> list =
                new ClassWorker(target, binders, wrappers).bindClassElements();

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
                        "methods or fields have same priority. It was: " + element + " and " + previousElement);
            }
            previousElement = element;
        }
    }

    public void handle(E event) {
        fire(event);
    }

    @Override
    public String toString() {
        return "In-class dispatcher(target is " + object + "; handlers are contained in " + target + ")";
    }

}