package ru.devg.dem.structures.dispatchers.inclass.binding;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.structures.dispatchers.inclass.Configuration;

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

    public List<Filter<?>> result() {
        List<AbstractBinder> binders = new LinkedList<AbstractBinder>();

        binders.add(new FieldWorker(target));
        binders.add(new MethodWorker(target));

        List<BindedMember> grabbed = new LinkedList<BindedMember>();

        Class clazz = target.getClass();
        while (clazz != Object.class) {
            for (AbstractBinder binder : binders) {
                binder.tryBindMembers(grabbed, clazz);
            }
            clazz = clazz.getSuperclass();
        }

        Collections.sort(grabbed,new MembersComparator());
        return grabResult(grabbed);
    }

    private List<Filter<?>> grabResult(List<BindedMember> entries) {
        List<Filter<?>> grabbed = new LinkedList<Filter<?>>();
        for (BindedMember entry : entries) {
            grabbed.add(entry.getFilter());
        }
        return grabbed;
    }

    private final class MembersComparator implements Comparator<BindedMember>{
        public int compare(BindedMember o1, BindedMember o2) {
            int result = o1.compareTo(o2);
            if(result ==0 && strictPrioritization){
                throw new RuntimeException("you required strict prioritization, but you have prodivded some" +
                        "methods/fields with same priority. It was:" + o1 + " and " + o2);
            }
            return result;
        }
    }
}
