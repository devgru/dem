package dem.inclass.binding;

import dem.bounding.Filter;
import dem.inclass.ClassIsUnbindableException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
public final class ClassWorker {
    private final Object target;
    private final List<AbstractBinder> binders =
            new LinkedList<AbstractBinder>();


    public ClassWorker(Object target) {
        this.target = target;
        this.binders.add(new FieldWorker(target));
        this.binders.add(new MethodWorker(target));
    }


    public ClassWorker(Object target, List<? extends AbstractBinder> binders) {
        this.target = target;
        this.binders.addAll(binders);
    }

    public List<? extends Filter<?>> bindClassElements() throws ClassIsUnbindableException {
        List<FilterWithPriority> elements = new LinkedList<FilterWithPriority>();

        Class targetClass = target.getClass();
        while (targetClass != Object.class) {
            for (AbstractBinder binder : binders) {
                binder.tryBindMembers(elements, targetClass);
                /*for (Class oneOfInterfaces : targetClass.getInterfaces()) {
                    todo what about interfaces? still nothing
                    binder.tryBindMembers(elements, oneOfInterfaces);
                }*/
            }
            targetClass = targetClass.getSuperclass();
        }

        Collections.sort(elements);
        return elements;
    }

}
