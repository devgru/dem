package ru.devg.dem.rmi;

import ru.devg.dem.quanta.Handler;
import ru.devg.dem.remote.RemoteEvent;

import java.rmi.RemoteException;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public final class ObtainedRemoteHandler<E extends RemoteEvent> implements Handler<E> {

    private final RemoteHandler<E> rh;

    public ObtainedRemoteHandler(RemoteHandler<E> rh) {
        this.rh = rh;
    }

    public void handle(E event) {
        try {
            rh.handleRemote(event);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
