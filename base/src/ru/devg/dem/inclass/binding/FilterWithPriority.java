package ru.devg.dem.inclass.binding;

import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
 */
final class FilterWithPriority
        implements Comparable<FilterWithPriority>, TypeFilter {

    private final TypeFilter filter;
    private final Long priority;

    public FilterWithPriority(TypeFilter filter, long priority) {
        this.filter = filter;
        this.priority = priority;
    }

    public boolean handleIfPossible(Event event) {
        return filter.handleIfPossible(event);
    }

    @SuppressWarnings("unchecked")
    public void handle(Event event) {
        filter.handle(event);
    }

    public int compareTo(FilterWithPriority o) {
        return o.priority.compareTo(priority);
    }

    public boolean equals(Object obj) {
        if (obj instanceof FilterWithPriority) {
            FilterWithPriority be = (FilterWithPriority) obj;
            return filter.equals(be.filter) && priority.equals(be.priority);
        }
        return false;
    }

    public int hashCode() {
        int result;
        result = filter.hashCode();
        result = 31 * result + priority.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "[" + filter.toString() + "; priority = " + priority + "]";
    }
}
