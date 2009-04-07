package ru.devg.dem.inclass.binding;

import ru.devg.dem.filtering.Filter;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
final class BindedElement implements Comparable<BindedElement> {
    private final Filter filter;
    private final Long priority;

    public BindedElement(Filter filter, long priority) {
        this.filter = filter;
        this.priority = priority;
    }

    public Filter getFilter() {
        return filter;
    }

    public int compareTo(BindedElement o) {
        return o.priority.compareTo(priority);
    }

    public boolean equals(Object obj) {
        if (obj instanceof BindedElement) {
            BindedElement be = (BindedElement) obj;
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
}
