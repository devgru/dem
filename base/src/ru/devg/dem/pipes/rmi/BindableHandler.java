package ru.devg.dem.pipes.rmi;

import ru.devg.dem.quanta.Handler;
import ru.devg.dem.pipes.api.RemoteEvent;

import java.rmi.RemoteException;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public final class BindableHandler<E extends RemoteEvent> implements RemoteHandler<E> {
    private final Handler<? super E> h;
    public BindableHandler(Handler<? super E> target) {
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
