package ru.devg.dem.inclass.binding;

import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.inclass.Configuration;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;

import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

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

        List<BoundElement> grabbed = new LinkedList<BoundElement>();

        Class clazz = target.getClass();
        while (clazz != Object.class) {
            for (AbstractBinder binder : binders) {
                binder.tryBindMembers(grabbed, clazz);
            }
            clazz = clazz.getSuperclass();
        }

        Collections.sort(grabbed);
        if (strictPrioritization) {
            BoundElement previousElement = grabbed.get(0);
            for (int i = 1; i < grabbed.size(); i++) {
                BoundElement element = grabbed.get(i);
                if (previousElement.compareTo(element) == 0 && strictPrioritization) {
                    throw new ClassIsUnbindableException("you required strict prioritization, but some " +
                            "methods or fields have same priority. It was: " + element + " and " + previousElement);
                }
            }
        }
        return grabResult(grabbed);
    }

    private List<TypeFilter<?>> grabResult(List<BoundElement> entries) {
        List<TypeFilter<?>> grabbed = new LinkedList<TypeFilter<?>>();
        for (BoundElement entry : entries) {
            grabbed.add(entry.getFilter());
        }
        return grabbed;
    }

}
