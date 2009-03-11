package ru.devg.dem.bundles.one2one.onclass;

import ru.devg.dem.filtering.Filter;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
final class DispatchedEntry implements Comparable<DispatchedEntry>{
    private final Filter filter;
    private final Long priority;

    public DispatchedEntry(Filter filter, long priority) {
        this.filter = filter;
        this.priority = priority;
    }

    public Filter getFilter() {
        return filter;
    }

    public int compareTo(DispatchedEntry o) {
        return o.priority.compareTo(priority);
    }
}
