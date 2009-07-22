package test.handlers;

import dem.quanta.Event;
import dem.quanta.Handler;

public final class Counter<E extends Event> implements Handler<E> {
    private int i = 0;

    private Counter() {
    }

    public void handle(E event) {
        i++;
    }

    public int getCount() {
        return i;
    }

    public static <E extends Event> Counter<E> getCounter() {
        return new Counter<E>();
    }
}
