package ru.devg.dem.inclass.binding;

import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.inclass.Configuration;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;

import java.util.*;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
public final class ClassWorker {
    private final Object target;
    private final boolean strictPrioritization;

    public ClassWorker(Object target, EnumSet<Configuration> config) {
        this.target = target;
        strictPrioritization = config.contains(Configuration.strictPrioritization);
    }

    public List<TypeFilter<?>> bindClassElements() throws ClassIsUnbindableException {
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

    private List<TypeFilter<?>> grabResult(List<BindedElement> entries) {
        List<TypeFilter<?>> grabbed = new LinkedList<TypeFilter<?>>();
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
