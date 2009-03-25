package ru.devg.dem.inclass.binding;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.inclass.Configuration;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.quanta.Event;

import java.util.*;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.176
 */
public final class ClassWorker {
    private final Object target;
    private final boolean strictPrioritization;
    private final Class<? extends Event> bound; //todo involve in checks

    public ClassWorker(Object target, EnumSet<Configuration> config, Class<? extends Event> bound) {
        this.target = target;
        this.bound = bound;
        strictPrioritization = config.contains(Configuration.strictPrioritization);
    }

    public List<Filter<?>> bindClassElements() throws ClassIsUnbindableException {
        List<AbstractBinder> binders = new LinkedList<AbstractBinder>();

        binders.add(new FieldWorker(target));
        binders.add(new MethodWorker(target));

        List<BindedElement> grabbed = new LinkedList<BindedElement>();

        Class clazz = target.getClass();
        while (clazz != Object.class) {
            for (AbstractBinder binder : binders) {
                binder.tryBindMembers(grabbed, clazz);
            }
            clazz = clazz.getSuperclass();
        }

        Collections.sort(grabbed, new MembersComparator());
        return grabResult(grabbed);
    }

    private List<Filter<?>> grabResult(List<BindedElement> entries) {
        List<Filter<?>> grabbed = new LinkedList<Filter<?>>();
        for (BindedElement entry : entries) {
            grabbed.add(entry.getFilter());
        }
        return grabbed;
    }

    private final class MembersComparator implements Comparator<BindedElement> {
        public int compare(BindedElement o1, BindedElement o2) {
            int result = o1.compareTo(o2);
            if (result == 0 && strictPrioritization) {
                throw new RuntimeException("you required strict prioritization, but you have prodivded some" +
                        "methods/fields with same priority. It was:" + o1 + " and " + o2);
            }
            return result;
        }
    }
}