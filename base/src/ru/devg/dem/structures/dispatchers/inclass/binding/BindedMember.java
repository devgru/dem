package ru.devg.dem.structures.dispatchers.inclass.binding;

import ru.devg.dem.filtering.Filter;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
final class BindedMember implements Comparable<BindedMember> {
    private final Filter filter;
    private final Long priority;

    public BindedMember(Filter filter, long priority) {
        this.filter = filter;
        this.priority = priority;
    }

    public Filter getFilter() {
        return filter;
    }

    public int compareTo(BindedMember o) {
        return o.priority.compareTo(priority);
    }
}
