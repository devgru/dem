package ru.devg.dem.inclass.binding;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.sources.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
final class DownPusher implements Filter {
    private final Source target;
    private final Filter targetMember;

    public DownPusher(Source target, Filter targetMember) {
        this.target = target;
        this.targetMember = targetMember;
    }

    @SuppressWarnings("unchecked")
    public void handle(Event event) {
        target.fire(event);
        targetMember.handle(event);
    }

    public boolean handleIfPossible(Event e) {
        return targetMember.handleIfPossible(e);
    }
}
