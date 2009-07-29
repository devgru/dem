package dem.inclass;

import dem.bounding.Filter;
import dem.quanta.Event;
import dem.quanta.Log;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
 */
final class FilterWithPriority
        implements Comparable<FilterWithPriority>, Filter<Event> {

    private Filter filter;

    private Long priority;

    FilterWithPriority(Filter filter) {
        this.filter = filter;
        this.priority = 0L;
    }

    public Filter getFilter() {
        return filter;
    }

    public Long getPriority() {
        return priority;
    }


    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }


    public boolean handleIfPossible(Event event) {
        return getFilter().handleIfPossible(event);
    }

    @Deprecated
    public void handle(Event event) {
    }

    public int compareTo(FilterWithPriority o) {
        return o.getPriority().compareTo(getPriority());
    }

    public boolean equals(Object obj) {
        return obj instanceof FilterWithPriority &&
                getPriority().equals(((FilterWithPriority) obj).getPriority());
    }

    public int hashCode() {
        int result;
        result = getFilter().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Filter with priority\n" +
                Log.offset("target is " + filter.toString() + "\npriority = " + priority);
    }
}
