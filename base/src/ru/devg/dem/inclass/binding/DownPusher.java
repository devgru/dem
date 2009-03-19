package ru.devg.dem.inclass.binding;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.sources.Source;
import ru.devg.dem.filtering.Filter;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public class DownPusher extends Filter {
    private final Source target;
    private final Filter targetMember;

    public DownPusher(Source target, Filter targetMember) {
        this.target = target;
        this.targetMember = targetMember;
    }

    @SuppressWarnings("unchecked")
    public void handle(Event event) {
        targetMember.handle(event);
        target.sendUp(event);
    }

    public boolean canHandle(Event e) {
        return targetMember.canHandle(e);
    }
}
