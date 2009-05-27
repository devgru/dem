package ru.devg.dem.inclass;

import ru.devg.dem.bounding.TypeFilter;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
 */
final class BoundElement implements Comparable<BoundElement> {

    private final TypeFilter filter;
    private final Long priority;

    public BoundElement(TypeFilter filter, long priority) {
        this.filter = filter;
        this.priority = priority;
    }

    public TypeFilter getFilter() {
        return filter;
    }

    public int compareTo(BoundElement o) {
        return o.priority.compareTo(priority);
    }

    public boolean equals(Object obj) {
        if (obj instanceof BoundElement) {
            BoundElement be = (BoundElement) obj;
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
