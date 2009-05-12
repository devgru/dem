package ru.devg.dem.inclass.binding;

import ru.devg.dem.bounding.TypeFilter;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
 */
final class BindedElement implements Comparable<BindedElement> {
    private final TypeFilter filter;
    private final Long priority;

    public BindedElement(TypeFilter filter, long priority) {
        this.filter = filter;
        this.priority = priority;
    }

    public TypeFilter getFilter() {
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
