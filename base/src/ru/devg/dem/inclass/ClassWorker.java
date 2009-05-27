package ru.devg.dem.inclass;

import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
public final class ClassWorker {
    private final Object target;
    private final boolean strictPrioritization;
    private final List<AbstractBinder> binders =
            new LinkedList<AbstractBinder>();


    public ClassWorker(Object target, ClassWorkerConfiguration config) {
        this.target = target;
        strictPrioritization = config.strictPrioritization;
        this.binders.add(new FieldWorker(target));
        this.binders.add(new MethodWorker(target));
    }


    public ClassWorker(Object target, ClassWorkerConfiguration config, List<? extends AbstractBinder> binders) {
        this.target = target;
        strictPrioritization = config.strictPrioritization;
        this.binders.addAll(binders);
    }

    public List<TypeFilter<?>> bindClassElements() throws ClassIsUnbindableException {
        List<BoundElement> elements = new LinkedList<BoundElement>();

        Class targetClass = target.getClass();
        while (targetClass != Object.class) {
            for (AbstractBinder binder : binders) {
                binder.tryBindMembers(elements, targetClass);
                for(Class oneOfInterfaces : targetClass.getInterfaces()){
                    binder.tryBindMembers(elements, oneOfInterfaces);
                }
            }
            targetClass = targetClass.getSuperclass();
        }

        Collections.sort(elements);
        if (strictPrioritization) {
            BoundElement previousElement = elements.get(0);
            for (int i = 1; i < elements.size(); i++) {
                BoundElement element = elements.get(i);
                if (previousElement.compareTo(element) == 0 && strictPrioritization) {
                    throw new ClassIsUnbindableException("you required strict prioritization, but some " +
                            "methods or fields have same priority. It was: " + element + " and " + previousElement);
                }
            }
        }
        return grabResult(elements);
    }

    private List<TypeFilter<?>> grabResult(List<BoundElement> entries) {
        List<TypeFilter<?>> grabbed = new LinkedList<TypeFilter<?>>();
        for (BoundElement entry : entries) {
            grabbed.add(entry.getFilter());
        }
        return grabbed;
    }

}
