package ru.devg.dem.inclass.binding;

import ru.devg.dem.bounding.Filter;
import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
 */
final class FilterWithPriority
        implements Comparable<FilterWithPriority>, Filter<Event> {

    private final Filter filter;
    private final Long priority;

    public FilterWithPriority(Filter filter, long priority) {
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
        return obj instanceof FilterWithPriority &&
                priority.equals(((FilterWithPriority) obj).priority);
    }

    public int hashCode() {
        int result;
        result = filter.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "[" + filter.toString() + "; priority = " + priority + "]";
    }
}
