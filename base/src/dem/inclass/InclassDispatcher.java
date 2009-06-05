package dem.inclass;

import dem.bounding.Filter;
import dem.bundles.Dispatcher;
import dem.inclass.binding.ClassWorker;
import dem.processing.MutableBypasser;
import dem.quanta.Event;

import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.17
 */
public final class InclassDispatcher<E extends Event>
        extends MutableBypasser<E> {

    public InclassDispatcher(Object handler) throws ClassIsUnbindableException {
        this(handler, false);
    }

    public InclassDispatcher(Object handler, boolean strictPrioritization) throws ClassIsUnbindableException {
        List<? extends Filter<?>> list =
                new ClassWorker(handler).bindClassElements();

        if (strictPrioritization) {
            ensureStrictPrioritization(list);
        }
        setHandler(new Dispatcher<Event>(list));
    }

    private void ensureStrictPrioritization(List<? extends Filter> handlers) throws ClassIsUnbindableException {
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

}