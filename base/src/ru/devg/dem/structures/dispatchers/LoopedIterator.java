package ru.devg.dem.structures.dispatchers;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.178
 */
class LoopedIterator<H> {


    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
    private final List<H> handlers
            = new LinkedList<H>();

    /**
     * It works, just believe me.
     */
    private ListIterator<H> storedIterator
            = handlers.listIterator();

    private final List<H> handlersToRemove
            = new LinkedList<H>();

    public synchronized H next() {
        H h = null;
        while (handlers.size() > 0 && h == null) {
            h = storedIterator.next();
            if (handlersToRemove.contains(h)) {
                storedIterator.remove();
                handlersToRemove.remove(h);
                h = null;
            }
            rewindIfNeeded();
        }
        return h;
    }

    public synchronized void add(H h) {
        assert h != null;
        storedIterator.add(h);
        storedIterator.previous();
    }

    private void rewindIfNeeded() {
        if (handlers.size() != 0 && !storedIterator.hasNext()) {
            storedIterator = handlers.listIterator();
        }
    }

    public synchronized void remove(H h) {
        if (h == null) return;
        if (!handlers.contains(h)) return;
        if (handlersToRemove.contains(h)) return;
        handlersToRemove.add(h);
    }

}
