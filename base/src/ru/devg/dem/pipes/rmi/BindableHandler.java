package ru.devg.dem.pipes.rmi;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.rmi.RemoteException;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public final class BindableHandler<E extends Event> implements RemoteHandler<E> {
    private final Handler<E> h;

    public BindableHandler(Handler<E> target) {
        this.h = target;
    }

    public void handleRemote(E event) throws RemoteException {
        try {
            h.handle(event);
        } catch (Exception e) {
            throw new RemoteException("Handling failed", e);
        }
    }
}
