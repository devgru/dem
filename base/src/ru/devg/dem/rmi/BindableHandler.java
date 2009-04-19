package ru.devg.dem.rmi;

import ru.devg.dem.remote.RemoteEvent;
import ru.devg.dem.quanta.Handler;

import java.rmi.RemoteException;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public final class BindableHandler<E extends RemoteEvent>
        implements RemoteHandler<E> {

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
